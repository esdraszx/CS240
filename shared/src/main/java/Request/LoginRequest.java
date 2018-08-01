package Request;


/**
 *An Login Request object which represents a login Request in javacode, contains the necessary information to find a user and verify the user's identity
 *<pre>
 *<b>Domain</b>:
 *     username : String
 *     password : String
 *</pre>
 */
public class LoginRequest {

    private String userName;
    private String password;
    private String serverHost;
    private String serverPort;

    public LoginRequest(){

    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getServerHost() {return serverHost;}

    public String getServerPort() {return serverPort;}

    public void setServerHost(String in){
        this.serverHost = in;
    }

    public void setServerPort(String in){
        this.serverPort = in;
    }

    public void setUserName(String input){
        this.userName = input;
    }

    public void setPassword(String input){
        this.password = input;
    }

}
