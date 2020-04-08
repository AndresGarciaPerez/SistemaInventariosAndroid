package com.example.sistemainventario;

import android.os.Bundle;

import com.example.sistemainventario.fragments.ChangePassword;
import com.example.sistemainventario.fragments.ListInicio;
import com.example.sistemainventario.fragments.Preferences;
import com.example.sistemainventario.fragments.Repuestos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sistemainventario.ui.main.SectionsPagerAdapter;

public class Inventario extends AppCompatActivity implements ListInicio.Enlace{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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
                .addToBackStack(null).replace(R.id.frContenidos, fragment).commit();
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