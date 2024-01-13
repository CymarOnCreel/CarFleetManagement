package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.Main;
import application.alert.AlertMessage;
import application.dao.EmployeeDao;
import application.dao.RoleDao;
import application.dto.EmployeeDto;
import application.dto.RoleDto;
import application.util.UserSession;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListUsersFrameController implements Initializable {

	@FXML
	private RadioButton active;

	@FXML
	private RadioButton all;

	@FXML
	private RadioButton inactive;

	@FXML
	private Button close;

	@FXML
	private TableView<EmployeeDto> employeeListTable;

	@FXML
	private ChoiceBox<String> role;

	private ToggleGroup toggleGroup;
	List<EmployeeDto> allEmployees;
	List<EmployeeDto> filteredEmployeeList;
	private EmployeeDao employeeDao;
	ObservableList<EmployeeDto> employeeData = FXCollections.observableArrayList();
	private String loggedInUserRole;
	private static final String userRoleWhoCanDeleteUsers = "superadmin";
	Map<String, List<String>> allowedTransitions = new HashMap<>();

	@FXML
	void close(ActionEvent event) {
		Node root = (Node) event.getSource();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		setUpDao();
		allowedTransitions.put("admin", Arrays.asList("user"));
		allowedTransitions.put("superadmin", Arrays.asList("admin", "superadmin", "user"));
		setupButtons();
		setTable();
		System.out.println(loggedInUserRole + " id");
		Platform.runLater(() -> {
			fillComboboxWithRoles();

		});
		role.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				handleComboBoxChange(newValue);
			}
		});
	}
	public void setTable() {
		Platform.runLater(() -> {
			setUpDao();
			updateTableView(filteredEmployeeList);
		});	
	}
	private void setUpDao() {
		employeeDao = new EmployeeDao();
		allEmployees = employeeDao.getAll();
		filteredEmployeeList = allEmployees;
		loggedInUserRole = employeeDao.findById(UserSession.getUserId()).getRoleName();
	}
	
	private void setupButtons() {
		toggleGroup = new ToggleGroup();
		active.setToggleGroup(toggleGroup);
		all.setToggleGroup(toggleGroup);
		inactive.setToggleGroup(toggleGroup);
		toggleGroup.selectToggle(all);
	}

	private void handleComboBoxChange(String selectedRole) {
		if (!selectedRole.equalsIgnoreCase("Összes")) {
			filteredEmployeeList = allEmployees.stream()
					.filter(employee -> employee.getRoleName().equalsIgnoreCase(selectedRole))
					.collect(Collectors.toList());
		} else {
			filteredEmployeeList = allEmployees.stream().collect(Collectors.toList());
		}
		updateTableView(filteredEmployeeList);
	}

	@FXML
	private void filterActiveEmployees(ActionEvent event) {
		updateFilteredList();
		toggleGroup.selectToggle(active);
		updateTableView(filteredEmployeeList);
	}

	@FXML
	void filterInactiveEmployees(ActionEvent event) {
		updateFilteredList();
		toggleGroup.selectToggle(inactive);
		updateTableView(filteredEmployeeList);
	}

	@FXML
	private void getAllEmployees(ActionEvent event) {
		updateFilteredList();
		toggleGroup.selectToggle(all);
		updateTableView(filteredEmployeeList);
	}

	private void fillComboboxWithRoles() {
		role.getItems().clear();
		ObservableList<String> items = FXCollections.observableArrayList();
		items.add("Összes");
		RoleDao roleDao = new RoleDao();
		List<RoleDto> roles = roleDao.getAll();
		if (!roles.isEmpty()) {
			for (RoleDto role : roles) {
				items.add(role.getNameRole());
			}
		}
		role.setItems(items);
		role.getSelectionModel().selectFirst();
	}



	private void updateTableView(List<EmployeeDto> filteredEmployeees) {
		clearTable();
		populatetableView(filteredEmployeees);
		updateColumnWidths();
		initializeTableView();
	}

	private void populatetableView(List<EmployeeDto> employeees) {
		employeeData.clear();
		employeeData.addAll(employeees);
		employeeListTable.setItems(employeeData);
	}

	private void clearTable() {
		employeeData.clear();
		employeeListTable.getItems().clear();
		employeeListTable.refresh();
	}

	private void initializeTableView() {
		addColumns();
		setTableHeight();
		employeeListTable.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void addColumns() {
		employeeListTable.getColumns().clear();
		TableColumn<EmployeeDto, String> employeeName = new TableColumn<>("Felhasználó neve:");
		employeeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullNameHun()));
		TableColumn<EmployeeDto, String> employeeRole = new TableColumn<>("Jogosultság");
		employeeRole.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoleName()));
		TableColumn<EmployeeDto, String> employeeEmail = new TableColumn<>("Email");
		employeeEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		TableColumn<EmployeeDto, String> employeeDriverLicense = new TableColumn<>("Jogosítvány száma");
		employeeDriverLicense
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDriverLicense()));
		TableColumn<EmployeeDto, String> employeeStatus = new TableColumn<>("Státusz");
		employeeStatus
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isEnabledToString()));
		TableColumn<EmployeeDto, Void> employeeDeleteOrActivate = new TableColumn<>("Státusz váltás");
		employeeDeleteOrActivate.setCellFactory(param -> new TableCell<EmployeeDto, Void>() {
			private final Button employeeDeleteOrActivate = new Button();

			{
				if (loggedInUserRole.equalsIgnoreCase(userRoleWhoCanDeleteUsers)) {
					employeeDeleteOrActivate.setOnAction(event -> {
						EmployeeDto employee = getTableView().getItems().get(getIndex());
						handleInactivateOrActivateUser(employee);
					});
				} else {
					employeeDeleteOrActivate.setDisable(true);
				}
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					EmployeeDto employee = getTableView().getItems().get(getIndex());
					if (employee.isEnabled()) {
						employeeDeleteOrActivate.setText("INAKTÍVÁL");
					} else {
						employeeDeleteOrActivate.setText("AKTÍVÁL");
					}
					setGraphic(employeeDeleteOrActivate);
				}
			}
		});
		TableColumn<EmployeeDto, Void> employeeUpdate = new TableColumn<>("Felhasználó frissítése");
		employeeUpdate.setCellFactory(param -> new TableCell<EmployeeDto, Void>() {
			private final Button employeeUpdate = new Button();
			{

				employeeUpdate.setOnAction(event -> {
					EmployeeDto employee = getTableView().getItems().get(getIndex());
					handleUpdate(employee);
				});

			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					EmployeeDto employee = getTableView().getItems().get(getIndex());
					employeeUpdate.setText("MÓDOSÍT");
					employeeUpdate.setDisable(!employee.isEnabled());
				}
				setGraphic(employeeUpdate);

			}
		});
		employeeListTable.getColumns().addAll(employeeName, employeeRole, employeeEmail, employeeDriverLicense,
				employeeStatus, employeeUpdate, employeeDeleteOrActivate);
		employeeListTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void handleUpdate(EmployeeDto employee) {

		if (allowedTransitions.containsKey(loggedInUserRole)) {
			List<String> allowedRoles = allowedTransitions.get(loggedInUserRole);
			if (allowedRoles.contains(employee.getRoleName())) {
				openUpdateEmployeeFrame(employee);
			} else {
				new AlertMessage().showUnknownError("Permission Denied",
						"You do not have permission to update users to the selected role.");
			}
		} else {
			new AlertMessage().showUnknownError("Permission Denied", "You do not have permission to update users.");
		}
	}

	private void openUpdateEmployeeFrame(EmployeeDto employee) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/frame/UpdateEmployeeFrame.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			UpdateEmployeeFrameController updateController = loader.getController();
		    updateController.setListUsersController(this);
			updateController.initialize(employee);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/util/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Update Employee");
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Main.getPrimaryStage());
			stage.getIcons().add(new Image("application/pictures/logo.png"));
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleInactivateOrActivateUser(EmployeeDto employee) {
		employeeDao = new EmployeeDao();
		if (employee.isEnabled()) {
			employee.setEnabled(false);
			employeeDao.update(employee);
			updateFilteredList();
			updateTableView(filteredEmployeeList);
		} else {
			employee.setEnabled(true);
			employeeDao.update(employee);
			updateFilteredList();
			updateTableView(filteredEmployeeList);
		}
	}

	private void updateFilteredList() {
		if (active.isSelected()) {
			if (!role.getValue().equalsIgnoreCase("Összes")) {
				filteredEmployeeList = allEmployees.stream().filter(
						employee -> employee.isEnabled() && employee.getRoleName().equalsIgnoreCase(role.getValue()))
						.collect(Collectors.toList());
			} else {
				filteredEmployeeList = allEmployees.stream().filter(employee -> employee.isEnabled())
						.collect(Collectors.toList());
			}
		} else if (inactive.isSelected()) {
			if (!role.getValue().equalsIgnoreCase("Összes")) {
				filteredEmployeeList = allEmployees.stream().filter(
						employee -> !employee.isEnabled() && employee.getRoleName().equalsIgnoreCase(role.getValue()))
						.collect(Collectors.toList());
			} else {
				filteredEmployeeList = allEmployees.stream().filter(employee -> !employee.isEnabled())
						.collect(Collectors.toList());
			}
		} else {
			if (!role.getValue().equalsIgnoreCase("Összes")) {
				filteredEmployeeList = allEmployees.stream()
						.filter(employee -> employee.getRoleName().equalsIgnoreCase(role.getValue()))
						.collect(Collectors.toList());
			} else {
				filteredEmployeeList = allEmployees.stream().collect(Collectors.toList());

			}
		}

	}

	private void updateColumnWidths() {
		for (TableColumn<EmployeeDto, ?> column : employeeListTable.getColumns()) {
			column.setPrefWidth(computeColumnWidth(column));
		}
	}

	@SuppressWarnings("unchecked")
	private <T> double computeColumnWidth(TableColumn<EmployeeDto, T> column) {
		double maxWidth = 0.0;
		for (EmployeeDto item : employeeListTable.getItems()) {
			TableCell<EmployeeDto, T> cell = column.getCellFactory().call(column);
			if (cell != null) {
				Callback<TableColumn<EmployeeDto, T>, TableCell<EmployeeDto, T>> cellFactory = column.getCellFactory();
				TableCell<EmployeeDto, T> currentCell = cellFactory.call(column);
				currentCell.itemProperty().setValue((T) item);
				Text text = new Text(String.valueOf(currentCell.getText()));
				double cellWidth = text.getBoundsInLocal().getWidth() + 5;
				maxWidth = Math.max(maxWidth, cellWidth);
			}
		}
		return maxWidth + 10.0;
	}

	private void setTableHeight() {
		double rowHeight = 36.0;
		double tableHeight = Math.min(employeeListTable.getItems().size(), 10) * rowHeight;
		employeeListTable.setMinHeight(tableHeight);
		employeeListTable.setPrefHeight(tableHeight);
		employeeListTable.setMaxHeight(tableHeight);
	}
}
