package DataAccessObjects;
import Model.UserModel;
import Model.AuthTokenModel;
import java.sql.*;

/**
 *An AuthToken Data access object which creates, selects, updates and deletes auth tokens from the SQL database
 *<pre>
 *<b>Domain</b>:
 *     connection : string
 *     databaseFileName : string
 *</pre>
 */
public class AuthTokenDAO {

    private Connection conn;

    public AuthTokenDAO(){

    }

    public void setConnection(Connection c) throws Database.DatabaseException{
        conn = c;
    }

    public void resetTable() throws Database.DatabaseException { //Also clears tables
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists authTable");
                stmt.executeUpdate("create table authTable (auth_Token VARCHAR(50) NOT NULL PRIMARY KEY,\n" +
                        "\tuserName VARCHAR(50) NOT NULL,\n" +
                        "\tpersonID VARCHAR(50) NOT NULL,\n" +
                        "        CONSTRAINT auth_info UNIQUE (auth_Token))");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("reset authTable failed");
        }
    }


    public void insertTokenIntoTable(AuthTokenModel auth) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            try {

                String sql = "insert into authTable (auth_Token, userName, personID) values (?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1,auth.getAuthToken());
                stmt.setString(2,auth.getUserName());
                stmt.setString(3,auth.getPersonID());

                if (stmt.executeUpdate() != 1) {
                    throw new Database.DatabaseException("insertTokenintoTable failed: Could not insert authToken");
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("insertTokenINtoTable failed");
        }
    }

    public String tableToString() throws Database.DatabaseException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from authTable";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String word = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);

                    out.append((word + "\t" + password + "\t" + email + "\n"));
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
            throw new Database.DatabaseException("seeTable auth failed");
        }
        return out.toString();
    }

    public boolean doesAuthTokenExist(String authToken) throws Database.DatabaseException {
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from authTable WHERE auth_Token = '" + authToken + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();

                if (!rs.next() ) {
                    throw new Database.DatabaseException("no such authToken");
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
            throw new Database.DatabaseException("no such authToken");
        }
    }

    public AuthTokenModel getAuthTokenModel(String auth) throws Database.DatabaseException {
        AuthTokenModel out = new AuthTokenModel();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from authTable WHERE auth_Token = '" + auth +"'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    out.setAuthToken(rs.getString(1));
                    out.setUserName(rs.getString(2));
                    out.setPersonID(rs.getString(3));
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
            throw new Database.DatabaseException("get AuthTokenModel of auth token failed");
        }
        return out;
    }

}




/*

I was told not to delete all auth tokens of user when filling

public void deleteAllAuthTokensOfUser(UserModel u) throws Database.DatabaseException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM authTable WHERE userName = '" + u.getUserName() + "'");

            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new Database.DatabaseException("deleteAllAuthTOkensOfUser failed");
        }
    }
 */