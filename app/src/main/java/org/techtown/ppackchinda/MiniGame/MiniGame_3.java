package org.techtown.ppackchinda.MiniGame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.ppackchinda.R;

public class MiniGame_3 extends AppCompatActivity {

    EditText answer;
    Button btnComplete;
    String correctAnswer = "고양이를 찾습니다";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minigame_3);

        answer = (EditText)findViewById(R.id.answer);
        btnComplete = (Button)findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = answer.getText().toString();

                if(userInput.equals(correctAnswer)){
                    Toast.makeText(MiniGame_3.this, "정답입니다!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MiniGame_3.this, "다시 한번 생각해보세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
