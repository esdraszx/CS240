package Services;

import org.junit.*;
import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Response.ClearResponse;
import static org.junit.Assert.*;

public class testClearService {
    private ClearService myClearService;

    @Before
    public void setUp() throws Database.DatabaseException {
        myClearService = new ClearService();
    }

    @After
    public void tearDown() throws Database.DatabaseException {
        myClearService = null;
    }

    @Test
    public void testClearService(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            UserModel testUser2 = new UserModel(); //user 2
            testUser2.setUserName("carla");
            testUser2.setPassWord("rhinos");
            testUser2.setEmail("carlsbad@gmail.com");
            testUser2.setFirstName("carla");
            testUser2.setLastName("gardner");
            testUser2.setGender("f");
            testUser2.setPersonID("blahblahblah");

            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            EventModel testEvent2 = new EventModel();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setDescendant("carlsbad");
            testEvent2.setPersonID("carla_j");
            testEvent2.setLatitude(99.0);
            testEvent2.setLongitude(-99.0);
            testEvent2.setCountry("Equador");
            testEvent2.setCity("Quito");
            testEvent2.setType("eatin lunch");
            testEvent2.setYear(1990);

            PersonModel testPerson1 = new PersonModel(testUser1);
            testPerson1.setSpouse("Druzinha");
            testPerson1.setMother("Nelly");
            testPerson1.setFather("Jeffrey");

            PersonModel testPerson2 = new PersonModel(testUser2);
            testPerson2.setSpouse("Daniel");
            testPerson2.setFather("Jeffrey");
            testPerson2.setMother("Nelly");

            AuthTokenModel testAuth1 = new AuthTokenModel();
            testAuth1.setAuthToken("authyauth");
            testAuth1.setPersonID("paul_j");
            testAuth1.setUserName("pjohnst5");;

            AuthTokenModel testAuth2 = new AuthTokenModel();
            testAuth2.setAuthToken("bauth");
            testAuth2.setPersonID("carla_j");
            testAuth2.setUserName("carlsbad");
            Database db = new Database();
            db.openConnection();
            db.resetTables();
            UserDAO myUserDAO = db.getUserDAO();
            PersonDAO myPersonDAO = db.getPersonDAO();
            EventDAO myEventDAO = db.getEventDAO();
            AuthTokenDAO myAuthDAO = db.getAuthTokenDAO();

            myUserDAO.insertUserIntoDatabase(testUser1);
            myUserDAO.insertUserIntoDatabase(testUser2);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);
            myPersonDAO.insertPersonIntoDatabase(testPerson2);
            myEventDAO.insertEventIntoDatabase(testEvent1);
            myEventDAO.insertEventIntoDatabase(testEvent2);
            myAuthDAO.insertTokenIntoTable(testAuth1);
            myAuthDAO.insertTokenIntoTable(testAuth2);
            db.closeConnection(true);

            String expected = "";

            ClearResponse expectedResponse = new ClearResponse();
            expectedResponse.setMessage("Clear succeeded");

            ClearResponse output = myClearService.clear();

            assertEquals(expectedResponse.getMessage(), output.getMessage());

            Database db2 = new Database();
            db.openConnection();
            db.resetTables();
            UserDAO myUserDAO2 = db.getUserDAO();
            PersonDAO myPersonDAO2 = db.getPersonDAO();
            EventDAO myEventDAO2 = db.getEventDAO();
            AuthTokenDAO myAuthDAO2 = db.getAuthTokenDAO();

            assertEquals(expected, myUserDAO2.tableToString() + myAuthDAO2.tableToString() + myEventDAO2.tableToString() + myPersonDAO2.tableToString());

            db.closeConnection(true);


        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }





    }
}


       /*

try{

            } catch (Database.DatabaseException e){
                assertEquals("Throwing exceptions is bad", e.getMessage());
            }
 */