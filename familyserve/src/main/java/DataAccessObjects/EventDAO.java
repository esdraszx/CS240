package DataAccessObjects;

import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import ObjectCodeDecode.*;

import java.sql.*;
import java.util.*;



public class EventDAO {

    private Connection conn;
    private LocationArray locationArray;

    public EventDAO(){
        locationArray = Decoder.decodeLocations("sql/locations.json");
    }

    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    public void resetTable() throws Database.DatabaseException { //Also clears tables
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists events");
                stmt.executeUpdate("create table events (eventID VARCHAR(50) NOT NULL PRIMARY KEY, descendant VARCHAR(50) NOT NULL, peronID VARCHAR(50) NOT NULL, latitude REAL NOT NULL, " +
                        "longitude REAL NOT NULL, country VARCHAR(50) NOT NULL, city VARCHAR(50) NOT NULL, eventType VARCHAR(50) NOT NULL, eventYear INT NOT NULL, CONSTRAINT event_info UNIQUE (eventID))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset Event table failed");
        }
    }

    public void insertEventIntoDatabase(EventModel e) throws Database.DatabaseException { //Assumes that all the necessary information is given
        try {
            PreparedStatement stmt = null;
            try {

                String sql = "insert into events (eventID, descendant, peronID, latitude, longitude, country, city, eventType, eventYear) values (?,?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1,e.getEventID());
                stmt.setString(2,e.getDescendant());
                stmt.setString(3,e.getPersonID());
                stmt.setDouble(4,e.getLatitude());
                stmt.setDouble(5,e.getLongitude());
                stmt.setString(6,e.getCountry());
                stmt.setString(7,e.getCity());
                stmt.setString(8, e.getType());
                stmt.setInt(9, e.getYear());

                if (stmt.executeUpdate() != 1) {
                    throw new Database.DatabaseException("insertEventIntoDatabase failed: Could not insert event");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException ex) {
            throw new Database.DatabaseException("insertEventIntoDatabase failed");
        }
    }

    public int makeRootsEvents(PersonModel root) throws Database.DatabaseException {
        int rootBirthYear = 1960;

        //making root's birth
        EventModel birth = new EventModel();
        Random rand = new Random();

        birth.setEventID(UUID.randomUUID().toString());
        birth.setDescendant(root.getDescendant());
        birth.setPersonID(root.getPersonID());
        int r = rand.nextInt(977);
        IndividualLocation randLocation = locationArray.getLocations()[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setType("Birth");
        birth.setYear(rootBirthYear);

        insertEventIntoDatabase(birth); //inserts birth into database

        //Making root's baptism
        EventModel baptism = new EventModel();
        baptism.setEventID(UUID.randomUUID().toString());
        baptism.setDescendant(root.getDescendant());
        baptism.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        baptism.setLatitude(randLocation.getLatitude());
        baptism.setLongitude(randLocation.getLongitude());
        baptism.setCountry(randLocation.getCountry());
        baptism.setCity(randLocation.getCity());
        baptism.setType("Baptism");
        baptism.setYear(rootBirthYear + 15);  //He's a convert

        insertEventIntoDatabase(baptism);

        //Making root's adventure
        EventModel adventure = new EventModel();
        adventure.setEventID(UUID.randomUUID().toString());
        adventure.setDescendant(root.getDescendant());
        adventure.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        adventure.setLatitude(randLocation.getLatitude());
        adventure.setLongitude(randLocation.getLongitude());
        adventure.setCountry(randLocation.getCountry());
        adventure.setCity(randLocation.getCity());
        adventure.setType("Adventure");
        adventure.setYear(rootBirthYear + 16);

        insertEventIntoDatabase(adventure);

        //Making root's puppy
        EventModel purchase = new EventModel();
        purchase.setEventID(UUID.randomUUID().toString());
        purchase.setDescendant(root.getDescendant());
        purchase.setPersonID(root.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        purchase.setLatitude(randLocation.getLatitude());
        purchase.setLongitude(randLocation.getLongitude());
        purchase.setCountry(randLocation.getCountry());
        purchase.setCity(randLocation.getCity());
        purchase.setType("Bought a puppy");
        purchase.setYear(rootBirthYear + 10);

        insertEventIntoDatabase(purchase);

        return rootBirthYear;
    }

    public int generateEventDataParents(PersonModel mother, PersonModel father, int orphanBirthYear) throws Database.DatabaseException { //not recursive but will make 4 events for the given person, for now just birth

        EventModel birth = new EventModel(); //making mothers's birth
        Random rand = new Random();
        int parentsBirthDate = orphanBirthYear - 26;

        birth.setEventID(UUID.randomUUID().toString());
        birth.setDescendant(mother.getDescendant());
        birth.setPersonID(mother.getPersonID());
        int r = rand.nextInt(977);
        IndividualLocation randLocation = locationArray.getLocations()[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setType("Birth");
        birth.setYear(parentsBirthDate);

        insertEventIntoDatabase(birth); //inserted mother's birth

        birth.setEventID(UUID.randomUUID().toString()); //Making father's birth
        birth.setDescendant(father.getDescendant());
        birth.setPersonID(father.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setType("Birth");
        birth.setYear(parentsBirthDate);

        insertEventIntoDatabase(birth); //inserts father's birth

        EventModel death = new EventModel();  //making mothers death

        death.setEventID(UUID.randomUUID().toString());
        death.setDescendant(mother.getDescendant());
        death.setPersonID(mother.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        death.setLatitude(randLocation.getLatitude());
        death.setLongitude(randLocation.getLongitude());
        death.setCountry(randLocation.getCountry());
        death.setCity(randLocation.getCity());
        death.setType("Death");
        death.setYear(orphanBirthYear + 56);

        insertEventIntoDatabase(death); //inserts mothers death

        death.setEventID(UUID.randomUUID().toString()); //makes father's death
        death.setDescendant(father.getDescendant());
        death.setPersonID(father.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        death.setLatitude(randLocation.getLatitude());
        death.setLongitude(randLocation.getLongitude());
        death.setCountry(randLocation.getCountry());
        death.setCity(randLocation.getCity());
        death.setType("Death");
        death.setYear(orphanBirthYear + 54);

        insertEventIntoDatabase(death); //inserts father's death into database

        EventModel marriage = new EventModel();  //making marriage event for mother

        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setDescendant(mother.getDescendant());
        marriage.setPersonID(mother.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        marriage.setLatitude(randLocation.getLatitude());
        marriage.setLongitude(randLocation.getLongitude());
        marriage.setCountry(randLocation.getCountry());
        marriage.setCity(randLocation.getCity());
        marriage.setType("Marriage");
        marriage.setYear(parentsBirthDate + 21);

        insertEventIntoDatabase(marriage); //inserts marriage in for mother

        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setDescendant(father.getDescendant());
        marriage.setPersonID(father.getPersonID());
        marriage.setLatitude(randLocation.getLatitude());
        marriage.setLongitude(randLocation.getLongitude());
        marriage.setCountry(randLocation.getCountry());
        marriage.setCity(randLocation.getCity());
        marriage.setType("Marriage");
        marriage.setYear(parentsBirthDate + 21);

        insertEventIntoDatabase(marriage); //inserts marriage in for father

        EventModel boughtHouse = new EventModel(); //making bought house for mother

        boughtHouse.setEventID(UUID.randomUUID().toString());
        boughtHouse.setDescendant(mother.getDescendant());
        boughtHouse.setPersonID(mother.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        boughtHouse.setLatitude(randLocation.getLatitude());
        boughtHouse.setLongitude(randLocation.getLongitude());
        boughtHouse.setCountry(randLocation.getCountry());
        boughtHouse.setCity(randLocation.getCity());
        boughtHouse.setType("Bought house");
        boughtHouse.setYear(parentsBirthDate + 23);

        insertEventIntoDatabase(boughtHouse); //inserting bought house

        EventModel mission = new EventModel(); //making mission for father

        mission.setEventID(UUID.randomUUID().toString());
        mission.setDescendant(father.getDescendant());
        mission.setPersonID(father.getPersonID());
        r = rand.nextInt(977);
        randLocation = locationArray.getLocations()[r];
        mission.setLatitude(randLocation.getLatitude());
        mission.setLongitude(randLocation.getLongitude());
        mission.setCountry(randLocation.getCountry());
        mission.setCity(randLocation.getCity());
        mission.setType("Served Mission");
        mission.setYear(parentsBirthDate + 19);

        insertEventIntoDatabase(mission); //inserting mission

        return parentsBirthDate;
    }

    public void deleteAllEventsOfUser(UserModel u) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM events WHERE descendant = '" + u.getUserName() + "'");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("deleteAllEventsOfUser failed");
        }
    }

    public EventModel selectSingleEvent(String eventId) throws Database.DatabaseException {
        EventModel out = new EventModel();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events WHERE eventID = '" + eventId +"'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    out.setEventID(rs.getString(1));
                    out.setDescendant(rs.getString(2));
                    out.setPersonID(rs.getString(3));
                    out.setLatitude(rs.getDouble(4));
                    out.setLongitude(rs.getDouble(5));
                    out.setCountry(rs.getString(6));
                    out.setCity(rs.getString(7));
                    out.setType(rs.getString(8));
                    out.setYear(rs.getInt(9));
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
            throw new Database.DatabaseException("get single Event failed");
        }
        return out;
    }

    public boolean doesEventExist(String eventId) throws Database.DatabaseException{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events WHERE eventID = '" + eventId + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such eventID");
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
            throw new Database.DatabaseException("no such eventID");
        }
    }

    public EventModel[] selectAllEvents(String userName) throws Database.DatabaseException{
        ArrayList<EventModel> outArray = new ArrayList<EventModel>();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events WHERE descendant = '" + userName + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    EventModel out = new EventModel();
                    out.setEventID(rs.getString(1));
                    out.setDescendant(rs.getString(2));
                    out.setPersonID(rs.getString(3));
                    out.setLatitude(rs.getDouble(4));
                    out.setLongitude(rs.getDouble(5));
                    out.setCountry(rs.getString(6));
                    out.setCity(rs.getString(7));
                    out.setType(rs.getString(8));
                    out.setYear(rs.getInt(9));
                    outArray.add(out);
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
            throw new Database.DatabaseException("Select all events failed");
        }
        EventModel[] outFinal = new EventModel[outArray.size()];
        outFinal = outArray.toArray(outFinal);
        return outFinal;
    }

    public String tableToString() throws Database.DatabaseException {
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String eventID = rs.getString(1);
                    String descendant = rs.getString(2);
                    String personId = rs.getString(3);
                    Double latitude = rs.getDouble(4);
                    Double longitude = rs.getDouble(5);
                    String country = rs.getString(6);
                    String city = rs.getString(7);
                    String eventType = rs.getString(8);
                    int year = rs.getInt(9);

                    out.append((eventID + "\t" + descendant + "\t" + personId + "\t" + latitude + "\t" + longitude + "\t" + country + "\t" + city + "\t" + eventType + "\t" + year + "\n"));
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
            throw new Database.DatabaseException("Table to string events failed");
        }
        return out.toString();
    }

}
