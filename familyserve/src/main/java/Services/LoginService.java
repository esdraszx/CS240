package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.UserDAO;
import Model.AuthTokenModel;
import Model.UserModel;
import Request.LoginRequest;
import Response.LoginResponse;

/**
 *A Login Service object which generates an LoginResponse object
 *<pre>
 *
 *</pre>
 */
public class LoginService {

    private Database myDB;

    public LoginService(){
        myDB = new Database();
    }


    public LoginResponse login(LoginRequest r){
        LoginResponse loginResponse = new LoginResponse();
        UserDAO myUserDAO = myDB.getUserDAO();
        AuthTokenDAO myAuthDAO = myDB.getAuthTokenDAO();

        try{
            myDB.openConnection();
            UserModel user = new UserModel(r);

            if (myUserDAO.doUsernameAndPasswordExist(user)) { //yes, the username and password are valid
                AuthTokenModel returnAuth = new AuthTokenModel(user);
                returnAuth.setPersonID(myUserDAO.getPersonIDOfUser(user)); // will fill in the personID of the user

                myAuthDAO.insertTokenIntoTable(returnAuth);

                loginResponse = new LoginResponse(returnAuth);
                loginResponse.setSuccess(true);

                myDB.closeConnection(true);
            }

        } catch (Database.DatabaseException d){
            loginResponse.setMessage(d.getMessage());
            loginResponse.setSuccess(false);
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException e){
                loginResponse.setSuccess(false);
                loginResponse.setMessage(e.getMessage());
            }
        }
        return loginResponse;
    }

}
