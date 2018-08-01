package Request;

/**
 *An Register Request object which represents a register Request in javacode, contains the necessary information for a new user to be put into the table of users of the database
 *<pre>
 *<b>Domain</b>:
 *     username : String
 *     password : String
 *     email : String
 *     firstName : String
 *     lastName : String
 *     gender : String
 *</pre>
 */
public class RegisterRequest {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String serverHost;
    private String serverPort;

    //Hopefully I don't need setters because I can just convert a JSONregister request body into this java object no problemo
    public RegisterRequest(){

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getServerHost() {return serverHost;}

    public String getServerPort() {return serverPort;}

    public void setServerHost(String in){
        this.serverHost = in;
    }

    public void setServerPort(String in){
        this.serverPort = in;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }




}
