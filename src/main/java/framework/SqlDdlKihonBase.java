package framework;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SqlDdlKihonBase {
    public static final String URL = "jdbc:sqlite::memory:";

    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             var stmt = conn.createStatement()) {
            setupDatabase(conn);
        } catch (SQLException e) {
            fail("SQLException was thrown. " + e.toString());
        }
    }

    @Test
    public void sampleTest() {
        try (Connection conn = DriverManager.getConnection(URL);
             var stmt = conn.createStatement();) {
            setupDatabase(conn);
            var sql = "select * from sqlite_master;";
            var sql2 = "PRAGMA table_info(Person)";
            try (var rs = stmt.executeQuery(sql2)) {
                while (rs.next()) {
                    var colCount = rs.getMetaData().getColumnCount();
                    for (int i = 1; i <= colCount; i++) {
                        System.out.println(rs.getMetaData().getColumnName(i) + " - " + rs.getString(i));
                    }
                    System.out.println("---");
                }
            }
        } catch (SQLException e) {
            fail("SQLException was thrown. " + e.toString());
        }
    }

    public void setupDatabase(Connection conn) throws SQLException {
        var stmt = conn.createStatement();
        stmt.execute(createPersonTable());
        //stmt.execute(createAddressTable());
        //stmt.execute(samplePeople());
        //stmt.execute(sampleAddresses());
    }

    public String samplePeople() {
        var sb = new StringBuilder();

        sb.append("INSERT INTO PERSON VALUES (1,'Tim','Rayburn',37),");
        sb.append("(2,'Kate','Rayburn',33),");
        sb.append("(3,'Chris','Jackson',38);");

        return sb.toString();
    }

    public String sampleAddresses() {
        var sb = new StringBuilder();

        sb.append("INSERT INTO ADDRESS VALUES (1,1,'1102 Angel Fire Lane',null,'Arlington','TX','76001'),");
        sb.append("(2,4,'1102 Angel Fire Lane',null,'Arlington','TX','76001');");

        return sb.toString();
    }

    public String createAddressTable() {
        var sb = new StringBuilder();

        sb.append("CREATE TABLE Address");
        sb.append("(");
        sb.append("AddressId int not null PRIMARY KEY, ");
        sb.append("PersonId int,");
        sb.append("Line1 varchar(50),");
        sb.append("Line2 varchar(50),");
        sb.append("City varchar(50),");
        sb.append("State varchar(50),");
        sb.append("Zip varchar(9)");
        sb.append(")");

        return sb.toString();
    }

    public String createPersonTable() {
        var sb = new StringBuilder();

        sb.append("CREATE TABLE Person");
        sb.append("(");
        sb.append("PersonId int not null PRIMARY KEY, ");
        sb.append("FirstName varchar(50),");
        sb.append("LastName varchar(50),");
        sb.append("Age int");
        sb.append(")");

        return sb.toString();
    }
}
