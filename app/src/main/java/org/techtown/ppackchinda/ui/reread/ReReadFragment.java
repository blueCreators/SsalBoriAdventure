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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.techtown.ppackchinda.R;

public class ReReadFragment extends Fragment {
    Spinner spinReadChap;
    int[] chapId={R.array.Chapter1,R.array.Chapter2,R.array.Chapter3};
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reread, container, false);
        spinReadChap=(Spinner)root.findViewById(R.id.spinReadChap);
        Context context=container.getContext();
        ArrayAdapter chapAdapter =ArrayAdapter.createFromResource(context,R.array.ChapterNumber, android.R.layout.simple_spinner_item);
        chapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinReadChap.setAdapter(chapAdapter);

        spinReadChap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] scripts=getResources().getStringArray(chapId[i]);
                LinearLayout inScrllLinear=(LinearLayout) root.findViewById(R.id.inScrllLinear);
                inScrllLinear.removeAllViews();
                for(int x=0;x<scripts.length;x++)
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