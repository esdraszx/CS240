package Response;

/**
 *A Load Response object which represents a Load Repsonse in javacode, contains the number of users persons and events that were loaded successfully into the database
 *<pre>
 *<b>Domain</b>:
 *     success : boolean
 *     message : String
 *     numPersons : int
 *     numEvents : int
 *     numUsers : int
 *</pre>
 */
public class LoadResponse {

    private boolean success;
    private String message;
    private int numUsers;
    private int numPersons;
    private int numEvents;


    /**
     *The defaault constructor for LoadResponse
     *<pre>
     *<b>Constraints on the input</b>:
     *  none
     *</pre>
     */
    public LoadResponse(){
        message = new String();
        numUsers = 0;
        numPersons = 0;
        numEvents = 0;
        success = false;
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String s){
        message = s;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public int getNumEvents() {
        return numEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        LoadResponse secondResponse = (LoadResponse) o;

        if(numEvents != secondResponse.numEvents){
            return false;
        }
        if(numPersons != secondResponse.numPersons){
            return false;
        }
        if (numUsers != secondResponse.numUsers){
            return false;
        }
        if (success != secondResponse.success) {
            return false;
        }
        if (message == null && secondResponse.message != null) {
            return false;
        }
        if (message != null && secondResponse.message == null){
            return false;
        }
        if (message != null && secondResponse.message != null){
            if (!message.equals(secondResponse.message)){
                return false;
            }
        }
        return true;
    }
}
