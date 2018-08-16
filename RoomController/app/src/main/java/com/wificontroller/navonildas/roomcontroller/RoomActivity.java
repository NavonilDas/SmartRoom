package com.wificontroller.navonildas.roomcontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class RoomActivity extends AppCompatActivity {
    ListView roomLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Intent room = getIntent();
        int x = room.getIntExtra("ROOM_NO",0);
    }
}
