package com.example.demo;
import java.sql.*;

public class DataBaseConnection {
    String dbUrl="jdbc:mysql://localhost:3306/ecommerce";
    String username="root";
    String password="Voda$1796";

    private Statement getStatement(){
        try {
            Connection conn= DriverManager.getConnection(dbUrl,username,password);
            return conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ResultSet getQueryTable(String query){
        Statement statement=getStatement();
        try {
            assert statement != null;
            return statement.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUpdate(String query){
        Statement statement=getStatement();
        try {
            statement.executeUpdate(query);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String query="SELECT * from product";
        DataBaseConnection dbConn=new DataBaseConnection();
        ResultSet rs=dbConn.getQueryTable(query);
        if(rs!=null){
            System.out.println("Connected to Database");
        }
    }

}
