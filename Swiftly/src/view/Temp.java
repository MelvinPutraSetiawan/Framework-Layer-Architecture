//package view;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//public class Temp {
//	// CODE UPLOAD IMAGE
////	private ArrayList<byte[]> imageByteData = new ArrayList<>();
////
////    @Override
////    public void start(Stage primaryStage) {
////        VBox root = new VBox(10);
////        Button uploadButton = new Button("Upload Image");
////        VBox imageDisplayBox = new VBox(10); // Container to display the images
////
////        uploadButton.setOnAction(e -> {
////            FileChooser fileChooser = new FileChooser();
////            fileChooser.getExtensionFilters().addAll(
////                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
////            );
////            File selectedFile = fileChooser.showOpenDialog(primaryStage);
////            if (selectedFile != null) {
////                try {
////                    // Convert the image file to a byte array
////                    FileInputStream fileInputStream = new FileInputStream(selectedFile);
////                    byte[] imageData = fileInputStream.readAllBytes();
////                    fileInputStream.close();
////
////                    // Store the byte array in the ArrayList
////                    imageByteData.add(imageData);
////                    System.out.println("Image uploaded: " + selectedFile.getName() + " (Size: " + imageData.length + " bytes)");
////
////                    // Convert the byte array to an Image and display it
////                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
////                    Image image = new Image(byteArrayInputStream);
////                    ImageView imageView = new ImageView(image);
////
////                    // Set image view properties (optional)
////                    imageView.setFitWidth(200); // Set desired width
////                    imageView.setPreserveRatio(true); // Maintain aspect ratio
////
////                    // Add the image to the display box
////                    imageDisplayBox.getChildren().add(imageView);
////                } catch (IOException ex) {
////                    ex.printStackTrace();
////                }
////            }
////        });
////
////        root.getChildren().addAll(uploadButton, imageDisplayBox);
////        Scene scene = new Scene(root, 1280, 720); // Increased height to display images
////        primaryStage.setScene(scene);
////        primaryStage.setTitle("Image Uploader and Viewer");
////        primaryStage.show();
////    }
////
////    public static void main(String[] args) {
////        launch(args);
////    }
//}
//
//
//
//
//package view;
//
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//// LIST DESIGN PATTERN DIGUNAKAN:
//// 1. Factory Method [Creational Design Pattern]
////    - Use for creating objects like User, Order, Product, etc., depending on the user role (Admin or Customer).
////    - Example: UserFactory that instantiates different types of users based on the input.
//// 2. Observer [Behavioral Design Pattern]
////    - Use for updating the UI or notifying other components when an order status changes or inventory updates.
////    - Example: Notify users when stock for a product is updated. Or their order status has changes.
//// 3. Template Method [Behavioral Design Pattern]
////    - Use for defining a common flow for order processing while allowing specific steps to vary.
////    - Example: A ProcessOrder class with steps like validation, payment, and shipment, where shipment details vary based on the delivery method.
//// 4. Adapter [Structural Design Pattern]
////    - Use for multiple payment method
////    - Example: Bank, QRIS, Paypal
//// 5. Adapter Decorator [Structural Design Pattern]
////    - Use for each product where some product can have promo, refund, etc tag
//
////public class Login extends Application {
////
////    @Override
////    public void start(Stage primaryStage) {
////    	showLoginPage(primaryStage);
////    }
////    
////    public void showLoginPage(Stage primaryStage) {
////    	StackPane leftPane = new StackPane();
////    	leftPane.setStyle("-fx-background-color: #343434;");
////    	
////    	Image image = new Image("file:images/loginbg.png");
////    	ImageView imageView = new ImageView(image);
////    	imageView.setPreserveRatio(false);
////    	imageView.setFitWidth(900);
////    	imageView.setFitHeight(720);
////    	imageView.setOpacity(0.25);
////    	
////    	VBox titleContainer = new VBox(10);
////    	titleContainer.setAlignment(Pos.TOP_CENTER);
////    	titleContainer.setPadding(new Insets(50));
////
////    	Text title = new Text("Swiftly");
////    	title.setFont(Font.font("Inter", FontWeight.BOLD, 40));
////    	title.setFill(Color.WHITE);
////    	
////    	titleContainer.getChildren().addAll(title);
////    	
////    	VBox descriptionContainer = new VBox(10);
////    	descriptionContainer.setAlignment(Pos.CENTER);
////    	descriptionContainer.setPadding(new Insets(20));
////    	
////    	Text descriptionTitle = new Text("Effortlessly purchase all your game items with ease!");
////    	descriptionTitle.setFont(Font.font("Inter", FontWeight.BOLD, 25));
////    	descriptionTitle.setFill(Color.LIGHTGRAY);
////    	descriptionTitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
////
////    	Text description = new Text("Quickly purchase all the in-game items you need on Swiftly for a seamless and hassle-free transaction.");
////    	description.setFont(Font.font("Inter", 18));
////    	description.setFill(Color.LIGHTGRAY);
////    	description.setWrappingWidth(450);
////    	description.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
////    	description.setTranslateY(10);
////
////    	Button viewMoreButton = new Button("REGISTER");
////    	viewMoreButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 5 20; -fx-font-weight : bold;");
////    	viewMoreButton.setTranslateY(30);
////    	
////    	descriptionContainer.getChildren().addAll(descriptionTitle, description, viewMoreButton);
////
////    	leftPane.getChildren().addAll(imageView, titleContainer, descriptionContainer);
////
////    	StackPane.setAlignment(titleContainer, Pos.TOP_CENTER);
////    	StackPane.setAlignment(descriptionContainer, Pos.TOP_CENTER);
////	
////    	StackPane rightPane = new StackPane();
////    	
////    	Image image2 = new Image("file:images/login.png");
////    	ImageView imageView2 = new ImageView(image2);
////    	imageView2.setPreserveRatio(false);
////    	imageView2.setFitWidth(380);
////    	imageView2.setFitHeight(720);
////
////        VBox loginForm = new VBox(20);
////        loginForm.setAlignment(Pos.CENTER);
////        loginForm.setStyle("-fx-background-color: rgba(59, 89, 152, 0.1);");
////
////        Text loginText = new Text("Sign In");
////        loginText.setFont(Font.font("Inter", FontWeight.BOLD, 28));
////        loginText.setFill(Color.ALICEBLUE);
////        loginText.setStyle("-fx-fill: #4444AA");
////
////        TextField usernameField = new TextField();
////        usernameField.setPromptText("Username");
////        usernameField.setMaxWidth(300);
////        usernameField.setStyle("-fx-font-size: 16;");
////
////        TextField passwordField = new TextField();
////        passwordField.setPromptText("Password");
////        passwordField.setMaxWidth(300);
////        passwordField.setStyle("-fx-font-size: 16;");
////
////        Button loginButton = new Button("LOGIN");
////        loginButton.setStyle("-fx-background-color: white; -fx-text-fill: #3b5998; -fx-font-size: 16; -fx-padding: 5 20; -fx-font-weight: bold;");
////
////        loginForm.getChildren().addAll(loginText, usernameField, passwordField, loginButton);
////        
////        rightPane.getChildren().addAll(imageView2, loginForm);
////
////        HBox root = new HBox();
////        root.getChildren().addAll(leftPane, rightPane);
////
////        leftPane.setPrefWidth(900);
////        rightPane.setPrefWidth(380);
////
////        Scene scene = new Scene(root, 1280, 720);
////        primaryStage.setTitle("3DMGame Login");
////        primaryStage.setScene(scene);
////        primaryStage.show();
////    }
////
////    public static void main(String[] args) {
////        launch(args);
////    }
////}
//
