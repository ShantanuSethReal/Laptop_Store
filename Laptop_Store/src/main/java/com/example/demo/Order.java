package com.example.demo;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {
     public TableView<Product> orderTable;
    public  boolean placeOrder(Customer customer,Product product){
        try{
            String placeOrder="INSERT INTO orders(customer_id,product_id,status) VALUES("+customer.getId()+","+product.getId()+",'Ordered')";
            DataBaseConnection dbConn=new DataBaseConnection();
            return dbConn.insertUpdate(placeOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public  int placeFinalOrder(ObservableList<Product> productObservableList,Customer customer){
        int count=0;
        for(Product product: productObservableList){
            if(placeOrder(customer,product)){
                count++;
            }
        }
        return count;
    }
    public  Pane createTableFromList(ObservableList<Product> productList){
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("Product_Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("Product_Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable =new TableView<>();
        orderTable.setTranslateY(40);
        orderTable.setItems(productList);
        orderTable.getColumns().addAll(id,name,price);
        id.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.1));
        name.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.65));
        price.prefWidthProperty().bind(orderTable.widthProperty().multiply(0.25));
        Pane tablePane=new Pane();
        tablePane.getChildren().add(orderTable);
        return tablePane;
    }
    public  Pane getOrder(Customer customer){
        String query="SELECT orders.order_id,product.product_id,product.product_name,product.price FROM orders INNER JOIN product ON orders.product_id=product.product_id where customer_id="+customer.getId();
        ObservableList<Product> order=Product.getProducts(query);
        return createTableFromList(order);
    }
}
