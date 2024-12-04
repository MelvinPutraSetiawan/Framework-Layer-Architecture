package view;

import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

// LIST DESIGN PATTERN DIGUNAKAN:
// 1. Factory Method [Creational Design Pattern]
//    - Use for creating objects like User, Order, Product, etc., depending on the user role (Admin or Customer).
//    - Example: UserFactory that instantiates different types of users based on the input.
// 2. Observer [Behavioral Design Pattern]
//    - Use for updating the UI or notifying other components when an order status changes or inventory updates.
//    - Example: Notify users when stock for a product is updated. Or their order status has changes.
// 3. Template Method [Behavioral Design Pattern]
//    - Use for defining a common flow for order processing while allowing specific steps to vary.
//    - Example: A ProcessOrder class with steps like validation, payment, and shipment, where shipment details vary based on the delivery method.
// 4. Adapter [Structural Design Pattern]
//    - Use for multiple payment method
//    - Example: Bank, QRIS, Paypal
// 5. Adapter Decorator [Structural Design Pattern]
//    - Use for each product where some product can have promo, refund, etc tag

public class Register extends Application {

	@Override
    public void start(Stage primaryStage) {
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

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007bff;");
        loginButton.setTextFill(javafx.scene.paint.Color.WHITE);
        loginButton.setPadding(new Insets(5, 20, 5, 20));
        loginButton.setFont(Font.font("System Bold", 12));
        StackPane.setMargin(loginButton, new Insets(80, 0, 0, 0));

        leftStackPane.getChildren().addAll(leftImageView, loginButton);
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

        Text loginText = new Text("Register");
        loginText.setFill(javafx.scene.paint.Color.WHITE);
        loginText.setFont(Font.font("Open Sans ExtraBold", 24));
        
        VBox formVBox = new VBox();
        formVBox.setAlignment(Pos.CENTER_LEFT);
        formVBox.setPrefSize(50, 50);
        VBox.setMargin(formVBox, new Insets(0, 40, 0, 40));
        
        Text nameText = new Text("Name");
        nameText.setFill(javafx.scene.paint.Color.WHITE);
        VBox.setMargin(nameText, new Insets(5, 0, 0, 0));

        TextField nameField = new TextField();
        nameField.setPrefSize(27, 27);
        nameField.setPromptText("John Doe");
        nameField.setPadding(new Insets(5, 10, 5, 10));
        VBox.setMargin(nameField, new Insets(5, 0, 0, 0));

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
        
        Text confirmPasswordText = new Text("Confirm Password");
        confirmPasswordText.setFill(javafx.scene.paint.Color.WHITE);
        VBox.setMargin(confirmPasswordText, new Insets(5, 0, 0, 0));

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefSize(27, 27);
        confirmPasswordField.setPromptText("••••••••");
        confirmPasswordField.setPadding(new Insets(5, 10, 5, 10));
        VBox.setMargin(confirmPasswordField, new Insets(5, 0, 0, 0));
        
        CheckBox ats = new CheckBox("Accept term and service.");
        ats.setTextFill(javafx.scene.paint.Color.WHITE);
        VBox.setMargin(ats, new Insets(5, 0, 0, 0));

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #007bff;");
        registerButton.setTextFill(javafx.scene.paint.Color.WHITE);
        registerButton.setPadding(new Insets(5, 20, 5, 20));
        registerButton.setFont(Font.font("System Bold", 12));
        VBox.setMargin(registerButton, new Insets(5, 0, 10, 0));
        
        Text errorText = new Text("Error Text");
        errorText.setFill(javafx.scene.paint.Color.RED);
        VBox.setMargin(errorText, new Insets(5, 0, 0, 0));
        
        formVBox.getChildren().addAll(nameText, nameField, emailText, emailField, passwordText, passwordField, confirmPasswordText, confirmPasswordField, ats);
        loginVBox.getChildren().addAll(loginText, formVBox, registerButton);
        rightStackPane.getChildren().addAll(rightImageView, loginVBox);
        rightVBox.getChildren().add(rightStackPane);
        
        mainHBox.getChildren().addAll(leftVBox, rightVBox);
        
        root.getChildren().addAll(blackBackground, backgroundImage, mainHBox);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Swiftly");
        primaryStage.show();
        
        // =========================
        // | Button Event Handling |
        // =========================
        registerButton.setOnAction(e->{
        	String name = nameField.getText();
        	String email = emailField.getText();
        	String password = passwordField.getText();
        	String confirmPassword = confirmPasswordField.getText();
        	Boolean atsChecked = ats.isSelected();
        	System.out.println(atsChecked);
        	UserController userController = new UserController();
        	String message = userController.registerUser(name, email, password, confirmPassword, atsChecked);
        	if(message!=null) {
        		errorText.setText(message);
        		try {
        			formVBox.getChildren().add(errorText);
				} catch (Exception e2) {
					// TODO: handle exception
				}
        	}else {
        		formVBox.getChildren().remove(errorText);
        	}
        });
        
        loginButton.setOnAction(e->{
        	Login login = new Login();
        	try {
				login.start(primaryStage);
			} catch (Exception e2) {
				// TODO: handle exception
			}
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
