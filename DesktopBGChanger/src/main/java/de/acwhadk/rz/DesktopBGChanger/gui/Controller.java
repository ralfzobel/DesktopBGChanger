package de.acwhadk.rz.DesktopBGChanger.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class Controller {

    @FXML
    private ListView<String> listInclude;

    @FXML
    private ListView<String> listExclude;

    @FXML
    private ComboBox<String> cbxShutdownMode;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

	@FXML
	private void initialize() {
		btnOk.setOnAction(e -> handleOk());
		btnCancel.setOnAction(e -> handleCancel());
	}
	
	private Object handleOk() {
		return null;
	}

	private Object handleCancel() {
		return null;
	}

	public static void showException(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("Exception");
        alert.setContentText("" + e);
        alert.showAndWait();
	}
}
