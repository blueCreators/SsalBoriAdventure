package org.techtown.ppackchinda;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetUserData extends StringRequest {

    //서버 URL 설정 (php 파일 연동)
    final static private String URL = "http://gb10111.dothome.co.kr/GetUserdata.php";
    private Map<String, String> map;
    public GetUserData(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    public Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}