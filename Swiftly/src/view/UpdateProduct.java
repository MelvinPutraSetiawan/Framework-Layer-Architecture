package view;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import controller.ProductController;
import factory.GameVoucher;
import factory.InGameItem;
import factory.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.Session;

public class UpdateProduct {

    private byte[] imageBytes;

    public void start(Stage primaryStage, Product product) {
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

        // Form Section
        VBox form = new VBox(20);
        form.setPadding(new Insets(20, 400, 20, 400));
        form.setAlignment(Pos.CENTER);

        // Form Fields
        TextField nameField = new TextField(product.getName());
        nameField.setPromptText("Product Name");

        TextArea descriptionField = new TextArea(product.getDescription());
        descriptionField.setPromptText("Description");
        descriptionField.setPrefHeight(80);

        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        priceField.setPromptText("Price");

        ImageView imageView = new ImageView();
        if (product.getImage() != null) {
            imageView.setImage(new Image(new ByteArrayInputStream(product.getImage())));
        }
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        Button imageButton = new Button("Change Image");
        Label imageLabel = new Label("No file selected");

        // Image Upload
        imageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            var file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                imageLabel.setText(file.getName());
                try (var fis = new java.io.FileInputStream(file)) {
                    imageBytes = fis.readAllBytes();
                    imageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Product Type Handling
        VBox dynamicFields = new VBox(10);
        dynamicFields.setPadding(new Insets(10));
        dynamicFields.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5px;");
        dynamicFields.setVisible(false);

        List<TextField> voucherFields = new ArrayList<>();
        TextField quantityField = new TextField();

        if (product instanceof GameVoucher) {
            dynamicFields.setVisible(true);
            var gameVoucher = (GameVoucher) product;

            Label addVoucherLabel = new Label("Edit Voucher Codes:");
            VBox voucherList = new VBox(5);

            for (String voucher : gameVoucher.getVoucherCodes()) {
                TextField voucherField = new TextField(voucher);
                voucherFields.add(voucherField);
                voucherList.getChildren().add(voucherField);
            }

            dynamicFields.getChildren().addAll(addVoucherLabel, voucherList);
        } else if (product instanceof InGameItem) {
            dynamicFields.setVisible(true);
            var inGameItem = (InGameItem) product;

            quantityField.setText(String.valueOf(inGameItem.getQuantity()));
            dynamicFields.getChildren().add(new Label("Quantity:"));
            dynamicFields.getChildren().add(quantityField);
        }

        // Submit Button
        Button submitButton = new Button("Update Product");
        submitButton.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;");

        // Error Text
        Text errorText = new Text();
        errorText.setFill(javafx.scene.paint.Color.RED);

        // Handle Submit Action
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String priceText = priceField.getText();

            ArrayList<String> vouchers = new ArrayList<>();
            if (product instanceof GameVoucher) {
                for (TextField voucherField : voucherFields) {
                    vouchers.add(voucherField.getText());
                }
            }

            String quantityText = "";
            if (product instanceof InGameItem) {
                quantityText = quantityField.getText();
            }

            ProductController productController = new ProductController();
            String error = productController.updateProduct(product, name, description, priceText, vouchers, quantityText, imageBytes);
            if (error != null) {
                errorText.setText(error);
                form.getChildren().add(errorText);
            } else {
                Home home = new Home();
                home.start(primaryStage);
            }
        });

        // Assemble Form
        VBox imageBox = new VBox(10, imageView, imageButton, imageLabel);
        form.getChildren().addAll(imageBox, nameField, descriptionField, priceField, dynamicFields, submitButton);

        // Main Layout
        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(new ScrollPane(form));

        // Scene and Stage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Update Product");
        primaryStage.setScene(scene);
        primaryStage.show();

        // ===============================================================
        // |                   Button Event Handling                     |
        // ===============================================================
        homeBtn.setOnAction(e->{
        	Home home = new Home();
        	home.start(primaryStage);
        });
        
        logoutBtn.setOnAction(e -> {
        	Session.clearSession();
        	Login login = new Login();
        	login.start(primaryStage);
        });
        
        myProductBtn.setOnAction(e->{
        	MyProduct myProduct = new MyProduct();
        	myProduct.start(primaryStage);
        });
        
        orderBtn.setOnAction(e->{
        	new UserOrder().show(primaryStage);
        });
        
        incomingOrderBtn.setOnAction(e->{
        	new IncomingOrder().show(primaryStage);
        });
    }
}
