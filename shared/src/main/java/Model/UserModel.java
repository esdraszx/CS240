package Model;
import java.util.UUID;

import Request.LoginRequest;
import Request.RegisterRequest;

/**
 *An Person model object which represents a person in javacode, contains the information necessar to completely represent a person in the database
 *<pre>
 *<b>Domain</b>:
 *     userName : string
 *     passWord : string
 *     email : string
 *     firstName : string
 *     lastName : string
 *     gender : string
 *     personID : string
 *</pre>
 */
public class UserModel {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;


    public UserModel(){

    }

    public UserModel(RegisterRequest r){
        this.userName = r.getUserName();
        this.password = r.getPassword();
        this.email = r.getEmail();
        this.firstName = r.getFirstName();
        this.lastName = r.getLastName();
        this.gender = r.getGender();
        String personID = UUID.randomUUID().toString();  //creates personID
        this.personID = personID;
    }

    public UserModel(LoginRequest l){
        this.userName = l.getUserName();
        this.password = l.getPassword();
        email = new String();
        firstName = new String();
        lastName = new String();
        gender = new String();
        personID = new String();
    }


    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
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

    public String getPersonID() {
        return personID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
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

    public void setPersonID(String personID) {
        this.personID = personID;
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

        UserModel secondUser = (UserModel) o;

        if (!userName.equals(secondUser.userName)) {
            return false;
        }
        if (!password.equals(secondUser.password)) {
            return false;
        }
        if (email != null && !email.equals(secondUser.email)) {
            return false;
        }
        if (firstName != null && !firstName.equals(secondUser.firstName)) {
            return false;
        }
        if (lastName != null && !lastName.equals(secondUser.lastName)) {
            return false;
        }
        if (gender != null && !gender.equals(secondUser.gender)) {
            return false;
        }
        if (personID!= null && !personID.equals(secondUser.personID)) {
            return false;
        }
        return true;
    }

}
