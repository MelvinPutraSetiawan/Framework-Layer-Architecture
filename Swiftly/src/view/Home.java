package view;

import java.util.List;

import controller.ProductController;
import factory.Product;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utilities.Session;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Navbar
        HBox navbar = new HBox(20);
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #2C3E50;");

        // Logo
        ImageView logo = new ImageView(new Image("file:images/logo.png"));
        logo.setFitWidth(170);
        logo.setFitHeight(30);

        // Navbar Buttons
        Button homeBtn = new Button("Home");
        Button myProductBtn = new Button("MyProduct");
        Button orderBtn = new Button("Order");
        Button incomingOrderBtn = new Button("Incoming Order");
        Button logoutBtn = new Button("Logout");

        homeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        myProductBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        orderBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        incomingOrderBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        HBox navButtons = new HBox(10, homeBtn, myProductBtn, orderBtn, incomingOrderBtn, logoutBtn);
        navButtons.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(navButtons, Priority.ALWAYS);

        navbar.getChildren().addAll(logo, navButtons);

        // Main Content with ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox content = new VBox(20);

        // Banner Image
        ImageView bannerImage = new ImageView(new Image("file:images/SWIFTLY.png"));
        bannerImage.setFitWidth(1280);
        bannerImage.setFitHeight(400);
        bannerImage.setPreserveRatio(false);

        // Search Bar
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setPadding(new Insets(20, 10, 20, 10));

        TextField searchField = new TextField();
        searchField.setPromptText("Search for products...");
        searchField.setPrefWidth(1000);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");
        
        Button addProductButton = new Button("+ Add Product");
        addProductButton.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");

        addProductButton.setOnAction(e->{
        	AddProduct addProduct = new AddProduct();
        	addProduct.start(primaryStage);
        });
        
        searchBar.getChildren().addAll(searchField, searchButton, addProductButton);

        // Product List (Fetched from Controller)
        VBox productList = new VBox(10);
        productList.setPadding(new Insets(10));
        productList.setStyle("-fx-background-color: #F8F9F9;");

        // Adding banner, search bar, and product list to content
        content.getChildren().addAll(bannerImage, searchBar, productList);
        scrollPane.setContent(content);

        // Fetch products on search
        ProductController productController = new ProductController();

        searchButton.setOnAction(event -> {
            productList.getChildren().clear();
            String keyword = searchField.getText().toLowerCase();
            List<Product> products = productController.getProducts();

            for (Product product : products) {
                if (product.getName().toLowerCase().contains(keyword)) {
                    Label productLabel = new Label(product.getName() + " - $" + product.getPrice());
                    productList.getChildren().add(productLabel);
                }
            }
        });

        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchButton.fire();
            }
        });

        // Main Layout
        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(scrollPane);

        // Scene and Stage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        homeBtn.setOnAction(e->{
        	Home home = new Home();
        	home.start(primaryStage);
        });
        
        logoutBtn.setOnAction(e -> {
        	Session.clearSession();
        	Login login = new Login();
        	login.start(primaryStage);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
