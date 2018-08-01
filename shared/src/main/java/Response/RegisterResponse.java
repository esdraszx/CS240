package Response;


import Model.AuthTokenModel;

/**
 *A Register Response object which represents a Register Repsonse in javacode, contains the authtoken, user name and personID of the user who is logining in
 *<pre>
 *<b>Domain</b>:
 *     success : boolean
 *     message : String
 *     authToken : String
 *     userName : String
 *     personID : String
 *</pre>
 */
public class RegisterResponse {

    private String authToken;
    private String userName;
    private String personId;
    private transient boolean success;
    private transient String message;

    /**
     *The default constructor for Register Response
     *<pre>
     *<b>Constraints on the input</b>:
     *  none
     *</pre>
     */
    public RegisterResponse(){
        success = false;
        authToken = new String();
        userName = new String();
        personId = new String();
    }

    public RegisterResponse(AuthTokenModel a){
        authToken = a.getAuthToken();
        userName = a.getUserName();
        personId = a.getPersonID();
    }

    public void setAuthToken(String intput){
        this.authToken = intput;
    }

    public void setUserName(String input){
        this.userName = input;
    }

    public void setPersonId(String input){
        this.personId = input;
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

    public String getAuthToken(){
        return authToken;
    }

    public String getPersonID(){
        return personId;
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

        RegisterResponse secondResponse = (RegisterResponse) o;

        if(!authToken.equals(secondResponse.authToken)){
            return false;
        }
        if (!userName.equals(secondResponse.userName)){
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
