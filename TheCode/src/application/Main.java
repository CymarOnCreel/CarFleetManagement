package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("frame/CarNewFrame.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("util/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Car Fleet Management");
			primaryStage.getIcons().add(new Image("application/pictures/logo.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
