package com.example.sistemainventario.ui.main;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.sistemainventario.fragments.FragContenedores.MainFragmentConfig;
import com.example.sistemainventario.fragments.FragContenedores.MainFragmentAccesorios;
import com.example.sistemainventario.fragments.FragContenedores.MainFragmentRepuestos;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private final Context mContext;
    public FragmentManager fragmentManager;

    public List tabsName;

    public SectionsPagerAdapter(Context context, FragmentManager fm, List tabsName) {
        super(fm);
        mContext = context;
        fragmentManager = fm;
        this.tabsName = tabsName;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MainFragmentAccesorios();
                break;
            case 1:
                fragment = new MainFragmentRepuestos();
                break;
            case 2:
                fragment = new MainFragmentConfig();
        }

        return fragment;
    }

    // Se ejecuta cada vez que se cambia de actividad
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
        return super.instantiateItem(container, position);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabsName.get(position).toString();
    }

    @Override
    public int getCount() {
        return tabsName.size();
    }
}