package DataAccessObjects;

import org.junit.*;

import static org.junit.Assert.* ;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;

public class EventDAOTest {

    private EventDAO myEventDAO;
    private Database db;

    @Before
    public void setUp() throws Database.DatabaseException {
        db = new Database();
        db.openConnection();
        db.resetTables();
        myEventDAO = db.getEventDAO();
    }

    @After
    public void tearDown() throws Database.DatabaseException {
        db.closeConnection(false);
        myEventDAO = null;
        db = null;
    }

    @Test
    public void testResetEventTable(){
        try{
            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);


            myEventDAO.insertEventIntoDatabase(testEvent1); //inserts event into event table

            String answer = "uniqueEventID\tpjohnst5\tpaul_j\t-1.0\t1.0\tNorth pole\tSanta's house\tCookies at santa's\t2017\n";
            assertEquals(answer, myEventDAO.tableToString()); //asserts the table isn't empty

            myEventDAO.resetTable(); //resets table

            assertEquals("",myEventDAO.tableToString());


        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testInsertEventIntoTable(){
        try{
            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            String expected = "uniqueEventID\tpjohnst5\tpaul_j\t-1.0\t1.0\tNorth pole\tSanta's house\tCookies at santa's\t2017\n";

            assertNotEquals(expected, myEventDAO.tableToString()); //asserts that the table is not hard coded just to pass the test
            myEventDAO.insertEventIntoDatabase(testEvent1);
            assertEquals(expected, myEventDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testMakeRootsEvents(){
        try{
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

            int expectedNumEvents = 4;
            int birthYear = 0;

            String eventTablePreliminary = myEventDAO.tableToString();
            String[] eventsPreliminary = eventTablePreliminary.split("\n");

            assertNotEquals(expectedNumEvents, eventsPreliminary.length);
            assertNotEquals(birthYear,1960);

            birthYear = myEventDAO.makeRootsEvents(testPerson1);

            String eventTable = myEventDAO.tableToString();
            String[] events = eventTable.split("\n");

            assertEquals(expectedNumEvents, events.length);
            assertEquals(birthYear,1960);

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testGenerateEventDataParents(){
        try{
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
            testUser2.setUserName("drukate");
            testUser2.setPassWord("rhinos");
            testUser2.setEmail("dru@gmail.com");
            testUser2.setFirstName("Dru");
            testUser2.setLastName("Hunsaker");
            testUser2.setGender("f");
            testUser2.setPersonID("blahblahblah");
            PersonModel testPerson2 = new PersonModel(testUser2);
            testPerson2.setSpouse("pjohnst5");
            testPerson2.setFather("Keith");
            testPerson2.setMother("Erin");

            int expectedNumEvents = 8;
            int exptectedBirthDateOfParents = 1934;
            int actualBirhtYearOFparents = 0;

            String eventsOutput = myEventDAO.tableToString();
            String[] eventsArray = eventsOutput.split("\n");

            assertNotEquals(expectedNumEvents, eventsArray.length);
            assertNotEquals(exptectedBirthDateOfParents, actualBirhtYearOFparents);

            actualBirhtYearOFparents = myEventDAO.generateEventDataParents(testPerson2,testPerson1,1960);

            eventsOutput = myEventDAO.tableToString();
            String[] outputArray = eventsOutput.split("\n");

            assertEquals(expectedNumEvents, outputArray.length);
            assertEquals(exptectedBirthDateOfParents, actualBirhtYearOFparents);


        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDeleteAllEventsOfuser(){
        try{
            UserModel testUser1 = new UserModel(); //person 1
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            EventModel testEvent2 = new EventModel();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setDescendant("carlsbad");
            testEvent2.setPersonID("carla_j");
            testEvent2.setLatitude(99.0);
            testEvent2.setLongitude(-99.0);
            testEvent2.setCountry("Equador");
            testEvent2.setCity("Quito");
            testEvent2.setType("eatin lunch");
            testEvent2.setYear(1990);

            myEventDAO.insertEventIntoDatabase(testEvent1);
            myEventDAO.insertEventIntoDatabase(testEvent2);

            String answer = "uniqueEventID2\tcarlsbad\tcarla_j\t99.0\t-99.0\tEquador\tQuito\teatin lunch\t1990\n";
            assertNotEquals(answer, myEventDAO.tableToString());

            myEventDAO.deleteAllEventsOfUser(testUser1);

            assertEquals(answer, myEventDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testSelectSingleEvent(){
        try{
            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            EventModel testEvent2 = new EventModel();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setDescendant("carlsbad");
            testEvent2.setPersonID("carla_j");
            testEvent2.setLatitude(99.0);
            testEvent2.setLongitude(-99.0);
            testEvent2.setCountry("Equador");
            testEvent2.setCity("Quito");
            testEvent2.setType("eatin lunch");
            testEvent2.setYear(1990);

            myEventDAO.insertEventIntoDatabase(testEvent1);
            myEventDAO.insertEventIntoDatabase(testEvent2);

            EventModel output = myEventDAO.selectSingleEvent(testEvent1.getEventID());

            assertEquals(testEvent1, output); //overloaded event equals

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDoesEventExist(){
        try{
            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            myEventDAO.insertEventIntoDatabase(testEvent1);

            boolean output = myEventDAO.doesEventExist(testEvent1.getEventID());
            assertEquals(output, true);

            try {
                output = myEventDAO.doesEventExist("BogusEventID");
                assertEquals(true,false);
            }catch (Database.DatabaseException e){
                String answer = "no such eventID";
                assertEquals(answer, e.getMessage());
            }

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testSelectAllEvents(){
        try{
            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            EventModel testEvent2 = new EventModel();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setDescendant("carlsbad");
            testEvent2.setPersonID("carla_j");
            testEvent2.setLatitude(99.0);
            testEvent2.setLongitude(-99.0);
            testEvent2.setCountry("Equador");
            testEvent2.setCity("Quito");
            testEvent2.setType("eatin lunch");
            testEvent2.setYear(1990);

            EventModel testEvent3 = new EventModel();
            testEvent3.setEventID("uniqueEventID3");
            testEvent3.setDescendant("pjohnst5");
            testEvent3.setPersonID("paul_j");
            testEvent3.setLatitude(55.0);
            testEvent3.setLongitude(-55.0);
            testEvent3.setCountry("Brazil");
            testEvent3.setCity("Brasilia");
            testEvent3.setType("mission lunch");
            testEvent3.setYear(2014);

            myEventDAO.insertEventIntoDatabase(testEvent1);
            myEventDAO.insertEventIntoDatabase(testEvent2);
            myEventDAO.insertEventIntoDatabase(testEvent3);

            EventModel[] expectedEvents = new EventModel[2];
            expectedEvents[0] = testEvent1;
            expectedEvents[1] = testEvent3;

            EventModel[] output = myEventDAO.selectAllEvents("pjohsnt5");

            for (int i = 0; i < output.length; i++){
                assertEquals(expectedEvents[i],output[i]);
            }


        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testTableToString(){
        try{
            String answer = "";
            assertEquals(answer,myEventDAO.tableToString());

            EventModel testEvent1 = new EventModel();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("pjohnst5");
            testEvent1.setPersonID("paul_j");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setType("Cookies at santa's");
            testEvent1.setYear(2017);

            answer = "uniqueEventID\tpjohnst5\tpaul_j\t-1.0\t1.0\tNorth pole\tSanta's house\tCookies at santa's\t2017\n";
            myEventDAO.insertEventIntoDatabase(testEvent1);
            assertEquals(answer, myEventDAO.tableToString());

            EventModel testEvent2 = new EventModel();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setDescendant("carlsbad");
            testEvent2.setPersonID("carla_j");
            testEvent2.setLatitude(99.0);
            testEvent2.setLongitude(-99.0);
            testEvent2.setCountry("Equador");
            testEvent2.setCity("Quito");
            testEvent2.setType("eatin lunch");
            testEvent2.setYear(1990);

            answer = "uniqueEventID\tpjohnst5\tpaul_j\t-1.0\t1.0\tNorth pole\tSanta's house\tCookies at santa's\t2017\n" +
                    "uniqueEventID2\tcarlsbad\tcarla_j\t99.0\t-99.0\tEquador\tQuito\teatin lunch\t1990\n";
            myEventDAO.insertEventIntoDatabase(testEvent2);
            assertEquals(answer, myEventDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

}
