package com.example.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView LvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        LvItems = (ListView) findViewById(R.id.lvItems);
        LvItems.setAdapter(itemsAdapter);

        //mock data
        //items.add("first item");
        //items.add("second item");

        setupListViewListener();
    }
        public void onAddItem(View v) {
            EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
            String itemText = etNewItem.getText().toString();
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeitems();
            Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
        }


            private void setupListViewListener(){
            Log.i("MainActivity","Setting up Listener on List View");
                LvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                        Log.i("Mainactivity","Item removed from List:"+position);
                       items.remove(position);
                       itemsAdapter.notifyDataSetChanged();
                       writeitems();
                       return true;
                    }

                });
            }

            private File getDataFile(){
               return new File(getFilesDir(), "todotxt");

            }


            private void readItems(){
                try {
                    items= new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
                } catch (IOException e) {
                    Log.e("MainActivity","Error reading File",e);
                    items= new ArrayList<>();

                }
            }

            private void writeitems(){
                try {
                    FileUtils.writeLines(getDataFile(),items);
                } catch (IOException e) {
                    Log.e("MainActivity","Error writing File",e);


                }
            }
        }



