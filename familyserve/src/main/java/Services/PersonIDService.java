package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.PersonDAO;
import Model.AuthTokenModel;
import Model.PersonModel;
import Response.PersonIDResponse;


public class PersonIDService {


    private Database myDB;

    public PersonIDService(){
        myDB = new Database();
    }


    public PersonIDResponse personID(String personId, String authToken){
        PersonIDResponse myResponse = new PersonIDResponse();

        try{
            myDB.openConnection();
            PersonDAO myPersonDAO = myDB.getPersonDAO();
            AuthTokenDAO myAuthDAO = myDB.getAuthTokenDAO();


            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                if (!personId.equals(auth.getPersonID())){
                    throw new Database.DatabaseException("PersonID does not match given authToken");
                }
                if (myPersonDAO.doesPersonExist(personId)){
                    PersonModel out = myPersonDAO.selectSinglePerson(personId);
                    myResponse = new PersonIDResponse(out);
                }

            }

            myResponse.setSuccess(true);
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
