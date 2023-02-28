package com.example.demo;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;


public class ProductList {

    public TableView<Product> productTable;

    public Pane getAllProducts(){
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("Product_Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


//        ObservableList<Product> data= FXCollections.observableArrayList();
//        data.addAll(new Product(1,"Laptop",9000.0),
//                 new Product(2,"Laptop_2",9500.0));

        ObservableList<Product> productList=Product.getAllProducts();

        productTable =new TableView<>();

        //productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        productTable.setTranslateX(100);
         productTable.setTranslateY(40);
       // productTable.setPrefSize(290,300);
        productTable.setItems(productList);
        productTable.getColumns().addAll(id,name,price);
        id.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        name.prefWidthProperty().bind(productTable.widthProperty().multiply(0.65));
        price.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));


        Pane tablePane=new Pane();
        tablePane.getChildren().add(productTable);
        return tablePane;
    }
    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }
    public Pane createTableFromList(ObservableList<Product> productList){
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("Product Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
       // productTable.setPrefSize(290,300);
        productTable =new TableView<>();
        //productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setTranslateY(40);

        productTable =new TableView<>();
        productTable.setItems(productList);
        productTable.getColumns().addAll(id,name,price);
        id.prefWidthProperty().bind(productTable.widthProperty().multiply(0.1));
        name.prefWidthProperty().bind(productTable.widthProperty().multiply(0.65));
        price.prefWidthProperty().bind(productTable.widthProperty().multiply(0.25));

        Pane tablePane=new Pane();
        tablePane.setTranslateY(40);
        tablePane.getChildren().add(productTable);
        return tablePane;
    }
}
