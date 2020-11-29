package org.techtown.ppackchinda.MiniGame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.techtown.ppackchinda.R;

public class MiniGame_2 extends AppCompatActivity {

    ImageView image;
    Button btn;
    final String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 1;
    String correctAnswer = "C01";

    TextView question;
    EditText answer;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minigame_2);

        image = (ImageView) findViewById(R.id.image);
        btn = (Button) findViewById(R.id.btn);

        question = (TextView)findViewById(R.id.question);
        answer = (EditText)findViewById(R.id.answer);
        btnComplete = (Button)findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = answer.getText().toString();

                if(userInput.equals(correctAnswer)){
                    Toast.makeText(MiniGame_2.this, "정답입니다!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(MiniGame_2.this, "다시 한번 생각해보세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(MiniGame_2.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "OnrRequestPermissionResult");
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode){
            case TAKE_PICTURE:
                if(resultCode == RESULT_OK && intent.hasExtra("data")){
                    Bitmap bitmap = (Bitmap)intent.getExtras().get("data");

                    if(bitmap != null){
                        btn.setVisibility(View.INVISIBLE);
                        image.setVisibility(View.VISIBLE);

                        image.setImageBitmap(bitmap);
                        image.setImageResource(R.drawable.result);
                        question.setVisibility(View.VISIBLE);
                        answer.setVisibility(View.VISIBLE);
                        btnComplete.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

}
