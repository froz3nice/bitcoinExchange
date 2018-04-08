package com.opop.brazius.bitcoinexchangerates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opop.brazius.bitcoinexchangerates.json_models.BitcoinItem;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity{

    private ListView bitcoinList;
    private ListAdapter adapter;
    private Context context;
    private ArrayList<BitcoinItem> list;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        list = new ArrayList<>();
        bitcoinList = findViewById(R.id.lv_bitcoin_list);
        adapter = new ListAdapter(this, R.layout.list_item, list);
        bitcoinList.setAdapter(adapter);
        Log.d("onCreatefire","true");
        if (savedInstanceState == null) {
            new BitcoinLoader().execute();
        } else {
            list = savedInstanceState.getParcelableArrayList("bitcoin_key");
            adapter.setItems(list);
        }

        Button openCalc = findViewById(R.id.btn_open_calculator);
        Button refresh = findViewById(R.id.btn_refresh);

        openCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Calculator.class);
                if(!list.isEmpty()) {
                    intent.putExtra("usd", list.get(0).getBpi().getUSD().getRateFloat());
                    intent.putExtra("eur", list.get(0).getBpi().getEUR().getRateFloat());
                    intent.putExtra("gbp", list.get(0).getBpi().getGBP().getRateFloat());
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "try refreshing list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!requestedInLastMinute()) {
                    new BitcoinLoader().execute();
                }
            }
        });
    }

    private boolean requestedInLastMinute(){
        Long lastRequestTime = prefs.getLong("lastRequested",0);
        if(lastRequestTime == 0) {
            return false;
        }
        Long now = System.currentTimeMillis();
        Long diff = now - lastRequestTime;
        int seconds = (int) (diff / 1000) ;
        Log.d("Difference",String.valueOf(seconds));
        return seconds < 60;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("bitcoin_key", list);
        super.onSaveInstanceState(outState);
    }

    class BitcoinLoader extends AsyncTask<Void,ArrayList<BitcoinItem>,ArrayList<BitcoinItem>> {

        @Override
        protected ArrayList<BitcoinItem> doInBackground(Void... voids) {
            ArrayList<BitcoinItem> list = new ArrayList<>();
            try {
                URL url = new URL(getString(R.string.web_url));
                InputStreamReader reader = new InputStreamReader(url.openStream());
                BitcoinItem data = new Gson().fromJson(reader, BitcoinItem.class);
                list.add(data);
                PreferenceManager.getDefaultSharedPreferences(context).edit()
                        .putLong("lastRequested",System.currentTimeMillis()).apply();

                Log.d("WasLoaded","loaded");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(javax.net.ssl.SSLException e){
                e.printStackTrace();
            } catch (IOException | com.google.gson.JsonIOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<BitcoinItem> data) {
            super.onPostExecute(data);
            if (!data.isEmpty()) {
                list = data;
                adapter.setItems(data);
                adapter.notifyDataSetChanged();
                Paper.book().write("BitcoinItem", data);
            }
        }
    }
}
