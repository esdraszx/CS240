package Services;
import DataAccessObjects.Database;
import Response.ClearResponse;

/**
 *A Clear Service object which generates a clear response object
 *<pre>
 *
 *</pre>
 */
public class ClearService {

    private Database myDB;

    public ClearService(){
        myDB = new Database();
    }


    public ClearResponse clear(){
        ClearResponse response = new ClearResponse();
        try{
            myDB.openConnection();
            myDB.resetTables();

            myDB.closeConnection(true);

        } catch (Database.DatabaseException e){
            System.out.println(e.getMessage());
            response.setMessage("Internal server error");

            try{
                myDB.closeConnection(false);
            }catch (Database.DatabaseException d){
                response.setMessage(d.getMessage());
                return response;
            }

            return response;
        }
        response.setMessage("Clear succeeded");
        return response;
    }

}
