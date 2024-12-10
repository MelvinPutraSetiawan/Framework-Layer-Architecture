package view;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import observer.Trader;
import utilities.Session;

// LIST DESIGN PATTERN DIGUNAKAN:
// 1. Factory Method [Creational Design Pattern]
//    - Digunakan untuk melakukan pembuatan InGameItem/GameVouchers. Ada di package: factory
// 2. Observer [Behavioral Design Pattern]
//    - Digunakan supaya admin dapat menambahkan user yang akan bisa menerima event2 tertentu. Ada di package: observer
// 3. Adapter [Structural Design Pattern]
//    - Digunakan untuk menampilkan harga barang yang dibeli dengan mata uang berbeda-beda. Mulai dari rupiah, dollar, atau euro. Ada di package : adapter
// 4. State Design Pattern [Behavioral Design Pattern]
//    - Sebuah Order akan memiliki 4 state, yaitu Payment, Processing, Complete, dan Canceled. Ada di package : State

public class Login extends Application {
	@Override
    public void start(Stage primaryStage) {
		// ===============================================================
		// |                     UI/Front End Codes                      |
		// ===============================================================
        StackPane root = new StackPane();
        root.setPrefSize(1280, 720);
        
        Pane blackBackground = new Pane();
        blackBackground.setStyle("-fx-background-color: #000000;");
        blackBackground.setPrefSize(200, 200);
        
        ImageView backgroundImage = new ImageView(new Image("file:images/5.jpg"));
        backgroundImage.setFitHeight(801);
        backgroundImage.setFitWidth(1377);
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setOpacity(0.2);
        
        HBox mainHBox = new HBox();
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setPrefSize(200, 100);
        
        VBox leftVBox = new VBox();
        leftVBox.setAlignment(Pos.CENTER_RIGHT);
        leftVBox.setPrefSize(298, 720);

        StackPane leftStackPane = new StackPane();
        leftStackPane.setPrefSize(200, 150);

        ImageView leftImageView = new ImageView(new Image("file:images/loginbg1.png"));
        leftImageView.setFitHeight(366);
        leftImageView.setFitWidth(296);
        leftImageView.setPreserveRatio(true);

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #007bff;");
        registerButton.setTextFill(javafx.scene.paint.Color.WHITE);
        registerButton.setPadding(new Insets(5, 20, 5, 20));
        registerButton.setFont(Font.font("System Bold", 12));
        StackPane.setMargin(registerButton, new Insets(80, 0, 0, 0));

        leftStackPane.getChildren().addAll(leftImageView, registerButton);
        leftVBox.getChildren().add(leftStackPane);
        
        VBox rightVBox = new VBox();
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPrefSize(293, 333);

        StackPane rightStackPane = new StackPane();
        rightStackPane.setAlignment(Pos.CENTER_LEFT);
        rightStackPane.setPrefSize(200, 150);

        ImageView rightImageView = new ImageView(new Image("file:images/loginbg2.png"));
        rightImageView.setFitHeight(333);
        rightImageView.setFitWidth(297);
        rightImageView.setPreserveRatio(true);

        VBox loginVBox = new VBox();
        loginVBox.setAlignment(Pos.CENTER);
        loginVBox.setPrefSize(100, 200);

        Text loginText = new Text("Login");
        loginText.setFill(javafx.scene.paint.Color.WHITE);
        loginText.setFont(Font.font("Open Sans ExtraBold", 24));
        
        VBox formVBox = new VBox();
        formVBox.setAlignment(Pos.CENTER_LEFT);
        formVBox.setPrefSize(50, 50);
        VBox.setMargin(formVBox, new Insets(0, 40, 0, 40));

        Text emailText = new Text("Email");
        emailText.setFill(javafx.scene.paint.Color.WHITE);
        VBox.setMargin(emailText, new Insets(5, 0, 0, 0));

        TextField emailField = new TextField();
        emailField.setPrefSize(27, 27);
        emailField.setPromptText("john@example.com");
        emailField.setPadding(new Insets(5, 10, 5, 10));
        VBox.setMargin(emailField, new Insets(5, 0, 0, 0));

        Text passwordText = new Text("Password");
        passwordText.setFill(javafx.scene.paint.Color.WHITE);
        VBox.setMargin(passwordText, new Insets(5, 0, 0, 0));

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefSize(27, 27);
        passwordField.setPromptText("••••••••");
        passwordField.setPadding(new Insets(5, 10, 5, 10));
        VBox.setMargin(passwordField, new Insets(5, 0, 0, 0));

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007bff;");
        loginButton.setTextFill(javafx.scene.paint.Color.WHITE);
        loginButton.setPadding(new Insets(5, 20, 5, 20));
        loginButton.setFont(Font.font("System Bold", 12));
        VBox.setMargin(loginButton, new Insets(20, 0, 0, 0));
        
        Text errorText = new Text("Error Text");
        errorText.setFill(javafx.scene.paint.Color.RED);
        VBox.setMargin(errorText, new Insets(5, 0, 0, 0));
        
        formVBox.getChildren().addAll(emailText, emailField, passwordText, passwordField);
        loginVBox.getChildren().addAll(loginText, formVBox, loginButton);
        rightStackPane.getChildren().addAll(rightImageView, loginVBox);
        rightVBox.getChildren().add(rightStackPane);
        
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        
        root.getChildren().addAll(blackBackground, backgroundImage, mainHBox);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Swiftly");
        primaryStage.show();
        
        // ===============================================================
        // |                   Button Event Handling                     |
        // ===============================================================
        loginButton.setOnAction(e->{
        	String email = emailField.getText();
        	String password = passwordField.getText();
        	UserController userController = new UserController();
        	String message = userController.loginUser(email, password);
        	if(message!=null) {
        		errorText.setText(message);
        		try {
        			formVBox.getChildren().add(errorText);
				} catch (Exception e2) {
					// TODO: handle exception
				}
        	}else {
        		formVBox.getChildren().remove(errorText);
        		if(Session.getCurrentUser() instanceof Trader) {
        			Home home = new Home();
            		home.start(primaryStage);
        		}else {
        			AdminDashboard adminDashboard = new AdminDashboard();
        			adminDashboard.start(primaryStage);
        		}
        	}
        });
        
        registerButton.setOnAction(e->{
        	Register register = new Register();
        	try {
				register.start(primaryStage);
			} catch (Exception e2) {
				// TODO: handle exception
			}
        });
        
        loginButton.setOnMouseEntered(e -> {
            loginButton.setStyle("-fx-background-color: #0056b3;");
        });

        loginButton.setOnMouseExited(e -> {
            loginButton.setStyle("-fx-background-color: #007bff;");
        });
        
        registerButton.setOnMouseEntered(e -> {
        	registerButton.setStyle("-fx-background-color: #0056b3;");
        });

        registerButton.setOnMouseExited(e -> {
        	registerButton.setStyle("-fx-background-color: #007bff;");
        });
    }

    public static void main(String[] args) {
    	UserController userController = new UserController();
    	userController.createAdmin();
        launch(args);
    }
}
