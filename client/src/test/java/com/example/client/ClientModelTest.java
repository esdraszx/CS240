package com.example.client;

import android.support.v4.util.ArraySet;

import org.junit.* ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.EventModel;
import Model.PersonModel;

import static org.junit.Assert.* ;


public class ClientModelTest {
    private ClientModel cm;
    private Map<String, EventModel> eventMap;
    private Map<String,PersonModel> peopleMap;
    private Map<String, List<EventModel>> peopleEventMap;
    private PersonModel user;
    private Set<PersonModel> paternalAncestors;
    private Set<PersonModel> maternalAncestors;
    private List<String> eventTypesForUser;
    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private Map<String, List<PersonModel>> childrenMap;
    private String authToken;


    @Before
    public void setUp(){
        cm = ClientModel.getInstance();
        eventMap = cm.getEventMap();
        peopleMap = cm.getPeopleMap();
        peopleEventMap = cm.getPeopleEventMap();
        user = cm.getUser();
        paternalAncestors = cm.getPaternalAncestors();
        maternalAncestors = cm.getMaternalAncestors();
        eventTypesForUser = cm.getEventTypesForUser();
        eventTypesForFemaleAncestors = cm.getEventTypesForFemaleAncestors();
        eventTypesForMaleAncestors = cm.getEventTypesForMaleAncestors();
        childrenMap = cm.getChildrenMap();
        authToken = cm.getAuthToken();
        cm.clearClientForTesting();
        cm = ClientModel.getInstance();
    }

    @After
    public void tearDown(){
        //putting stuff back
        cm.setEventMap(eventMap);
        cm.setPeopleMap(peopleMap);
        cm.setPeopleEventMap(peopleEventMap);
        cm.setUser(user);
        cm.setPaternalAncestors(paternalAncestors);
        cm.setMaternalAncestors(maternalAncestors);
        cm.setEventTypesForUser(eventTypesForUser);
        cm.setEventTypesForFemaleAncestors(eventTypesForFemaleAncestors);
        cm.setEventTypesForMaleAncestors(eventTypesForMaleAncestors);
        cm.setChildrenMap(childrenMap);
        cm = null;
    }

    @Test
    public void testSetPeople(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();
        PersonModel person4 = new PersonModel();
        PersonModel person5 = new PersonModel();
        PersonModel person6 = new PersonModel();
        PersonModel person7 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");
        person4.setDescendant("joe");
        person5.setDescendant("joe");
        person6.setDescendant("joe");
        person7.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person4.setFirstName("joe's paternal grandpa");
        person4.setLastName("doe");
        person4.setPersonID("user's paternal grandpa");

        person5.setFirstName("joe's paternal grandma");
        person5.setLastName("doe");
        person5.setPersonID("user's paternal grandma");

        person6.setFirstName("joe's maternal grandpa");
        person6.setLastName("jones");
        person6.setPersonID("user's maternal grandpa");

        person7.setFirstName("joe's maternal grandma");
        person7.setLastName("jones");
        person7.setPersonID("user's maternal grandma");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        person2.setFather("user's paternal grandpa");
        person2.setMother("user's paternal grandma");

        person3.setFather("user's maternal grandpa");
        person3.setMother("user's maternal grandma");


        PersonModel[] family = new PersonModel[7];
        family[0] = person1;
        family[1] = person2;
        family [2] = person3;
        family [3] = person4;
        family [4] = person5;
        family [5] = person6;
        family [6] = person7;

        cm.setPeople(family);

        Map<String, PersonModel> peopleMap = cm.getPeopleMap();

        for (int i = 0; i < family.length; i++){
            assertEquals(family[i], peopleMap.get(family[i].getPersonID()));
        }
    }

