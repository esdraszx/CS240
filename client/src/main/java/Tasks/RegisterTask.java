package Tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.client.ClientModel;
import com.example.client.R;
import com.example.client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Response.EventGetAllResponse;
import Response.PersonGetAllResponse;
import Response.RegisterResponse;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {

    private Fragment fragActivity;
    private Context myMainActivity;
    private RegisterRequest taskRequest;
    private RegisterResponse answer = new RegisterResponse();
    private PersonGetAllResponse peopleAnswer = new PersonGetAllResponse();
    private EventGetAllResponse eventAnswer = new EventGetAllResponse();

    public RegisterTask(Fragment fragAct, Context in){
        this.fragActivity = fragAct;
        this.myMainActivity = in;
    }

    public RegisterResponse doInBackground(RegisterRequest... myRegisterRequests){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
            taskRequest = myRegisterRequests[0];
        try {
            ServerProxy myServerProxy = new ServerProxy();

            URL urla = new URL("http://" + myRegisterRequests[0].getServerHost() + ":" + myRegisterRequests[0].getServerPort() + "/user/register");
            answer = myServerProxy.getRegisterUrl(urla, myRegisterRequests[0]);

/*
            String url = new String ("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/person/");
            GetPeopleTask peopleTast = new GetPeopleTask(fragActivity);
            fragActivity.getActivity().runOnUiThread(new Runnable);
*/

            return answer;

        } catch (MalformedURLException e){
            answer.setMessage("Bad URl");
            answer.setSuccess(false);
            return answer;
        }
    }

    protected void onPostExecute(RegisterResponse response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful register

            String url = new String ("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/person/");
            String stringForToastIfSuccessful = new String("Register Success!" + "\n" + taskRequest.getFirstName() + "\n" + taskRequest.getLastName());

            GetPeopleTask peopleTask = new GetPeopleTask(fragActivity, taskRequest, response, myMainActivity,stringForToastIfSuccessful);
            peopleTask.execute(url, response.getAuthToken());

            /*
            Toast.makeText(fragActivity.getContext(),
                    "Register Success!" + "\n" + taskRequest.getFirstName() + "\n" + taskRequest.getLastName(),
                    Toast.LENGTH_SHORT).show();


            GetEventsTask eventTask = new GetEventsTask(fragActivity);
            url = new String("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/event/");
            eventTask.execute(url, response.getAuthToken());

            Toast.makeText(fragActivity.getContext(),
                    "Register Success!" + "\n" + taskRequest.getFirstName() + "\n" + taskRequest.getLastName(),
                    Toast.LENGTH_SHORT).show();
                    */

        } else { //was not a successful register
            //display failed register toast
            Toast.makeText(fragActivity.getContext(),
                    R.string.registerNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

    }
}
