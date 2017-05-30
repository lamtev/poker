package com.lamtev.poker.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RoomActivity extends AppCompatActivity {

    private EditText roomName;
    private EditText playersNumber;
    private EditText stackSize;
    private Button start;
    private Button create;
    private RoomInfo roomInfo;
    private final View.OnFocusChangeListener inputFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //SAVE THE DATA
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Bundle room = getIntent().getBundleExtra("Room");
        if (room == null) {
            roomInfo = new RoomInfo();
        } else {
            roomInfo = new RoomInfo(room.getString("name"), room.getInt("playersNumber"), room.getInt("stack"), true);
        }
        roomName = (EditText) findViewById(R.id.room_name_input);
        roomName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String name = roomName.getText().toString();
                    roomInfo.setName(name);
                }
            }
        });
        playersNumber = (EditText) findViewById(R.id.players_number_input);
        playersNumber.setOnFocusChangeListener(inputFocusChangeListener);
        stackSize = (EditText) findViewById(R.id.stack_size_input);
        stackSize.setOnFocusChangeListener(inputFocusChangeListener);
        start = (Button) findViewById(R.id.start);
        create = (Button) findViewById(R.id.create);
        roomName.setText(roomInfo.getName());
        playersNumber.setText(roomInfo.getPlayersNumber() + "");
        stackSize.setText(roomInfo.getStack() + "");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "start game clicked");
//                RoomInfo roomInfo = new RoomInfo("x", 5, 15000, false);
//                Bundle extras = new Bundle();
//                extras.putString("roomId", .getId());
//                Intent intent = new Intent(RoomActivity.this, GameActivity.class).putExtras(extras);
//                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context packageContext, RoomInfo roomInfo) {
        Intent i = new Intent(packageContext, RoomActivity.class);
        Bundle room = new Bundle();
        room.putString("name", roomInfo.getName());
        room.putInt("playersNumber", roomInfo.getPlayersNumber());
        room.putInt("stack", roomInfo.getStack());
        room.putBoolean("free", roomInfo.isFree());
        i.putExtra("Room", room);
        return i;
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, RoomActivity.class);
        return i;
    }

}
