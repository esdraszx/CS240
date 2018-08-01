package Services;

import org.junit.*;


import DataAccessObjects.*;
import Model.AuthTokenModel;

import Model.PersonModel;
import Model.UserModel;

import Response.PersonIDResponse;

import static org.junit.Assert.*;

public class testPersonIDService {

    private PersonIDService myPersonIDService;

    @Before
    public void setUp() {
        myPersonIDService = new PersonIDService();
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
        myPersonIDService = null;
    }

    @Test
    public void testPersonID(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("paul_j");

        PersonModel testPerson1 = new PersonModel(testUser1);
        testPerson1.setSpouse("Druzinha");
        testPerson1.setMother("Nelly");
        testPerson1.setFather("Jeffrey");

        UserModel testUser2 = new UserModel(); //person 2
        testUser2.setUserName("carla");
        testUser2.setPassWord("rhinos");
        testUser2.setEmail("carlsbad@gmail.com");
        testUser2.setFirstName("carla");
        testUser2.setLastName("gardner");
        testUser2.setGender("f");
        testUser2.setPersonID("carla_j");

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

        try{
            Database db = new Database();
            db.openConnection();
            db.resetTables();
            PersonDAO myPersonDAO = db.getPersonDAO();
            myPersonDAO.insertPersonIntoDatabase(testPerson1);
            myPersonDAO.insertPersonIntoDatabase(testPerson2);
            AuthTokenDAO myAuthDAO = db.getAuthTokenDAO();
            myAuthDAO.insertTokenIntoTable(testAuth1);
            myAuthDAO.insertTokenIntoTable(testAuth2);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("Throwsing exceptions is bad", e.getMessage());
        }

        PersonIDResponse expectedResponse = new PersonIDResponse(testPerson1);
        expectedResponse.setSuccess(true);

        PersonIDResponse outputResponse = myPersonIDService.personID(testPerson1.getPersonID(), testAuth1.getAuthToken());

        assertEquals(expectedResponse, outputResponse);

        PersonIDResponse badExpectedResponse = new PersonIDResponse();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("PersonID does not match given authToken");

        PersonIDResponse badOutputResponse = myPersonIDService.personID(testPerson1.getPersonID(), testAuth2.getAuthToken());

        assertEquals(badExpectedResponse, badOutputResponse);
    }
}
