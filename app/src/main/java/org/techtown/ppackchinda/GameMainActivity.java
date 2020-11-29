package org.techtown.ppackchinda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
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

public class GameMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    static int chapter;
    static int page=0;

    static RequestQueue requestQueue;
    static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        userID=intent.getExtras().getString("userID");
        //db 접근
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        SetDataRequest(userID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
        SetUserData setUserRequest=new SetUserData(userID,"0","00:00:00",responseSetListener);
        requestQueue.add(setUserRequest);
    }
    public void GetDataRequest(String userID)
    {
        Response.Listener<String> responseGetListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success=jsonResponse.getBoolean("success");
                    if(success){
                        int chap=jsonResponse.getInt("userChap");
                        System.out.println("Chap"+chap);
                        String time=jsonResponse.getString("userTime");
                        System.out.println("Time"+time);
                        println(chap+" 띄어쓰기 "+time);
                        chapter=chap;
                    }
                    else
                    {
                        System.out.println("실패했음");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        GetUserData getUserRequest=new GetUserData(userID,responseGetListener);
        requestQueue.add(getUserRequest);
    }
    public void println(String data)
    {
        Toast.makeText(this,"유저 데이터 설정 결과 : "+data,Toast.LENGTH_SHORT).show();
        Log.d("유저 데이터 설정 결과 : ",data);
    }
    static public String getUserID()
    {
        return userID;
    }
}