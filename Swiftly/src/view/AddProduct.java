package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import controller.ProductController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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

public class AddProduct extends Application {
	
	private byte[] imageBytes;

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

        // Form Section
        VBox form = new VBox(20);
        form.setPadding(new Insets(20, 400, 20, 400));
        form.setAlignment(Pos.CENTER);

        // Form Fields
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        descriptionField.setPrefHeight(80);

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button imageButton = new Button("Upload Image");
        Label imageLabel = new Label("No file selected");

        // File Chooser for Image
        FileChooser fileChooser = new FileChooser();
        imageButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                imageLabel.setText(file.getName());
                try (FileInputStream fis = new FileInputStream(file)) {
                    imageBytes = fis.readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Radio Buttons for Product Type
        ToggleGroup productTypeGroup = new ToggleGroup();
        RadioButton gameVoucherRadio = new RadioButton("Game Voucher");
        RadioButton inGameItemRadio = new RadioButton("In-Game Item");
        gameVoucherRadio.setToggleGroup(productTypeGroup);
        inGameItemRadio.setToggleGroup(productTypeGroup);

        // Dynamic Fields for Game Voucher or In-Game Item
        VBox dynamicFields = new VBox(10);
        dynamicFields.setPadding(new Insets(10));
        dynamicFields.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5px;");
        dynamicFields.setVisible(false);

        List<TextField> voucherFields = new ArrayList<>();
        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        gameVoucherRadio.setOnAction(event -> {
            dynamicFields.getChildren().clear();
            dynamicFields.setVisible(true);

            Label addVoucherLabel = new Label("Add Game Vouchers:");
            Button addVoucherButton = new Button("Add Voucher");
            VBox voucherList = new VBox(5);

            addVoucherButton.setOnAction(e -> {
                TextField newVoucherField = new TextField();
                newVoucherField.setPromptText("Voucher Code");
                voucherFields.add(newVoucherField);
                voucherList.getChildren().add(newVoucherField);
            });

            dynamicFields.getChildren().addAll(addVoucherLabel, addVoucherButton, voucherList);
        });

        inGameItemRadio.setOnAction(event -> {
            dynamicFields.getChildren().clear();
            dynamicFields.setVisible(true);
            dynamicFields.getChildren().add(quantityField);
        });

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white;");

        HBox imageUploadBox = new HBox(20);
        imageUploadBox.setAlignment(Pos.CENTER_LEFT);
        imageUploadBox.getChildren().addAll(imageButton, imageLabel);

        HBox radioButtonBox = new HBox(20);
        radioButtonBox.setAlignment(Pos.CENTER);
        radioButtonBox.getChildren().addAll(gameVoucherRadio, inGameItemRadio);
        
        Text errorText = new Text("Error Text");
        errorText.setFill(javafx.scene.paint.Color.RED);
        VBox.setMargin(errorText, new Insets(5, 0, 0, 0));

        // Add all elements to the form
        form.getChildren().addAll(
                imageUploadBox,
                nameField, descriptionField, priceField,
                radioButtonBox,
                dynamicFields, submitButton
        );

        // Wrap the form in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        // Main Layout
        BorderPane root = new BorderPane();
        root.setTop(navbar);
        root.setCenter(scrollPane);

        // Scene and Stage
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Add Product");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String priceText = priceField.getText();
            Boolean gameVoucher = gameVoucherRadio.isSelected();
            Boolean inGameItem = inGameItemRadio.isSelected();
            ArrayList<String> vouchers = new ArrayList<>();
            String quantityText="";

            if (gameVoucherRadio.isSelected()) {
                for (TextField voucherField : voucherFields) {
                    vouchers.add(voucherField.getText());
                }
            } else if (inGameItemRadio.isSelected()) {
                quantityText = quantityField.getText();
            }
            ProductController productController = new ProductController();
            String error = productController.createProduct(name, description, priceText, gameVoucher, inGameItem, vouchers, quantityText, imageBytes);
            if(error!=null) {
            	errorText.setText(error);
            	try {
            		form.getChildren().add(errorText);
				} catch (Exception e) {
					// TODO: handle exception
				}
            }else {
            	Home home = new Home();
            	home.start(primaryStage);
            }
        });
        
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

    public static void main(String[] args) {
        launch(args);
    }
}
