package com.example.sistemainventario.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public final class ChangeFragment extends Fragment {

    ChangeFragment(){

    }

    public static void changeFragment(int contentFragment, FragmentActivity activity, Fragment nextFragment){

        final FragmentManager fragmentManager = activity.getSupportFragmentManager();

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(contentFragment, nextFragment);
        fragmentTransaction.commit();

    }

}
