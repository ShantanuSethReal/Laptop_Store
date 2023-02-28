package com.example.demo;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SignUp {
    private static byte[] getSha(String input){
        try{
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
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
    public static boolean customerSignUp(String userName,String passWord,String email){
        //SELECT * FROM customer WHERE email='shan@gmail.com' AND password='1234';
        try{
            String query="INSERT INTO customer(name,password,email) VALUES('"+userName+"','"+getEncryptedPassword(passWord)+"','"+email+"')";
            DataBaseConnection dbConn=new DataBaseConnection();
            return dbConn.insertUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
