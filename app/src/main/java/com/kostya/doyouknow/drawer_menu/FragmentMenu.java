package com.kostya.doyouknow.drawer_menu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.kostya.doyouknow.R;
import com.kostya.doyouknow.listeners.NavigationItemClickedListener;


public class FragmentMenu extends Fragment {

    private NavigationItemClickedListener listener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_menu, container, false);

        NavigationView navigationView = view.findViewById(R.id.vNavigation);
        //Устанавливаем выбранный item по default
        navigationView.setCheckedItem(R.id.id_planets);

        navigationView.setNavigationItemSelectedListener(item -> {
            listener.onItemNavClicked(item);
            return true;
        });

            return view;
        }

        public void setNavigationItemNav (NavigationItemClickedListener listener){
            this.listener = listener;
        }

    }


