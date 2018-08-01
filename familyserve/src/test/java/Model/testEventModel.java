package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class testEventModel {
    private EventModel myEventModel;

    @Before
    public void setUp() {
        myEventModel = new EventModel();
    }

    @After
    public void tearDown() {
        myEventModel = null;
    }

    @Test
    public void testEquals(){
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
        testEvent2.setEventID("uniqueEventID");
        testEvent2.setDescendant("pjohnst5");
        testEvent2.setPersonID("paul_j");
        testEvent2.setLatitude(-1.0);
        testEvent2.setLongitude(1.0);
        testEvent2.setCountry("North pole");
        testEvent2.setCity("Santa's house");
        testEvent2.setType("Cookies at santa's");
        testEvent2.setYear(2017);

        boolean output = testEvent1.equals(testEvent2);
        assertTrue(output);

        testEvent2.setEventID("pickles");

        output = testEvent1.equals(testEvent2);
        assertFalse(output);

    }

}
