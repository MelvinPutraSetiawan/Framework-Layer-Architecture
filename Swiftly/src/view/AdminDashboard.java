package view;

import java.util.ArrayList;
import java.util.stream.Collectors;

import controller.UserController;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
        // Downcast to Admin to access admin-specific methods
        Admin currentAdmin = (Admin) Session.getCurrentUser();

        // Fetch data
        ArrayList<Observer> traderInObserver = currentAdmin.getTraders();
        UserController userController = new UserController();
        ArrayList<User> allUsersList = userController.getUsers();

        // Observable lists for tables
        ObservableList<User> observerList = FXCollections.observableArrayList(
                traderInObserver.stream()
                        .map(observer -> (User) observer) // Downcast Observer to User
                        .collect(Collectors.toList()));

        ObservableList<User> allUsers = FXCollections.observableArrayList(
                allUsersList.stream()
                        .filter(user -> !observerList.contains(user) && !(user instanceof Admin))
                        .collect(Collectors.toList()));

        // Tables
        TableView<User> adapterTableView = new TableView<>();
        TableView<User> allUsersTableView = new TableView<>();

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

        // "Users on Adapter" Table
        TableColumn<User, String> adapterIdColumn = new TableColumn<>("ID");
        adapterIdColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getId())));

        TableColumn<User, String> adapterUsernameColumn = new TableColumn<>("Username");
        adapterUsernameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        adapterUsernameColumn.setPrefWidth(150);

        TableColumn<User, String> adapterEmailColumn = new TableColumn<>("Email");
        adapterEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        adapterEmailColumn.setPrefWidth(150);

        TableColumn<User, String> adapterActionColumn = new TableColumn<>("Action");
        adapterActionColumn.setCellFactory(col -> {
            TableCell<User, String> cell = new TableCell<>() {
                private final Button removeButton = new Button("Remove");

                {
                    removeButton.setOnAction(e -> {
                        User user = getTableView().getItems().get(getIndex());
                        currentAdmin.removeObserver((Observer) user); // Remove observer
                        observerList.remove(user);
                        adapterTableView.setItems(FXCollections.observableArrayList(observerList));
                        updateAllUsersTable(observerList, allUsers, allUsersTableView);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(removeButton);
                    }
                }
            };
            return cell;
        });

        adapterTableView.getColumns().addAll(adapterIdColumn, adapterUsernameColumn, adapterEmailColumn, adapterActionColumn);
        adapterTableView.setItems(observerList);

        // "View All Users" Table
        TableColumn<User, String> allUsersIdColumn = new TableColumn<>("ID");
        allUsersIdColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getId())));

        TableColumn<User, String> allUsersUsernameColumn = new TableColumn<>("Username");
        allUsersUsernameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        allUsersUsernameColumn.setPrefWidth(150);

        TableColumn<User, String> allUsersEmailColumn = new TableColumn<>("Email");
        allUsersEmailColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEmail()));
        allUsersEmailColumn.setPrefWidth(150);

        TableColumn<User, String> allUsersActionColumn = new TableColumn<>("Action");
        allUsersActionColumn.setCellFactory(col -> {
            TableCell<User, String> cell = new TableCell<>() {
                private final Button addButton = new Button("Add");

                {
                    addButton.setOnAction(e -> {
                        User user = getTableView().getItems().get(getIndex());
                        currentAdmin.addObserver((Observer) user); // Add observer
                        observerList.add(user);
                        adapterTableView.setItems(FXCollections.observableArrayList(observerList));
                        updateAllUsersTable(observerList, allUsers, allUsersTableView);
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(addButton);
                    }
                }
            };
            return cell;
        });

        allUsersTableView.getColumns().addAll(allUsersIdColumn, allUsersUsernameColumn, allUsersEmailColumn, allUsersActionColumn);
        updateAllUsersTable(observerList, allUsers, allUsersTableView);

        // Event creation text fields
        Label eventNameLabel = new Label("Event Name:");
        TextField eventNameField = new TextField();
        eventNameField.setPromptText("Enter event name");

        Label eventDescriptionLabel = new Label("Event Description:");
        TextField eventDescriptionField = new TextField();
        eventDescriptionField.setPromptText("Enter event description");

        Label eventDiscountLabel = new Label("Event Discount:");
        TextField eventDiscountField = new TextField();
        eventDiscountField.setPromptText("Enter event discount");

        Button createEventButton = new Button("Create Event");
        createEventButton.setOnAction(e -> {
            String name = eventNameField.getText();
            String description = eventDescriptionField.getText();
            int discount = Integer.parseInt(eventDiscountField.getText());

            currentAdmin.createEvent(name, description, discount); // Create the event
        });

        // Layout for event creation
        VBox eventFormBox = new VBox(10, eventNameLabel, eventNameField, eventDescriptionLabel, eventDescriptionField, eventDiscountLabel, eventDiscountField, createEventButton);
        eventFormBox.setPadding(new Insets(10));
        eventFormBox.setPrefWidth(300);

        // Layout
        VBox leftTableBox = new VBox(10, new Label("Users on Adapter"), adapterTableView);
        leftTableBox.setPadding(new Insets(10));
        leftTableBox.setPrefWidth(600);

        VBox rightTableBox = new VBox(10, new Label("View All Users (Not in Adapter)"), allUsersTableView);
        rightTableBox.setPadding(new Insets(10));
        rightTableBox.setPrefWidth(600);

        HBox tablesLayout = new HBox(10, leftTableBox, rightTableBox);
        tablesLayout.setPadding(new Insets(10));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navbar);
        mainLayout.setCenter(tablesLayout);
        mainLayout.setRight(eventFormBox); // Add event creation form on the right

        // Scene and Stage
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

    // Helper method to update "View All Users" table
    private void updateAllUsersTable(ObservableList<User> observerList, ObservableList<User> allUsers, TableView<User> allUsersTableView) {
        ObservableList<User> nonObserverUsers = FXCollections.observableArrayList(
                allUsers.stream()
                        .filter(user -> !observerList.contains(user))
                        .collect(Collectors.toList()));
        allUsersTableView.setItems(nonObserverUsers);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
