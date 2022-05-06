package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.fragments.FragmentButtonAdds;
import com.example.login.services.MyService;

import java.time.Instant;

public class Home extends AppCompatActivity {
    TextView labelDatos;
    Integer contador;
    Button btnPintar;
    Pintar objPintar;
    FragmentManager fragmentManager; // = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction; // = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        labelDatos = findViewById(R.id.tvDatos);
        btnPintar = findViewById(R.id.btnPintar);
        Bundle recibo = getIntent().getExtras();
        String name = recibo.getString("name");
        String passwd = recibo.getString("passwd");
        labelDatos.setText("User: " + name + " Passwd: " + passwd);
        contador = 0;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        FragmentButtonAdds fragmento = new FragmentButtonAdds();
        // FragmentButtonAdds fragmento = FragmentButtonAdds.newInstance("a","b");
        fragmentTransaction.add(R.id.contenedorFragment, fragmento);
        fragmentTransaction.commit();
    }

    public void contar(View l) {
        contador = contador + 1;
        labelDatos.setText("contador: " + contador);
    }

    public void atras(View l) {
        //Toast.makeText(this,"Hola boton en linea", Toast.LENGTH_SHORT).show();
        Intent ir = new Intent(this, MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contador", contador);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contador = savedInstanceState.getInt("contador");
    }

    // demostración que la UI esta acomplada al hilo principal de la app
    public void pintar(View p) {
        /*for (int i = 0; i < contador; i++) {
            btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        // Opcion 2  por medio de Runnable
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= contador; i++) {
                    //System.out.println(aleatorio());
                    btnPintar.setBackgroundColor(Color.rgb(aleatorio(), aleatorio(), aleatorio()));
                    btnPintar.setText("Pintar: " + i);
                    //if(i == contador){
                        //Toast.makeText(getApplicationContext(), "Terminé",Toast.LENGTH_LONG).show();
                    //}
                    try {
                        Thread.sleep(1000); //<-- en este contexto hace referencia al hilo recien creado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */
        //Toast.makeText(getApplicationContext(), "Terminé", Toast.LENGTH_LONG).show();
        objPintar = new Pintar();
        objPintar.execute();
    }

    public void iniciarServicio(View p){
     Intent irService = new Intent(this, MyService.class);
     startService(irService);
    }

    public int aleatorio() {
        return ((int) (Math.random() * 255 + 1));
    }

    public class Pintar extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... integers) {
            for (int i = 0; i < contador; i++) {
                //btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } // parametro,progeso, resultado

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnPintar.setBackgroundColor(Color.rgb(aleatorio(),aleatorio(),aleatorio()));
            btnPintar.setText("Pintar: " + values[0]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(getApplicationContext(), "Terminé", Toast.LENGTH_LONG).show();
        }
    }
}