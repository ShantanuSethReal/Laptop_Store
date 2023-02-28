package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Ecommerce extends Application {
    private final int height=550,width=350,headerLine=50;
    ProductList productList=new ProductList();
    Pane bodyPane;
    Order Orderobj=new Order();
    Customer loggedInCustomer=null;
    Customer SignUpCustomer=null;
    ObservableList<Product> cartItemList= FXCollections.observableArrayList();
    Button signInButton=new Button("Sign In");

    Label welcomeLabel=new Label("Welcome Customer");
    private void addItemstoCart(Product product){
        if(cartItemList.contains(product)){
            return;
        }
        cartItemList.add(product);
        System.out.println("Products in Cart "+ cartItemList.stream().count() );
    }
    private GridPane headerBar(){
        TextField searchBar=new TextField();
        searchBar.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        Button searchButton=new Button("Search");
        searchButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
//        Button cartButton=new Button("Cart");
//        Button ordersButton=new Button("Your Orders");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                if(searchBar.getText().isEmpty()) {
                    bodyPane.getChildren().add(productList.getAllProducts());
                }
                else{
                    bodyPane.getChildren().add(productList.createTableFromList(Product.getAllSearchProducts(searchBar.getText())));
                }
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });

//        cartButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                bodyPane.getChildren().clear();
//                bodyPane.getChildren().add(productList.createTableFromList(cartItemList));
//            }
//        });

//        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                bodyPane.getChildren().clear();
//                bodyPane.getChildren().add(Orderobj.getOrder(loggedInCustomer));
//            }
//        });
        GridPane header=new GridPane();
        header.setTranslateX(65);
        header.setTranslateY(25);
        header.setHgap(10);
        header.setVgap(5);
        header.add(searchBar,0,0);
        header.add(searchButton,1,0);
        return header;
    }

    public GridPane headerAfterSignIn(){
        Button cartButton=new Button("View Cart   ");
        cartButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.createTableFromList(cartItemList));
            }
        });

        Button ordersButton=new Button("Your Orders");
        ordersButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(Orderobj.getOrder(loggedInCustomer));
            }
        });

        Button addToCart=new Button("Add to Cart");
        addToCart.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                if(product==null){
                    showMessage("Please select a product to add it to the Cart","No Item Selected");
                }
                else {
                    addItemstoCart(product);
                }
            }
        });
        Button placeOrderButton=new Button("Place Order");
        placeOrderButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int orderCount=0;
                if(!cartItemList.isEmpty()&&loggedInCustomer!=null){
                    orderCount=Orderobj.placeFinalOrder(cartItemList,loggedInCustomer);
                }
                if(orderCount>0){
                    showMessage("Order for "+orderCount+" products placed successfully!!","Order Update");
                }
                else{

                }


            }
        });

        Button buyNowButton=new Button("Buy Now- Instant Checkout");
        buyNowButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productList.getSelectedProduct();
                boolean orderStatus=false;
                if(product!=null&&loggedInCustomer!=null){
                    orderStatus=Orderobj.placeOrder(loggedInCustomer,product);
                    orderStatus=true;
                }
                if(orderStatus==true){
                    showMessage("Order placed","Orders");
                }
                else{

                }

            }
        });
        Button signOut=new Button("Sign Out");
        signOut.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        signOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });
        Label customerName=new Label();
        GridPane headerafter=new GridPane();
        headerafter.setTranslateX(65);
        headerafter.setTranslateY(520);
        headerafter.setHgap(15);
        headerafter.setVgap(10);
        headerafter.add(addToCart,0,0);
        headerafter.add(cartButton,1,0);

        headerafter.add(ordersButton,0,1);
        headerafter.add(customerName,0,3);
        headerafter.add(placeOrderButton,1,1);
        headerafter.add(signOut,0,2);

        //headerafter.add(buyNowButton,0,2);

        return headerafter;
    }
    private void showMessage(String message,String title){
        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle(title);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    private GridPane loginPage(){
        Label userLabel=new Label("User Name");
        Label passLabel=new Label("Password");
        TextField userName=new TextField();
        userName.setPromptText("Enter User Name");

        userName.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        PasswordField password=new PasswordField();
        password.setPromptText("Enter password");
        password.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        Button loginButton=new Button("Login");
        loginButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        Label messageLabel=new Label("Please Login");
        Label customerName=new Label();

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user= userName.getText();
                String pass=password.getText();
                loggedInCustomer=Login.customerLogin(user,pass);
                if(loggedInCustomer!=null){
                    welcomeLabel.setText("Welcome"+loggedInCustomer.getName());
                    messageLabel.setText("Login Successfull!!");
                    showMessage("You have Logged In for now\nWelcome to our Laptop Store","Log In Successfull!!");
                    customerName.setText("Currently logged in: " +loggedInCustomer.getName());
                    root.getChildren().add(headerAfterSignIn());
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productList.getAllProducts());
                }
                else{
                    messageLabel.setText("Login Failed");
                    showMessage("Please enter correct Login credentials","Log In Failed!!");
                }
            }
        });
        GridPane loginPane=new GridPane();
        loginPane.setTranslateX(50);
        loginPane.setTranslateY(75);
        loginPane.setHgap(20);
        loginPane.setVgap(20);
        loginPane.setTranslateX(10);
        loginPane.add(userLabel,0,0);
        loginPane.add(userName,1,0);
        loginPane.add(passLabel,0,1);
        loginPane.add(password,1,1);
        loginPane.add(loginButton,0,2);
        loginPane.add(messageLabel,1,2);
        return loginPane;
    }
    private GridPane SignUpPage(){
        Label userLabel=new Label("User Name");

        Label passLabel=new Label("Password");
        Label emailLabel=new Label("Email");
        Label contactLabel=new Label("Contact No");
        Button SignUpButton=new Button("Sign Up");
        SignUpButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        Button SignInButton=new Button("Sign In");
        SignInButton.setStyle( "-fx-background-color: yellow; -fx-border-color: black; -fx-border-radius: 5;" );
        TextField userName=new TextField();
        userName.setPromptText("Enter User Name");
        userName.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        PasswordField password=new PasswordField();
        password.setPromptText("Enter password");
        password.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        TextField emailName=new TextField();
        emailName.setPromptText("Enter Email");
        emailName.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        TextField contactName=new TextField();
        contactName.setPromptText("Enter Mobile Number");
        contactName.setStyle("-fx-border-color: black; -fx-border-radius: 6;" );
        Label accountLabel=new Label("Already have an account");
        Label messageLabel=new Label("Please Login");


        GridPane SignUpPane=new GridPane();

        SignUpPane.setPrefSize(250,260);
        SignUpPane.setTranslateX(50);
        SignUpPane.setTranslateY(75);
        SignUpPane.setHgap(20);
        SignUpPane.setVgap(20);
        SignUpPane.setTranslateX(10);
        SignUpPane.add(userLabel,0,0);
        SignUpPane.add(userName,1,0);
        SignUpPane.add(passLabel,0,1);
        SignUpPane.add(password,1,1);
        SignUpPane.add(emailLabel,0,2);
        SignUpPane.add(emailName,1,2);
        SignUpPane.add(contactLabel,0,3);
        SignUpPane.add(contactName,1,3);
        SignUpPane.add(SignUpButton,1,4);
//        accountLabel.setTranslateX(40);
        SignUpPane.add(accountLabel,0,5,2,1);
        SignInButton.setTranslateX(80);
        SignUpPane.add(SignInButton,1,5);
//        SignUpPane.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        SignInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });
        SignUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user= userName.getText();
                String pass=password.getText();
                String email=emailName.getText();
                String password=contactName.getText();
                boolean s=SignUp.customerSignUp(user,pass,email);
                if(s==true){
                    showMessage("You have created an account with us","Sign-Up Successfull!!");
                }
                else{
                    messageLabel.setText("Login Failed");
                }
            }
        });
        return SignUpPane;
    }
