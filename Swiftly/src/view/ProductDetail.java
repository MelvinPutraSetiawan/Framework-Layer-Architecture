package view;

import java.io.ByteArrayInputStream;

import controller.OrderController;
import factory.GameVoucher;
import factory.InGameItem;
import factory.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import observer.Trader;
import utilities.Session;

public class ProductDetail {
	
	OrderController orderController = new OrderController();

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

        // Navbar Button Actions
        homeBtn.setOnAction(e -> {
            Home home = new Home();
            home.start(primaryStage);
        });

        logoutBtn.setOnAction(e -> {
            Session.clearSession();
            Login login = new Login();
            login.start(primaryStage);
        });

        myProductBtn.setOnAction(e -> {
            MyProduct myProduct = new MyProduct();
            myProduct.start(primaryStage);
        });

        // Product image
        ImageView productImage = new ImageView();
        try {
            productImage.setImage(new Image(new ByteArrayInputStream(product.getImage())));
            productImage.setFitWidth(300);
            productImage.setFitHeight(300);
            productImage.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Failed to load product image: " + e.getMessage());
            productImage.setImage(new Image("https://via.placeholder.com/300")); // Default image
        }

        // Product details
        Label nameLabel = new Label("Name: " + product.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        Label priceLabel = new Label("Price: Rp. " + product.getPrice());
        priceLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");

        Label descriptionLabel = new Label("Description: " + product.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Quantity spinner
        int maxQuantity = product instanceof InGameItem
                ? ((InGameItem) product).getQuantity()
                : ((GameVoucher) product).getVoucherCodes().size();

        Label stockLabel = new Label("Available Stock: " + maxQuantity);
        stockLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        Label quantityLabel = new Label("Select Quantity:");
        quantityLabel.setStyle("-fx-font-size: 14px;");

        Spinner<Integer> quantitySpinner = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxQuantity, 1);
        quantitySpinner.setValueFactory(valueFactory);

        HBox quantityBox = new HBox(10, quantityLabel, quantitySpinner);
        quantityBox.setAlignment(Pos.CENTER_LEFT);
        
        // Checkout button
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-size: 16px;");
        checkoutButton.setOnAction(event -> {
            int selectedQuantity = quantitySpinner.getValue();
            double discount = 0.0;
            if (Session.getCurrentUser() instanceof Trader && ((Trader) Session.getCurrentUser()).getEvent() != null) {
                discount = ((Trader) Session.getCurrentUser()).getEvent().getDiscount();
            }

            // Calculate total price with discount
            Double totalPrice = (product.getPrice() * selectedQuantity) * (1 - discount/100);
            System.out.println("[SYSTEM] : Checking out [ Quantity : " + selectedQuantity + " ] Product: " + product.getName());
            // Add checkout logic here
            new Payment().show(primaryStage, orderController.CheckOut(product, selectedQuantity, Session.getCurrentUser().getId(), totalPrice));
        });

        // Layout
        VBox productInfo = new VBox(10, nameLabel, priceLabel, descriptionLabel, stockLabel, quantityBox, checkoutButton);
        productInfo.setAlignment(Pos.CENTER_LEFT);
        productInfo.setPadding(new Insets(20));
        productInfo.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: lightgray; -fx-border-radius: 10; -fx-padding: 15;");
        productInfo.setPrefHeight(300);
        productInfo.setMinHeight(300);
        productInfo.setMaxHeight(300);

        HBox productContent = new HBox(30, productImage, productInfo);
        productContent.setPadding(new Insets(30));
        productContent.setAlignment(Pos.CENTER);

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(productContent);

        // Scene
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Product Detail");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