    @Test
    public void testCreatePaternalAndMaternalSets(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();
        PersonModel person4 = new PersonModel();
        PersonModel person5 = new PersonModel();
        PersonModel person6 = new PersonModel();
        PersonModel person7 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");
        person4.setDescendant("joe");
        person5.setDescendant("joe");
        person6.setDescendant("joe");
        person7.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person4.setFirstName("joe's paternal grandpa");
        person4.setLastName("doe");
        person4.setPersonID("user's paternal grandpa");

        person5.setFirstName("joe's paternal grandma");
        person5.setLastName("doe");
        person5.setPersonID("user's paternal grandma");

        person6.setFirstName("joe's maternal grandpa");
        person6.setLastName("jones");
        person6.setPersonID("user's maternal grandpa");

        person7.setFirstName("joe's maternal grandma");
        person7.setLastName("jones");
        person7.setPersonID("user's maternal grandma");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        person2.setFather("user's paternal grandpa");
        person2.setMother("user's paternal grandma");

        person3.setFather("user's maternal grandpa");
        person3.setMother("user's maternal grandma");


        PersonModel[] family = new PersonModel[7];
        family[0] = person1;
        family[1] = person2;
        family [2] = person3;
        family [3] = person4;
        family [4] = person5;
        family [5] = person6;
        family [6] = person7;

        cm.setPeople(family);

        Set<PersonModel> expectedParentalAncestors = new ArraySet<>();
        expectedParentalAncestors.add(person2);
        expectedParentalAncestors.add(person4);
        expectedParentalAncestors.add(person5);

        Set<PersonModel> expectedMaternalAncestors = new ArraySet<>();
        expectedMaternalAncestors.add(person3);
        expectedMaternalAncestors.add(person6);
        expectedMaternalAncestors.add(person7);

        assertEquals(cm.getPaternalAncestors(), expectedParentalAncestors);

        assertEquals(cm.getMaternalAncestors(), expectedMaternalAncestors);
    }

    @Test
    public void testSetEvents(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        PersonModel[] family = new PersonModel[3];
        family[0] = person1;
        family[1] = person2;
        family[2] = person3;

        cm.setPeople(family);


        EventModel event1 = new EventModel();
        EventModel event2 = new EventModel();
        EventModel event3 = new EventModel();
        EventModel event4 = new EventModel();
        EventModel event5 = new EventModel();
        EventModel event6 = new EventModel();

        event1.setDescendant("joe");
        event1.setEventID("thebaptismEvent");
        event1.setPersonID("user");
        event1.setType("Baptism");
        event1.setCountry("Bulgaria");
        event1.setLongitude(0.01);
        event1.setLatitude(5.5);
        event1.setCity("Bulgaria city");
        event1.setYear(1990);

        event2.setDescendant("joe");
        event2.setEventID("puppybuyingOfJoe");
        event2.setPersonID("user");
        event2.setType("Bought a puppy");
        event2.setYear(1985);
        event2.setCity("salt lake city");
        event2.setCountry("USA");
        event2.setLatitude(800.0);
        event2.setLongitude(757.0);

        event3.setDescendant("joe");
        event3.setEventID("adventureGoingOfJoe");
        event3.setPersonID("user");
        event3.setType("Adventure");
        event3.setYear(1995);
        event3.setCity("Lanki");
        event3.setCountry("Sri Lanka");
        event3.setLatitude(830.0);
        event3.setLongitude(7357.0);

        event4.setDescendant("joe");
        event4.setEventID("deathOfJoe'sFather");
        event4.setPersonID("user's father");
        event4.setType("Death");
        event4.setYear(1991);
        event4.setCity("Death Valley");
        event4.setCountry("California");
        event4.setLatitude(50.0);
        event4.setLongitude(57.0);

        event5.setDescendant("joe");
        event5.setEventID("deathOfJoe'sMother");
        event5.setPersonID("user's mother");
        event5.setType("Death");
        event5.setYear(1990);
        event5.setCity("Las Vegas");
        event5.setCountry("Nevada");
        event5.setLatitude(51.0);
        event5.setLongitude(51.0);

        event6.setDescendant("joe");
        event6.setEventID("birthOfJoe'sMother");
        event6.setPersonID("user's mother");
        event6.setType("Birth");
        event6.setYear(1960);
        event6.setCity("Berkely");
        event6.setCountry("California");
        event6.setLatitude(100.0);
        event6.setLongitude(100.0);

        EventModel[] familyEvents = new EventModel[6];

        familyEvents[0] = event1;
        familyEvents[1] = event2;
        familyEvents[2] = event3;
        familyEvents[3] = event4;
        familyEvents[4] = event5;
        familyEvents[5] = event6;

        cm.setEvents(familyEvents);

        Map<String, EventModel> events = cm.getEventMap();

        for (int i = 0; i < familyEvents.length; i++){
            assertEquals(familyEvents[i], events.get(familyEvents[i].getEventID()));
        }
    }

