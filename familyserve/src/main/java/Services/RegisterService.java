package Services;

import DataAccessObjects.*;
import Model.EventModel;
import Model.UserModel;
import Model.PersonModel;
import Model.AuthTokenModel;
import Response.RegisterResponse;
import Request.RegisterRequest;



public class RegisterService {

    private Database myDB;


    public RegisterService(){
        myDB = new Database();
    }


    public RegisterResponse register(RegisterRequest r){
        RegisterResponse sponse = new RegisterResponse();

        try{
            myDB.openConnection();


            UserDAO myUserDAO = myDB.getUserDAO();
            PersonDAO myPersonDAO = myDB.getPersonDAO();
            EventDAO myEventDAO = myDB.getEventDAO();
            AuthTokenDAO myAuthDAO = myDB.getAuthTokenDAO();



            UserModel u = new UserModel(r);
            myUserDAO.insertUserIntoDatabase(u);

            PersonModel root = new PersonModel(u);

            myPersonDAO.insertPersonIntoDatabase(root); //inserts root into database

            int rootBirthYear = myEventDAO.makeRootsEvents(root); //make root's events

            //Now were going to give generations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made
            myPersonDAO.generateGenerations(root, 4, myEventDAO, rootBirthYear);


            //Auth token stuff
            AuthTokenModel returnAuth = new AuthTokenModel(u);
            myAuthDAO.insertTokenIntoTable(returnAuth);
            sponse = new RegisterResponse(returnAuth);
            sponse.setSuccess(true);


            myDB.closeConnection(true);

        } catch (Database.DatabaseException e){
            sponse.setSuccess(false);
            sponse.setMessage(e.getMessage());

            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                sponse.setSuccess(false);
                sponse.setMessage(d.getMessage());
            }
        }
        return sponse;
    }
}
