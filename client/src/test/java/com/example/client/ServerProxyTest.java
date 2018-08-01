package com.example.client;


import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import org.junit.Assert.*;

import Model.EventModel;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.EventGetAllResponse;
import Response.LoginResponse;
import Response.PersonGetAllResponse;
import Response.PersonIDResponse;
import Response.RegisterResponse;

import static junit.framework.Assert.*;

public class ServerProxyTest {

    private URL testURL;
    private ServerProxy proxy;

    @Before
    public void setUP(){
        proxy = new ServerProxy();
        try{
            testURL = new URL("http://");
        } catch (Exception e ){
            assertEquals("Throwing exception", e.getMessage());
        }
    }

    @After
    public void tearDown(){
        testURL = null;
        proxy = null;
    }


    @Test
    public void testGetRegisterURL(){
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("Joe");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("Joe");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResponse expectedResponse = new RegisterResponse();

        expectedResponse.setUserName(request.getUserName());
        expectedResponse.setSuccess(true);

        RegisterResponse out = proxy.getRegisterUrl(testURL, request);
        expectedResponse.setAuthToken(out.getAuthToken());
        expectedResponse.setPersonId(out.getPersonID());

        assertEquals(expectedResponse, out);

    }

    @Test
    public void testGetRegisterURLShouldFail(){
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("John");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("John");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResponse expectedResponse = new RegisterResponse();

        expectedResponse.setUserName(request.getUserName());
        expectedResponse.setSuccess(true);

        RegisterResponse out = proxy.getRegisterUrl(testURL, request);
        expectedResponse.setAuthToken(out.getAuthToken());
        expectedResponse.setPersonId(out.getPersonID());

        assertEquals(expectedResponse, out);

        //registering the same guy twice
        RegisterResponse expectedResponse2 = new RegisterResponse();
        expectedResponse2.setSuccess(false);
        expectedResponse2.setMessage("Bad Request");


        out = proxy.getRegisterUrl(testURL, request);

        assertEquals(expectedResponse2, out);
    }

