package com.example.rpi_online;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    Button btnUp;
    Button btnDown;
    EditText txtAddress;

    Socket myAppSocket = null;
    public static String wifiModuleIp="";
    public static int wifiModulePort=0;
    public static String CMD ="0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUp = (Button) findViewById(R.id.btnUP);
        btnDown = (Button) findViewById(R.id.btnDown);

        txtAddress = (EditText) findViewById(R.id.ipAddress);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "UP";
                Soket_AsyncTask cmd_increase_servo = new Soket_AsyncTask();
                cmd_increase_servo.execute();
            }
        });

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                CMD = "DOWN";
                Soket_AsyncTask cmd_decrease_servo = new Soket_AsyncTask();
                cmd_decrease_servo.execute();
            }
        });

    }

    public void getIPandPort(){
        String iPandPort = txtAddress.getText().toString();
        String temp[]= iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);


            //Log.d("MYTEST","IP String" +iPandPort);
            //Log.d("MY TEST","IP:"+wifiModuleIp);
            // Log.d("MY TEST","PORT"+wifiModulePort);


    }
    public class Soket_AsyncTask extends AsyncTask<Void,Void,Void>
    {
        Socket socket;
        protected Void doInBackground(Void... params){
            try{
                InetAddress inetAdress = InetAddress.getByName(MainActivity.wifiModuleIp);
                socket = new java.net.Socket(inetAdress,MainActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;

        }

    }
    //Here you need to change the code...
}
