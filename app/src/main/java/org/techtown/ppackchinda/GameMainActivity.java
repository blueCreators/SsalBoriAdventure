package org.techtown.ppackchinda;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

public class GameMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    static int chapter;
    static int page=0;

    static RequestQueue requestQueue;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //db 접근
        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        //userID=getuserID() 로그인에서 받아오기
        userID="abc";
        makeRequest(userID);
        chapter=0;//db에서 챕터 받아오기

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
    public void makeRequest(String userID)
    {
        System.out.println("에반디");
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("실행 되긴 함");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        println("성공");
                    }
                    else
                    {
                        println("실패");
                    }

                } catch (JSONException e) {
                    println("오류");
                    e.printStackTrace();
                }
            }
        };
        SetUserData setUserRequest=new SetUserData(userID,"1","00:00:00",responseListener);
        requestQueue.add(setUserRequest);
        System.out.println("개에반디");
    }
    public void println(String data)
    {
        Toast.makeText(this,"유저 데이터 설정 결과 : "+data,Toast.LENGTH_SHORT).show();
        Log.d("유저 데이터 설정 결과 : ",data);
    }
}