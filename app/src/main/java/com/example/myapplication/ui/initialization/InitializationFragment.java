package com.example.myapplication.ui.initialization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.KMainActivity;
import com.example.myapplication.R;

import java.sql.Timestamp;

public class InitializationFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_initialization, container, false);
        Button ini_yes_Button =(Button) rootView.findViewById(R.id.ini_y_Button);
        Button ini_no_Button =(Button) rootView.findViewById(R.id.ini_n_Button);

        ini_yes_Button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                int data=0; //유저 데이터 받아와서 챕터 정보랑 시간 0으로 세팅
                Timestamp t =null;
                //startActivity(new intent(getContext(),TargetActivity.class)); 세팅 후

            }
        });
        ini_no_Button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v)
            {
                KMainActivity a = (KMainActivity) getActivity();
                Toast.makeText(a,"홈화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
                a.moveToHome();
            }
        });

        return rootView;
    }

}