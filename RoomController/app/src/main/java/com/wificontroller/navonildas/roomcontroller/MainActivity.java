package com.wificontroller.navonildas.roomcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView rooms;
    String[] roomsLabel = {"Room 1","Room 2","Room 3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rooms = findViewById(R.id.roomsLv);
        rooms.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,roomsLabel));
        rooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent room = new Intent(MainActivity.this,RoomActivity.class);
                room.putExtra("ROOM_NO",i);
                startActivity(room);
            }
        });
    }
}
