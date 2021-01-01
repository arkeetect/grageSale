package com.muhammadyaseen.classifiedapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;


public class garageSaleHome extends AppCompatActivity {
    NavController navController;
    ImageView imageMenu;


    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_sale_home);

        final DrawerLayout drawerLayout=findViewById(R.id.garage_Sale_DrawerLayout);


        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);


            }
        });

        navigationView=findViewById(R.id.garageNavigationView);
        navigationView.setItemIconTintList(null);
        navController= Navigation.findNavController(this,R.id.garageNav_Host_fragment);


        NavigationUI.setupWithNavController(navigationView,navController);

        final TextView textTittle=findViewById(R.id.garage_Menu_tittle);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTittle.setText(destination.getLabel());


            }
        });
        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuid=item.getItemId();


                if(menuid==R.id.garageSaleHomeFragment)
                {
                    navController.navigate(R.id.garageSaleHomeFragment);
                    drawerLayout.closeDrawers();
                    return true;

                }

                if(menuid==R.id.postFragment)
                {
                    navController.navigate(R.id.postFragment);
                    drawerLayout.closeDrawers();
                    return true;

                }




                if(menuid==R.id.settingFragment){
                    navController.navigate(R.id.settingFragment);
                    drawerLayout.closeDrawers();
                    return true;

                }


                return false;
            }

        });

    }
}



