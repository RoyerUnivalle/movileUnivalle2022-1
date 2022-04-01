package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.login.fragments.FragmentButtonAdds;

import java.time.Instant;

public class Home extends AppCompatActivity {
    TextView labelDatos;
    Integer contador;
    FragmentManager fragmentManager; // = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction; // = fragmentManager.beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        labelDatos = findViewById(R.id.tvDatos);
        Bundle recibo = getIntent().getExtras();
        String name = recibo.getString("name");
        String passwd = recibo.getString("passwd");
        labelDatos.setText("User: "+ name + " Passwd: "+ passwd);
        contador = 0;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        FragmentButtonAdds fragmento = new FragmentButtonAdds();
        // FragmentButtonAdds fragmento = FragmentButtonAdds.newInstance("a","b");
        fragmentTransaction.add(R.id.contenedorFragment, fragmento);
        fragmentTransaction.commit();
    }

    public void contar(View l){
        contador = contador + 1;
        labelDatos.setText("contador: "+contador);
    }

    public void atras(View l){
        //Toast.makeText(this,"Hola boton en linea", Toast.LENGTH_SHORT).show();
        Intent ir = new Intent(this,MainActivity.class);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("contador",contador);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contador = savedInstanceState.getInt("contador");
    }
}