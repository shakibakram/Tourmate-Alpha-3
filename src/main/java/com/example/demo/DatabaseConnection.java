package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaselink;

     public Connection getConnection() {
          String databaseName ="tourmate";
          String databaseuser = "shakram";
          String databasepassword = "111222333";
          String url = "jdbc:mysql://localhost/"+ databaseName;

          try {
               Class.forName("com.mysql.cj.jdbc.Driver");
               databaselink = DriverManager.getConnection(url, databaseuser, databasepassword);
          }
          catch (Exception e){
               e.printStackTrace();
          }


          return databaselink;
     }
}
