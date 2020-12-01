package org.techtown.ppackchinda;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ppackchinda.LogIn.GetUserData;
import org.techtown.ppackchinda.LogIn.SetUserData;
import org.techtown.ppackchinda.ui.MyService;
import org.techtown.ppackchinda.ui.Setting;

public class GameMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    static int chapter;
    static int page=0;
    static String usertime = null;
    MediaPlayer m;

    static RequestQueue requestQueue;
    static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        if((intent.getExtras().getString("from")).equals("Login"))
        {
            userID=intent.getExtras().getString("userID");
            System.out.println("user id 처음은 "+ userID);
        }
        else if(intent.getExtras().getString("from").equals("Guest"))
        {
            userID="";
            chapter=0;
        }

        //db 접근
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        SetDataRequest(userID);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_item, R.id.nav_initialization, R.id.nav_hof,R.id.nav_reread)
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
        getMenuInflater().inflate(R.menu.game_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    static public int getChap()
    {
        System.out.println(chapter+" "+page+"가 페이지 수");
        return chapter;
    }

    static public String getUsertime() {
        if(userID.isEmpty()){
            return "00:00:00";
        }
        return usertime;
    }
    static public int getPage()
    {
        return page;
    }
    static public void setChapPage(int a,int b)

    {
        chapter=a;
        page=b;
    }
    public void SetDataRequest(String userID)
    {
        Response.Listener<String> responseSetListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        println("성공");
                        chapter=0;
                    }
                    else
                    {
                        println("실패");
                        GetDataRequest(userID);
                    }

                } catch (JSONException e) {
                    println("오류");
                    e.printStackTrace();
                }
            }
        };
        if(!userID.isEmpty()){
            SetUserData setUserRequest=new SetUserData(userID,"0","00:00:00",responseSetListener);
            requestQueue.add(setUserRequest);
        }
    }
    public static void GetDataRequest(String userID)
    {
        Response.Listener<String> responseGetListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");
                    if(success){
                        int chap=jsonResponse.getInt("userChap");
                        //System.out.println("Chap"+chap);
                        String time=jsonResponse.getString("userTime");
                        System.out.println("Time"+time);
//                        println(chap+" 띄어쓰기 "+time);
                        chapter=chap;
                        usertime = time;
                    }
                    else
                    {
                        System.out.println("실패했음");
                    }
                    //천마아트센터
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        if(!userID.isEmpty()){
            GetUserData getUserRequest=new GetUserData(userID,responseGetListener);
            requestQueue.add(getUserRequest);
        }
    }
    public void println(String data)
    {
        Log.d("유저 데이터 설정 결과 : ",data);
    }
    static public String getUserID()
    {
        return userID;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MediaPlayer m = this.m;
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        TextView t = (TextView)findViewById(R.id.ptextView);
        TextView InMenuUserName = (TextView)findViewById(R.id.userNameinProgress);
        // chapter정보, user 이름 입력받아야 함
        String uname = GameMainActivity.getUserID();
        // db에서 uname 받기
        InMenuUserName.setText(uname);
        int progressSu = 100/11*chapter;
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