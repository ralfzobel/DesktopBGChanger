package de.acwhadk.rz.DesktopBGChanger.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Image icon = new Image(getClass().getResourceAsStream("Apps-Dialog-Shutdown-icon_32x32.png"));
			primaryStage.getIcons().add(icon);
			
			Parent root=null;
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("desktopBGChanger.fxml"));
			root = (Parent) loader.load();
			Controller controller = loader.<Controller>getController();
			controller.setStage(primaryStage);
			controller.initialize();
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("DesktopBGChangerGui");
		} catch (Exception e) {
			Controller.showException(e);
			System.exit(1);
		}
		
		primaryStage.show();
		
	}
}
