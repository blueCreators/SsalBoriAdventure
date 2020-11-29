package org.techtown.ppackchinda.MiniGame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.ppackchinda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniGame_1 extends AppCompatActivity {
    public ArrayList<String> order = new ArrayList<String>(
            Arrays.asList("1/5","2/5", "3/5", "4/5", "5/5"));

    public ArrayList<String> questionList = new ArrayList<String>(
            Arrays.asList("외국인 교환학생의 수강신청 및 전반적인 학교 생활을 도와주는 국제교류팀의 1대1 매칭 프로그램은?",
                    "개발도상국의 빈곤 극복과 삶의 질 향상에 기여하는 인재를 양성하는 영남대학교에만 존재하는 대학원은?",
                    "다음 중 영남대학교 어울림에서 상담 받을 수 있는 내용이 아닌 것은?",
                    "다음 중 동아리 분과 중 영남대학교에 존재하지 않는 것은?",
                    "공연과 전시를 위한 영남대학교만의 복합문화예술공간은?"));

    public ArrayList<List<String>> exlist = new ArrayList<List<String>>(){{
        add(Arrays.asList("GSP", "Buddy", "LPP", "GTS"));
        add(Arrays.asList("환경보건대학원", "스포츠과학대학원", "박정희새마을대학원", "교육대학원"));
        add(Arrays.asList("국제교류상담", "진로상담", "장학상담", "심리상담"));
        add(Arrays.asList("교양분과", "예술분과", "종교분과", "IT분과"));
        add(Arrays.asList("", "", "", ""));
    }};

    public ArrayList<String> answerList = new ArrayList<String>(
            Arrays.asList("Buddy", "박정희새마을대학원", "장학상담", "IT분과", "천마아트센터"));

    private TextView questionOrder, question;
    private Button btn1, btn2, btn3, btn4, btnComplete;
    private EditText editText;
    private LinearLayout editTextLayout, btnLayout;
    private String userInput; //사용자가 입력한 버튼의 문자열 저장
    private boolean isCorrected = false; //퀴즈 정답 오답 여부
    private int quizCnt = 0; //지금까지 푼 퀴즈 개수
    private int currentQuiz = 0; //현재 문제 번호


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minigame_1);

        questionOrder = (TextView)findViewById(R.id.questionOrder);
        question = (TextView)findViewById(R.id.question);
        editText = (EditText)findViewById(R.id.editText);
        btnLayout = (LinearLayout)findViewById(R.id.btnLayout);
        editTextLayout = (LinearLayout)findViewById(R.id.editTextLayout);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btnComplete = (Button)findViewById(R.id.btnComplete);

        BtnListener listener = new BtnListener();

        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);


    }

    public boolean isInputCorrect(String userInput){ //사용자 input이 정답인지 검사
        String answer = answerList.get(currentQuiz).toString();//answer = 현재 문제 번호의 답

        if(userInput.equals(answer)){
            return true;
        }else{
            return false;
        }
    }

    public void checkEditText(){
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = editText.getText().toString();

                if(isInputCorrect(userInput) == true){
                    Toast.makeText(MiniGame_1.this, "모두 정답입니다!", Toast.LENGTH_SHORT).show();
                    finish();

                    //다음 스크립트로 넘어가게 한다
                    quizCnt++;
                    currentQuiz++;
                }
                else{
                    Toast.makeText(MiniGame_1.this, "다시 한번 생각해보세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changeScreen(){ //문제 번호, 문제, 선지 바꾸기
        if(currentQuiz == 4){
            questionOrder.setText(order.get(currentQuiz)+"");
            question.setText(questionList.get(currentQuiz)+"");

            btnLayout.setVisibility(View.INVISIBLE);
            editTextLayout.setVisibility(View.VISIBLE);

            checkEditText();
        }
        else{
            String tmpOrder = order.get(currentQuiz);
            String tmpQuestion = questionList.get(currentQuiz);
            List<String> tmpEx = exlist.get(currentQuiz);

            questionOrder.setText(tmpOrder+"");
            question.setText(tmpQuestion+"");

            btn1.setText(tmpEx.get(0));
            btn2.setText(tmpEx.get(1));
            btn3.setText(tmpEx.get(2));
            btn4.setText(tmpEx.get(3));
        }
    }

    class BtnListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button tmpBtn = (Button) v;

            //사용자 input
            userInput = tmpBtn.getText().toString();

            if(isInputCorrect(userInput) == true){
                Toast.makeText(MiniGame_1.this, "정답입니다", Toast.LENGTH_SHORT).show();
                quizCnt++; //정답 문제 개수 + 1
                currentQuiz++; //현재 문제 번호 + 1
                System.out.println("정답 문제 개수: "+quizCnt+" 현재 문제 번호: "+currentQuiz);

                changeScreen();

            }else{
                Toast.makeText(MiniGame_1.this, "다시 한번 생각해보세요", Toast.LENGTH_SHORT).show();
            }
        }
    }
}