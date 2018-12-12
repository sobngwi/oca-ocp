package org.sobngwi.oca.jdbc;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class JdbcTests extends AbstractJDBC {

    @Before
    public void setUp() throws Exception {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO species VALUES (1, 'African Elephant', 7.5)");
            stmt.executeUpdate("INSERT INTO species VALUES (2, 'Zebra', 1.2)");

            stmt.executeUpdate("INSERT INTO animal VALUES (1, 1, 'Elsa', '2001-05-06 02:15:00')");
            stmt.executeUpdate("INSERT INTO animal VALUES (2, 2, 'Zelda', '2002-08-15 09:12:00')");
            stmt.executeUpdate("INSERT INTO animal VALUES (3, 1, 'Ester', '2002-09-09 10:36:00')");
            stmt.executeUpdate("INSERT INTO animal VALUES (4, 1, 'Eddie', '2010-06-08 01:24:00')");
            stmt.executeUpdate("INSERT INTO animal VALUES (5, 2, 'Zoe', '2005-11-12 03:44:00')");
        }
    }

    @After
    public void tearDown() throws Exception {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM animal");
            stmt.executeUpdate("DELETE FROM species");
        }
    }

    @Test
    public void checkConnectionOK() {
        try (Connection conn = DriverManager.getConnection(url)) {

            assertThat(conn.toString(), allOf(containsString("org.apache.derby.impl.jdbc.EmbedConnection"), containsString("(DATABASE = zoo)"), containsString("(DRDAID = null)")));
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = SQLException.class)
    public void checkConnectionKO_WrongDriverName() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:xxxx:zoo")) {
        }
    }


    @Test
    public void createAndExecuteSimpleStatement() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select count(*) from animal");
            rs.next();
            assertThat(rs.getInt(1), IsEqual.equalTo(5));
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void createAndExecutStatementWithResultSetTypeAndResultSetConcurencyModeDefaultValues() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet rs = stmt.executeQuery("select count(*) from animal");
            rs.next();
            assertThat(rs.getInt(1), IsEqual.equalTo(5));
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void statementType_executeUdate_insert() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = stmt.executeQuery("select count(*) from species");
            rs.next();
            int countBefore = rs.getInt(1);

            int result = stmt.executeUpdate("INSERT INTO species VALUES (3, 'African Panther', 9.5)");


            assertThat(result, IsEqual.equalTo(1));
            assertThat(countBefore, IsEqual.equalTo((result + 1)));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void statementType_executeUdate_update() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            int resultZero = stmt.executeUpdate("update species set name ='' where name='None'");
            int resultOne = stmt.executeUpdate( "update species set name ='Black Panther' where name='African Elephant'");

            assertThat(resultZero, IsEqual.equalTo(0));
            assertThat(resultOne, IsEqual.equalTo(1));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void statementType_executeUpdate_delete() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = stmt.executeQuery("select count(*) from animal");
            rs.next();
            int countBefore = rs.getInt(1);

            int resultZero = stmt.executeUpdate("delete from animal where id = 10");
            int resultOne = stmt.executeUpdate("delete from animal where id = 1");

            rs = stmt.executeQuery("select count(*) from animal");
            rs.next();
            int countAfter = rs.getInt(1);

            assertThat(resultZero, IsEqual.equalTo(0));
            assertThat(resultOne, IsEqual.equalTo(1));
            assertThat(countAfter, IsEqual.equalTo((countBefore - resultOne)));

            //roll back
            stmt.executeUpdate("INSERT INTO animal VALUES (1, 1, 'Elsa', '2001-05-06 02:15:00')");
            rs = stmt.executeQuery("select count(*) from animal");
            rs.next();
            int countRollBack = rs.getInt(1);

            assertThat(countRollBack, IsEqual.equalTo(5));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void execute_serveTo_executeQuery_Or_ExecuteUpdate() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            boolean isResultSet = stmt.execute("select count(*) from animal");

            if (isResultSet) {
                ResultSet rs = stmt.getResultSet();
                rs.next();
                assertThat(rs.getInt(1), IsEqual.equalTo(5));
            } else fail(); // The request was an insert--update-delete

            isResultSet = stmt.execute("update species set name ='' where name='None'");
            if (isResultSet) {
                fail();
            } else {
                int result = stmt.getUpdateCount();
                assertThat(result, equalTo(0));
            }

        } catch (SQLException e) {
            fail();
        }

    }

    @Test(expected = SQLException.class)
    public void aWrongMethodForSQLStatementThrowsRTE_UPDATE() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            stmt.executeUpdate("select * from animal");
        }
    }

    @Test(expected = SQLException.class)
    public void aWrongMethodForSQLStatementThrowsRTE_SELECT() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            stmt.executeQuery("update species set name ='' where name='None'");
        }
    }

    @Test
    public void readResultSetByColumnName() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet resultSet = stmt.executeQuery("Select * from species");
            Map<Integer, String> idToNameMap = new TreeMap<>();

            while (resultSet.next()) {
                idToNameMap.put(resultSet.getInt("id"), resultSet.getString("name"));
            }

            assertThat(idToNameMap.keySet().toString(),
                    allOf(containsString("1"), containsString("2")));
            assertThat(idToNameMap.values().toString(), allOf(containsString("African"),
                    containsString("Elephant"),
                    containsString("Zebra"), containsString("African Elephant")));
        } catch (SQLException e) {
            fail();
        }

    }

    @Test(expected = SQLException.class)
    public void attemptingToAccessNotExistingColumnsThrowsException() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet resultSet = stmt.executeQuery("Select * from species");

            if (resultSet.next())
                resultSet.getInt(0); // Datas start at columns 1
        }
    }

    @Test(expected = SQLException.class)
    public void attemptingToAccessInvalidDataThrowsException() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet resultSet = stmt.executeQuery("Select * from species where id = 100");

            resultSet.next();
            resultSet.getInt(1); // no specy with id = 100.
        }
    }

    @Test(expected = SQLException.class)
    public void notCollingTheNexMethodOnRseultSetThrowsException() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet resultSet = stmt.executeQuery("Select * from species");
            resultSet.getInt(1); // rs is till at the invalid position ie before the first row
        }
    }

    @Test(expected = SQLException.class)
    public void callingTheResultSetOnBadColumnNameThrowsException() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {

            ResultSet resultSet = stmt.executeQuery("Select * from species");

            if (resultSet.next())
                resultSet.getInt("BadColumnName");
        }

    }

    @Test
    public void beforeFirstOnResultSet() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = stmt.executeQuery("Select count(*) from animal");

            assertTrue(resultSet.isBeforeFirst());

            while (resultSet.next()) ; // Go to the end .
            assertFalse(resultSet.next());

            resultSet.beforeFirst();
            resultSet.next();

            assertThat(resultSet.getInt(1), equalTo(5));

        }
    }

    @Test
    public void afterLastOnResultSet() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = stmt.executeQuery("Select count(*) from animal");

            assertFalse(resultSet.isAfterLast());

            while (resultSet.next()) ; // Go to the end .
            assertTrue(resultSet.isAfterLast());

            assertTrue(resultSet.previous());
            assertThat(resultSet.getInt(1), equalTo(5));
        }
    }

    @Test
    public void firstAndLastGetRespectivelyToFirstAndLastElementOfRS_bothRetrunBoolean() throws SQLException {

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = stmt.executeQuery("Select count(*) from animal");

            assertTrue(resultSet.isBeforeFirst());
            assertFalse(resultSet.isAfterLast());
            resultSet.last();
            int firstCount = resultSet.getInt(1);
            resultSet.first();
            int secondCount = resultSet.getInt(1);
            assertEquals("There is only  one element, thus the first and the last should be the same ", firstCount, secondCount);

            resultSet = stmt.executeQuery("Select * from animal where id = 99"); // Not existing ID , ths no firs or last

            assertFalse(resultSet.last());
            assertFalse(resultSet.first());
        }
    }

    @Test
    public void absolutePositionFromResultSet_bothReturn_boolean() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = stmt.executeQuery("Select * from animal order by id asc ");

            assertTrue(resultSet.absolute(2));
            assertThat(resultSet.getString("name"), equalTo("Zelda"));
            assertTrue(resultSet.absolute(-2));
            assertThat(resultSet.getString("name"), equalTo("Eddie"));
            assertFalse(resultSet.absolute(0));
            assertTrue(resultSet.isBeforeFirst());

            assertTrue(resultSet.last());
            String last = resultSet.getString("name");
            assertTrue(resultSet.absolute(-1));
            String lastAbsOne = resultSet.getString("name");
            assertThat(last, equalTo(lastAbsOne));

        } catch (SQLException e) {
            fail();
        }
    }
}
