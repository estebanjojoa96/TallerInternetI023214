package com.example.estebanjojoa.tallerinterneti023214;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estebanjojoa.tallerinterneti023214.Model.Users;
import com.example.estebanjojoa.tallerinterneti023214.Parser.Json;
import com.example.estebanjojoa.tallerinterneti023214.URL.HttpManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;


    List<Users> UsersList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.id_pb_data);
        textView=(TextView)findViewById(R.id.id_tv_data);
        loadData();

    }


    public Boolean isOnLine() {
        // Hacer llamado al servicio de conectividad utilizando el ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Obtener el estado de la conexion a internet en el dispositivo
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Validar el estado obtenido de la conexion
        if (networkInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public void loadData(){
        if (isOnLine()){
            // Hacer llamado a la tarea
            MyTask task = new MyTask();
            task.execute("http://pastoral.iucesmag.edu.co/practica/listar.php");
        }else {
            Toast.makeText(this, "Sin conexion", Toast.LENGTH_SHORT).show();
        }
    }

    public void processData(){


        Toast.makeText(this, String.valueOf(UsersList.size()), Toast.LENGTH_SHORT).show();

        for(Users str : UsersList) {
            textView.append(str.getNombre() + "\n");
            textView.append(str.getCorreo()+"\n");
            textView.append("\n");
        }
    }

    public class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            /*for (int i = 1; i <= 50; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(String.valueOf(i));
            }
            return "fin";*/

            String content = null;
            try {
                content = HttpManager.getDataJson(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                UsersList  = Json.getData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            processData();

            progressBar.setVisibility(View.GONE);
        }
    }
}