    @Test
    public void testCreateEventsLists(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        PersonModel[] family = new PersonModel[3];
        family[0] = person1;
        family[1] = person2;
        family[2] = person3;

        cm.setPeople(family);


        EventModel event1 = new EventModel();
        EventModel event2 = new EventModel();
        EventModel event3 = new EventModel();
        EventModel event4 = new EventModel();
        EventModel event5 = new EventModel();
        EventModel event6 = new EventModel();

        event1.setDescendant("joe");
        event1.setEventID("thebaptismEvent");
        event1.setPersonID("user");
        event1.setType("Baptism");
        event1.setCountry("Bulgaria");
        event1.setLongitude(0.01);
        event1.setLatitude(5.5);
        event1.setCity("Bulgaria city");
        event1.setYear(1990);

        event2.setDescendant("joe");
        event2.setEventID("puppybuyingOfJoe");
        event2.setPersonID("user");
        event2.setType("Bought a puppy");
        event2.setYear(1985);
        event2.setCity("salt lake city");
        event2.setCountry("USA");
        event2.setLatitude(800.0);
        event2.setLongitude(757.0);

        event3.setDescendant("joe");
        event3.setEventID("adventureGoingOfJoe");
        event3.setPersonID("user");
        event3.setType("Adventure");
        event3.setYear(1995);
        event3.setCity("Lanki");
        event3.setCountry("Sri Lanka");
        event3.setLatitude(830.0);
        event3.setLongitude(7357.0);

        event4.setDescendant("joe");
        event4.setEventID("deathOfJoe'sFather");
        event4.setPersonID("user's father");
        event4.setType("Death");
        event4.setYear(1991);
        event4.setCity("Death Valley");
        event4.setCountry("California");
        event4.setLatitude(50.0);
        event4.setLongitude(57.0);

        event5.setDescendant("joe");
        event5.setEventID("deathOfJoe'sMother");
        event5.setPersonID("user's mother");
        event5.setType("Death");
        event5.setYear(1990);
        event5.setCity("Las Vegas");
        event5.setCountry("Nevada");
        event5.setLatitude(51.0);
        event5.setLongitude(51.0);

        event6.setDescendant("joe");
        event6.setEventID("birthOfJoe'sMother");
        event6.setPersonID("user's mother");
        event6.setType("Birth");
        event6.setYear(1960);
        event6.setCity("Berkely");
        event6.setCountry("California");
        event6.setLatitude(100.0);
        event6.setLongitude(100.0);

        EventModel[] familyEvents = new EventModel[6];

        familyEvents[0] = event1;
        familyEvents[1] = event2;
        familyEvents[2] = event3;
        familyEvents[3] = event4;
        familyEvents[4] = event5;
        familyEvents[5] = event6;

        cm.setEvents(familyEvents);

        List<EventModel> expectedEventsOfUser = new ArrayList<>();
        expectedEventsOfUser.add(event2);
        expectedEventsOfUser.add(event1);
        expectedEventsOfUser.add(event3);

        assertEquals(expectedEventsOfUser, cm.getEventsOfPersonByPersonId("user"));

        List<EventModel> expectedEventsOfMother = new ArrayList<>();
        expectedEventsOfMother.add(event6);
        expectedEventsOfMother.add(event5);


        assertEquals(expectedEventsOfMother, cm.getEventsOfPersonByPersonId("user's mother"));

        List<EventModel> expectedEventsOfFather = new ArrayList<>();
        expectedEventsOfFather.add(event4);

        assertEquals(expectedEventsOfFather, cm.getEventsOfPersonByPersonId("user's father"));
    }

