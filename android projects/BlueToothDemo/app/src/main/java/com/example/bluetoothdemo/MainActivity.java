package com.example.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter BA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(BA.isEnabled())
        {
            Toast.makeText(this, "it's on", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
            if (BA.isEnabled())
            {
                Toast.makeText(this, "it has been turned on", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void turnBluetoothOff(View view)
    {
        BA.disable();
        if (BA.isEnabled())
        {
            Toast.makeText(this, "bluetooth couldn't be disabled", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "bluetooth is off", Toast.LENGTH_SHORT).show();

        }
    }
    public void findDiscoverableDevices(View view)
    {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(i);
    }
    public void viewPairedDevices(View view)
    {
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        ListView pairedDevicesLitView = findViewById(R.id.pairedDevicesListView);
        ArrayList pairedDevicesArrayList = new ArrayList();
        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            pairedDevicesArrayList.add(bluetoothDevice.getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , pairedDevicesArrayList);
        pairedDevicesLitView.setAdapter(arrayAdapter);
    }

}