package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.EventDAO;
import Model.AuthTokenModel;
import Model.EventModel;
import Response.EventIDResponse;

/**
 *A EventID Service object which generates an EventIDRespone object
 *<pre>
 *
 *</pre>
 */
public class EventIDService {

    private Database myDB;

    public EventIDService(){
        myDB = new Database();
    }


    public EventIDResponse eventID(String eventId, String authToken){
        EventIDResponse myResponse = new EventIDResponse();

        try{
            myDB.openConnection();
            EventDAO myEventDAO = myDB.getEventDAO();
            AuthTokenDAO myAuthDAO = myDB.getAuthTokenDAO();


            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                if (myEventDAO.doesEventExist(eventId)){
                    EventModel event = myEventDAO.selectSingleEvent(eventId);
                    if (!event.getDescendant().equals(auth.getUserName())){
                        throw new Database.DatabaseException("Descendant of event and username of auth token do not match");
                    }

                    myResponse = new EventIDResponse(event);
                }
            }

            myDB.closeConnection(true);
            myResponse.setSuccess(true);

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
