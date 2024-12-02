package org.hyejung.persistence;


import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

import lombok.extern.java.Log;
@Log
public class JDBCTests {
   static {
      try {
         Class.forName("oracle.jdbc.OracleDriver");         
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void testConnection() {
      try(Connection con = 
            DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xepdb1", "ace", "ace")){
            System.out.println(con);         
         
      } catch (SQLException e) {
         fail(e.getMessage());
      }
   }
   
}