    @Test
    public void testFindParentsByID(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();
        PersonModel person4 = new PersonModel();
        PersonModel person5 = new PersonModel();
        PersonModel person6 = new PersonModel();
        PersonModel person7 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");
        person4.setDescendant("joe");
        person5.setDescendant("joe");
        person6.setDescendant("joe");
        person7.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person4.setFirstName("joe's paternal grandpa");
        person4.setLastName("doe");
        person4.setPersonID("user's paternal grandpa");

        person5.setFirstName("joe's paternal grandma");
        person5.setLastName("doe");
        person5.setPersonID("user's paternal grandma");

        person6.setFirstName("joe's maternal grandpa");
        person6.setLastName("jones");
        person6.setPersonID("user's maternal grandpa");

        person7.setFirstName("joe's maternal grandma");
        person7.setLastName("jones");
        person7.setPersonID("user's maternal grandma");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        person2.setFather("user's paternal grandpa");
        person2.setMother("user's paternal grandma");

        person3.setFather("user's maternal grandpa");
        person3.setMother("user's maternal grandma");


        PersonModel[] family = new PersonModel[7];
        family[0] = person1;
        family[1] = person2;
        family [2] = person3;
        family [3] = person4;
        family [4] = person5;
        family [5] = person6;
        family [6] = person7;

        cm.setPeople(family);

        List<PersonModel> expectedParents = new ArrayList<>();
        expectedParents.add(person2);
        expectedParents.add(person3);

        List<PersonModel> output = cm.findParentsByPersonID(person1);
        assertTrue(expectedParents.contains(output.get(0)));
        assertTrue(expectedParents.contains(output.get(1)));
    }

    @Test
    public void testFindParentsByIDShouldFail(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();
        PersonModel person4 = new PersonModel();
        PersonModel person5 = new PersonModel();
        PersonModel person6 = new PersonModel();
        PersonModel person7 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");
        person4.setDescendant("joe");
        person5.setDescendant("joe");
        person6.setDescendant("joe");
        person7.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person4.setFirstName("joe's paternal grandpa");
        person4.setLastName("doe");
        person4.setPersonID("user's paternal grandpa");

        person5.setFirstName("joe's paternal grandma");
        person5.setLastName("doe");
        person5.setPersonID("user's paternal grandma");

        person6.setFirstName("joe's maternal grandpa");
        person6.setLastName("jones");
        person6.setPersonID("user's maternal grandpa");

        person7.setFirstName("joe's maternal grandma");
        person7.setLastName("jones");
        person7.setPersonID("user's maternal grandma");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        person2.setFather("user's paternal grandpa");
        person2.setMother("user's paternal grandma");

        person3.setFather("user's maternal grandpa");
        person3.setMother("user's maternal grandma");


        PersonModel[] family = new PersonModel[7];
        family[0] = person1;
        family[1] = person2;
        family [2] = person3;
        family [3] = person4;
        family [4] = person5;
        family [5] = person6;
        family [6] = person7;

        cm.setPeople(family);

        List<PersonModel> expectedParents = new ArrayList<>();
        expectedParents.add(person4);
        expectedParents.add(person5);

        List<PersonModel> output = cm.findParentsByPersonID(person1);
        assertFalse(expectedParents.contains(output.get(0)));
        assertFalse(expectedParents.contains(output.get(1)));
    }

