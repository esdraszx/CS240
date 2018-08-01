package DataAccessObjects;

import org.junit.* ;
import static org.junit.Assert.* ;
import Model.AuthTokenModel;


public class AuthTokenDAOTest {
    private AuthTokenDAO myAuthDAO;
    private Database db;

    @Before
    public void setUp() throws Database.DatabaseException {
        db = new Database();
        db.openConnection();
        db.resetTables();
        myAuthDAO = db.getAuthTokenDAO();
    }

    @After
    public void tearDown() throws Database.DatabaseException {
        db.closeConnection(false);
        myAuthDAO = null;
        db = null;
    }

    @Test
    public void testResetTable(){
        try{
            AuthTokenModel testAuth1 = new AuthTokenModel();
            testAuth1.setAuthToken("authyauth");
            testAuth1.setPersonID("paul_j");
            testAuth1.setUserName("pjohnst5");

            String expected = "authyauth\tpjohnst5\tpaul_j\n";

            myAuthDAO.insertTokenIntoTable(testAuth1);

            assertEquals(expected, myAuthDAO.tableToString());

            expected = "";
            assertNotEquals(expected, myAuthDAO.tableToString());
            myAuthDAO.resetTable();
            assertEquals(expected,myAuthDAO.tableToString());


        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }


    }

    @Test public void testInsertTokenIntoAuthTable(){
        try{
            AuthTokenModel testAuth1 = new AuthTokenModel();
            testAuth1.setAuthToken("authyauth");
            testAuth1.setPersonID("paul_j");
            testAuth1.setUserName("pjohnst5");

            String expected = "authyauth\tpjohnst5\tpaul_j\n";

            assertNotEquals(expected, myAuthDAO.tableToString());

            myAuthDAO.insertTokenIntoTable(testAuth1);

            assertEquals(expected, myAuthDAO.tableToString());

            AuthTokenModel testAuth2 = new AuthTokenModel();
            testAuth2.setAuthToken("bauth");
            testAuth2.setPersonID("carla_j");
            testAuth2.setUserName("carlsbad");

            expected = "authyauth\tpjohnst5\tpaul_j\nbauth\tcarlsbad\tcarla_j\n";

            assertNotEquals(expected, myAuthDAO.tableToString());

            myAuthDAO.insertTokenIntoTable(testAuth2);

            assertEquals(expected, myAuthDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test public void testTableToString(){
        try{
            AuthTokenModel testAuth1 = new AuthTokenModel();
            testAuth1.setAuthToken("authyauth");
            testAuth1.setPersonID("paul_j");
            testAuth1.setUserName("pjohnst5");


            assertEquals("",myAuthDAO.tableToString());
            myAuthDAO.insertTokenIntoTable(testAuth1);

            String expected = "authyauth\tpjohnst5\tpaul_j\n";

            assertEquals(expected, myAuthDAO.tableToString());

            AuthTokenModel testAuth2 = new AuthTokenModel();
            testAuth2.setAuthToken("bauth");
            testAuth2.setPersonID("carla_j");
            testAuth2.setUserName("carlsbad");
            myAuthDAO.insertTokenIntoTable(testAuth2);

            expected = "authyauth\tpjohnst5\tpaul_j\nbauth\tcarlsbad\tcarla_j\n";
            assertEquals(expected, myAuthDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test public void testDoesAuthTokenExist(){
        try{
            AuthTokenModel testAuth1 = new AuthTokenModel();
            testAuth1.setAuthToken("authyauth");
            testAuth1.setPersonID("paul_j");
            testAuth1.setUserName("pjohnst5");;

            AuthTokenModel testAuth2 = new AuthTokenModel();
            testAuth2.setAuthToken("bauth");
            testAuth2.setPersonID("carla_j");
            testAuth2.setUserName("carlsbad");

            myAuthDAO.insertTokenIntoTable(testAuth1);
            myAuthDAO.insertTokenIntoTable(testAuth2);

            boolean output = myAuthDAO.doesAuthTokenExist(testAuth1.getAuthToken());
            assertEquals(true, output);

            try{
                output = myAuthDAO.doesAuthTokenExist("bogus");
                assertEquals(true,false);
            } catch(Database.DatabaseException e){
                assertEquals("no such authToken", e.getMessage());
            }

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test public void testGetAuthTokenModel(){
        try{
            AuthTokenModel testAuth1 = new AuthTokenModel();
            testAuth1.setAuthToken("authyauth");
            testAuth1.setPersonID("paul_j");
            testAuth1.setUserName("pjohnst5");;

            AuthTokenModel testAuth2 = new AuthTokenModel();
            testAuth2.setAuthToken("bauth");
            testAuth2.setPersonID("carla_j");
            testAuth2.setUserName("carlsbad");

            myAuthDAO.insertTokenIntoTable(testAuth1);
            myAuthDAO.insertTokenIntoTable(testAuth2);

            AuthTokenModel output = myAuthDAO.getAuthTokenModel(testAuth1.getAuthToken());
            assertEquals(testAuth1, output);

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

}


