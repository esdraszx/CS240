package Tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.client.ClientModel;
import com.example.client.R;
import com.example.client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Response.PersonGetAllResponse;
import Response.RegisterResponse;

public class GetPeopleTask extends AsyncTask<String,Void,PersonGetAllResponse> {
    private Fragment myFrag;
    private RegisterRequest taskRequest;
    private RegisterResponse responseFromRegister;
    private Context whereICameFrom;
    private String stringForToastIfSuccessful;
    private PersonGetAllResponse myResponse = new PersonGetAllResponse();

    public GetPeopleTask(Fragment in, RegisterRequest inTwo, RegisterResponse inThree, Context inFour, String inFive){
        this.myFrag = in;
        taskRequest = inTwo;
        responseFromRegister = inThree;
        whereICameFrom = inFour;
        stringForToastIfSuccessful = inFive;
    }

    public PersonGetAllResponse doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            myResponse = myServerProxy.getAllPeopleUrl(url, urlAuth[1]);



            return myResponse;

        } catch (MalformedURLException e){
            myResponse.setMessage("Bad URl");
            myResponse.setSuccess(false);
            return myResponse;
        }
    }

    protected void onPostExecute(PersonGetAllResponse response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful peoppleGetAll
            ClientModel cm = ClientModel.getInstance();
            cm.setPeople(myResponse.getData());

            //Now making EventGetAll task
            GetEventsTask eventTask = new GetEventsTask(myFrag, taskRequest, whereICameFrom, stringForToastIfSuccessful);
            String url = new String("http://" + taskRequest.getServerHost() + ":" + taskRequest.getServerPort() + "/event/");
            eventTask.execute(url, responseFromRegister.getAuthToken());


        } else { //was not a successful peopleGetAll
            //display failed register toast
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulPeopleGetAll,
                    Toast.LENGTH_SHORT).show();
        }

    }

}
