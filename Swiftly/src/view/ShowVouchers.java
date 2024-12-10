package view;

import java.util.ArrayList;

import factory.GameVoucher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShowVouchers {

    public void start(Stage primaryStage, GameVoucher gameVoucher, int quantity) {
        // Navbar
        VBox navbar = new VBox();
        navbar.setPadding(new Insets(10));
        navbar.setStyle("-fx-background-color: #2C3E50;");

        Label titleLabel = new Label("Order Summary");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        navbar.getChildren().add(titleLabel);

        // Fetch the voucher codes for the given quantity
        ArrayList<String> purchasedVouchers = gameVoucher.getVouchersForQuantity(quantity);

        // Display the purchased vouchers
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER_LEFT);

        Label productInfo = new Label("Product: " + gameVoucher.getName());
        productInfo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label totalLabel = new Label("Total Quantity: " + quantity);
        totalLabel.setStyle("-fx-font-size: 14px;");

        VBox voucherList = new VBox(5);
        for (String voucher : purchasedVouchers) {
            Label voucherLabel = new Label(voucher);
            voucherLabel.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 5px;");
            voucherList.getChildren().add(voucherLabel);
        }

        content.getChildren().addAll(productInfo, totalLabel, new Label("Your Voucher Codes:"), voucherList);

        // Back to Home Button
        Button backButton = new Button("Back to Home");
        backButton.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            Home home = new Home();
            home.start(primaryStage);
        });

        // Confirm Button
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> {
            System.out.println("Order Confirmed!");
            // You can redirect or perform other actions after confirmation
        });

        VBox buttonContainer = new VBox(10, confirmButton, backButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox mainContent = new VBox(20, content, buttonContainer);
        mainContent.setPadding(new Insets(20));
        mainContent.setAlignment(Pos.CENTER);

        // Main Layout
        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(mainContent);

        // Scene and Stage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Order Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
