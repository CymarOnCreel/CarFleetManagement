package application.util;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NextEvent {
	
	private final ObjectProperty<LocalDate> date;
	private final StringProperty licensePlate;
	private final StringProperty type;
	private final StringProperty remainingDistance;
	
	public NextEvent(LocalDate date, String licensePlate, String type, String remainingDistance) {
		this.date = new SimpleObjectProperty<>(date);
		this.licensePlate = new SimpleStringProperty(licensePlate);
		this.type = new SimpleStringProperty(type);
		this.remainingDistance = new SimpleStringProperty(remainingDistance);
	}

	public ObjectProperty<LocalDate> getDate() {
		return date;
	}

	public StringProperty getLicensePlate() {
		return licensePlate;
	}

	public StringProperty getType() {
		return type;
	}

	public StringProperty getRemainingDistance() {
		return remainingDistance;
	}

	@Override
	public String toString() {
		return date.getValue() + " - " + licensePlate.getValue() + 
				" - " + type.getValue() + " - " + remainingDistance.getValue();
	}
	

}
