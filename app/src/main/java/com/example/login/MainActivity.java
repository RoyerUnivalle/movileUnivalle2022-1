package com.example.login;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // instancias
    EditText ed1, ed2;
    Button btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.ed1);
        ed1 = findViewById(R.id.ed2);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
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
        startActivity(ir);
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
}