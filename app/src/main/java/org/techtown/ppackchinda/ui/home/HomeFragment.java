package org.techtown.ppackchinda.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.techtown.ppackchinda.GameMainActivity;
import org.techtown.ppackchinda.R;

public class HomeFragment extends Fragment {
    Button btnLastScr,btnNextScr;
    TextView txtNPC,txtScript;
    String[] scripts;
    int[] chapId={R.array.Chapter1,R.array.Chapter2,R.array.Chapter3};
    int chapter=0;
    int page=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        btnLastScr=(Button)root.findViewById(R.id.btnLastScr);
        btnNextScr=(Button)root.findViewById(R.id.btnNextScr);
        txtNPC=(TextView)root.findViewById(R.id.txtNPC);
        txtScript=(TextView)root.findViewById(R.id.txtScript);

        //저장된 챕터 불러오기 추가해야함
        //int loadChap=0;//get chapter num 수정해야함
        //showScript(loadChap,0);
        chapter=GameMainActivity.getChap();
        page=GameMainActivity.getPage();
        scripts=root.getResources().getStringArray(chapId[chapter]);
        showScript(chapter,page);


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
                    //미니게임이나 완전 끝 만나면 일어날 거 if로 쓰기
                    //자동저장 부분 토스트 화면?
                }
                else{
                    page++;
                }
                showScript(chapter,page);
            }
        });
        return root;
    }
    public void showScript(int selChap,int selPage)
    {
        GameMainActivity.setChapPage(selChap,page);
        if(selChap==chapter)
        {
            scripts=getResources().getStringArray(chapId[selChap]);
            chapter=selChap;
        }
        if(scripts[selPage].contains(":"))
        {
            String[] temp=scripts[selPage].split(":");
            txtNPC.setText(temp[0]);
            txtScript.setText(temp[1]);
        }
        else{
            txtNPC.setText(" ");
            txtScript.setText(scripts[selPage]);
        }
    }
}