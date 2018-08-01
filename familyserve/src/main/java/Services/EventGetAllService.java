package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import Model.AuthTokenModel;
import Response.EventGetAllResponse;


public class EventGetAllService {

    private Database myDB;

    public EventGetAllService(){
        myDB = new Database();
    }


    public EventGetAllResponse eventGetAll(String authToken){
        EventGetAllResponse myResponse = new EventGetAllResponse();
        try{
            myDB.openConnection();
            EventDAO myEventDAO = myDB.getEventDAO();
            AuthTokenDAO myAuthDAO = myDB.getAuthTokenDAO();


            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                myResponse.setData(myEventDAO.selectAllEvents(auth.getUserName()));
                myDB.closeConnection(true);
                myResponse.setSuccess(true);
            } else {
                myResponse.setSuccess(false);
                myDB.closeConnection(false);
            }



        } catch (Database.DatabaseException e){
            myResponse.setSuccess(false);
            myResponse.setMessage(e.getMessage());
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                myResponse.setSuccess(false);
                myResponse.setMessage(d.getMessage());
            }
        }

        return myResponse;
    }
}
