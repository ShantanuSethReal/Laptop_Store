package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class HomePageSignUp {
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
    public GridPane SignUpPage(Pane bodyPane,GridPane loginPage){
        Label userLabel=new Label("User Name");
        Label passLabel=new Label("Password");
        Label emailLabel=new Label("Email");
        Label contactLabel=new Label("Contact No");
        Button SignUpButton=new Button("Sign Up");
        Button SignInButton=new Button("Sign In");
        TextField userName=new TextField();
        userName.setPromptText("Enter User Name");
        PasswordField password=new PasswordField();
        password.setPromptText("Enter password");
        TextField emailName=new TextField();
        emailName.setPromptText("Enter Email");
        TextField contactName=new TextField();
        contactName.setPromptText("Enter Mobile Number");
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
                bodyPane.getChildren().add(loginPage);
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
}
