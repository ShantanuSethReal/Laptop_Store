package com.example.demo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;

    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public Double getPrice(){
        return price.get();
    }

    public Product(int _id,String _name,Double _price){
        this.id=new SimpleIntegerProperty(_id);
        this.name=new SimpleStringProperty(_name);
        this.price=new SimpleDoubleProperty(_price);
    }
    public static ObservableList<Product> getAllProducts() {
        String query = "SELECT * from product";
        return getProducts(query);
    }
    public static ObservableList<Product> getAllSearchProducts(String search) {
        String query = "SELECT * from product where product_name like '%"+search+"%'";
        return getProducts(query);
    }

    public static ObservableList<Product> getProducts(String query){
        DataBaseConnection dbConn=new DataBaseConnection();
        ResultSet rs=dbConn.getQueryTable(query);
        ObservableList<Product> result= FXCollections.observableArrayList();
        try{
            if(rs!=null) {
                while (rs.next()) {
                    result.add(new Product(rs.getInt("product_id"), rs.getString("product_name"), rs.getDouble("price")));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
