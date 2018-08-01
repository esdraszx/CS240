package DataAccessObjects;
import java.sql.*;

import Model.UserModel;

/**
 *A Database class which receives a connection from the client and uses this connection for whatever the client pleases, this is done in order to use one connection per client
 *<pre>
 *<b>Domain</b>:
 *     connection : string
 *     myUserDAO : UserDAO
 *     myPersonDAO : PersonDAO
 *     myEventDAO : EventDAO
 *     myAuthTokenDAO : AuthTokenDAO
 *
 *</pre>
 */

public class Database {

    public static class DatabaseException extends Exception {
        private String message;

        public DatabaseException(){
            message = new String();
        }
        public DatabaseException(String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }

    private UserDAO myUserDAO;
    private PersonDAO myPersonDAO;
    private EventDAO myEventDAO;
    private AuthTokenDAO myAuthTokenDAO;
    private Connection conn;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Database(){
        myUserDAO = new UserDAO();
        myPersonDAO = new PersonDAO();
        myEventDAO = new EventDAO();
        myAuthTokenDAO = new AuthTokenDAO();

    }

    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:sql/fmsDatabase.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            //Sets connections for DAOs
            myUserDAO.setConnection(conn);
            myEventDAO.setConnection(conn);
            myPersonDAO.setConnection(conn);
            myAuthTokenDAO.setConnection(conn);


            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new DatabaseException("Opening connection failed");
        }
    }

    public void resetTables() throws DatabaseException {
        myUserDAO.resetTable();
        myPersonDAO.resetTable();
        myEventDAO.resetTable();
        myAuthTokenDAO.resetTable();
    }

    public void deleteEverythingOfUser(UserModel u) throws DatabaseException { //does not make new usermodel of username given
        myUserDAO.deleteUser(u);
        myPersonDAO.deleteAllPeopleOfUser(u);
        myEventDAO.deleteAllEventsOfUser(u);

    }

    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed");
        }
    }

    public UserDAO getUserDAO() {
        return myUserDAO;
    }

    public PersonDAO getPersonDAO(){
        return myPersonDAO;
    }

    public EventDAO getEventDAO(){
        return myEventDAO;
    }

    public AuthTokenDAO getAuthTokenDAO(){
        return myAuthTokenDAO;
    }

}
