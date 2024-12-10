package view;

import java.util.ArrayList;

import factory.GameVoucher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import state.Order;

public class ShowVouchers {

    public void start(Stage primaryStage, Order order, GameVoucher gameVoucher, int quantity) {
        // Main content box
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(30));
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #2C3E50; -fx-border-radius: 10px; -fx-padding: 20px; -fx-border-width: 2px;");
        contentBox.setMaxWidth(500);
        contentBox.setMaxHeight(500);
        
        // Product information
        Label productInfo = new Label("Product: " + gameVoucher.getName());
        productInfo.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #2C3E50;");

        Label priceInfo = new Label("Price: $" + gameVoucher.getPrice());
        priceInfo.setStyle("-fx-font-size: 16px; -fx-text-fill: #2C3E50;");

        Label totalLabel = new Label("Total Quantity: " + quantity);
        totalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2C3E50;");

        Label voucherHeading = new Label("Your Voucher Codes:");
        voucherHeading.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2C3E50;");

        // Display vouchers
        VBox voucherList = new VBox(10);
        voucherList.setAlignment(Pos.CENTER);
        ArrayList<String> purchasedVouchers = gameVoucher.getVouchersForQuantity(quantity);

        for (String voucher : purchasedVouchers) {
            Label voucherLabel = new Label(voucher);
            voucherLabel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 5px; -fx-padding: 10px; -fx-font-size: 14px;");
            voucherList.getChildren().add(voucherLabel);
        }

        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-padding: 10px 20px;");
        confirmButton.setOnAction(e -> {
            order.nextState();
            new UserOrder().show(primaryStage);
        });

        HBox buttonBox = new HBox(20, confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Combine all components
        contentBox.getChildren().addAll(productInfo, priceInfo, totalLabel, voucherHeading, voucherList, buttonBox);

        // Main Layout
        BorderPane root = new BorderPane();
        root.setCenter(contentBox);

        // Scene and Stage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Order Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
