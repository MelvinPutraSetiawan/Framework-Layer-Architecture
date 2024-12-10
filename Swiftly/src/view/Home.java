package view;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import controller.ProductController;
import controller.UserController;
import factory.Product;
import observer.Event;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import observer.Trader;
import utilities.Session;

public class Home extends Application {
	// ===============================================================
	// |                       Variable Used                         |
	// ===============================================================
	ProductController productController = new ProductController();
	UserController userController = new UserController();
	Trader currentUser = (Trader) Session.getCurrentUser();
    @Override
    public void start(Stage primaryStage) {
    	// ===============================================================
    	// |                     UI/Front End Codes                      |
    	// ===============================================================
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
        
        ArrayList<Product> products = productController.getProducts();

        // Product Grid Pane
        GridPane productList = new GridPane();
        productList.setHgap(20);
        productList.setVgap(20); 
        productList.setStyle("-fx-background-color: #F8F9F9;");
        productList.setPadding(new Insets(10, 60, 60, 60));
        
        HBox eventBox = new HBox();
        eventBox.setAlignment(Pos.CENTER);
        eventBox.setPadding(new Insets(0));
        eventBox.setStyle("-fx-background-color: #E74C3C;");
        eventBox.setPrefHeight(30);
        if (currentUser.getEvent() != null) {
            Event event = currentUser.getEvent();
            Label eventLabel = new Label(
                event.getName() + " - " + event.getDescription() + " - " + event.getDiscount() + "%"
            );
            eventLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            eventLabel.setTextFill(javafx.scene.paint.Color.WHITE);
            eventLabel.setMaxWidth(Double.MAX_VALUE);
            eventLabel.setAlignment(Pos.CENTER);
            eventBox.getChildren().add(eventLabel);
        }
        
        Label noProduct = new Label("No products found!");
        noProduct.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        noProduct.setTextFill(Color.GRAY);
        noProduct.setAlignment(Pos.CENTER);
        noProduct.setPadding(new Insets(20, 20, 20, 60));
        
        if (products.isEmpty()) {
            content.getChildren().addAll(bannerImage, eventBox, searchBar, noProduct);
        }else {
            int column = 0;
            int row = 0;
            for (Product product : products) {
            	VBox productCard = createProductCard(product);
            	Button productButton = new Button();
            	productButton.setGraphic(productCard);
            	productButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
            	
            	productButton.setOnAction(e -> {
            	    ProductDetail productDetail = new ProductDetail();
            	    productDetail.show(primaryStage, product);
            	});
                productList.add(productButton, column, row);

                column++;
                if (column == 6) {
                    column = 0;
                    row++;
                }
            }
            content.getChildren().addAll(bannerImage, eventBox, searchBar, productList);
        }
        scrollPane.setContent(content);

        // Purpose: Search Logic, Searching for products.
        searchButton.setOnAction(event -> {
            productList.getChildren().clear();
            String keyword = searchField.getText().toLowerCase();
            ArrayList<Product> products1 = productController.getProducts();
            
            int column1=0;
            int row1=0;
            for (Product product : products1) {
                if (product.getName().toLowerCase().contains(keyword)) {
                	VBox productCard = createProductCard(product);
                	Button productButton = new Button();
                	productButton.setGraphic(productCard);
                	productButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                	
                	productButton.setOnAction(e -> {
                		ProductDetail productDetail = new ProductDetail();
                	    productDetail.show(primaryStage, product);
                	});
                    productList.add(productButton, column1, row1);

                    column1++;
                    if (column1 == 6) {
                        column1 = 0;
                        row1++;
                    }
                }
            }
            if(column1 == 0 && row1 == 0) {
            	content.getChildren().add(noProduct);
            }else {
            	content.getChildren().remove(noProduct);
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
    
    // Purpose: Use to make the product card UI
    private VBox createProductCard(Product product) {
    	byte[] imageData = product.getImage();
    	ImageView productImage = new ImageView();

    	if (imageData != null) {
    	    try {
    	        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
    	        Image image = new Image(inputStream);
    	        productImage.setImage(image);
    	    } catch (Exception e) {
    	    	
    	    }
    	}
    	productImage.setFitWidth(150);
    	productImage.setFitHeight(150);
    	productImage.setPreserveRatio(false);

        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label sellerLabel = new Label("By " + userController.getUserById(product.getCreatorId()).getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 10px;");

        Label priceLabel = new Label("Rp." + product.getPrice());
        priceLabel.setStyle("-fx-text-fill: green;");

        VBox productCard = new VBox(10, productImage, nameLabel, sellerLabel, priceLabel);
        productCard.setStyle("-fx-border-color: lightgray; -fx-border-radius: 10; -fx-padding: 10; -fx-background-radius: 10; -fx-background-color: white;");
        return productCard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
