package org.techtown.ppackchinda.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ppackchinda.GameMainActivity;
import org.techtown.ppackchinda.MiniGame.MiniGame_1;
import org.techtown.ppackchinda.MiniGame.MiniGame_2;
import org.techtown.ppackchinda.MiniGame.MiniGame_3;
import org.techtown.ppackchinda.MiniGame.MiniGame_4;
import org.techtown.ppackchinda.MiniGame.MiniGame_5;
import org.techtown.ppackchinda.R;
import org.techtown.ppackchinda.LogIn.UpdateUserData;

public class HomeFragment extends Fragment {
    Button btnLastScr,btnNextScr;
    TextView txtNPC,txtScript;
    String[] scripts;
    int[] chapId={R.array.Chapter1,R.array.Chapter2,R.array.Chapter3,R.array.Chapter4,R.array.Chapter5,R.array.Chapter6,R.array.Chapter7,
            R.array.Chapter8,R.array.Chapter9,R.array.Chapter10,R.array.Chapter11};
    int chapter;
    int page=0;
    String userID=GameMainActivity.getUserID();
    static RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        btnLastScr=(Button)root.findViewById(R.id.btnLastScr);
        btnNextScr=(Button)root.findViewById(R.id.btnNextScr);
        txtNPC=(TextView)root.findViewById(R.id.txtNPC);
        txtScript=(TextView)root.findViewById(R.id.txtScript);

        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        btnLastScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page==0)
                {
                    Context context=container.getContext();
                    Toast.makeText(context,"챕터의 첫번째 스크립트입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                page--;
                showScript(chapter,page);
            }
        });
        btnNextScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(page+1==scripts.length)
                {
                    chapter++;
                    page=0;
                    GameMainActivity.setChapPage(chapter, page);
                    UpdateDataRequest(userID,chapter,"12:34:00");
                    System.out.println("Chapter: "+(chapter));
                    switch (chapter)
                    {
                        case 3:
                            System.out.println("퀴즈 게임 시작!");
                            Intent quizgame = new Intent(getActivity().getApplicationContext(), MiniGame_1.class);
                            startActivity(quizgame);
                            break;
                        case 5:
                            System.out.println("천공카드 게임 시작!");
                            Intent cardgame = new Intent(getActivity().getApplicationContext(), MiniGame_2.class);
                            startActivity(cardgame);
                            break;
                        case 6:
                            System.out.println("게시판 게임 시작!");
                            Intent boardgame = new Intent(getActivity().getApplicationContext(), MiniGame_3.class);
                            startActivity(boardgame);
                            break;
                        case 7:
                            System.out.println("고양이 찾기 게임 시작!");
                            Intent findcatgame = new Intent(getActivity().getApplicationContext(), MiniGame_4.class);
                            startActivity(findcatgame);
                            break;
                        case 10:
                            System.out.println("민속촌 게임 시작!");
                            Intent timergame = new Intent(getActivity().getApplicationContext(), MiniGame_5.class);
                            startActivity(timergame);
                            break;
                    }
                }
                else if(chapter==0&&page==0)
                {
                    chapter=GameMainActivity.getChap();
                    System.out.println("getChap 결과 "+chapter);
                    if(chapter!=0)
                    {
                        page=0;
                        scripts=root.getResources().getStringArray(chapId[chapter]);
                    }
                    else
                    {
                        page++;
                    }
                }
                else{
                    page++;
                }
                showScript(chapter,page);
            }
        });
        chapter=GameMainActivity.getChap();
        page=GameMainActivity.getPage();
        scripts=root.getResources().getStringArray(chapId[chapter]);
        showScript(chapter,page);

        return root;
    }
    public void showScript(int selChap,int selPage)
    {
        GameMainActivity.setChapPage(selChap, selPage);
        if(!scripts.equals(chapId[chapter]))
        {
            scripts=getResources().getStringArray(chapId[selChap]);
        }
        if(scripts[selPage].contains(":"))
        {
            String[] temp=scripts[selPage].split(":");
            txtNPC.setText(temp[0]);
            txtScript.setText(temp[1]);
        }
        else{
            txtNPC.setText("");
            txtScript.setText(scripts[selPage]);
        }
    }
    public void UpdateDataRequest(String userID,int userChap,String userTime)
    {
        Response.Listener<String> responseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        System.out.println("업데이트 성공");
                    }
                    else
                    {
                        System.out.println("업데이트 실패");
                    }

                } catch (JSONException e) {
                    System.out.println("오류");
                    e.printStackTrace();
                }
            }
        };
        UpdateUserData updateUserRequest=new UpdateUserData(userID,String.valueOf(userChap),userTime,responseListener);
        requestQueue.add(updateUserRequest);
    }

}