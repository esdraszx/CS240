package DataAccessObjects;
import org.junit.* ;

import java.util.UUID;

import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;

import static org.junit.Assert.* ;

public class DatabaseTest {

    private Database db;

    @Before
    public void setUp() throws Database.DatabaseException {
        db = new Database();
        db.openConnection();
        db.resetTables();
    }

    @After
    public void tearDown() throws Database.DatabaseException {
        db.closeConnection(false);
        db = null;
    }

    @Test
    public void testNotThrowingExceptionWhenOpeningAndClosingConnection(){
        try{
            db.closeConnection(false);
            db.openConnection();
            assertEquals("hello", "hello");
        } catch (Database.DatabaseException e){
            assertEquals("Throwing exception" , e.getMessage());
        }
    }

    @Test
    public void testResetTables(){
        try{

            UserDAO myUserDAO = db.getUserDAO();
            PersonDAO myPersonDAO = db.getPersonDAO();
            AuthTokenDAO myAuthTokenDAO = db.getAuthTokenDAO();
            EventDAO myEventDAO = db.getEventDAO();

            UserModel testUser2 = new UserModel(); //user
            testUser2.setUserName("carla");
            testUser2.setPassWord("rhinos");
            testUser2.setEmail("carlsbad@gmail.com");
            testUser2.setFirstName("carla");
            testUser2.setLastName("gardner");
            testUser2.setGender("f");
            testUser2.setPersonID(UUID.randomUUID().toString());
            myUserDAO.insertUserIntoDatabase(testUser2); //inserts user into table

            PersonModel testPerson = new PersonModel(testUser2); //person
            myPersonDAO.insertPersonIntoDatabase(testPerson); //inserts person into table

            EventModel testEvent = new EventModel(); //event
            testEvent.setEventID(UUID.randomUUID().toString());
            testEvent.setDescendant(testPerson.getDescendant());
            testEvent.setPersonID(testPerson.getPersonID());
            testEvent.setLatitude(1.0);
            testEvent.setLongitude(-1.0);
            testEvent.setCountry("north pole");
            testEvent.setCity("Santa's house");
            testEvent.setType("Cookies at santa's");
            testEvent.setYear(2017);

            myEventDAO.insertEventIntoDatabase(testEvent); //inserts event into table

            AuthTokenModel auth = new AuthTokenModel(); //auth
            auth.setAuthToken(UUID.randomUUID().toString());
            auth.setPersonID(testPerson.getPersonID());
            auth.setUserName(testPerson.getDescendant());

            myAuthTokenDAO.insertTokenIntoTable(auth);  //inserts auth token into table

            String answer = "";
            assertNotEquals(answer, myUserDAO.tableToString());  //assert that the tables aren't empty
            assertNotEquals(answer, myPersonDAO.tableToString());
            assertNotEquals(answer, myEventDAO.tableToString());
            assertNotEquals(answer, myAuthTokenDAO.tableToString());

            db.resetTables(); //reset tables

            assertEquals(answer, myUserDAO.tableToString()); //asserts that the tables are empty
            assertEquals(answer,myPersonDAO.tableToString());
            assertEquals(answer, myAuthTokenDAO.tableToString());
            assertEquals(answer,myEventDAO.tableToString());


        } catch(Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDeleteEverythingOfUser(){

        try{
            UserDAO myUserDAO = db.getUserDAO();
            PersonDAO myPersonDAO = db.getPersonDAO();
            EventDAO myEventDAO = db.getEventDAO();

            UserModel testUser1 = new UserModel(); //user 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID(UUID.randomUUID().toString());
            myUserDAO.insertUserIntoDatabase(testUser1); //inserts user1 into table

            PersonModel testPerson1 = new PersonModel(testUser1); //person1
            myPersonDAO.insertPersonIntoDatabase(testPerson1); //inserts person1 into table

            EventModel testEvent1 = new EventModel(); //event1
            testEvent1.setEventID(UUID.randomUUID().toString());
            testEvent1.setDescendant(testPerson1.getDescendant());
            testEvent1.setPersonID(testPerson1.getPersonID());
            testEvent1.setLatitude(99.00);
            testEvent1.setLongitude(-99.00);
            testEvent1.setCountry("Equador");
            testEvent1.setCity("Quito");
            testEvent1.setType("Eatin lunch");
            testEvent1.setYear(2000);
            myEventDAO.insertEventIntoDatabase(testEvent1); //inserts event1 into table (of User1)


            UserModel testUser2 = new UserModel(); //user2
            testUser2.setUserName("carla");
            testUser2.setPassWord("rhinos");
            testUser2.setEmail("carlsbad@gmail.com");
            testUser2.setFirstName("carla");
            testUser2.setLastName("gardner");
            testUser2.setGender("f");
            testUser2.setPersonID("blahblahblah");
            myUserDAO.insertUserIntoDatabase(testUser2); //inserts user2 into table

            PersonModel testPerson2 = new PersonModel(testUser2); //person2
            myPersonDAO.insertPersonIntoDatabase(testPerson2); //inserts person2 into table (of User 2)

            EventModel testEvent2 = new EventModel(); //event2
            testEvent2.setEventID("eventID2");
            testEvent2.setDescendant(testPerson2.getDescendant());
            testEvent2.setPersonID(testPerson2.getPersonID());
            testEvent2.setLatitude(1.0);
            testEvent2.setLongitude(-1.0);
            testEvent2.setCountry("north pole");
            testEvent2.setCity("Santa's house");
            testEvent2.setType("Cookies at santa's");
            testEvent2.setYear(2017);
            myEventDAO.insertEventIntoDatabase(testEvent2); //inserts event2 into table (of User2)

            //Now deleteing everything of User1
            db.deleteEverythingOfUser(testUser1);
            //should now only be User 2 things
            String userTable = "carla\trhinos\tcarlsbad@gmail.com\tcarla\tgardner\tf\tblahblahblah\n";
            String personTable = "blahblahblah\tcarla\tcarla\tgardner\tf\t\t\t\n";
            String eventTable = "eventID2\tcarla\tblahblahblah\t1.0\t-1.0\tnorth pole\tSanta's house\tCookies at santa's\t2017\n";

            assertEquals(userTable, myUserDAO.tableToString());
            assertEquals(personTable, myPersonDAO.tableToString());
            assertEquals(eventTable, myEventDAO.tableToString());

        }catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }

    }


}