    @Test
    public void testGetLoginURL(){
        //Uses register to register user then login in with that user's info
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("Jane");
        request.setPassword("password");
        request.setGender("f");
        request.setEmail("email");
        request.setFirstName("Jane");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResponse expectedResponse = new RegisterResponse();

        expectedResponse.setUserName(request.getUserName());
        expectedResponse.setSuccess(true);

        RegisterResponse out = proxy.getRegisterUrl(testURL, request);
        expectedResponse.setAuthToken(out.getAuthToken());
        expectedResponse.setPersonId(out.getPersonID());

        assertEquals(expectedResponse, out);


        LoginRequest requestLogin = new LoginRequest();
        requestLogin.setServerPort("8080");
        requestLogin.setServerHost("192.168.2.32");
        requestLogin.setUserName("Jane");
        requestLogin.setPassword("password");

        try{
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/login");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        LoginResponse expectedResponseLogin = new LoginResponse();
        expectedResponseLogin.setSuccess(true);
        expectedResponseLogin.setUserName(request.getUserName());

        LoginResponse outLogin = proxy.getLoginUrl(testURL, requestLogin);
        expectedResponseLogin.setPersonId(outLogin.getPersonId());
        expectedResponseLogin.setAuthToken(outLogin.getAuthToken());

        assertEquals(expectedResponseLogin, outLogin);
    }

    @Test
    public void testGetLoginURLShouldFail(){
        //Logging someone in that hasn't been registered

        LoginRequest requestLogin = new LoginRequest();
        requestLogin.setServerPort("8080");
        requestLogin.setServerHost("192.168.2.32");
        requestLogin.setUserName("Bob");
        requestLogin.setPassword("password");

        try{
            testURL = new URL("http://" + requestLogin.getServerHost() + ":" + requestLogin.getServerPort() + "/user/login");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        LoginResponse expectedResponseLogin = new LoginResponse();
        expectedResponseLogin.setSuccess(false);
        expectedResponseLogin.setMessage("Bad Request");

        LoginResponse outLogin = proxy.getLoginUrl(testURL, requestLogin);

        assertEquals(expectedResponseLogin, outLogin);
    }

    @Test
    public void testEventGetAllURL(){
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("Jacob");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("Jacob");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResponse out = proxy.getRegisterUrl(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        EventGetAllResponse outTwo = proxy.getAllEventsUrl(testURL, authToken);
        //because I didn't feel like creating hudnreds of event models, I will verify that the size of the array is 124 and that the success is true

        assertEquals(outTwo.getData().length, 124);
        assertTrue(outTwo.getSuccess());
    }

    @Test
    public void testEventGetAllURLShouldFail(){
        String authToken = "BogusAuthToken";
        try {
            testURL = new URL("http://192.168.2.32:8080/event/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        EventGetAllResponse outTwo = proxy.getAllEventsUrl(testURL, authToken);

        assertEquals(outTwo.getMessage(), "Bad Request");
        assertFalse(outTwo.getSuccess());
    }

    @Test
    public void testPersonGetAllURL(){
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("Jingleheimer");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("Jingleheimer");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }



        RegisterResponse out = proxy.getRegisterUrl(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/person/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        PersonGetAllResponse outTwo = proxy.getAllPeopleUrl(testURL, authToken);
        //because I didn't feel like creating 30 people, I will verify that the size of the array is 31 and that the success is true

        assertEquals(outTwo.getData().length, 31);
        assertTrue(outTwo.getSuccess());
    }

    @Test
    public void testPersonGetALLURLshouldFail(){
        String authToken = "Bogus";
        try {
            testURL = new URL("http://192.168.2.32:8080/person/");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        PersonGetAllResponse outTwo = proxy.getAllPeopleUrl(testURL, authToken);
        //because I didn't feel like creating 30 people, I will verify that the size of the array is 31 and that the success is true

        assertEquals(outTwo.getMessage(), "Bad Request");
        assertFalse(outTwo.getSuccess());
    }

    @Test
    public void testGetPersonURL(){
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("Jimmy");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("Jimmy");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }



        RegisterResponse out = proxy.getRegisterUrl(testURL, request);

        String authToken = out.getAuthToken();
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/person/" + out.getPersonID());
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }


        PersonIDResponse outTwo = proxy.getPersonURL(testURL, authToken);

        assertEquals(outTwo.getFirstName(), "Jimmy");
        assertEquals(outTwo.getLastName(), "Doe");
        assertTrue(outTwo.getSuccess());
    }

    @Test
    public void testGetPersonURLShouldFail(){
        RegisterRequest request = new RegisterRequest();
        request.setServerPort("8080");
        request.setServerHost("192.168.2.32");
        request.setUserName("Jeane");
        request.setPassword("password");
        request.setGender("m");
        request.setEmail("email");
        request.setFirstName("Jeane");
        request.setLastName("Doe");
        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/user/register");
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        RegisterResponse out = proxy.getRegisterUrl(testURL, request);
        String authToken = out.getAuthToken();

        RegisterRequest requestTwo = new RegisterRequest();
        requestTwo.setServerPort("8080");
        requestTwo.setServerHost("192.168.2.32");
        requestTwo.setUserName("BillyBob");
        requestTwo.setPassword("password");
        requestTwo.setGender("m");
        requestTwo.setEmail("email");
        requestTwo.setFirstName("BillyBob");
        requestTwo.setLastName("Doe");

        RegisterResponse out2 = proxy.getRegisterUrl(testURL, requestTwo);


        try {
            testURL = new URL("http://" + request.getServerHost() + ":" + request.getServerPort() + "/person/" + out2.getPersonID());
        } catch (Exception e){
            assertEquals("Throwing exception", e.getMessage());
        }

        //We're giving the proxy out2's personID but out1's auth token

        PersonIDResponse outTwo = proxy.getPersonURL(testURL, authToken);

        assertEquals(outTwo.getMessage(), "Bad Request");
        assertFalse(outTwo.getSuccess());
    }

}
