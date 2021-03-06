package com.example.a13110091.testweb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView idText = (TextView)findViewById(R.id.idText);
        TextView passwordText = (TextView)findViewById(R.id.passwordText);
        TextView welcomeMessage = (TextView)findViewById(R.id.welcomeMessage);
        Button managementButton = (Button)findViewById(R.id.managementButton);


        Button exercise = (Button)findViewById(R.id.From);
        Button borad = (Button)findViewById(R.id.borad);

        Intent intent = getIntent(); //가져오기
        String userID = intent.getStringExtra("userID"); //getStringExtra
        String userPassword = intent.getStringExtra("userPassword"); //getStringExtra
        String message = "환영합니다" + userID+ "님!";

//        idText.setText(userID); //idTEXT에 USERID를 붙임
//        passwordText.setText(userPassword);
        welcomeMessage.setText(message);

        if(!userID.equals("admin")){
            managementButton.setVisibility(View.GONE);
//            managementButton.setEnabled(false);
        }
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"눌럿다",Toast.LENGTH_LONG).show();
                new BackgroundTask().execute();
            }
        });
        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2.class);
                startActivity(intent);
            }
        });
        borad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask1().execute();
//                Intent intent = new Intent(MainActivity.this, Boardselect.class);
//                startActivity(intent);
            }
        });
//        graph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(MainActivity.this, ChartActivity.class);
//                startActivity(intent1);
//            }
//        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>{

        String target;

        protected void onPreExecute(){
            target = "http://210.119.85.215/List1.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) !=null ){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList", result);
            MainActivity.this.startActivity(intent);
        }
    }

    class BackgroundTask1 extends AsyncTask<Void, Void, String>{

        String target;

        protected void onPreExecute(){
            target = "http://210.119.85.215/List2.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) !=null ){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, Boardselect.class);
            intent.putExtra("selectList", result);
            MainActivity.this.startActivity(intent);
        }
    }
}
