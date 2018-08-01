package com.example.client;


import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.PersonModel;
import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.Encoder;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.EventGetAllResponse;
import Response.LoginResponse;
import Response.PersonGetAllResponse;
import Response.PersonIDResponse;
import Response.RegisterResponse;

public class ServerProxy {

    public LoginResponse getLoginUrl(URL url, LoginRequest loginRequest) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");

            //connect maybe goes here

            String json = Encoder.encode(loginRequest);

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                LoginResponse out = Decoder.decodeLoginResponse(reader);
                out.setSuccess(true);
                connection.getInputStream().close();
                return out;
            } else {
                LoginResponse out = new LoginResponse();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;
    }

    public RegisterResponse getRegisterUrl(URL url, RegisterRequest request){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");


            String json = Encoder.encode(request);

            OutputStream reqBody = connection.getOutputStream();
            writeString(json, reqBody);
            reqBody.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                RegisterResponse out = Decoder.decodeRegisterResponse(reader);
                out.setSuccess(true);
                connection.getInputStream().close();
                return out;
            } else {
                RegisterResponse out = new RegisterResponse();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }
        }
        catch (Exception e) {
            Log.e("HttpClient", e.getMessage(), e);
        }
        return null;

    }

    public EventGetAllResponse getAllEventsUrl(URL url, String auth){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                EventGetAllResponse out = Decoder.decodeEventGetAllResponse(reader);
                out.setSuccess(true);
                return out;

            } else {
                EventGetAllResponse out = new EventGetAllResponse();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
            EventGetAllResponse badResponse = new EventGetAllResponse();
            badResponse.setMessage("Bad Request");
            badResponse.setSuccess(false);
            return badResponse;
        }
    }

    public PersonGetAllResponse getAllPeopleUrl(URL url, String auth){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                PersonGetAllResponse out = Decoder.decodePersonGetAllResponse(reader);
                out.setSuccess(true);

                return out;

            } else {
                PersonGetAllResponse out = new PersonGetAllResponse();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }

    public PersonIDResponse getPersonURL(URL url, String auth){
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                PersonIDResponse out = Decoder.decodePersonIDResponse(reader);
                out.setSuccess(true);
                return out;

            } else {
                PersonIDResponse out = new PersonIDResponse();
                out.setSuccess(false);
                out.setMessage(connection.getResponseMessage());
                return out;
            }

        } catch (Exception e){
            Log.e("Httpclient", e.getMessage(), e);
        }
        return null;
    }


    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}














/*
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                Reader reader = new InputStreamReader(connection.getInputStream());
                LoginResponse out = Decoder.decodeLoginResponse(reader);
                return out;
            } else {
                Reader reader = new InputStreamReader(connection.getInputStream());
                LoginResponse out = Decoder.decodeLoginResponse(reader);
                return out;
            }
*/






/*
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                Reader reader = new InputStreamReader(connection.getInputStream());
                LoginResponse out = Decoder.decodeLoginResponse(reader);
                return out;


                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                return responseBodyData;

            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST){
                Reader reader = new InputStreamReader(connection.getInputStream());
                LoginResponse out = Decoder.decodeLoginResponse(reader);
            }
            */