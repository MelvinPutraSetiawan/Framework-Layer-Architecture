package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

// LIST DESIGN PATTERN DIGUNAKAN:
// 1. Singleton (Tidak ada di PPT) [Creational Design Pattern]
//    - Buat DatabaseConnection. Memastikan koneksi ke dalam database hanya terbuat sekali saja.
// 2. Factory Method [Creational Design Pattern]
//    - Use for creating objects like User, Order, Product, etc., depending on the user role (Admin or Customer).
//    - Example: UserFactory that instantiates different types of users based on the input.
// 3. Observer [Behavioral Design Pattern]
//    - Use for updating the UI or notifying other components when an order status changes or inventory updates.
//    - Example: Notify users when stock for a product is updated. Or their order status has changes.
// 4. Template Method [Behavioral Design Pattern]
//    - Use for defining a common flow for order processing while allowing specific steps to vary.
//    - Example: A ProcessOrder class with steps like validation, payment, and shipment, where shipment details vary based on the delivery method.
// 5. Adapter [Structural Design Pattern]
//    - Use for multiple payment method
//    - Example: Bank, QRIS, Paypal
// 6. Adapter Decorator [Structural Design Pattern]
//    - Use for each product where some product can have promo, etc tag

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Click me");
        btn.setOnAction(e -> System.out.println("Button clicked!"));

        Scene scene = new Scene(btn, 200, 100);
        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
