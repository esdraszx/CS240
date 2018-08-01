package Model;

import java.util.UUID;

/**
 *An AuthToken model object which represents an AuthToken in javacode, contains the information necessary to fully represent an auth token in the database
 *<pre>
 *<b>Domain</b>:
 *     authToken : string
 *     personID : string
 *     userName : string
 *</pre>
 */
public class AuthTokenModel {

    private String authToken;
    private String personID;
    private String userName;


    public AuthTokenModel(){
       authToken = new String();
        personID = new String();
        userName = new String();
    }

    public AuthTokenModel(UserModel u){  //Be weary, generates a unique AuthToken for the user
        authToken = UUID.randomUUID().toString();
        personID = u.getPersonID();
        userName = u.getUserName();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUserName (){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
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

        AuthTokenModel secondAuth = (AuthTokenModel) o;

        if (!authToken.equals(secondAuth.authToken)) {
            return false;
        }
        if (!personID.equals(secondAuth.personID)) {
            return false;
        }
        if (!userName.equals(secondAuth.userName)) {
            return false;
        }
        return true;
    }


}
