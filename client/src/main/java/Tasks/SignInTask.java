package Tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginRequest;
import Response.LoginResponse;

public class SignInTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private LoginResponse answer = new LoginResponse();
    private LoginRequest myRequest;
    private Fragment myFrag;
    private Context myMainActivity;

    public SignInTask(Fragment in, Context inActivity){
        this.myFrag = in;
        myMainActivity = inActivity;
    }



    public LoginResponse doInBackground(LoginRequest... myLoginRequest) {
        myRequest = myLoginRequest[0];
        try {
            ServerProxy myProxy = new ServerProxy();

            URL url = new URL("http://" + myLoginRequest[0].getServerHost() + ":" + myLoginRequest[0].getServerPort() + "/user/login");
            answer = myProxy.getLoginUrl(url, myLoginRequest[0]);

            return answer;

        } catch (MalformedURLException e){
            answer.setMessage("Bad URl");
            answer.setSuccess(false);
            return answer;
        }
    }

    protected void onPostExecute(LoginResponse response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (answer.getSuccess()){

            String url = new String("http://" + myRequest.getServerHost() + ":" + myRequest.getServerPort() + "/person/" + response.getPersonId());
            GetPersonTask personTask = new GetPersonTask(myFrag, myMainActivity,myRequest, response);
            personTask.execute(url, response.getAuthToken());

        } else {
            Toast.makeText(myFrag.getContext(),
                    R.string.loginNotSuccessful,
                    Toast.LENGTH_SHORT).show();
        }

        //make task of get events and people
        //peopletask response in it
        //make a toast if its bad
        //make toast
        //totalSizeTextView.setText("Total Size: " + result);
    }

}
