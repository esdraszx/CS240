package ObjectCodeDecode;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import Model.PersonModel;
import Request.*;
import Response.EventGetAllResponse;
import Response.LoginResponse;
import Response.PersonGetAllResponse;
import Response.PersonIDResponse;
import Response.RegisterResponse;

public class Decoder {



    public Decoder(){
        //To be implemented later
    }

    public static LoginRequest decodeLoginRequest(Reader json){
        LoginRequest out;
        Gson gson = new Gson();

        out = gson.fromJson(json, LoginRequest.class);
        return out;
    }

    public static LoadRequest decodeLoadRequest(Reader json)  {
        LoadRequest out;
        Gson gson = new Gson();

        out = gson.fromJson(json, LoadRequest.class);
        return out;
    }

    public static LoginResponse decodeLoginResponse(Reader json){
        LoginResponse out;
        Gson gson = new Gson();
        out = gson.fromJson(json, LoginResponse.class);
        return out;
    }

    public static RegisterResponse decodeRegisterResponse(Reader json){
        RegisterResponse out;
        Gson gson = new Gson();
        out = gson.fromJson(json, RegisterResponse.class);
        return out;
    }

    public static PersonGetAllResponse decodePersonGetAllResponse(Reader json){
        PersonGetAllResponse out;
        Gson gson = new Gson();
        out = gson.fromJson(json, PersonGetAllResponse.class);
        return out;
    }

    public static EventGetAllResponse decodeEventGetAllResponse(Reader json){
        EventGetAllResponse out;
        Gson gson = new Gson();
        out = gson.fromJson(json, EventGetAllResponse.class);
        return out;
    }

    public static PersonIDResponse decodePersonIDResponse(Reader json){
        PersonIDResponse out;
        Gson gson = new Gson();
        out = gson.fromJson(json, PersonIDResponse.class);
        return out;
    }


    public static RegisterRequest decodeRegisterRequest(Reader json)  {
        RegisterRequest out;
        Gson gson = new Gson();

            out = gson.fromJson(json, RegisterRequest.class);
            return out;
    }

    public static StringArray decodeNames(String file){
        Gson gson = new Gson();
        try{
            StringArray temp = gson.fromJson(new FileReader(file), StringArray.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static LocationArray decodeLocations(String file){
        Gson gson = new Gson();
        try{
            LocationArray temp = gson.fromJson(new FileReader(file), LocationArray.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
