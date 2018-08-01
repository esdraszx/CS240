package Model;

import org.junit.*;

import Request.LoginRequest;
import Request.RegisterRequest;

import static org.junit.Assert.*;


public class testUserModel {

    private UserModel myUserModel;

    @Before
    public void setUp() {
        myUserModel = new UserModel();

    }

    @After
    public void tearDown() {
        myUserModel = null;
    }

    @Test
    public void testUserModelRegisterRequestConstructor(){
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

        myUserModel = new UserModel(inputRequest);
        testUser1.setPersonID(myUserModel.getPersonID());
        assertEquals(testUser1, myUserModel);
    }

    @Test
    public void testUserModelLoginRequestConstructor(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");

        LoginRequest inputRequest = new LoginRequest();
        inputRequest.setUserName(testUser1.getUserName());
        inputRequest.setPassword(testUser1.getPassWord());

        myUserModel = new UserModel(inputRequest);

        assertEquals(testUser1, myUserModel);
    }

    @Test
    public void testEquals(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("paul_j");

        UserModel testUser2 = new UserModel(); //person 2
        testUser2.setUserName("pjohnst5");
        testUser2.setPassWord("puffinz");
        testUser2.setEmail("paulthegreat01@gmail.com");
        testUser2.setFirstName("paul");
        testUser2.setLastName("johnston");
        testUser2.setGender("m");
        testUser2.setPersonID("paul_j");

        boolean output = testUser1.equals(testUser2);
        assertTrue(output);

        testUser2.setUserName("pumpkins");
        output = testUser1.equals(testUser2);
        assertFalse(output);
    }
}
