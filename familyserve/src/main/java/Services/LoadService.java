package Services;
import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import DataAccessObjects.PersonDAO;
import DataAccessObjects.UserDAO;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Response.LoadResponse;
import Request.LoadRequest;

/**
 *A Load Service object which generates an LoadResponse object
 *<pre>
 *
 *</pre>
 */
public class LoadService {

    private Database myDB;

    public LoadService(){
        myDB = new Database();
    }


    public LoadResponse load(LoadRequest r){
        LoadResponse response = new LoadResponse();
        try{
            myDB.openConnection();
            myDB.resetTables();

            UserDAO myUserDAO = myDB.getUserDAO();
            PersonDAO myPersonDAO = myDB.getPersonDAO();
            EventDAO myEventDAO = myDB.getEventDAO();

            UserModel[] users = r.getUsers();
            PersonModel[] persons = r.getPersons();
            EventModel[] events = r.getEvents();

            for (int i = 0; i < users.length; i++){
                myUserDAO.insertUserIntoDatabase(users[i]);
            }

            for (int i = 0; i < persons.length; i++){
                myPersonDAO.insertPersonIntoDatabase(persons[i]);
            }

            for (int i = 0; i < events.length; i++){
                myEventDAO.insertEventIntoDatabase(events[i]);
            }

            myDB.closeConnection(true);
            response.setSuccess(true);
            response.setNumUsers(users.length);
            response.setNumEvents(events.length);
            response.setNumPersons(persons.length);

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
