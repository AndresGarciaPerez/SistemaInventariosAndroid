package com.example.sistemainventario;

import android.content.Intent;
import android.os.Bundle;

import com.example.sistemainventario.fragments.ListProducts;
import com.example.sistemainventario.fragments.Repuestos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListProducts.Enlace {
    ListView lista;
    String[] titulo;
    String[] subTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changePassword) {
            Toast.makeText(this, "Fragment_changePass", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.preferences) {
            Toast.makeText(this, "Fragment_preferences", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.changePassword) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeFragments(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null).replace(R.id.frContenido, fragment).commit();
    }


    @Override
    public void enviarData(String product, String quantity, String categorie, String description, int idImage) {
        Repuestos repuestos = new Repuestos();
        Bundle data = new Bundle();
        data.putString("product", product);
        data.putString("quantity", quantity);
        data.putString("categorie", categorie);
        data.putString("description", description);
        data.putInt("idImage", idImage);
        repuestos.setArguments(data);
        changeFragments(repuestos);
    }
}
