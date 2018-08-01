package DataAccessObjects;


import org.junit.* ;


import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.* ;
import Model.PersonModel;
import Model.UserModel;


public class PersonDAOTest {

        private PersonDAO myPersonDAO;
        private Database db;

        @Before
        public void setUp() throws Database.DatabaseException {
            db = new Database();
            db.openConnection();
            db.resetTables();
            myPersonDAO = db.getPersonDAO();
        }

        @After
        public void tearDown() throws Database.DatabaseException {
            db.closeConnection(false);
            myPersonDAO = null;
            db = null;
        }

        @Test
        public void testinsertPersonIntoDatabaseDAO() {
            try{
                UserModel testUser = new UserModel();
                testUser.setUserName("pjohnst5");
                testUser.setPassWord("puffinz");
                testUser.setEmail("paulthegreat01@gmail.com");
                testUser.setFirstName("paul");
                testUser.setLastName("johnston");
                testUser.setGender("m");
                testUser.setPersonID(UUID.randomUUID().toString());

                String out = new String(testUser.getPersonID() + "\t" + "pjohnst5" +  "\t" + "paul" + "\t" + "johnston" + "\t" +  "m" + "\t" + "\t" + "\t" +"\n");

                assertNotEquals(myPersonDAO.tableToString(), out); //asserts that the table is not hardcoded just to pass the test

                PersonModel pMod = new PersonModel(testUser);
                myPersonDAO.insertPersonIntoDatabase(pMod);

                assertEquals(myPersonDAO.tableToString(), out);

            } catch (Database.DatabaseException e){
                assertEquals("Throwing exceptions is bad", e.getMessage());
            }
        }

        @Test
        public void testResetPersonTable(){
            try{
                UserModel testUser1 = new UserModel();
                testUser1.setUserName("pjohnst5");
                testUser1.setPassWord("puffinz");
                testUser1.setEmail("paulthegreat01@gmail.com");
                testUser1.setFirstName("paul");
                testUser1.setLastName("johnston");
                testUser1.setGender("m");
                testUser1.setPersonID("person_Id");

                PersonModel testPerson1 = new PersonModel(testUser1);
                myPersonDAO.insertPersonIntoDatabase(testPerson1); //inserts person into person table

                String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t\t\n";
                assertEquals(answer, myPersonDAO.tableToString()); //asserts the table isn't empty

                myPersonDAO.resetTable(); //resets table

                assertEquals("",myPersonDAO.tableToString());


            } catch (Database.DatabaseException e){
                assertEquals("Throwing exceptions is bad", e.getMessage());
            }

        }

