package com.kostya.doyouknow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.kostya.doyouknow.adapter.MainAdapter;
import com.kostya.doyouknow.drawer_menu.FragmentMenu;
import com.kostya.doyouknow.listeners.NavigationItemClickedListener;
import com.kostya.doyouknow.listeners.OnItemClick;
import com.kostya.doyouknow.model.Post;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationItemClickedListener, OnItemClick {

    public static final String MY_TAG = "myTag";
    private FlowingDrawer flowingDrawer;

    private RecyclerView mainRecView;
    private LinearLayoutManager manager;
    private MainAdapter adapter;
    private List<Post> postList;

    private SharedPreferences pref;
    private String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Создание DO YOU KNOW CHANELL NECO проще там посмотртеь,чем стараться понять мои комменты)))))
        pref = getSharedPreferences("Category",MODE_PRIVATE);
        initRecyclerView();
        setUpMenu();



    }

    private void setUpMenu(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentMenu menu = (FragmentMenu) manager.findFragmentById(R.id.container_menu);
        if (menu == null){
            menu = new FragmentMenu();
            //set listener
            menu.setNavigationItemNav(this);
        }

        manager.beginTransaction().replace(R.id.container_menu,menu).commit();
    }

    @Override
    public void onItemNavClicked(MenuItem currentItem) {

        flowingDrawer = findViewById(R.id.drawerlayout);



        final int currentItemItemId = currentItem.getItemId();

        switch (currentItemItemId){
            case R.id.id_favorite:

                Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_planets:

                String[] arrayPlanets = getResources().getStringArray(R.array.planets);
                updateAdapter(arrayPlanets,"planets");
                break;
            case R.id.id_stars:

                String[] arrayStars = getResources().getStringArray(R.array.stars);
                updateAdapter(arrayStars,"stars");
                break;
            case R.id.id_travel:

                String[] arrayTravels = getResources().getStringArray(R.array.travels);
                updateAdapter(arrayTravels,"travels");
                break;
            case R.id.id_adventure:

                String[] arrayAdventure = getResources().getStringArray(R.array.adventure);
                updateAdapter(arrayAdventure,"adventures");
                break;
        }

        //По клику закрываем drawerLayout
        flowingDrawer.closeMenu();
    }

    private void initRecyclerView(){
        mainRecView = findViewById(R.id.mainRecView);
        manager = new LinearLayoutManager(this);
        mainRecView.setLayoutManager(manager);

        postList = new ArrayList<>();

        //Массив из элементов космоса
        String[] planets = getResources().getStringArray(R.array.planets);

        adapter = new MainAdapter(postList,this);

        updateAdapter(planets,"planets");

        mainRecView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    private void updateAdapter(String[] array,String categor){

        //Создали глобальную переменную category и присваиваем ей значение categor ,т.е категорию которую мы передаем в Listener 58стр. кода
        category = categor;
        String favoriteCategory = "";

        //Таким образом мы получаем значение по ключу в данном случае key: category,def value = none
        //И если  при получении данных из preference они равны none,то это значит,что preference не был создан
        //И мы создаем его
        if (pref.getString(categor,"none").equals("none")){

            for (int i = 0; i < array.length; i++) {
                //Проходясь по циклу массива который мы передаем в зависимости от нажатого в navigation view itema мы в нашу переменную
                //favoriteCategory записываем столько нулей,сколько итемов содержит данный массив
                 favoriteCategory += "0";
            }
            Log.d(MY_TAG,"Нулей в переменной :" + favoriteCategory);
            saveString(favoriteCategory);

        } else {

        }

        List<Post> updatedList = new ArrayList<>();
        for (int i = 0; i < array.length ; i++) {
            Post post = new Post();
            post.setText(array[i]);
            post.setCategory(categor);

            updatedList.add(post);
        }

        adapter.updateAdapter(updatedList);
    }

    @Override
    public void itemClick(int position, Post currentPost) {
        String tempCat = pref.getString(category,"none");

        if (tempCat != null) {
            if (tempCat.charAt(position) == '0') {
                saveString(replaceCharAtPosition(position,'1',tempCat));
            } else {
                saveString(replaceCharAtPosition(position,'0',tempCat));
            }
        }

    }

    private String replaceCharAtPosition(int position,char ch,String st){
        char[] charArray = st.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }

    private void saveString(String stToSave){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(category,stToSave);
        editor.apply();
        Log.d(MY_TAG,"saved data fav " + pref.getString(category,"none"));
    }

}