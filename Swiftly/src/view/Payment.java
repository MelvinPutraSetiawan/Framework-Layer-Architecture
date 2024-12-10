package view;

import adapter.Dollar;
import adapter.DollarAdapter;
import adapter.EURO;
import adapter.EUROAdapter;
import adapter.Rupiah;
import controller.OrderController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import observer.Trader;
import state.Order;
import utilities.Session;

import java.io.ByteArrayInputStream;

public class Payment {
    OrderController orderController = new OrderController();

    public void show(Stage primaryStage, Order order) {
        // Currency Adapters
        Rupiah rupiah = new Rupiah();
        EUROAdapter euroAdapter = new EUROAdapter(new EURO());
        DollarAdapter dollarAdapter = new DollarAdapter(new Dollar());

        // Check for discount
        double discount = 0.0;
        if (Session.getCurrentUser() instanceof Trader && ((Trader) Session.getCurrentUser()).getEvent() != null) {
            discount = ((Trader) Session.getCurrentUser()).getEvent().getDiscount();
        }

        // Calculate total price with discount
        double totalPrice = (order.getProduct().getPrice() * order.getQuantity()) * (1 - discount/100);

        // Currency Conversion
        double[] displayedPrice = {rupiah.calculatePrice(totalPrice)}; // Default to Rupiah

        // Root layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);
        root.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 10; -fx-padding: 20; -fx-effect: dropshadow(gaussian, gray, 10, 0.5, 0.0, 0.0);");
        root.setPrefHeight(500);
        root.setMinHeight(500);
        root.setMaxHeight(600);
        root.setPrefWidth(500);
        root.setMinWidth(500);
        root.setMaxWidth(550);
        
        // Product Image
        byte[] imageBytes = order.getProduct().getImage();
        Image productImage = new Image(new ByteArrayInputStream(imageBytes));
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitWidth(150);
        productImageView.setFitHeight(150);
        productImageView.setPreserveRatio(true);

        // Title
        Label titleLabel = new Label("Payment Details");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Product Details
        Label productNameLabel = new Label("Product: " + order.getProduct().getName());
        Label quantityLabel = new Label("Quantity: " + order.getQuantity());
        Label priceLabel = new Label("Price per unit: Rp " + order.getProduct().getPrice());
        Label discountLabel = new Label("Discount: " + (discount) + "%");
        Label totalLabel = new Label("Total (Rp): Rp " + displayedPrice[0]);
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        productNameLabel.setStyle("-fx-font-size: 14px;");
        quantityLabel.setStyle("-fx-font-size: 14px;");
        priceLabel.setStyle("-fx-font-size: 14px;");
        discountLabel.setStyle("-fx-font-size: 14px;");

        // Currency Options
        ToggleGroup currencyGroup = new ToggleGroup();
        RadioButton rupiahButton = new RadioButton("Rupiah (Rp)");
        RadioButton dollarButton = new RadioButton("Dollar ($)");
        RadioButton euroButton = new RadioButton("Euro (€)");
        rupiahButton.setToggleGroup(currencyGroup);
        dollarButton.setToggleGroup(currencyGroup);
        euroButton.setToggleGroup(currencyGroup);
        rupiahButton.setSelected(true);

        HBox currencyBox = new HBox(10, rupiahButton, dollarButton, euroButton);
        currencyBox.setAlignment(Pos.CENTER);

        // Update total price on currency change
        currencyGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == rupiahButton) {
                displayedPrice[0] = orderController.getPrice(rupiah, totalPrice);
                totalLabel.setText("Total (Rp): Rp " + displayedPrice[0]);
            } else if (newValue == dollarButton) {
                displayedPrice[0] = orderController.getPrice(dollarAdapter, totalPrice);
                totalLabel.setText("Total ($): $ " + displayedPrice[0]);
            } else if (newValue == euroButton) {
                displayedPrice[0] = orderController.getPrice(euroAdapter, totalPrice);
                totalLabel.setText("Total (€): € " + displayedPrice[0]);
            }
        });

        // Action Buttons
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: #27AE60; -fx-text-fill: white; -fx-font-size: 14px;");
        checkoutButton.setOnAction(event -> {
            System.out.println("[SYSTEM] : Payment completed!");
            System.out.println("[SYSTEM] : Total payment: " + displayedPrice[0]);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment Successful!", ButtonType.OK);
            alert.showAndWait();
            orderController.nextState(order);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-size: 14px;");
        cancelButton.setOnAction(event -> {
            System.out.println("[SYSTEM] : Cancelling order!");
            orderController.CancelOrder(order);
            new Home().start(primaryStage);
        });

        HBox buttonBox = new HBox(20, checkoutButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Layout assembly
        root.getChildren().addAll(productImageView, titleLabel, productNameLabel, quantityLabel, priceLabel, discountLabel, currencyBox, totalLabel, buttonBox);

        // Scene and stage setup
        Scene scene = new Scene(root, 1280, 720);
        StackPane container = new StackPane(root); // Center the box
        container.setAlignment(Pos.CENTER);
        Scene centeredScene = new Scene(container, 1280, 720);
        container.setStyle("-fx-background-color: #f4f4f4;");

        primaryStage.setTitle("Payment Page");
        primaryStage.setScene(centeredScene);
        primaryStage.show();
    }
}
