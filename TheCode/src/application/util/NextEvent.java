package application.util;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NextEvent {
	
	private final ObjectProperty<LocalDate> date;
	private final StringProperty licensePlate;
	private final StringProperty type;
	private final IntegerProperty remainingDistance;
	
	public NextEvent(LocalDate date, String licensePlate, String type, int remainingDistance) {
		this.date = new SimpleObjectProperty<>(date);
		this.licensePlate = new SimpleStringProperty(licensePlate);
		this.type = new SimpleStringProperty(type);
		this.remainingDistance = new SimpleIntegerProperty(remainingDistance);
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

	public IntegerProperty getRemainingDistance() {
		return remainingDistance;
	}
	

}
