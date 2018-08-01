package Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.client.MainActivity;
import com.example.client.R;
import com.example.client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Model.PersonModel;
import Request.LoginRequest;
import Request.RegisterRequest;
import Response.LoginResponse;
import Response.PersonIDResponse;
import Response.RegisterResponse;

/**
 * Created by pjohnst5 on 11/17/17.
 */

public class GetPersonTask extends AsyncTask<String, Void, PersonIDResponse> {
    PersonIDResponse person;
    Fragment myFrag;
    Context whereICameFrom;
    LoginRequest loginRequest;
    LoginResponse loginResponse;
    RegisterRequest requestForGetPeopleTask;
    RegisterResponse responseForGetPeopleTask;

    public GetPersonTask(Fragment in, Context inTwo, LoginRequest inThree, LoginResponse inFour){
        myFrag = in;
        whereICameFrom = inTwo;
        loginRequest = inThree;
        requestForGetPeopleTask = new RegisterRequest();
        requestForGetPeopleTask.setServerHost(loginRequest.getServerHost());
        requestForGetPeopleTask.setServerPort(loginRequest.getServerPort());
        responseForGetPeopleTask = new RegisterResponse();
        loginResponse = inFour;
    }

    public PersonIDResponse doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            person = myServerProxy.getPersonURL(url, urlAuth[1]);

            return person;

        } catch (MalformedURLException e){
            person.setMessage("Bad URl");
            person.setSuccess(false);
            return person;
        }
    }

    protected void onPostExecute(PersonIDResponse response){

        if (response.getSuccess()){

            String stringForToastIfSuccessful = new String("Login Success!" + "\n" + response.getFirstName() + "\n" + response.getLastName());
            responseForGetPeopleTask.setAuthToken(loginResponse.getAuthToken());
            String url = new String ("http://" + requestForGetPeopleTask.getServerHost() + ":" + requestForGetPeopleTask.getServerPort() + "/person/");

            GetPeopleTask myPeopleTask = new GetPeopleTask(myFrag,requestForGetPeopleTask, responseForGetPeopleTask, whereICameFrom, stringForToastIfSuccessful);
            myPeopleTask.execute(url, responseForGetPeopleTask.getAuthToken());


        }
        else {
            Toast.makeText(myFrag.getContext(),
                    R.string.loginNotSuccessfulPerson,
                    Toast.LENGTH_SHORT).show();
        }

    }


}
