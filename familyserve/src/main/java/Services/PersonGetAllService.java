package Services;
import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.Database;
import DataAccessObjects.PersonDAO;
import Model.AuthTokenModel;
import Model.PersonModel;
import Response.PersonGetAllResponse;



public class PersonGetAllService {

    private Database myDB;

    public PersonGetAllService(){
        myDB = new Database();
    }


    public PersonGetAllResponse personGetAll(String authToken){
        PersonGetAllResponse myPersonGetALLResponse = new PersonGetAllResponse();

        try{
            myDB.openConnection();
            PersonDAO myPersonDAO = myDB.getPersonDAO();
            AuthTokenDAO myAuthDAO = myDB.getAuthTokenDAO();


            if(myAuthDAO.doesAuthTokenExist(authToken)){
                AuthTokenModel auth = myAuthDAO.getAuthTokenModel(authToken);
                myPersonGetALLResponse.setArray(myPersonDAO.selectAllPersons(auth.getUserName()));
            }

            myDB.closeConnection(true);
            myPersonGetALLResponse.setSuccess(true);

        } catch (Database.DatabaseException e){
            myPersonGetALLResponse.setSuccess(false);
            myPersonGetALLResponse.setMessage(e.getMessage());
            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                myPersonGetALLResponse.setSuccess(false);
                myPersonGetALLResponse.setMessage(d.getMessage());
            }
        }

        return myPersonGetALLResponse;
    }


}
