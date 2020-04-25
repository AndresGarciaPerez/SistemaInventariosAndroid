package com.electivaIII.sistemainventario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.electivaIII.sistemainventario.fragments.ChangePassword;
import com.electivaIII.sistemainventario.fragments.Preferences;
import com.electivaIII.sistemainventario.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    public List tabsName = new ArrayList<>();
    Preferences preferences;
    Boolean language=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = new Preferences();

        tabsName.add("Accesorios");
        tabsName.add("Repuestos");

        SharedPreferences idioma = this.getSharedPreferences("idioma", Context.MODE_PRIVATE);
        language = idioma.getBoolean("trueIdioma",false);
        if (language==true){
            tabsName.set(0,"Spare part");
            tabsName.set(1,"Accesories");
            setTitle("Inventory System");
        } else if(language==false){
            setTitle("Sistema Inventario");
        }


        sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(), tabsName);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position<2){

                    if (tabsName.size() == 3) {
                        // eliminamos el tab que se habia agregado de forma dinámica

                        tabsName.remove(2);
                        // instansiamos nuevamente el adaptador
                        sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(),
                                getSupportFragmentManager(), tabsName);

                        // Notificamos al viewPager para que nos actualize la vista de las tabs activity
                        viewPager.getAdapter().notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem changePass = menu.findItem(R.id.changePassword);
        MenuItem preferences = menu.findItem(R.id.preferences);
        MenuItem logout = menu.findItem(R.id.logout);
        if (language==true){
            changePass.setTitle("Change password");
            preferences.setTitle("Preferences");
            logout.setTitle("Log out");
        }else if (language==false){
            changePass.setTitle("Cambiar contraseña");
            preferences.setTitle("Preferencias");
            logout.setTitle("Cerrar sesion");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.changePassword:
                fragment = new ChangePassword();
                if (tabsName.size() == 3){
                    tabsName.remove(2);
                }
                if (language==true){
                    tabsName.add("Change password");
                    UpdateTabActivity(fragment);
                } else if(language==false){
                    tabsName.add("Cambiar contraseña");
                    UpdateTabActivity(fragment);
                }
                break;
            case R.id.preferences:
                fragment = new Preferences();
                if (tabsName.size() == 3){
                    tabsName.remove(2);
                }
                if (language==true){
                    tabsName.add("Preferences");
                    UpdateTabActivity(fragment);
                } else if(language==false){
                    tabsName.add("Preferencias");
                    UpdateTabActivity(fragment);
                }
                break;
            case R.id.logout:
                SharedPreferences sesionActiva = this.getSharedPreferences("sesionActiva",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sesionActiva.edit();
                editor.putString("token","");
                editor.apply();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                this.finish();
        }


        return super.onOptionsItemSelected(item);

    }
    public void UpdateTabActivity(Fragment fragment) {
        changeFragments(fragment);

        // Establecemos una nueva instancia para crear la nueva tab Activity
        sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(), tabsName);

        // Notificamos al viewPager para que nos actualize la vista de las tabs activity
        viewPager.getAdapter().notifyDataSetChanged();

        // Se valida que en el caso que el item actual no esta en la posicion del nuevo tab activity
        if (viewPager.getCurrentItem() != 2){
            viewPager.setCurrentItem(2);
        }

    }

    public void changeFragments(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null).replace(R.id.frMainConfiguration, fragment).commit();
    }


}
