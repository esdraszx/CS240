package Services;

import org.junit.*;


import DataAccessObjects.*;

import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoadRequest;

import Response.LoadResponse;

import static org.junit.Assert.*;


public class testLoadService {
    private LoadService myLoadService;

    @Before
    public void setUp() {
        myLoadService = new LoadService();
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
        myLoadService = null;
    }

    @Test
    public void testLoad(){
            UserModel testUser1 = new UserModel();
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

            UserModel testUser2 = new UserModel(); //user 2
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


            UserModel[] usersInput = new UserModel[2];
            usersInput[0] = testUser1;
            usersInput[1] = testUser2;
            PersonModel[] personsInput = new PersonModel[2];
            personsInput[0] = testPerson1;
            personsInput[1] = testPerson2;
            EventModel[] eventsInput = new EventModel[3];
            eventsInput[0] = testEvent1;
            eventsInput[1] = testEvent2;
            eventsInput[2] = testEvent3;
            LoadRequest inputRequest = new LoadRequest();
            inputRequest.setUsers(usersInput);
            inputRequest.setEvents(eventsInput);
            inputRequest.setPersons(personsInput);

            LoadResponse expectedResponse = new LoadResponse();
            expectedResponse.setSuccess(true);
            expectedResponse.setNumEvents(3);
            expectedResponse.setNumPersons(2);
            expectedResponse.setNumUsers(2);

            LoadResponse outputResponse = myLoadService.load(inputRequest);

            assertEquals(expectedResponse, outputResponse);
    }
}

/*

try{

            } catch (Database.DatabaseException e){
                assertEquals("Throwing exceptions is bad", e.getMessage());
            }
 */