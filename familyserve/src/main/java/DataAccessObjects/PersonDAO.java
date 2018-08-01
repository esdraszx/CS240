package DataAccessObjects;

import Model.PersonModel;
import Model.UserModel;
import ObjectCodeDecode.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 *An Person Data access object which creates, selects, updates and deletes People from the SQL database
 *<pre>
 *<b>Domain</b>:
 *     connection : string
 *     databaseFileName : string
 *</pre>
 */
public class PersonDAO {

    private Connection conn;
    private String[] femaleNames;
    private String[] maleNames;
    private String[] lastNames;

    public PersonDAO(){

        femaleNames = new String[147];
        maleNames = new String[142];
        lastNames = new String[150];

        StringArray temp = Decoder.decodeNames("sql/fnames.json"); //gson.fromJson(new FileReader("fnames.json"), StringArray.class);
        StringArray temp2 = Decoder.decodeNames("sql/mnames.json"); //gson.fromJson(new FileReader("mnames.json"), StringArray.class);
        StringArray temp3 = Decoder.decodeNames("sql/snames.json"); //  gson.fromJson(new FileReader("snames.json"), StringArray.class);

            for (int i = 0; i < 147; i++){
                femaleNames[i] = temp.getValueAt(i);
            }

            for (int i = 0; i < 142; i++){
                maleNames[i] = temp2.getValueAt(i);
            }

            for (int i = 0; i < 150; i++){
                lastNames[i] = temp3.getValueAt(i);
            }

    }

    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    public void resetTable() throws Database.DatabaseException { //Also clears tables
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists people");
                stmt.executeUpdate("create table people (personID VARCHAR(50) NOT NULL PRIMARY KEY, descendant VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                        "gender CHAR(1) NOT NULL, father VARCHAR(50), mother VARCHAR(50), spouse VARCHAR(50), CONSTRAINT person_info UNIQUE (personID))");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset Person table failed");
        }
    }

    public void insertPersonIntoDatabase(PersonModel p) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {

                String sql = "insert into people (personID, descendant, firstName, lastName, gender, father, mother, spouse) values (?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1,p.getPersonID());
                stmt.setString(2,p.getDescendant());
                stmt.setString(3,p.getFirstName());
                stmt.setString(4,p.getLastName());
                if(p.getGender().length() != 1 || (!p.getGender().equals("m") && !p.getGender().equals("f"))){
                    throw new Database.DatabaseException("gender is incorrect format person");
                }
                stmt.setString(5,p.getGender());
                stmt.setString(6,p.getFather());
                stmt.setString(7,p.getMother());
                stmt.setString(8,p.getSpouse());

                if (stmt.executeUpdate() != 1) {
                    throw new Database.DatabaseException("insert person into database failed: Could not insert person");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("insert Person Into Database failed");
        }
    }

    public void updateMother(PersonModel p, String motherID) throws Database.DatabaseException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE people\n" +
                        "SET mother = '" + motherID + "' " +
                        "WHERE personID = '" + p.getPersonID() + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("update Mother failed");
        }
    }

    public void updateFather(PersonModel p, String fatherID) throws Database.DatabaseException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE people\n" +
                        "SET father = '" + fatherID + "' " +
                        "WHERE personID = '" + p.getPersonID() + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("updateFather failed");
        }
    }

    public void updateSpouse(PersonModel person, String spouseID) throws Database.DatabaseException{
        try {
            Statement stmt = null;
            try {

                String sql = "UPDATE people\n" +
                        "SET spouse = '" + spouseID + "' " +
                        "WHERE personID = '" + person.getPersonID() + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("update Spouse failed");
        }
    }

    public void generateGenerations(PersonModel orphan, int numGenerations, EventDAO myEventDAO, int orphanBirthYear) throws Database.DatabaseException { //recursive, receives person whose parent's need to be generated
        PersonModel mother = makeMother(orphan); //makes orphan's mother
        PersonModel father = makeFather(orphan); //makes orphan's father
        updateSpouse(father, mother.getPersonID()); //adds mother to be father's spouse
        updateSpouse(mother, father.getPersonID());  //adds father to be mother's spouse

        //Now make events for parents, root's events were taken care of in register, in other cases, orphan would be a father or mother already.

        int birthDateOfBoth = myEventDAO.generateEventDataParents(mother, father, orphanBirthYear); //Generates events for parents


        numGenerations--;
        if (numGenerations > 0){
            generateGenerations(mother, numGenerations, myEventDAO, birthDateOfBoth);
            generateGenerations(father, numGenerations,myEventDAO, birthDateOfBoth);
        }
    }

    public PersonModel makeMother(PersonModel orphan) throws Database.DatabaseException {
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(146);

        String motherID = UUID.randomUUID().toString();
        String descedantOfMother = orphan.getDescendant();
        String motherFirstName = femaleNames[r];
        r = rand.nextInt(149);
        String motherLastName = lastNames[r];
        String gender = "f";

        //Updates orphan's mother
        updateMother(orphan, motherID);

        //Make mother model
        PersonModel mother = new PersonModel();
        mother.setPersonID(motherID);
        mother.setDescendant(descedantOfMother);
        mother.setFirstName(motherFirstName);
        mother.setLastName(motherLastName);
        mother.setGender(gender);

        //insert mothermodel into table
        insertPersonIntoDatabase(mother);

        return mother;
    }

    public PersonModel makeFather(PersonModel orphan) throws Database.DatabaseException {
        //Orphan will always have a personID already, even user
        Random rand = new Random();
        int r = rand.nextInt(141);

        String fatherID = UUID.randomUUID().toString();
        String descedantOfFather = orphan.getDescendant();
        String fatherFirstName = maleNames[r];
        String fatherLastName = orphan.getLastName();
        String gender = "m";

        //Updates orphan's father
        updateFather(orphan, fatherID);

        //Make father model
        PersonModel father = new PersonModel();
        father.setPersonID(fatherID);
        father.setDescendant(descedantOfFather);
        father.setFirstName(fatherFirstName);
        father.setLastName(fatherLastName);
        father.setGender(gender);

        //insert father model into table
        insertPersonIntoDatabase(father);

        return father;
    }

    public String tableToString() throws Database.DatabaseException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    String firstName = rs.getString(4);
                    String lastName = rs.getString(5);
                    String gender = rs.getString(6);
                    String personID = rs.getString(7);
                    String extra = rs.getString(8);
                    out.append((word + "\t" + password + "\t" + email + "\t" + firstName + "\t" + lastName + "\t" + gender + "\t" + personID + "\t" + extra + "\n"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("seePeopleTable failed");
        }
        return out.toString();
    }

    public PersonModel[] selectAllPersons(String userName) throws Database.DatabaseException{ //person model is user's person representation
        ArrayList<PersonModel> out = new ArrayList<PersonModel>();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people WHERE descendant = '" + userName + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    PersonModel tempPerson = new PersonModel();
                    tempPerson.setPersonID(rs.getString(1));
                    tempPerson.setDescendant(rs.getString(2));
                    tempPerson.setFirstName(rs.getString(3));
                    tempPerson.setLastName(rs.getString(4));
                    tempPerson.setGender(rs.getString(5));
                    tempPerson.setFather(rs.getString(6));
                    tempPerson.setMother(rs.getString(7));
                    tempPerson.setSpouse(rs.getString(8));
                    out.add(tempPerson);
                    tempPerson = null;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("selectAllPersons failed");
        }
        PersonModel[] outFinal = new PersonModel[out.size()];
        outFinal = out.toArray(outFinal);
        return outFinal;
    }  //Working

    public void deleteAllPeopleOfUser(UserModel u) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM people WHERE descendant = '" + u.getUserName() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("deleteAllPeopleOfUser failed");
        }
    }

    public PersonModel selectSinglePerson(String personId) throws Database.DatabaseException{
        PersonModel out = new PersonModel();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people WHERE personID = '" + personId +"'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    out.setPersonID(rs.getString(1));
                    out.setDescendant(rs.getString(2));
                    out.setFirstName(rs.getString(3));
                    out.setLastName(rs.getString(4));
                    out.setGender(rs.getString(5));
                    out.setFather(rs.getString(6));
                    out.setMother(rs.getString(7));
                    out.setSpouse(rs.getString(8));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("getSinglePerson failed");
        }
        return out;
    }

    public boolean doesPersonExist(String personId) throws Database.DatabaseException{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from people WHERE personID = '" + personId + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such personID");
                } else {
                    return true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("no such personID");
        }
    }



}
