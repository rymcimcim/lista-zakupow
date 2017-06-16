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
import android.widget.EditText;

import com.example.listazakupow.db.DBManager;

public class AddItemActivity extends Activity implements OnClickListener {

    private Button addTodoBtn;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText currencyEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add_item);

        nameEditText = (EditText) findViewById(R.id.name_edittext);
        priceEditText = (EditText) findViewById(R.id.price_edittext);
        quantityEditText = (EditText) findViewById(R.id.quantity_edittext);
        currencyEditText = (EditText) findViewById(R.id.currency_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_item);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_item:

                final String name = nameEditText.getText().toString();
                final Float price = Float.parseFloat(priceEditText.getText().toString());
                final Integer quantity = Integer.parseInt(quantityEditText.getText().toString());
                final Float currency = Float.parseFloat(currencyEditText.getText().toString());

                dbManager.insert(name, price, quantity, currency, false);

                Intent main = new Intent(AddItemActivity.this, ShoppingListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }

}
