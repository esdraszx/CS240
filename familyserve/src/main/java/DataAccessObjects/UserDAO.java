package DataAccessObjects;

import Model.AuthTokenModel;
import Model.UserModel;
import Model.PersonModel;
import java.sql.*;
import java.util.UUID;


/**
 *A User Data access object which creates, selects, updates and deletes Users from the SQL database
 *<pre>
 *<b>Domain</b>:
 *     connection : string
 *     databaseFileName : string
 *</pre>
 */
public class UserDAO {

    private Connection conn;


    public UserDAO()  {


    }

    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    public void resetTable() throws Database.DatabaseException { //Also clears tables
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table users (username VARCHAR(50) NOT NULL PRIMARY KEY, password VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, firstName VARCHAR(50) NOT NULL, " +
                        "lastName VARCHAR(50) NOT NULL, gender CHAR(1) NOT NULL, personId VARCHAR(50) NOT NULL, CONSTRAINT user_info UNIQUE (username))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset UserTable failed");
        }
    }

    public void insertUserIntoDatabase(UserModel u) throws Database.DatabaseException { //Assumes that the personID is given, load, fill
        try {
            PreparedStatement stmt = null;
            try {

                String sql = "insert into users (username, password, email, firstName, lastName, gender, personId) values (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1,u.getUserName());
                stmt.setString(2,u.getPassWord());
                stmt.setString(3,u.getEmail());
                stmt.setString(4,u.getFirstName());
                stmt.setString(5,u.getLastName());

                if(u.getGender().length() != 1 || (!u.getGender().equals("m") && !u.getGender().equals("f"))){
                    throw new Database.DatabaseException("gender is incorrect format user");
                }
                stmt.setString(6,u.getGender());
                stmt.setString(7,u.getPersonID());
                if (stmt.executeUpdate() != 1) {
                    throw new Database.DatabaseException("insertUserIntoDatabase failed: Could not insert user");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("User already exists failed");
        }
    }

    public String tableToString() throws Database.DatabaseException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users";
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
                    out.append((word + "\t" + password + "\t" + email + "\t" + firstName + "\t" + lastName + "\t" + gender + "\t" + personID + "\n"));
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
            throw new Database.DatabaseException("seeTable users failed");
        }
        return out.toString();
    }

    public boolean doesUserNameExist(String u) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE username = '" + u + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such username");
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
            throw new Database.DatabaseException("no such username");
        }

    }

    public void deleteUser(UserModel u) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM users WHERE username = '" + u.getUserName() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("delete User failed");
        }
    }

    public UserModel getUserModel(String u) throws Database.DatabaseException { //copies everything from username to new user model EXCEPT personID where it makes a new PersonID
        UserModel out = new UserModel();

        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE username = '" + u +"'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    out.setUserName(rs.getString(1));
                    out.setPassWord(rs.getString(2));
                    out.setEmail(rs.getString(3));
                    out.setFirstName(rs.getString(4));
                    out.setLastName(rs.getString(5));
                    out.setGender(rs.getString(6));
                    out.setPersonID(UUID.randomUUID().toString()); //
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
            throw new Database.DatabaseException("getUserModel failed");
        }
        return out;
    }

    public boolean doUsernameAndPasswordExist(UserModel u) throws Database.DatabaseException{
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE username = '" + u.getUserName() + "' AND password = '" + u.getPassWord() + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such username and/or password");
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
            throw new Database.DatabaseException("no such username and/or password");
        }
    }

    public String getPersonIDOfUser(UserModel u) throws Database.DatabaseException {
        String personID = new String();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from users WHERE username = '" + u.getUserName() + "' AND password = '" + u.getPassWord() + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    personID = rs.getString(7);
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
            throw new Database.DatabaseException("getPersonIDOfUser failed");
        }

        return personID;
    }

}
