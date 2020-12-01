package org.techtown.ppackchinda.ui.reread;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.techtown.ppackchinda.GameMainActivity;
import org.techtown.ppackchinda.R;

public class ReReadFragment extends Fragment {
    Spinner spinReadChap;
    int[] chapId={R.array.Chapter1,R.array.Chapter2,R.array.Chapter3,R.array.Chapter4,R.array.Chapter5,R.array.Chapter6,R.array.Chapter7,
            R.array.Chapter8,R.array.Chapter9,R.array.Chapter10,R.array.Chapter11};
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reread, container, false);
        spinReadChap=(Spinner)root.findViewById(R.id.spinReadChap);
        Context context=container.getContext();
        ArrayAdapter chapAdapter =ArrayAdapter.createFromResource(context,R.array.ChapterNumber, android.R.layout.simple_spinner_item);
        chapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinReadChap.setAdapter(chapAdapter);

        int chapter= GameMainActivity.getChap();
        int page=GameMainActivity.getPage();

        spinReadChap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(chapter<i)
                {
                    Toast.makeText(context,"아직 플레이하지 않은 챕터입니다",Toast.LENGTH_LONG).show();
                    return;
                }
                String[] scripts=getResources().getStringArray(chapId[i]);
                LinearLayout inScrllLinear=(LinearLayout) root.findViewById(R.id.inScrllLinear);
                inScrllLinear.removeAllViews();
                int limit;
                if(chapter==i)
                {
                    limit=page+1;
                }
                else {
                    limit=scripts.length;
                }
                for(int x=0;x<limit;x++)
                {
                    RelativeLayout ret=(RelativeLayout)View.inflate(context,R.layout.reread_script_msg,null);
                    TextView txtMsgScript=(TextView)ret.findViewById(R.id.txtMsgScript);
                    TextView txtMsgNPC=(TextView)ret.findViewById(R.id.txtMsgNPC);
                    if(scripts[x].contains(":"))
                    {
                        String[] temp=scripts[x].split(":");
                        txtMsgNPC.setText(temp[0]);
                        txtMsgScript.setText(temp[1]);
                    }
                    else{
                        txtMsgNPC.setText(" ");
                        txtMsgScript.setText(scripts[x]);
                    }
                    inScrllLinear.addView(ret);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return root;
    }
}