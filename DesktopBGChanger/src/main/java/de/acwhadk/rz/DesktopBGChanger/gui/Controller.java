package de.acwhadk.rz.DesktopBGChanger.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import de.acwhadk.rz.DesktopBGChanger.cfg.Config;
import de.acwhadk.rz.DesktopBGChanger.cfg.ConfigToXML;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Controller {

	private static String[] ShutdownModes = { "Sign off", "Hibernate", "Shutdown", "Do nothing" };

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
	private Button btnAddInclude;

	@FXML
	private Button btnRemoveInclude;

	@FXML
	private Button btnAddExclude;

	@FXML
	private Button btnRemoveExclude;

	@FXML
	private TextField txtMinNum;

	@FXML
	private TextField txtMaxNum;

    @FXML
    private TextField txtSelectFolder;

    @FXML
    private Button btnSelectFolder;
    
    @FXML
    private CheckBox cbCaption;

    @FXML
    private CheckBox cbLeft;

    @FXML
    private CheckBox cbTop;

    @FXML
    private TextField txtFontSize;
    
	private Stage stage;
	private String currentFolder;
	
	void initialize() {
		btnOk.setOnAction(e -> handleOk());
		btnCancel.setOnAction(e -> handleCancel());
		btnAddInclude.setOnAction(e -> handleAddInclude());
		btnRemoveInclude.setOnAction(e -> handleRemoveInclude());
		btnAddExclude.setOnAction(e -> handleAddExclude());
		btnRemoveExclude.setOnAction(e -> handleRemoveExclude());
		btnSelectFolder.setOnAction(e -> handleSelectFolder());

		ObservableList<String> shutdownModes = FXCollections.observableArrayList();
		shutdownModes.addAll(ShutdownModes);
		cbxShutdownMode.setItems(shutdownModes);

		Config cfg = loadConfig();
		ObservableList<String> listIn = FXCollections.observableArrayList();
		listIn.addAll(cfg.getIncludes());
		listInclude.setItems(listIn);
		
		ObservableList<String> listEx = FXCollections.observableArrayList();
		listEx.addAll(cfg.getExcludes());
		listExclude.setItems(listEx);
		
		txtMinNum.setText(Integer.toString(cfg.getMinFiles()));
		txtMaxNum.setText(Integer.toString(cfg.getMaxFiles()));
		txtFontSize.setText(Integer.toString(cfg.getFontSize()));
		txtSelectFolder.setText(cfg.getDesktopFolder());
		cbxShutdownMode.getSelectionModel().select(cfg.getShutdownMode());
		
		cbCaption.setSelected(cfg.getCaption());
		cbLeft.setSelected(cfg.getLeft());
		cbTop.setSelected(cfg.getTop());
		
		currentFolder = cfg.getCurrentFile();
	}

	private Config loadConfig() {
		try {
			File file = ConfigToXML.getConfigFile();
			return ConfigToXML.load(file);
		} catch (JAXBException | IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cannot read configuration file");
			alert.setHeaderText("Configuration");
			alert.setContentText("using default configuration");
			alert.showAndWait();
		}
		Config cfg = new Config();
		cfg.setMaxFiles(100);
		cfg.setMinFiles(50);
		cfg.setShutdownMode(ShutdownModes[1]);
		cfg.setIncludes(new ArrayList<>());
		cfg.setExcludes(new ArrayList<>());
		return cfg;
	}

	private void saveConfig(Config cfg) {
		try {
			File file = ConfigToXML.getConfigFile();
			ConfigToXML.save(file, cfg);
		} catch (JAXBException | IOException e) {
			showException(e);
		}
	}


	private Object handleRemoveExclude() {
		ObservableList<String> items = listInclude.getSelectionModel().getSelectedItems();
		listExclude.getItems().removeAll(items);
		return null;
	}

	private Object handleAddExclude() {
		String folder = getFolder();
		if (folder != null) {
			listExclude.getItems().add(folder);
		}
		return null;
	}

	private Object handleRemoveInclude() {
		ObservableList<String> items = listInclude.getSelectionModel().getSelectedItems();
		listInclude.getItems().removeAll(items);
		return null;
	}

	private Object handleAddInclude() {
		String folder = getFolder();
		if (folder != null) {
			listInclude.getItems().add(folder);
		}
		return null;
	}

	private Object handleSelectFolder() {
		txtSelectFolder.setText(getFolder());
		return null;
	}

	private Object handleOk() {
		Config cfg = new Config();
		List<String> inList = new ArrayList<>();
		inList.addAll(listInclude.getItems());
		cfg.setIncludes(inList);
		List<String> exList = new ArrayList<>();
		exList.addAll(listExclude.getItems());
		cfg.setExcludes(exList);
		
		String shutdownMode = cbxShutdownMode.getSelectionModel().getSelectedItem();
		if (shutdownMode == null || shutdownMode.isEmpty()) {
			showException(new IllegalArgumentException("you must specify a shutdown mode"));
			return null;
		}
		cfg.setShutdownMode(shutdownMode);
		
		try {
			cfg.setMinFiles(Integer.parseInt(txtMinNum.getText()));
			cfg.setMaxFiles(Integer.parseInt(txtMaxNum.getText()));
			cfg.setFontSize(Integer.parseInt(txtFontSize.getText()));
		} catch(NumberFormatException e) {
			showException(e);
			return null;
		}
		
		String desktopFolder = txtSelectFolder.getText();
		if (desktopFolder == null || desktopFolder.isEmpty()) {
			showException(new IllegalArgumentException("you must select a target folder"));
			return null;
		}
		cfg.setDesktopFolder(desktopFolder);
		cfg.setCurrentFile(currentFolder);
		
		cfg.setCaption(cbCaption.isSelected());
		cfg.setLeft(cbLeft.isSelected());
		cfg.setTop(cbTop.isSelected());
		
		saveConfig(cfg);
		
		Platform.exit();
		return null;
	}

	private Object handleCancel() {
		Platform.exit();
		return null;
	}

	private String getFolder() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		final File selectedDirectory = directoryChooser.showDialog(stage);
		if (selectedDirectory != null) {
			return selectedDirectory.getAbsolutePath();
		}
		return null;
	}

	public static void showException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText("Exception");
		alert.setContentText("" + e);
		alert.showAndWait();
	}

	public void setStage(Stage stage) {
		this.stage = stage;		
	}
}
