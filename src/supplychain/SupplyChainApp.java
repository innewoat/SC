package supplychain;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import supplychain.SupplyChainView;

public class SupplyChainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SupplyChainModel model = new SupplyChainModel();
        SupplyChainView view = new SupplyChainView(model,primaryStage);

        Scene scene = new Scene(view.getLogInView(), 800, 600);
        SupplyChainPresenter presenter = new SupplyChainPresenter(model, view);

        primaryStage.setScene(scene);
        primaryStage.setTitle("供应链");
        primaryStage.show();
    }
}
