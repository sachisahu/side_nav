package com.sachi.sidenav;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.sachi.sidenav.databinding.ActivityMainBinding;
import com.sachi.sidenav.ui.Utils.Sp;
import com.sachi.sidenav.ui.gallery.GalleryFragment;
import com.sachi.sidenav.ui.home.HomeFragment;
import com.sachi.sidenav.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    String shared;
    ActionBarDrawerToggle toggle;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        activity = this;
        shared = Sp.getSharedPrefrenceses(this, "Login", "name");

        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.appBarMain.toolbar,R.string.Open,R.string.Close);
        toggle.syncState();



        if (shared != "Logout" || shared !="") {
            Toast.makeText(this, "Shared P contains"+shared+" !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Empty"+shared, Toast.LENGTH_SHORT).show();
            Sp.saveSharedPrefrences(this, "Login", "name", "Logout");
        }

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast.makeText(MainActivity.this, "" + Sp.getSharedPrefrenceses(MainActivity.this, "Login", "name"), Toast.LENGTH_SHORT).show();


            }
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        callFragment(new HomeFragment());
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_gallery:
                        callFragment(new GalleryFragment());
                        binding.drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_login:
                        callFragment(new LoginFragment());
                        break;
                }


                return false;
            }
        });

    }

    public void setLoginName(String name){
        Menu menu =binding.navView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_login);
        menuItem.setTitle(""+name);

    }
    public void callFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main,fragment);
        transaction.commit();
    }
}





