package com.example.cursapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Document doc;
    private Thread secThread;
    private Runnable runnable;
    private ListView listView;
    private CustomArrayAdapter adapter;
    private List<ListItemClass> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new CustomArrayAdapter(this,R.layout.list_item_1,arrayList,getLayoutInflater());
        listView.setAdapter(adapter);
        runnable = new Runnable(){
            @Override
            public void run(){

                getWeb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
//        ListItemClass items = new ListItemClass();
//        items.setData_1("Dollar");
//        items.setData_2("213");
//        items.setData_3("657");
//        items.setData_4("78");
//        items.setData_5("Dollar");
//        items.setData_6("Dollar");
//        arrayList.add(items);
//        items = new ListItemClass();
//        items.setData_1("Dollar");
//        items.setData_2("22");
//        items.setData_3("61");
//        items.setData_4("99");
//        items.setData_5("Dollar");
//        items.setData_6("Dollar");
//        arrayList.add(items);
//        adapter.notifyDataSetChanged();
    }
    private void getWeb()
    {
        try {
            doc = Jsoup.connect("https://www.micb.md/schimb-valutar-ru/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element our_table = tables.get(0);
            Elements elements_from_table = our_table.children();
            Element dollar = elements_from_table.get(0);
            Elements dollar_elements = dollar.children();
        Log.d("MyLog","Tbody size : " + our_table.children().get(0).text() );
            for(int i = 0;i < our_table.childrenSize();i++ )
            {
                ListItemClass items = new ListItemClass();
                items.setData_1(our_table.children().get(i).child(0).text());
                items.setData_2(our_table.children().get(i).child(1).text());
                items.setData_3(our_table.children().get(i).child(2).text());
                items.setData_4(our_table.children().get(i).child(3).text());



                arrayList.add(items);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}