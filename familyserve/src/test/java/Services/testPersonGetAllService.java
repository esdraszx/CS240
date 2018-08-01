package Services;

import org.junit.*;


import DataAccessObjects.*;
import Model.AuthTokenModel;

import Model.PersonModel;
import Model.UserModel;

import Response.PersonGetAllResponse;

import static org.junit.Assert.*;


public class testPersonGetAllService {

    private PersonGetAllService myPersonGetAllService;

    @Before
    public void setUp() {
        myPersonGetAllService = new PersonGetAllService();
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
        myPersonGetAllService = null;
    }

    @Test
    public void testPersonGetAll(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("person_Id");

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
        testUser2.setPersonID("blahblahblah");

        PersonModel testPerson2 = new PersonModel(testUser2);
        testPerson2.setSpouse("Daniel");
        testPerson2.setFather("Jeffrey");
        testPerson2.setMother("Nelly");

        PersonModel testPerson3 = new PersonModel(); //person 3
        testPerson3.setDescendant("pjohnst5");
        testPerson3.setPersonID("Jeffrey");
        testPerson3.setFather("jorge");
        testPerson3.setSpouse("nancy");
        testPerson3.setMother("jane");
        testPerson3.setFirstName("Jeff");
        testPerson3.setLastName("johnston");
        testPerson3.setGender("m");

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
            UserDAO myUserDAO = db.getUserDAO();
            myUserDAO.insertUserIntoDatabase(testUser1);
            myUserDAO.insertUserIntoDatabase(testUser2);
            PersonDAO myPersonDAO = db.getPersonDAO();
            myPersonDAO.insertPersonIntoDatabase(testPerson1);
            myPersonDAO.insertPersonIntoDatabase(testPerson2);
            myPersonDAO.insertPersonIntoDatabase(testPerson3);
            AuthTokenDAO myAuthDAO = db.getAuthTokenDAO();
            myAuthDAO.insertTokenIntoTable(testAuth1);
            myAuthDAO.insertTokenIntoTable(testAuth2);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("Throwsing exceptions is bad", e.getMessage());
        }
        PersonModel[] inputPersons = new PersonModel[2];
        inputPersons[0] = testPerson1;
        inputPersons[1] = testPerson3;

        PersonGetAllResponse expectedResponse = new PersonGetAllResponse();
        expectedResponse.setArray(inputPersons);
        expectedResponse.setSuccess(true);

        PersonGetAllResponse outputResponse = myPersonGetAllService.personGetAll(testAuth1.getAuthToken());

        assertEquals(expectedResponse, outputResponse);

        PersonGetAllResponse badExpectedResponse = new PersonGetAllResponse();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("no such authToken");

        PersonGetAllResponse badOutputResponse = myPersonGetAllService.personGetAll("bogus");

        assertEquals(badExpectedResponse, badOutputResponse);
    }

}
