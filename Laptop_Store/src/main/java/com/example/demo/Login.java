package com.example.demo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
//MD5 is a cryptographic algorithm that provides the hash functions to get a fixed length 128-bit (16 bytes) hash value. Using Java, we can implement the MD5 hash in an application by using the MessageDigest class which is defined in java.security package. The Java MessageDigest class provides the following types of hash algorithms
public class Login {
    private static byte[] getSha(String input){
        try{
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Sign Magnitude -Big Integer
    private static String getEncryptedPassword(String password){
        try{
            BigInteger num=new BigInteger(1,getSha(password));
            StringBuilder hexString=new StringBuilder(num.toString(16));
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Customer customerLogin(String userEmail,String passWord){
        //String query="SELECT * FROM customer WHERE email='shantanu@gmail.com' AND password='1234'";
        String query="SELECT * FROM customer WHERE email='"+userEmail+"' and password='"+getEncryptedPassword(passWord)+"'";
        //System.out.println(query);
        DataBaseConnection dbConn=new DataBaseConnection();
        try {
            ResultSet rs = dbConn.getQueryTable (query);
            if (rs != null&& rs.next()) {
                return new Customer(rs.getInt("customer_id"),rs.getString("name"),rs.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        //System.out.println(customerLogin("shantanu@gmail.com","1234"));
//        System.out.println(getEncryptedPassword("angad"));
//    }
}
