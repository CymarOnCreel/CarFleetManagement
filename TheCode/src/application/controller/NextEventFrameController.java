package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import application.util.InspectionExpirySorter;
import application.util.InsuranceSorter;
import application.util.MaintenanceSorter;
import application.util.NextEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NextEventFrameController implements Initializable{

    @FXML
    private TableView<NextEvent> tableNextEvent;

    @FXML
    private TableColumn<NextEvent, LocalDate> columnDate;

    @FXML
    private TableColumn<NextEvent, String> columnLicensPlate;

    @FXML
    private TableColumn<NextEvent, String> columnType;

    @FXML
    private TableColumn<NextEvent, Integer> columnRemainingDistance;
    
    private ObservableList<NextEvent> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillTableData();
		
	}

	@SuppressWarnings("unchecked")
	private void fillTableData() {
		tableNextEvent.getColumns().clear();
		tableNextEvent.getItems().clear();
		
		collectData();
		sortNextEventByDate(data);
		
		columnDate.setCellValueFactory(cellData -> cellData.getValue().getDate());
		columnLicensPlate.setCellValueFactory(cellData -> cellData.getValue().getLicensePlate());
		columnType.setCellValueFactory(cellData -> cellData.getValue().getType());
		columnRemainingDistance.setCellValueFactory(cellData -> cellData.getValue().getRemainingDistance().asObject());
		
		tableNextEvent.getColumns().addAll(columnDate, columnLicensPlate, columnType, columnRemainingDistance);
		tableNextEvent.setItems(data);
		
	}
	
	private void collectData() {
		MaintenanceSorter maintenanceSorter = new MaintenanceSorter();
		addToData(maintenanceSorter.nextMaintenanceDates());
		
		InsuranceSorter insuranceSorter	= new InsuranceSorter();
		addToData(insuranceSorter.insuranceRenewalDates());
		
		InspectionExpirySorter inspectionExpirySorter = new InspectionExpirySorter();
		addToData(inspectionExpirySorter.inspectionExpiriesDates());
	}
	
	private void addToData(List<NextEvent> list) {
		for (NextEvent nextEvent : list) {
			data.add(nextEvent);
		}
	}
	
	private void sortNextEventByDate(List<NextEvent> nextEventList) {
		  Collections.sort(nextEventList, new Comparator<NextEvent>() {
		      @Override
		      public int compare(NextEvent o1, NextEvent o2) {
		    	  return o1.getDate().getValue().compareTo(o2.getDate().getValue());
		      }
		  });
	}

    
    
}
