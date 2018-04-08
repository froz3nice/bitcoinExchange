package com.opop.brazius.bitcoinexchangerates;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

public class Calculator extends AppCompatActivity {

    private EditText usd;
    private EditText euro;
    private EditText gbp;
    private EditText bitcoin;
    private Button convertToBit;
    private Button convertFromBit;
    private Double usdRate;
    private Double eurRate;
    private Double gbpRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.poweredByCoindesk));
        setContentView(R.layout.activity_calculator);
        usd = findViewById(R.id.et_usd);
        euro = findViewById(R.id.et_euro);
        gbp = findViewById(R.id.et_gbp);
        bitcoin = findViewById(R.id.et_bitcoin);
        convertToBit = findViewById(R.id.btn_convert_to_bit);
        convertFromBit = findViewById(R.id.btn_convert_from_bit);

        usdRate = getIntent().getDoubleExtra("usd", 1);
        eurRate = getIntent().getDoubleExtra("eur", 1);
        gbpRate = getIntent().getDoubleExtra("gbp", 1);

        if(savedInstanceState != null){
            euro.setText(savedInstanceState.getString("euro"));
            usd.setText(savedInstanceState.getString("dollar"));
            gbp.setText(savedInstanceState.getString("pound"));
            bitcoin.setText(savedInstanceState.getString("bit"));
        }
        convertToBit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertToBitcoin();
            }
        });

        convertFromBit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bitcoin.getText().toString().isEmpty()){
                    convertFromBitcoin();
                }else{
                    Toast.makeText(Calculator.this, "enter bitcoin value", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("euro", euro.getText().toString());
        outState.putString("dollar", usd.getText().toString());
        outState.putString("pound", gbp.getText().toString());
        outState.putString("bit",bitcoin.getText().toString());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void convertFromBitcoin() {

        Double euroValue = Double.parseDouble(bitcoin.getText().toString()) * eurRate;
        Double usdValue = Double.parseDouble(bitcoin.getText().toString()) * usdRate;
        Double gbpValue = Double.parseDouble(bitcoin.getText().toString()) * gbpRate;

        euro.setText(String.valueOf(BigDecimal.valueOf(euroValue)));
        usd.setText(String.valueOf(BigDecimal.valueOf(usdValue)));
        gbp.setText(String.valueOf(BigDecimal.valueOf(gbpValue)));

    }

    private void convertToBitcoin() {
        String whichCurrency = "";
        int counter = 0;
        if (!euro.getText().toString().isEmpty()) {
            counter++;
            whichCurrency = "eur";
        }
        if (!usd.getText().toString().isEmpty()) {
            counter++;
            whichCurrency = "usd";
        }
        if (!gbp.getText().toString().isEmpty()) {
            counter++;
            whichCurrency = "gbp";
        }
        if (counter != 1) {
            Toast.makeText(Calculator.this, "Please enter 1 value", Toast.LENGTH_SHORT).show();
        } else {
            switch (whichCurrency) {
                case "eur":
                    Double amount = Double.parseDouble(euro.getText().toString());
                    bitcoin.setText(getBitcoin(amount, eurRate));
                    break;
                case "usd":
                    Double amount2 = Double.parseDouble(usd.getText().toString());
                    bitcoin.setText(getBitcoin(amount2, usdRate));
                    break;
                case "gbp":
                    Double amount3 = Double.parseDouble(gbp.getText().toString());
                    bitcoin.setText(getBitcoin(amount3, gbpRate));
                    break;
            }
        }
    }

    private String getBitcoin(Double amount, Double rate) {
        Double bit = amount / rate;
        return String.valueOf(BigDecimal.valueOf(bit));
    }
}
