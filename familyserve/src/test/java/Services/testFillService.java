package Services;

import org.junit.*;

import java.util.Random;

import DataAccessObjects.*;

import Model.UserModel;

import Response.FillResponse;

import static org.junit.Assert.*;

public class testFillService {
    private FillService myFillService;

    @Before
    public void setUp() {
        myFillService = new FillService();
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
        myFillService = null;
    }

    @Test
    public void testFill(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");
            Database db = new Database();
            db.openConnection();
            db.resetTables();
            UserDAO myUserDAO = db.getUserDAO();
            myUserDAO.insertUserIntoDatabase(testUser1);
            db.closeConnection(true);

            Random rand = new Random();
            int r = rand.nextInt(5);
            double numG = (double) r;
            double numPeople = (Math.pow(2.0, (numG + 1.0)) - 1.0);
            int finalNumPeople = (int) numPeople; //final
            int finalNumEvents = (finalNumPeople * 4);


            FillResponse expectedResponse = new FillResponse();
            expectedResponse.setSuccess(true);
            expectedResponse.setNumEvents(finalNumEvents);
            expectedResponse.setNumPersons(finalNumPeople);

            FillResponse outputResponse = myFillService.fill(testUser1.getUserName(), r);
            assertEquals(expectedResponse, outputResponse);

            FillResponse badFillExpectedResponse = new FillResponse();
            badFillExpectedResponse.setSuccess(false);
            badFillExpectedResponse.setMessage("no such username");

            FillResponse badOutputResponse = myFillService.fill("bogus", r);

            assertEquals(badFillExpectedResponse, badOutputResponse);

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }
}

