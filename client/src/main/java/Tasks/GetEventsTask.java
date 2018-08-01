package Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.client.ClientModel;
import com.example.client.MainActivity;
import com.example.client.R;
import com.example.client.ServerProxy;

import java.net.MalformedURLException;
import java.net.URL;

import Request.RegisterRequest;
import Response.EventGetAllResponse;


public class GetEventsTask extends AsyncTask<String,Void, EventGetAllResponse> {
    private Fragment myFrag;
    private RegisterRequest taskRequest;
    private Context whereICameFrom;
    private String stringForToasIfSuccessful;
    private EventGetAllResponse myResponse = new EventGetAllResponse();

    public GetEventsTask(Fragment in, RegisterRequest inTwo, Context inThree, String inFour){
        this.myFrag = in;
        taskRequest = inTwo;
        whereICameFrom = inThree;
        stringForToasIfSuccessful = inFour;
    }

    public EventGetAllResponse doInBackground(String... urlAuth){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            ServerProxy myServerProxy = new ServerProxy();
            URL url = new URL(urlAuth[0]);

            ClientModel cm = ClientModel.getInstance();
            cm.setAuthToken(urlAuth[1]);

            myResponse = myServerProxy.getAllEventsUrl(url, urlAuth[1]);



            return myResponse;

        } catch (MalformedURLException e){
            myResponse.setMessage("Bad URl");
            myResponse.setSuccess(false);
            return myResponse;
        }
    }

    protected void onPostExecute(EventGetAllResponse response) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (response.getSuccess()){ //was a successful EventGetAll
            ClientModel cm = ClientModel.getInstance();
            cm.setEvents(myResponse.getData());



            Toast.makeText(myFrag.getContext(),
                    stringForToasIfSuccessful,
                    Toast.LENGTH_SHORT).show();

            //This is when we call onLoginRegisterSuccess for when we are both logging in and registering
            MainActivity source = (MainActivity) whereICameFrom;
            source.onLoginRegisterSuccess();


        } else { //was not a successful eventGetAll
            //display failed eventGetAll toast
            Toast.makeText(myFrag.getContext(),
                    R.string.registerNotSuccessfulEventGetALL,
                    Toast.LENGTH_SHORT).show();
        }

    }

}
