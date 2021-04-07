package com.cursoandroid.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView txtResultado;
    private Button btnResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResultado = findViewById(R.id.txtResultado);
        btnResultado = findViewById(R.id.btnResultado);

        String linkUrl = "https://blockchain.info/ticker";
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(linkUrl);
    }


    class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String linkUrl = strings[0];
            StringBuffer linhaBuffer = null;
            String brl = new String();
            JSONObject last = new JSONObject();
            String lastValor = new String();
            try {
                URL url = new URL(linkUrl);
                URLConnection urlConnection = url.openConnection();

                // getInputStream ecura os dados em bytes
                InputStream inputStream = urlConnection.getInputStream();

                // InputStreamReader lê os dados em bytes e decodifica para caracteres
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                // BufferedReader objeto utilizado para leitura dos caracteres do inputStreamReader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);



                String linha = "";
                linhaBuffer = new StringBuffer();
                while ((linha = bufferedReader.readLine())!= null){
                    linhaBuffer.append(linha);
                }


                JSONObject jsonObject = new JSONObject(linhaBuffer.toString());
                brl = jsonObject.getString("BRL");
                last = new JSONObject(brl);
                lastValor = last.getString("last");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return brl+" / "+lastValor;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            txtResultado.setText(result);
        }
    }
}