    @Test
    public void testUpdateMother(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            PersonModel testPerson1 = new PersonModel(testUser1);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\tNelly\t\n";

            assertNotEquals(answer, myPersonDAO.tableToString()); //asserts the person has no mother

            myPersonDAO.updateMother(testPerson1, "Nelly");

            assertEquals(answer, myPersonDAO.tableToString()); //assert the person now has a mother

            String answer2 = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\tMargret\t\n";

            assertNotEquals(answer2, myPersonDAO.tableToString()); //asserts that the person's mother isn't Margret
            myPersonDAO.updateMother(testPerson1, "Margret");
            assertEquals(answer2, myPersonDAO.tableToString());//asserts the person's mother now is Margret

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testUpdateFather(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            PersonModel testPerson1 = new PersonModel(testUser1);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\tJeffrey\t\t\n";

            assertNotEquals(answer, myPersonDAO.tableToString()); //asserts the person has no father

            myPersonDAO.updateFather(testPerson1, "Jeffrey");

            assertEquals(answer, myPersonDAO.tableToString()); //assert the person now has a father

            String answer2 = "person_Id\tpjohnst5\tpaul\tjohnston\tm\tBob\t\t\n";

            assertNotEquals(answer2, myPersonDAO.tableToString()); //asserts that the person's father isn't Bob
            myPersonDAO.updateFather(testPerson1, "Bob");
            assertEquals(answer2, myPersonDAO.tableToString());//asserts the person's father now is Bob

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testUpdateSpouse(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            PersonModel testPerson1 = new PersonModel(testUser1);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t\tDru\n";

            assertNotEquals(answer, myPersonDAO.tableToString()); //asserts the person has no spouse

            myPersonDAO.updateSpouse(testPerson1, "Dru");

            assertEquals(answer, myPersonDAO.tableToString()); //assert the person now has a spouse

            String answer2 = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t\tjane\n";

            assertNotEquals(answer2, myPersonDAO.tableToString()); //asserts that the person's spouse isn't jane
            myPersonDAO.updateSpouse(testPerson1, "jane");
            assertEquals(answer2, myPersonDAO.tableToString());//asserts the person's father now is jane

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testMakeMother(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            PersonModel testPerson1 = new PersonModel(testUser1);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t\t\n";

            assertEquals(answer, myPersonDAO.tableToString()); //shows that table has no mother at all

            PersonModel mother = myPersonDAO.makeMother(testPerson1);

            answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t"+ mother.getPersonID() +"\t\n" +
                    mother.getPersonID() + "\tpjohnst5\t"+mother.getFirstName() +"\t"+mother.getLastName()+"\tf\tnull\tnull\tnull\n";


            assertEquals(answer, myPersonDAO.tableToString()); //asserts the mother is now in the database

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testMakeFather(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            PersonModel testPerson1 = new PersonModel(testUser1);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t\t\n";

            assertEquals(answer, myPersonDAO.tableToString()); //shows that table has no father at all

            PersonModel father = myPersonDAO.makeFather(testPerson1);

            answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t"+ father.getPersonID() +"\t\t\n" +
                    father.getPersonID() + "\tpjohnst5\t"+father.getFirstName() +"\t"+father.getLastName()+"\tm\tnull\tnull\tnull\n";

            assertEquals(answer, myPersonDAO.tableToString()); //asserts the father is now in the database

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testGenerateGenerations(){
        try{
            UserModel testUser1 = new UserModel();
            testUser1.setUserName("pjohnst5");
            testUser1.setPassWord("puffinz");
            testUser1.setEmail("paulthegreat01@gmail.com");
            testUser1.setFirstName("paul");
            testUser1.setLastName("johnston");
            testUser1.setGender("m");
            testUser1.setPersonID("person_Id");

            PersonModel testPerson1 = new PersonModel(testUser1);
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\t\t\t\n";

            assertEquals(answer, myPersonDAO.tableToString()); //shows that table is just the one person
            EventDAO myEventDAO = db.getEventDAO();

            Random rand = new Random();
            int r = rand.nextInt((5 - 1)+1) + 1;
            double numG = (double) r;
            double numPeople = (Math.pow(2.0, (numG + 1.0)) - 1.0);
            int finalNumPeople = (int) numPeople; //final
            int finalNumEvents = (finalNumPeople * 4) - 4;

            myPersonDAO.generateGenerations(testPerson1, r, myEventDAO, 2017);


            String output = myPersonDAO.tableToString();
            String[] people = output.split("\n");

            assertEquals(finalNumPeople, people.length);

            output = myEventDAO.tableToString();
            String[] events = output.split("\n");

            assertEquals(finalNumEvents, events.length);



        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testTableToString(){
        try{
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

            String answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\tJeffrey\tNelly\tDruzinha\n";

            assertNotEquals(answer,myPersonDAO.tableToString());

            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            assertEquals(answer, myPersonDAO.tableToString()); //shows that table is all the fields of a person

            UserModel testUser2 = new UserModel(); //person 2
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

            answer = "person_Id\tpjohnst5\tpaul\tjohnston\tm\tJeffrey\tNelly\tDruzinha\n" +
                    "blahblahblah\tcarla\tcarla\tgardner\tf\tJeffrey\tNelly\tDaniel\n";

            assertNotEquals(answer, myPersonDAO.tableToString());

            myPersonDAO.insertPersonIntoDatabase(testPerson2); //inserts person2 into table

            assertEquals(answer,myPersonDAO.tableToString()); //shows that the table correctly "to-strings" multiple rows and all columns of the person table

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }

    }

    @Test
    public void testSelectAllPersons(){
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

            PersonModel testPerson3 = new PersonModel(); //person 3
            testPerson3.setDescendant("pjohnst5");
            testPerson3.setPersonID("Jeffrey");
            testPerson3.setFather("jorge");
            testPerson3.setSpouse("nancy");
            testPerson3.setMother("jane");
            testPerson3.setFirstName("Jeff");
            testPerson3.setLastName("johnston");
            testPerson3.setGender("m");

            myPersonDAO.insertPersonIntoDatabase(testPerson1);
            myPersonDAO.insertPersonIntoDatabase(testPerson2);
            myPersonDAO.insertPersonIntoDatabase(testPerson3);

            PersonModel[] expected = new PersonModel[2];
            expected[0] = testPerson1;
            expected[1] = testPerson3;

            PersonModel[] output = myPersonDAO.selectAllPersons(testUser1.getUserName()); //should only include person 1 and 3

            for (int i = 0; i < output.length; i++){
                assertEquals(expected[i],output[i]); //I overloaded the equals method in PersonModel
            }

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDeleteAllPeopleOfUser(){
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

            PersonModel testPerson3 = new PersonModel(); //person 3
            testPerson3.setDescendant("pjohnst5");
            testPerson3.setPersonID("Jeffrey");
            testPerson3.setFather("jorge");
            testPerson3.setSpouse("nancy");
            testPerson3.setMother("jane");
            testPerson3.setFirstName("Jeff");
            testPerson3.setLastName("johnston");
            testPerson3.setGender("m");

            myPersonDAO.insertPersonIntoDatabase(testPerson1);
            myPersonDAO.insertPersonIntoDatabase(testPerson2);
            myPersonDAO.insertPersonIntoDatabase(testPerson3);

            String answer = "blahblahblah\tcarla\tcarla\tgardner\tf\tJeffrey\tNelly\tDaniel\n";

            assertNotEquals(answer, myPersonDAO.tableToString());

            myPersonDAO.deleteAllPeopleOfUser(testUser1);

            assertEquals(answer, myPersonDAO.tableToString());

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testSelectSinglePerson(){
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

            PersonModel testPerson3 = new PersonModel(); //person 3
            testPerson3.setDescendant("pjohnst5");
            testPerson3.setPersonID("Jeffrey");
            testPerson3.setFather("jorge");
            testPerson3.setSpouse("nancy");
            testPerson3.setMother("jane");
            testPerson3.setFirstName("Jeff");
            testPerson3.setLastName("johnston");
            testPerson3.setGender("m");

            myPersonDAO.insertPersonIntoDatabase(testPerson1);
            myPersonDAO.insertPersonIntoDatabase(testPerson2);
            myPersonDAO.insertPersonIntoDatabase(testPerson3);

            PersonModel output = myPersonDAO.selectSinglePerson(testPerson1.getPersonID());

            assertEquals(testPerson1,output); //I overloaded the equals method of PersonModel

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

    @Test
    public void testDoesPersonExist(){
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
            myPersonDAO.insertPersonIntoDatabase(testPerson1);

            try{
                boolean output = myPersonDAO.doesPersonExist(testPerson1.getPersonID());
                assertEquals(true,output);
                output = myPersonDAO.doesPersonExist("not a valid personID");
                assertEquals(true,false);
            } catch (Database.DatabaseException e){
                String answer = "no such personID";
                assertEquals(answer, e.getMessage());
            }

        } catch (Database.DatabaseException e){
            assertEquals("Throwing exceptions is bad", e.getMessage());
        }
    }

}

