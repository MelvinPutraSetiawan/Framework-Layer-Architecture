package view;

import java.util.ArrayList;
import java.util.stream.Collectors;

import controller.UserController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import observer.Admin;
import observer.Observer;
import observer.User;
import utilities.Session;

public class AdminDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        Admin currentAdmin = (Admin) Session.getCurrentUser();

        // Fetch data
        ArrayList<Observer> traderInObserver = currentAdmin.getTraders();
        UserController userController = new UserController();
        ArrayList<User> allUsersList = userController.getUsers();

        // Observable lists for users
        ObservableList<User> observerList = FXCollections.observableArrayList(
                traderInObserver.stream()
                        .map(observer -> (User) observer) // Downcast Observer to User
                        .collect(Collectors.toList()));

        ObservableList<User> allUsers = FXCollections.observableArrayList(
                allUsersList.stream()
                        .filter(user -> !observerList.contains(user) && !(user instanceof Admin))
                        .collect(Collectors.toList()));

        // Observer list VBox
        VBox observerListBox = new VBox(10);
        observerListBox.setPadding(new Insets(10));

        // Non-observer list VBox
        VBox allUsersListBox = new VBox(10);
        allUsersListBox.setPadding(new Insets(10));

        // Initial population of user lists
        updateUserLists(observerListBox, allUsersListBox, observerList, allUsers, currentAdmin);

        // Create ScrollPanes for the lists
        ScrollPane observerScrollPane = new ScrollPane(observerListBox);
        observerScrollPane.setFitToWidth(true);

        ScrollPane allUsersScrollPane = new ScrollPane(allUsersListBox);
        allUsersScrollPane.setFitToWidth(true);

        VBox observerBox = new VBox(10, new Label("User List On Observer"), observerScrollPane);
        observerBox.setPadding(new Insets(10));
        observerBox.setPrefWidth(480);

        VBox allUsersBox = new VBox(10, new Label("User Not On Observer"), allUsersScrollPane);
        allUsersBox.setPadding(new Insets(10));
        allUsersBox.setPrefWidth(480);

        HBox userListsBox = new HBox(20, observerBox, allUsersBox);

        // Left Panel for Current Event and Set Event
        Label currentEventLabel = new Label("Current Event");
        currentEventLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label eventNameLabel;
        Label eventDescriptionLabel;
        Label eventDiscountLabel;

        if (currentAdmin.getCurrentEvent() != null) {
            eventNameLabel = new Label("Event Name: " + currentAdmin.getCurrentEvent().getName());
            eventDescriptionLabel = new Label("Event Description: " + currentAdmin.getCurrentEvent().getDescription());
            eventDiscountLabel = new Label("Discount: " + currentAdmin.getCurrentEvent().getDiscount());
        } else {
            eventNameLabel = new Label("Event Name: None");
            eventDescriptionLabel = new Label("Event Description: None");
            eventDiscountLabel = new Label("Discount: None");
        }

        VBox currentEventBox = new VBox(10, currentEventLabel, eventNameLabel, eventDescriptionLabel, eventDiscountLabel);
        currentEventBox.setPadding(new Insets(10));
        currentEventBox.setStyle("-fx-border-color: #CCCCCC; -fx-background-color: #FFFFFF;");

        Label setEventLabel = new Label("Set Event");
        setEventLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label eventNameInputLabel = new Label("Event Name");
        javafx.scene.control.TextField eventNameInput = new javafx.scene.control.TextField();

        Label eventDescriptionInputLabel = new Label("Event Description");
        javafx.scene.control.TextField eventDescriptionInput = new javafx.scene.control.TextField();

        Label eventDiscountInputLabel = new Label("Discount");
        javafx.scene.control.TextField eventDiscountInput = new javafx.scene.control.TextField();

        Button createEventButton = new Button("Create Event");
        createEventButton.setOnAction(e -> {
            String name = eventNameInput.getText();
            String description = eventDescriptionInput.getText();
            int discount = Integer.parseInt(eventDiscountInput.getText());

            currentAdmin.createEvent(name, description, discount);

            // Update current event labels
            try {
            	eventNameLabel.setText("Event Name: " + currentAdmin.getCurrentEvent().getName());
                eventDescriptionLabel.setText("Event Description: " + currentAdmin.getCurrentEvent().getDescription());
                eventDiscountLabel.setText("Discount: " + currentAdmin.getCurrentEvent().getDiscount());
			} catch (Exception e2) {
				// TODO: handle exception
			}
        });

        VBox setEventBox = new VBox(10, setEventLabel, eventNameInputLabel, eventNameInput,
                eventDescriptionInputLabel, eventDescriptionInput, eventDiscountInputLabel, eventDiscountInput,
                createEventButton);
        setEventBox.setPadding(new Insets(10));
        setEventBox.setStyle("-fx-border-color: #CCCCCC; -fx-background-color: #FFFFFF;");

        VBox leftPanel = new VBox(20, currentEventBox, setEventBox);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(300);

        // Navbar
        HBox navbar = new HBox();
        navbar.setPadding(new Insets(10));
        navbar.setSpacing(10);
        navbar.setStyle("-fx-background-color: #2A2A2A;");

        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Button logoutButton = new Button("Logout");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navbar.getChildren().addAll(titleLabel, spacer, logoutButton);

        // Main Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navbar);
        mainLayout.setLeft(leftPanel);
        mainLayout.setCenter(userListsBox);

        Scene scene = new Scene(mainLayout, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();

        logoutButton.setOnAction(e -> {
            Session.clearSession();
            Login login = new Login();
            login.start(primaryStage);
        });
    }

    private void updateUserLists(VBox observerListBox, VBox allUsersListBox, ObservableList<User> observerList, ObservableList<User> allUsers, Admin currentAdmin) {
        // Clear the existing list
        observerListBox.getChildren().clear();
        allUsersListBox.getChildren().clear();

        // Populate the observer list
        for (User user : observerList) {
            HBox userBox = createUserBox(user, "Remove", e -> {
                currentAdmin.removeObserver((Observer) user);
                observerList.remove(user);
                allUsers.add(user);
                updateUserLists(observerListBox, allUsersListBox, observerList, allUsers, currentAdmin);
            });
            observerListBox.getChildren().add(userBox);
        }

        // Populate the non-observer list
        for (User user : allUsers) {
            HBox userBox = createUserBox(user, "Add", e -> {
                currentAdmin.addObserver((Observer) user);
                allUsers.remove(user);
                observerList.add(user);
                updateUserLists(observerListBox, allUsersListBox, observerList, allUsers, currentAdmin);
            });
            allUsersListBox.getChildren().add(userBox);
        }
    }

    private HBox createUserBox(User user, String actionButtonText, EventHandler<ActionEvent> actionHandler) {
        Label nameLabel = new Label("Name: " + user.getName());
        Label emailLabel = new Label("Email: " + user.getEmail());
        Button actionButton = new Button(actionButtonText);
        actionButton.setOnAction(actionHandler);

        HBox userBox = new HBox(10, nameLabel, emailLabel, actionButton);
        userBox.setPadding(new Insets(5));
        userBox.setStyle("-fx-border-color: #CCCCCC; -fx-background-color: #FFFFFF;");
        return userBox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
