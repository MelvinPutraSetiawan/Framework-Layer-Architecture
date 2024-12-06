package view;

import java.io.ByteArrayInputStream;

import factory.GameVoucher;
import factory.InGameItem;
import factory.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utilities.Session;

public class ProductDetail {

    public void show(Stage primaryStage, Product product) {
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
        
        homeBtn.setOnAction(e->{
        	Home home = new Home();
        	home.start(primaryStage);
        });
        
        logoutBtn.setOnAction(e -> {
        	Session.clearSession();
        	Login login = new Login();
        	login.start(primaryStage);
        });
        
        // Create a VBox for layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Product image
        ImageView productImage = new ImageView();
        try {
            productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
            productImage.setFitWidth(200);
            productImage.setFitHeight(200);
            productImage.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Failed to load product image: " + e.getMessage());
            productImage.setImage(new Image("https://via.placeholder.com/200")); // Default image
        }

        // Product name
        Label nameLabel = new Label("Name: " + product.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // Product price
        Label priceLabel = new Label("Price: $" + product.getPrice());
        priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

        // Product description
        Label descriptionLabel = new Label("Description: " + product.getDescription());
        descriptionLabel.setWrapText(true);

        // Product quantity
        Label quantityLabel = new Label();
        if (product instanceof InGameItem) {
            quantityLabel.setText("Quantity: " + ((InGameItem) product).getQuantity());
        } else {
            quantityLabel.setText("Quantity: " + ((GameVoucher) product).getVoucherCodes().size());
        }

        // Checkout button
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(event -> {
            System.out.println("Checkout for product: " + product.getName());
            // Add checkout logic here
        });

        // Add components to the layout
        root.getChildren().addAll(navbar, productImage, nameLabel, priceLabel, descriptionLabel, quantityLabel, checkoutButton);

        // Create a scene and set it on the primaryStage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Product Detail");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
