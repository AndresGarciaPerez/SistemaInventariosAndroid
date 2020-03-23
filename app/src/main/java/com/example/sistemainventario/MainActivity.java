package com.example.sistemainventario;

import android.os.Bundle;

import com.example.sistemainventario.fragments.ChangePassword;
import com.example.sistemainventario.fragments.ListInicio;
import com.example.sistemainventario.fragments.Preferences;
import com.example.sistemainventario.fragments.Repuestos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements ListInicio.Enlace {
    ListView lista;
    String[] titulo;
    String[] subTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Control de inventarios");
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
        switch (id){
            case R.id.changePassword:
                changeFragments(new ChangePassword());
                break;
            case R.id.preferences:
                changeFragments(new Preferences());
                break;
            case R.id.logout:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeFragments(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
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
