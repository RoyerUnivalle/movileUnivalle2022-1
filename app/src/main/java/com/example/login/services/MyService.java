package com.example.login.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"hola servicio", Toast.LENGTH_SHORT).show();
        MostrarHora obj = new MostrarHora();
        obj.execute();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public  class  MostrarHora extends AsyncTask<Void,Integer,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 10; i++) {
                //Toast.makeText(getApplicationContext(),"hola servicio: posicion:"+i, Toast.LENGTH_SHORT).show();
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(),"hola servicio: posicion:"+values[0], Toast.LENGTH_SHORT).show();
        }
    }


}