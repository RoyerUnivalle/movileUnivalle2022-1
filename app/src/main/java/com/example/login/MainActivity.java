package com.example.login;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
        ed2 = findViewById(R.id.ed2);
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
}