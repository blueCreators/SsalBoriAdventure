package org.techtown.ppackchinda.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.ppackchinda.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_email,et_passck;
    private Button btn_register,validateButton;
    private AlertDialog dialog;
    private boolean validate=false; //아이디 중복확인

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_passck=findViewById(R.id.et_passck);
        validateButton=findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() { //id중복체크
            @Override
            public void onClick(View view) {
                String userID=et_id.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                } //아이디 빈 칸이면 메시지가 나온다.
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      try {
                          JSONObject jsonResponse = new JSONObject(response);
                          boolean success = jsonResponse.getBoolean("success");

                          if (success) {
                              AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                              dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                      .setPositiveButton("확인", null)
                                      .create();
                              dialog.show();
                              et_id.setEnabled(false);
                              validate = true;
                              validateButton.setText("확인");
                          }
                          else {
                              AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                              dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                      .setNegativeButton("확인", null)
                                      .create();
                              dialog.show();
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                          }
                      }
                    };
                ValidateRequest validateRequest=new ValidateRequest(userID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

                }
            });

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText에 현재 입력되어 있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                final String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                String userEmail = et_email.getText().toString();
                final String PassCk=et_passck.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(userPass.equals(PassCk)) {
                                if (success) { //회원등록에 성공한 경우
                                    Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else { //회원등록에 실패할 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 volley를 이용해서 요청을 함
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}

