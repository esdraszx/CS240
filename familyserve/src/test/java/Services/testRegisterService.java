package Services;

import org.junit.*;


import DataAccessObjects.*;

import Model.UserModel;

import Request.RegisterRequest;

import Response.RegisterResponse;

import static org.junit.Assert.*;


public class testRegisterService {

    private RegisterService myRegisterService;

    @Before
    public void setUp() {
        myRegisterService = new RegisterService();
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
        myRegisterService = null;
    }

    @Test
    public void testRegister(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("paul_j");

        RegisterRequest inputRequest = new RegisterRequest();
        inputRequest.setGender(testUser1.getGender());
        inputRequest.setUserName(testUser1.getUserName());
        inputRequest.setFirstName(testUser1.getFirstName());
        inputRequest.setLastName(testUser1.getLastName());
        inputRequest.setEmail(testUser1.getEmail());
        inputRequest.setPassword(testUser1.getPassWord());

        RegisterResponse expectedResponse = new RegisterResponse();
        expectedResponse.setUserName(testUser1.getUserName());
        expectedResponse.setPersonId(testUser1.getPersonID());
        expectedResponse.setSuccess(true);

        RegisterResponse outputResonse = myRegisterService.register(inputRequest);
        expectedResponse.setAuthToken(outputResonse.getAuthToken());
        expectedResponse.setPersonId(outputResonse.getPersonID());

        assertEquals(expectedResponse, outputResonse);

        RegisterResponse badExpectedResonse = new RegisterResponse();
        badExpectedResonse.setSuccess(false);
        badExpectedResonse.setMessage("User already exists failed");

        RegisterResponse badOutputResponse = myRegisterService.register(inputRequest);
        assertEquals(badExpectedResonse, badOutputResponse);
    }
}
