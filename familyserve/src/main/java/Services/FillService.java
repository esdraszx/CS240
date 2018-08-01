package Services;
import java.util.UUID;

import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import DataAccessObjects.PersonDAO;
import Model.PersonModel;
import Model.UserModel;
import Response.FillResponse;
import DataAccessObjects.UserDAO;


public class FillService {

    private Database myDB;

    public FillService(){
        myDB = new Database();
    }

    public FillResponse fill(String userName, int numGenerations){
        FillResponse response = new FillResponse();

        try{
            myDB.openConnection();

            UserDAO myUserDAO = myDB.getUserDAO();
            EventDAO myEventDAO = myDB.getEventDAO();
            PersonDAO myPersonDAO = myDB.getPersonDAO();

            if (!myUserDAO.doesUserNameExist(userName)){
                throw new Database.DatabaseException("username does not exist");
            }

            UserModel user = myUserDAO.getUserModel(userName); //user does not have the same personID as the userName user
            user.setPersonID(UUID.randomUUID().toString());


            myDB.deleteEverythingOfUser(user); //deletes all things of user, including the user

            myUserDAO.insertUserIntoDatabase(user); //inserts same user but with new personID into the database

            PersonModel root = new PersonModel(user);  //creates a person representation of the user

            myPersonDAO.insertPersonIntoDatabase(root); //inserts root into database

            int rootBirthYear = myEventDAO.makeRootsEvents(root); //make root's events

            //Now were going to give generateGenerations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made

            if (numGenerations == -1){//default case
                myPersonDAO.generateGenerations(root, 4, myEventDAO, rootBirthYear); //default is four generations
                response.setNumPersons(31);
                response.setNumEvents(124);
                response.setSuccess(true);
            } else {
                myPersonDAO.generateGenerations(root, numGenerations, myEventDAO, rootBirthYear);
                double numG = (double) numGenerations;
                double answer = (Math.pow(2.0, (numG + 1.0)) - 1.0);
                int finalAnswer = (int) answer;
                response.setNumPersons(finalAnswer);
                response.setNumEvents(finalAnswer * 4);
                response.setSuccess(true);
            }

            myDB.closeConnection(true);

        } catch (Database.DatabaseException e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());

            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                response.setSuccess(false);
                response.setMessage(d.getMessage());
            }
        }
        return response;
    }
}