    @Test
    public void testFindSpouseByPersonID(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();
        PersonModel person4 = new PersonModel();
        PersonModel person5 = new PersonModel();
        PersonModel person6 = new PersonModel();
        PersonModel person7 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");
        person4.setDescendant("joe");
        person5.setDescendant("joe");
        person6.setDescendant("joe");
        person7.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person4.setFirstName("joe's paternal grandpa");
        person4.setLastName("doe");
        person4.setPersonID("user's paternal grandpa");

        person5.setFirstName("joe's paternal grandma");
        person5.setLastName("doe");
        person5.setPersonID("user's paternal grandma");

        person6.setFirstName("joe's maternal grandpa");
        person6.setLastName("jones");
        person6.setPersonID("user's maternal grandpa");

        person7.setFirstName("joe's maternal grandma");
        person7.setLastName("jones");
        person7.setPersonID("user's maternal grandma");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        person2.setFather("user's paternal grandpa");
        person2.setMother("user's paternal grandma");

        person3.setFather("user's maternal grandpa");
        person3.setMother("user's maternal grandma");

        person2.setSpouse(person3.getPersonID());
        person3.setSpouse(person2.getPersonID());

        person4.setSpouse(person5.getPersonID());
        person5.setSpouse(person4.getPersonID());

        person6.setSpouse(person7.getPersonID());
        person7.setSpouse(person6.getPersonID());


        PersonModel[] family = new PersonModel[7];
        family[0] = person1;
        family[1] = person2;
        family [2] = person3;
        family [3] = person4;
        family [4] = person5;
        family [5] = person6;
        family [6] = person7;

        cm.setPeople(family);

        PersonModel output = cm.findSpouseByPersonID(person3);

        assertEquals(person2, output);

        output = cm.findSpouseByPersonID(person6);

        assertEquals(person7, output);
    }

    @Test
    public void testFindSpouseByPersonIDShouldFail(){
        PersonModel person1 = new PersonModel();
        PersonModel person2 = new PersonModel();
        PersonModel person3 = new PersonModel();
        PersonModel person4 = new PersonModel();
        PersonModel person5 = new PersonModel();
        PersonModel person6 = new PersonModel();
        PersonModel person7 = new PersonModel();

        person1.setDescendant("joe");
        person2.setDescendant("joe");
        person3.setDescendant("joe");
        person4.setDescendant("joe");
        person5.setDescendant("joe");
        person6.setDescendant("joe");
        person7.setDescendant("joe");

        person1.setGender("m"); //root
        person2.setGender("m");
        person3.setGender("f");
        person4.setGender("m");
        person5.setGender("f");
        person6.setGender("m");
        person7.setGender("f");

        person1.setFirstName("joe");
        person1.setLastName("doe");
        person1.setPersonID("user");

        person2.setFirstName("joe's father");
        person2.setLastName("doe");
        person2.setPersonID("user's father");

        person3.setFirstName("joe's mother");
        person3.setLastName("doe");
        person3.setPersonID("user's mother");

        person4.setFirstName("joe's paternal grandpa");
        person4.setLastName("doe");
        person4.setPersonID("user's paternal grandpa");

        person5.setFirstName("joe's paternal grandma");
        person5.setLastName("doe");
        person5.setPersonID("user's paternal grandma");

        person6.setFirstName("joe's maternal grandpa");
        person6.setLastName("jones");
        person6.setPersonID("user's maternal grandpa");

        person7.setFirstName("joe's maternal grandma");
        person7.setLastName("jones");
        person7.setPersonID("user's maternal grandma");

        person1.setFather("user's father");
        person1.setMother("user's mother");

        person2.setFather("user's paternal grandpa");
        person2.setMother("user's paternal grandma");

        person3.setFather("user's maternal grandpa");
        person3.setMother("user's maternal grandma");

        person2.setSpouse(person3.getPersonID());
        person3.setSpouse(person2.getPersonID());

        person4.setSpouse(person5.getPersonID());
        person5.setSpouse(person4.getPersonID());

        person6.setSpouse(person7.getPersonID());
        person7.setSpouse(person6.getPersonID());


        PersonModel[] family = new PersonModel[7];
        family[0] = person1;
        family[1] = person2;
        family [2] = person3;
        family [3] = person4;
        family [4] = person5;
        family [5] = person6;
        family [6] = person7;

        cm.setPeople(family);

        PersonModel output = cm.findSpouseByPersonID(person3);
        assertNotEquals(person6, output);

        output = cm.findSpouseByPersonID(person5);
        assertNotEquals(person2, output);
    }


}
