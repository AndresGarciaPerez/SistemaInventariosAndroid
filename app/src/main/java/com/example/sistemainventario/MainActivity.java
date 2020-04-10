package com.example.sistemainventario;

import android.os.Bundle;

import com.example.sistemainventario.fragments.ChangePassword;
import com.example.sistemainventario.fragments.ListInicio;
import com.example.sistemainventario.fragments.Preferences;
import com.example.sistemainventario.fragments.RepuestosFile.Repuestos;
import com.example.sistemainventario.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListInicio.Enlace {

    private TabLayout tabs;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    public List tabsName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabsName.add("Accesorios");
        tabsName.add("Repuestos");


        sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(), tabsName);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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
                .addToBackStack(null).replace(R.id.frMainAccesorio, fragment).commit();
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
