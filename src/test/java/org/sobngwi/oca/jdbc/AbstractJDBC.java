package org.sobngwi.oca.jdbc;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AbstractJDBC {

    protected static String url = "jdbc:derby:zoo;create=true";

    @BeforeClass
    public static void setUpClass() throws Exception {

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {


            stmt.executeUpdate("CREATE TABLE species ("
                    + "id INTEGER PRIMARY KEY, "
                    + "name VARCHAR(255), "
                    + "num_acres DECIMAL(4,1))");

            stmt.executeUpdate("CREATE TABLE animal ("
                    + "id INTEGER PRIMARY KEY, "
                    + "species_id integer REFERENCES species (id), "
                    + "name VARCHAR(255), "
                    + "date_born TIMESTAMP)");

        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE animal");
            stmt.executeUpdate("DROP TABLE species");

        }
    }
}
