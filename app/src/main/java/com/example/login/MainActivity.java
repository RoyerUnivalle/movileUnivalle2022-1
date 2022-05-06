package com.example.login;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // instancias
    EditText ed1, ed2,edRespuestaHTTP;
    Button btn2,btn3;
    //RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        edRespuestaHTTP = findViewById(R.id.edRespuestaHTTP);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Hola boton delegado", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        btn3.setOnClickListener(this); //<--interfaz
    }

    public void saludar(View l){
       //Toast.makeText(this,"Hola boton en linea", Toast.LENGTH_SHORT).show();
        Intent ir = new Intent(this,Home.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
        if(ed1.getText().toString().matches("") || ed2.getText().toString().matches("")){
            //Toast.makeText(this,"Debe diligenciar todos los campos", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Debe diligenciar todos los campos")
                    .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),"Dio aceptar", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),"Dio cancelar", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            Bundle datos = new Bundle();
            datos.putString("name",ed1.getText().toString());
            datos.putString("passwd",ed2.getText().toString());
            ir.putExtras(datos);
            startActivity(ir);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn3:
                Toast.makeText(this,"Hola boton interfaz", Toast.LENGTH_SHORT)
                        .show();
                break;
            default: break;
        }
    }

    public  void dataVolley(View l){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://run.mocky.io/v3/e533faf2-42dd-47d9-aff5-710ac91a744d";
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //VolleyLog.v("Response:%n %s",response.getInt("count"));
                            Log.d("", "Respuesta: "+response.toString());
                            JSONArray jsonArray = response.getJSONArray("eventos");
                            Log.d("", "Respuesta: "+jsonArray.toString());
                            String eventos = "";
                            for (int i = 0; i < response.getInt("count"); i++) {
                                //eventos += jsonArray[]
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                eventos += jsonObject2.getString("nombre_evento") +"\n";
                            }
                            edRespuestaHTTP.setText(eventos);
                            //cantidadRegistros=response.getInt("count");
                            //dataAgenda = response.getJSONArray("agenda"); // for foreach
                            //System.out.println("cantidadRegistros1: "+cantidadRegistros);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        queue.add(req);
    }

    public  void getData(View j) {
        ConsultaHttp obj = new ConsultaHttp();
        obj.execute();
    }

    public  class ConsultaHttp extends AsyncTask<Void, String, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("https://run.mocky.io/v3/e533faf2-42dd-47d9-aff5-710ac91a744d");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    try {
                        //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        InputStream in = urlConnection.getInputStream();
                        BufferedReader bR = new BufferedReader(  new InputStreamReader(in));
                        String line = "";
                        StringBuilder responseStrBuilder = new StringBuilder();
                        while((line =  bR.readLine()) != null){

                            responseStrBuilder.append(line);
                        }
                        in.close();
                        Log.d("", "Respuesta: "+in);
                        Log.d("", "Respuesta: "+responseStrBuilder.toString());
                        JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("eventos");

                        String eventos = "";
                        for (int i = 0; i < jsonObject.getInt("count"); i++) {
                            //eventos += jsonArray[]
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            eventos += jsonObject2.getString("nombre_evento") +"\n";
                        }

                        publishProgress(eventos);
                    } finally {
                        urlConnection.disconnect();
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            edRespuestaHTTP.setText(""+values[0]);
        }
    }
}