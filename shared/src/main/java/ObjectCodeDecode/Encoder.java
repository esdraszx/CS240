package ObjectCodeDecode;

import Request.LoginRequest;
import Request.RegisterRequest;
import Response.*;
import com.google.gson.Gson;


public class Encoder {


    public Encoder(){
        //To be implemented later
    }

    public static String encode(LoginResponse response) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(RegisterResponse response) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(ClearResponse response){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(PersonIDResponse response){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(PersonGetAllResponse response){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(EventIDResponse response){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(EventGetAllResponse response){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(LoginRequest request){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }

    public static String encode (RegisterRequest request){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }









}
