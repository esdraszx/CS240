package DataAccessObjects;

import org.junit.* ;

import java.util.UUID;

import static org.junit.Assert.* ;
import Model.UserModel;


public class UserDAOTest {
    private UserDAO myUserDAO;
    private Database db;

    @Before
    public void setUp() throws Database.DatabaseException {
        myUserDAO = new UserDAO();
        db = new Database();
        db.openConnection();
        db.resetTables();
        myUserDAO = db.getUserDAO();

    }

    @After
    public void tearDown() throws Database.DatabaseException {
        db.closeConnection(false);
        myUserDAO = null;
        db = null;
    }

    @Test
    public void testInsertUserIntoDatabaseDAO() {
        try{
            UserModel testUser = new UserModel();
            testUser.setUserName("pjohnst5");
            testUser.setPassWord("puffinz");
            testUser.setEmail("paulthegreat01@gmail.com");
            testUser.setFirstName("paul");
            testUser.setLastName("johnston");
            testUser.setGender("m");
            testUser.setPersonID(UUID.randomUUID().toString());

            myUserDAO.insertUserIntoDatabase(testUser);

            String out = new String("pjohnst5" + "\t" + "puffinz" + "\t" + "paulthegreat01@gmail.com" + "\t" + "paul" + "\t" + "johnston" + "\t" + "m" + "\t" + testUser.getPersonID() + "\n");

            assertEquals(myUserDAO.tableToString(), out);
        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testResetTable(){
        try{
            UserModel testUser1 = new UserModel(); //person 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            myUserDAO.insertUserIntoDatabase(testUser1);

            String expected = "pjohnst5\tpuffinz\tpaulthegreat01@gmail.com\tpaul\tjohnston\tm\tperson_Id\n";
            assertEquals(expected, myUserDAO.tableToString());

            expected = "";
            myUserDAO.resetTable();
            assertEquals(expected,myUserDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testTableToString(){
        try{
            UserModel testUser1 = new UserModel(); //user 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            String expected = "pjohnst5\tpuffinz\tpaulthegreat01@gmail.com\tpaul\tjohnston\tm\tperson_Id\n";
            myUserDAO.insertUserIntoDatabase(testUser1);
            assertEquals(expected,myUserDAO.tableToString());

            UserModel testUser2 = new UserModel(); //user 2
            testUser2.setUserName("carla");
            testUser2.setPassWord("rhinos");
            testUser2.setEmail("carlsbad@gmail.com");
            testUser2.setFirstName("carla");
            testUser2.setLastName("gardner");
            testUser2.setGender("f");
            testUser2.setPersonID("blahblahblah");

            expected = "pjohnst5\tpuffinz\tpaulthegreat01@gmail.com\tpaul\tjohnston\tm\tperson_Id\n" +
                    "carla\trhinos\tcarlsbad@gmail.com\tcarla\tgardner\tf\tblahblahblah\n";
            myUserDAO.insertUserIntoDatabase(testUser2);
            assertEquals(expected,myUserDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDoesUserExist(){
        try{
            UserModel testUser1 = new UserModel(); //user 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");
            myUserDAO.insertUserIntoDatabase(testUser1);

            boolean output = myUserDAO.doesUserNameExist(testUser1.getUserName());
            assertEquals(output, true);

            try{
                output = myUserDAO.doesUserNameExist("bogus");
            } catch(Database.DatabaseException e){
                assertEquals("no such username", e.getMessage());
            }

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDeleteUser(){
        try{
            UserModel testUser1 = new UserModel(); //user 1
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

            myUserDAO.insertUserIntoDatabase(testUser1);
            myUserDAO.insertUserIntoDatabase(testUser2);

            String answer = "pjohnst5\tpuffinz\tpaulthegreat01@gmail.com\tpaul\tjohnston\tm\tperson_Id\n";

            assertNotEquals(answer,myUserDAO.tableToString());
            myUserDAO.deleteUser(testUser2);
            assertEquals(answer,myUserDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testGetUserModel(){
        try{
            UserModel testUser1 = new UserModel(); //user 1
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

            myUserDAO.insertUserIntoDatabase(testUser1);
            myUserDAO.insertUserIntoDatabase(testUser2);

            UserModel output = myUserDAO.getUserModel(testUser1.getUserName());
            output.setPersonID(testUser1.getPersonID());
            assertEquals(testUser1,output);
        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }

    }

    @Test
    public void testDoUsernameAndPasswordExist(){
        try{
            UserModel testUser1 = new UserModel(); //user 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");
            myUserDAO.insertUserIntoDatabase(testUser1);

            boolean output = myUserDAO.doUsernameAndPasswordExist(testUser1);
            assertEquals(true, output);

            try{
                UserModel bogus = new UserModel();
                bogus.setUserName("bogus");
                bogus.setPassWord("bogus");
                output = myUserDAO.doUsernameAndPasswordExist(bogus);
                assertEquals(true,false);
            } catch(Database.DatabaseException e){
                assertEquals("no such username and/or password",e.getMessage());
            }

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testGetPersonIDofUser(){
        try{
            UserModel testUser1 = new UserModel(); //user 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");
            myUserDAO.insertUserIntoDatabase(testUser1);

            String expected = "person_Id";

            String output = myUserDAO.getPersonIDOfUser(testUser1);

            assertEquals(expected,output);
        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }
}

