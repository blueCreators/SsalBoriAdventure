package com.example.myapplication.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.MyService;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

public class KMainActivity extends AppCompatActivity {
    MediaPlayer m;

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_item, R.id.nav_initialization, R.id.nav_hof)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        startService(new Intent(getApplicationContext(), MyService.class));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MediaPlayer m = this.m;
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        TextView t = (TextView)findViewById(R.id.ptextView);
        TextView InMenuUserName = (TextView)findViewById(R.id.userNameinProgress);
        // chapter정보, user 이름 입력받아야 함
        String uname = "임시길";
        // db에서 uname 받기
        InMenuUserName.setText(uname);
        int progressSu = 100/7*3; //여기서 3이 입력값에 해당
        p.setProgress(progressSu);
        t.setText(progressSu+"%");

        switch(item.getItemId())
        {
            case R.id.action_settings:
                Intent intent = new Intent(this, Setting.class);
                intent.putExtra("mediaplayer", R.raw.letsmarch);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void moveToHome()
    {
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        navController.navigate(R.id.nav_home);
    }




}