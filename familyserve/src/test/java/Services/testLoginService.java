package Services;
import org.junit.*;



import DataAccessObjects.*;

import Model.UserModel;

import Request.LoginRequest;

import Response.LoginResponse;

import static org.junit.Assert.*;

public class testLoginService {
    private LoginService myLoginService;

    @Before
    public void setUp() {
        myLoginService = new LoginService();
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
        myLoginService = null;
    }

    @Test
    public void testLogin(){
        UserModel testUser1 = new UserModel();
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("person_Id");
        try{
            Database db = new Database();
            db.openConnection();
            db.resetTables();
            UserDAO myUserDAO = db.getUserDAO();
            myUserDAO.insertUserIntoDatabase(testUser1);
            db.closeConnection(true);
        } catch (Database.DatabaseException e){
            assertEquals("Throwsing exceptions is bad", e.getMessage());
        }

        LoginRequest inputRequest = new LoginRequest();
        inputRequest.setUserName(testUser1.getUserName());
        inputRequest.setPassword(testUser1.getPassWord());

        LoginResponse expectedResponse = new LoginResponse();
        expectedResponse.setSuccess(true);
        expectedResponse.setUserName(testUser1.getUserName());
        expectedResponse.setPersonId(testUser1.getPersonID());

        LoginResponse outputResponse = myLoginService.login(inputRequest);
        expectedResponse.setAuthToken(outputResponse.getAuthToken());

        assertEquals(expectedResponse, outputResponse);

        LoginResponse badExpectedResponse = new LoginResponse();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("no such username and/or password");

        inputRequest.setUserName("Bogus");
        LoginResponse badOutputResponse = myLoginService.login(inputRequest);

        assertEquals(badExpectedResponse, badOutputResponse);
    }

}
