package Model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class testPersonModel {

    private PersonModel myPersonModel;

    @Before
    public void setUp() {
        myPersonModel = new PersonModel();

    }

    @After
    public void tearDown() {
        myPersonModel = null;
    }

    @Test
    public void testPersonModelUserModelConstructor(){
        UserModel testUser1 = new UserModel(); //person 1
        testUser1.setUserName("pjohnst5");
        testUser1.setPassWord("puffinz");
        testUser1.setEmail("paulthegreat01@gmail.com");
        testUser1.setFirstName("paul");
        testUser1.setLastName("johnston");
        testUser1.setGender("m");
        testUser1.setPersonID("paul_j");

        PersonModel expected = new PersonModel();
        expected.setGender(testUser1.getGender());
        expected.setLastName(testUser1.getLastName());
        expected.setFirstName(testUser1.getFirstName());
        expected.setDescendant(testUser1.getUserName());


        myPersonModel = new PersonModel(testUser1);
        expected.setPersonID(myPersonModel.getPersonID());

        assertEquals(expected, myPersonModel);
    }

    @Test public void testEquals(){
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

        PersonModel testPerson1 = new PersonModel(testUser1);
        PersonModel testPerson2 = new PersonModel(testUser2);
        testPerson2.setPersonID(testPerson1.getPersonID());

        boolean output = testPerson1.equals(testPerson2);
        assertTrue(output);

        testPerson2.setDescendant("carla");
        output = testPerson1.equals(testPerson2);
        assertFalse(output);
    }
}
