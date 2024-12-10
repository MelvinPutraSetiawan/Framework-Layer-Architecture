package view;

import controller.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import state.Order;
import utilities.Session;

import java.util.ArrayList;

public class UserOrder {

    OrderController orderController = new OrderController();

    public void show(Stage primaryStage) {
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

        // Orders Container
        VBox ordersContainer = new VBox();
        ordersContainer.setSpacing(10);
        ordersContainer.setPadding(new Insets(10));
        ordersContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-padding: 15;");

        // Get user orders
        ArrayList<Order> allOrders = orderController.getOrders();
        int currentUserId = Session.getCurrentUser().getId();
        ArrayList<Order> userOrders = new ArrayList<>();

        // Filter orders for the current user
        for (Order order : allOrders) {
        	System.out.println(order.getBuyerId() + "|" + currentUserId);
            if (order.getBuyerId() == currentUserId) {
                userOrders.add(order);
            }
        }

        // Display user orders
        for (Order order : userOrders) {
            HBox orderBox = new HBox();
            orderBox.setSpacing(20);
            orderBox.setAlignment(Pos.CENTER_LEFT);
            orderBox.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

            // Order details
            Label orderIdLabel = new Label("Order ID: #" + order.getBuyerId());
            orderIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Label priceLabel = new Label("Price: Rp " + order.getProduct().getPrice());
            priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Label statusLabel = new Label(order.getCurrentState());
            statusLabel.setFont(Font.font("Arial", 14));
            statusLabel.setStyle("-fx-background-color: #f1c40f; -fx-padding: 5; -fx-border-radius: 5;");
            
            // Add components to the order box
            orderBox.getChildren().addAll(orderIdLabel, priceLabel, statusLabel);
            ordersContainer.getChildren().add(orderBox);
        }

        // No orders message
        if (userOrders.isEmpty()) {
            Label noOrdersLabel = new Label("You have no orders.");
            noOrdersLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            noOrdersLabel.setTextFill(Color.GRAY);
            noOrdersLabel.setAlignment(Pos.CENTER);
            ordersContainer.getChildren().add(noOrdersLabel);
        }
        // Root layout
        VBox content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f5f5f5;");

        // Add navbar and orders container to root
        content.getChildren().addAll(ordersContainer);
        
     // Main layout
        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(content);

        // Scene and Stage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("My Orders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
