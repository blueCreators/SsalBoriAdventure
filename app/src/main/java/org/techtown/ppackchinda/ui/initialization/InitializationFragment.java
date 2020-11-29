package org.techtown.ppackchinda.ui.initialization;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import org.techtown.ppackchinda.GameMainActivity;
import org.techtown.ppackchinda.GetUserData;
import org.techtown.ppackchinda.MainActivity;
import org.techtown.ppackchinda.R;
import org.techtown.ppackchinda.UpdateUserData;
import org.techtown.ppackchinda.ui.home.HomeFragment;

import java.sql.Timestamp;
import java.util.Map;

public class InitializationFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_initialization, container, false);
        Button ini_yes_Button =(Button) rootView.findViewById(R.id.ini_y_Button);
        Button ini_no_Button =(Button) rootView.findViewById(R.id.ini_n_Button);

        ini_yes_Button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                //유저 데이터 받아와서 챕터 정보랑 시간 0으로 세팅
                HomeFragment f = new HomeFragment();
                f.UpdateDataRequest("KIL",0,"00:00:00");
                Toast.makeText(getContext(),"초기화 성공. 초기 화면으로 돌아갑니다.",Toast.LENGTH_LONG).show();
                GameMainActivity.setChapPage(0,0);
                startActivity(new Intent(getContext(),GameMainActivity.class));
                //로그인 엑티비티로 수정
            }
        });
        ini_no_Button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                GameMainActivity a = (GameMainActivity) getActivity();
                Toast.makeText(a,"홈화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                a.moveToHome();
            }
        });

        return rootView;
    }

}