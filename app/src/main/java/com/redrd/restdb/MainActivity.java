package com.redrd.restdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editPlaca,editModelo,editMarca,editPrecio;
    GestorDb Ventas_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editPlaca =findViewById(R.id.editPlaca);
        editModelo =findViewById(R.id.editModelo);
        editMarca =findViewById(R.id.editMarca);
        editPrecio =findViewById(R.id.editPrecio);
        Ventas_db=new GestorDb(this,"Ventas",null,1);
    }
    public void CreateVehiculo(View v){
        SQLiteDatabase bd= Ventas_db.getWritableDatabase();
        ContentValues crear = new ContentValues();
        crear.put("placa",editPlaca.getText().toString().trim());
        crear.put("marca",editMarca.getText().toString());
        crear.put("modelo",editModelo.getText().toString());
        crear.put("precio",editPrecio.getText().toString());
        try {
            bd.insert("Vehiculos",null,crear);
            Toast.makeText(this,"Vehiculo Registrado",Toast.LENGTH_SHORT).show();
            editPlaca.setText("");
            editMarca.setText("");
            editModelo.setText("");
            editPrecio.setText("");
        }catch (SQLException e) {
            Toast.makeText(this,"Error al registrar vehiculo",Toast.LENGTH_SHORT).show();
        }
    }
    public void UpdateVehicle(View v){
        SQLiteDatabase bd= Ventas_db.getWritableDatabase();
        ContentValues update = new ContentValues();
        update.put("placa",editPlaca.getText().toString().trim());
        update.put("marca",editMarca.getText().toString());
        update.put("modelo",editModelo.getText().toString());
        update.put("precio",editPrecio.getText().toString());
        int updateRows=bd.update("Vehiculos",update,"placa = ?",new String[]{String.valueOf(editPlaca.getText().toString())});
        if (updateRows >0) {
            Toast.makeText(this, "Vehiculo Actualizado", Toast.LENGTH_SHORT).show();
            editPlaca.setText("");
            editMarca.setText("");
            editModelo.setText("");
            editPrecio.setText("");
        } else{
            Toast.makeText(this, "Error al Actualizar el Vehiculo", Toast.LENGTH_SHORT).show();
        }
    }
    public void DeleteVehicle(View v){

        SQLiteDatabase bd = Ventas_db.getWritableDatabase();
        String placa=editPlaca.getText().toString().trim();
        int deleteRows=bd.delete("Vehiculos","placa = ?",new String[]{String.valueOf(placa)});

        if(deleteRows>0){
            Toast.makeText(this,"Fila eliminada con Exito",Toast.LENGTH_SHORT).show();
            editPlaca.setText("");
        }else{
            Toast.makeText(this,"Error al eliminar la fila",Toast.LENGTH_SHORT).show();
        }
    }
    public void SearchRow(View v){

        SQLiteDatabase bd = Ventas_db.getReadableDatabase();
        String placa=editPlaca.getText().toString().trim();
        Cursor row = bd.rawQuery("select marca, modelo, precio from Vehiculos where placa = ?",new String[]{placa});
        if(row != null && row.moveToFirst()){
            editPrecio.setText(String.valueOf(row.getFloat(row.getColumnIndexOrThrow("precio"))));
            editMarca.setText(row.getString(row.getColumnIndexOrThrow("marca")));
            editModelo.setText(row.getString(row.getColumnIndexOrThrow("modelo")));
            Toast.makeText(this,"Datos Encontrados",Toast.LENGTH_SHORT).show();
            row.close();
        }else{
            Toast.makeText(this,"Datos no Encontrados",Toast.LENGTH_SHORT).show();
        }
    }
}