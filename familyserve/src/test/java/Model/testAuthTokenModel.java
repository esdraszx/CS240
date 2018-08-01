package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class testAuthTokenModel {
    private AuthTokenModel myAuth;

    @Before
    public void setUp() {
        myAuth = new AuthTokenModel();
    }

    @After
    public void tearDown() {
        myAuth = null;
    }

    @Test
    public void testAuthTokenFromUserModelConstructor(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("paul_j");

        AuthTokenModel expected = new AuthTokenModel();
        expected.setUserName(testUser1.getUserName());
        expected.setPersonID(testUser1.getPersonID());

        myAuth = new AuthTokenModel(testUser1);
        expected.setAuthToken(myAuth.getAuthToken());

        assertEquals(expected, myAuth);
    }

    @Test
    public void testEquals(){
        AuthTokenModel testAuth1 = new AuthTokenModel();
        testAuth1.setAuthToken("authyauth");
        testAuth1.setPersonID("paul_j");
        testAuth1.setUserName("pjohnst5");;

        AuthTokenModel testAuth2 = new AuthTokenModel();
        testAuth2.setAuthToken("authyauth");
        testAuth2.setPersonID("paul_j");
        testAuth2.setUserName("pjohnst5");

        boolean output = testAuth1.equals(testAuth2);
        assertTrue(output);

        testAuth2.setAuthToken("bogus");
        output = testAuth1.equals(testAuth2);

        assertFalse(output);
    }
}