//    private GridPane footerBar(){
//        //Button buyNowButton=new Button("Buy Now");
//        Button addToCart=new Button("Add to Cart");
////        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
////            @Override
////            public void handle(ActionEvent actionEvent) {
////                Product product=productList.getSelectedProduct();
////                boolean orderStatus=false;
////                if(product!=null&&loggedInCustomer!=null){
////                    orderStatus=Orderobj.placeOrder(loggedInCustomer,product);
////                    orderStatus=true;
////                }
////                if(orderStatus==true){
////                    showMessage("Order placed","Orders");
////                }
////                else{
////
////                }
////
////            }
////        });
//
//        addToCart.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Product product=productList.getSelectedProduct();
//                addItemstoCart(product);
//            }
//        });
//        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                int orderCount=0;
//                if(!cartItemList.isEmpty()&&loggedInCustomer!=null){
//                    orderCount=Orderobj.placeFinalOrder(cartItemList,loggedInCustomer);
//                }
//                if(orderCount>0){
//                    showMessage("Order for "+orderCount+" products placed successfully!!","Order Update");
//                }
//                else{
//
//                }
//
//
//            }
//        });
//        GridPane footer=new GridPane();
//        footer.setTranslateX(65);
//        footer.setHgap(10);
//        footer.setTranslateY(headerLine+height);
//        //footer.add(buyNowButton,0,0);
//        footer.add(addToCart,0,0);
//        footer.add(placeOrderButton,1,0);
//        return footer;
//    }
    Pane root=new Pane();
    private Pane createContent(){
        root.setPrefSize(width,2*headerLine+height);
        bodyPane=new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateX(50);
        bodyPane.setTranslateY(headerLine);
        //bodyPane.getChildren().add(loginPage());
        bodyPane.getChildren().add(SignUpPage());
        root.getChildren().addAll(headerBar(),
                //loginPage(),
                //productList.getAllProducts()
        bodyPane);
        for (int i = 0; i < bodyPane.getChildren().size(); i++) {
            bodyPane.getChildren().get(i).setStyle("-fx-font-family: Verdana;");
        }

        root.setBackground(new Background(new BackgroundFill(Color.rgb(3, 252, 132),
                CornerRadii.EMPTY,
                Insets.EMPTY)));;
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}