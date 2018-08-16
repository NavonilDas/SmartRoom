package com.wificontroller.navonildas.roomcontroller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.wificontroller.navonildas.roomcontroller.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class RoomAdapter extends BaseAdapter {
    String[] items;
    short[] toggle;
    LayoutInflater inflater;
    MqttAndroidClient client;

    public RoomAdapter(String[] items, Context ctx, MqttAndroidClient client) {
        this.items = items;
        this.client = client;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        toggle = new short[items.length];
        for (int i = 0; i < items.length; i++)
            toggle[i] = 0;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = inflater.inflate(R.layout.room_item, null);
        TextView tv = vi.findViewById(R.id.title);
        final Switch sv = vi.findViewById(R.id.onoff);
        tv.setText(
                items[i]
        );
        final int itemInd = i;

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] encodedPayload = new byte[0];
                MqttMessage message;
                String payload = "";
                if (sv.isChecked()) {
                    /// on
                    sv.setText("ON");
                    payload = ""+ itemInd;

                } else {
                    // off
                    sv.setText("OFF");
                    payload = ""+ (4+itemInd);
                }
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    message = new MqttMessage(encodedPayload);
                    client.publish("9EB1928E", message);
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        return vi;
    }
}
