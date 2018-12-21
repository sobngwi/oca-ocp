package org.sobngwi.oca.exam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Q11 {

    public static void main(String[] args) throws Exception {
       // Which resources are closed by the following code? (Choose all that apply.)
        Connection conn = DriverManager.getConnection("jdbc:derby:zoo");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select count(*) from animal");
        rs.close();
    }
}
