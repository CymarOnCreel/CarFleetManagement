package application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class ReservationDto {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private int idReservation;

    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false)
    private EmployeeDto employee;

    @ManyToOne
    @JoinColumn(name = "license_plate", nullable = false)
    private CarDto car;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted;


	

	public ReservationDto(int id, EmployeeDto employee, CarDto car, LocalDateTime startDate, LocalDateTime endDate, String description,
			LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted) {
		super();
		this.idReservation = id;
		this.employee = employee;
		this.car = car;
		this.startDateTime = startDate;
		this.endDateTime = endDate;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted=deleted;
	}
	
	public ReservationDto() {
		super();
	}
	@Override
	public String toString() {
		return "ReservationDto [id=" + idReservation + ", employee=" + employee + ", car=" + car + ", startDate=" + startDateTime
				+ ", endDate=" + endDateTime + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", isDeleted=" + deleted + "]";
	}

	public int getIdReservation() {
		return idReservation;
	}


	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}


	public EmployeeDto getEmployee() {
		return employee;
	}


	public void setEmployee(EmployeeDto employee) {
		this.employee = employee;
	}


	public CarDto getCar() {
		return car;
	}


	public void setCar(CarDto car) {
		this.car = car;
	}


	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}


	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}


	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}


	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}


	public void updateReservationDto(EmployeeDto employee, CarDto car, LocalDateTime startDateTime,
			LocalDateTime endDateTime, String description) {
		this.employee = employee;
		this.car = car;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.updatedAt = LocalDateTime.now();
	}

	public void deleteReservation() {
		this.deleted = true;
		this.updatedAt = LocalDateTime.now();
	}
	
	public String isDeletedToString() {
		return deleted ? "törölve":"aktív";
	}
}
