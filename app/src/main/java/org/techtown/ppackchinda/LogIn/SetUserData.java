package org.techtown.ppackchinda.LogIn;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SetUserData extends StringRequest {

    //서버 URL 설정 (php 파일 연동)
    final static private String URL = "http://gb10111.dothome.co.kr/SetUserData.php";
    private Map<String, String> map;
    public SetUserData(String userID, String userChap, String userTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userChap",userChap);
        map.put("userTime",userTime);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}