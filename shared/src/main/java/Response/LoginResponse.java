package Response;
import Model.AuthTokenModel;

/**
 *A Login Response object which represents a Login Repsonse in javacode, contains the authtoken user name and unique person Id of the user who logged in
 *<pre>
 *<b>Domain</b>:
 *     success : boolean
 *     message : String
 *     authToken : String
 *     userName : String
 *     personID : String
 *</pre>
 */
public class LoginResponse {

    private String authToken;
    private String userName;
    private String personId;
    private transient boolean success;
    private transient String message;


    public LoginResponse(){
        authToken = new String();
        userName = new String();
        personId = new String();
        success = false;
        message = new String();
    }


    public LoginResponse(AuthTokenModel a){
        this.authToken = a.getAuthToken();
        this.userName = a.getUserName();
        this.personId = a.getPersonID();
        success = true;
        message = new String();
    }

    public void setSuccess(boolean b){
        success = b;
    }

    public void setMessage(String s){
        message = s;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public void setAuthToken(String input){
        this.authToken = input;
    }
    public void setUserName(String input){
        this.userName = input;
    }
    public void setPersonId(String input){
        this.personId = input;
    }

    public String getAuthToken(){
        return authToken;
    }

    public String getPersonId(){return personId;}

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

        LoginResponse secondResponse = (LoginResponse) o;

        if(!userName.equals(secondResponse.userName)){
            return false;
        }
        if(!authToken.equals(secondResponse.authToken)){
            return false;
        }
        if(!personId.equals(secondResponse.personId)){
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
