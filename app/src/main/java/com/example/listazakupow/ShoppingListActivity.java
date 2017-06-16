package com.example.listazakupow;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.listazakupow.db.DBManager;
import com.example.listazakupow.db.DatabaseHelper;


public class ShoppingListActivity extends AppCompatActivity {

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {
            DatabaseHelper.ID,
            DatabaseHelper.NAME,
            DatabaseHelper.PRICE
    };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.price };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_empty_list);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_item, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView nameTextView = (TextView) view.findViewById(R.id.title);
                TextView priceTextView = (TextView) view.findViewById(R.id.price);

                String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();
                String price = priceTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyItemActivity.class);
                modify_intent.putExtra("name", name);
                modify_intent.putExtra("price", price);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_item) {

            Intent add_mem = new Intent(this, AddItemActivity.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }

}
