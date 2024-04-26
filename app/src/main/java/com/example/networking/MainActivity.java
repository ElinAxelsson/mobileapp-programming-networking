package com.example.networking;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {
    private Gson gson;
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
    private final String JSON_FILE = "mountains.json";
    ArrayList<Mountain> items = new ArrayList<>();
    ArrayList<RecyclerViewItem> recyclerViewItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson =new Gson();


        for (int i=0;i< items.size();i++) {
            Log.d("hej", items.get(i).toString());
            recyclerViewItems.add(new RecyclerViewItem(items.get(i).toString()));
        }
        adapter = new RecyclerViewAdapter(this, recyclerViewItems, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(RecyclerViewItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView view = findViewById(R.id.recyclerView);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
        new JsonFile(this, this).execute(JSON_FILE);
        new JsonTask(this).execute(JSON_URL);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onPostExecute(String json) {
        Log.d("Hej", "" + json);
        Type type = new TypeToken<List<Mountain>>() {}.getType();
        List<Mountain> listOfMountains = gson.fromJson(json, type);
        items.clear();
        if(listOfMountains != null) {
            items.addAll(listOfMountains);
            for (int i = 0; i < items.size(); i++) {
                Log.d("Elinparsear", items.get(i).toString());
                recyclerViewItems.add(new RecyclerViewItem(items.get(i).toString()));
            }
        }
        adapter.notifyDataSetChanged();
    }

}