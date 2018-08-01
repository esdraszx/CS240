package Services;
import org.junit.*;
import DataAccessObjects.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.UserModel;

import Response.EventIDResponse;

import static org.junit.Assert.*;


public class testEventIDService {
    private EventIDService myEventIDService;

    @Before
    public void setUp() {
        myEventIDService = new EventIDService();
        Database db = new Database();
        try {
            db.openConnection();
            db.resetTables();
            db.closeConnection(true);
            db = null;
        } catch (Database.DatabaseException e){
            assertEquals("throwing excpetions is bad", e.getMessage());
        }
    }

    @After
    public void tearDown() {
        myEventIDService = null;
    }


    @Test
    public void testEventID(){
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

            EventModel testEvent3 = new EventModel();
            testEvent3.setEventID("uniqueEventID3");
            testEvent3.setDescendant("pjohnst5");
            testEvent3.setPersonID("paul_j");
            testEvent3.setLatitude(55.0);
            testEvent3.setLongitude(-55.0);
            testEvent3.setCountry("Brazil");
            testEvent3.setCity("Brasilia");
            testEvent3.setType("mission lunch");
            testEvent3.setYear(2014);

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
            myEventDAO.insertEventIntoDatabase(testEvent1);
            myEventDAO.insertEventIntoDatabase(testEvent2);
            myEventDAO.insertEventIntoDatabase(testEvent3);
            myAuthDAO.insertTokenIntoTable(testAuth1);
            myAuthDAO.insertTokenIntoTable(testAuth2);
            db.closeConnection(true);

            EventIDResponse expectedResponse = new EventIDResponse(testEvent1);
            expectedResponse.setSuccess(true);

            EventIDResponse outputResponse = myEventIDService.eventID(testEvent1.getEventID(), testAuth1.getAuthToken());

            assertEquals(expectedResponse, outputResponse);

            EventIDResponse badExpectedResponse = new EventIDResponse();
            badExpectedResponse.setSuccess(false);
            badExpectedResponse.setMessage("Descendant of event and username of auth token do not match");

            EventIDResponse badOutputResponse = myEventIDService.eventID(testEvent1.getEventID(), testAuth2.getAuthToken());

            assertEquals(badExpectedResponse, badOutputResponse);

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

}

