package com.example.listazakupow;

/**
 * Created by jedrzej on 16.06.17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.listazakupow.db.DBManager;

public class ModifyItemActivity extends Activity implements OnClickListener {

    private EditText nameText;
    private EditText priceText;
    private EditText quantityText;
    private EditText currencyText;
    private CheckBox isBoughtCheckBox;
    private Button updateBtn, deleteBtn;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_item);

        dbManager = new DBManager(this);
        dbManager.open();

        nameText = (EditText) findViewById(R.id.name_edittext);
        priceText = (EditText) findViewById(R.id.price_edittext);
        quantityText = (EditText) findViewById(R.id.quantity_edittext);
        currencyText = (EditText) findViewById(R.id.currency_edittext);
        isBoughtCheckBox = (CheckBox) findViewById(R.id.checkBox);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        Float price = intent.getFloatExtra("price", 1.0f);
        String quantity = intent.getStringExtra("quantity");
        Float currency = intent.getFloatExtra("currency", 1.0f);
        Boolean isBought = intent.getBooleanExtra("isBought", false);

        _id = Long.parseLong(id);

        nameText.setText(name);
        priceText.setText(String.format("%.2f", price));
        quantityText.setText(quantity);
        currencyText.setText(String.format("%.2f", currency));
        isBoughtCheckBox.setChecked(isBought);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                final String name = nameText.getText().toString();
                final Float price = Float.parseFloat(priceText.getText().toString());
                final Integer quantity = Integer.parseInt(quantityText.getText().toString());
                final Float currency = Float.parseFloat(currencyText.getText().toString());
                final Boolean isBought = Boolean.parseBoolean(currencyText.getText().toString());

                dbManager.update(_id, name, price, quantity, currency, isBought);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ShoppingListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
