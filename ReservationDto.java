package application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class ReservationDto {

	@Column(name="id_reservation")
	@Id
	private int idReservation;
	
	@Column(name="id_employee")
	private int idEmployee;
	
    @Column(name = "license_plate")
	private String licensePlate;
    
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;
    
    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;
    
    @Column(name="description")
	private String description;
    
    @Column(name="created_at")
	private LocalDate createdAt;
	
	@Column(name="updated_at")
	private LocalDate updatedAt;

	public ReservationDto(int idReservation, int idEmployee, String licensePlate, LocalDateTime startDateTime,
			LocalDateTime endDateTime, String description, LocalDate createdAt, LocalDate updatedAt) {
		this.idReservation = idReservation;
		this.idEmployee = idEmployee;
		this.licensePlate = licensePlate;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public ReservationDto() {

	}

	public int getIdReservation() {
		return idReservation;
	}

	public int getIdEmployee() {
		return idEmployee;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String toString() {
		return "ReservationDto [idReservation=" + idReservation + ", idEmployee=" + idEmployee + ", licensePlate="
				+ licensePlate + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", description="
				+ description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	public void updateReservationDto(int idEmployee, String licensePlate, LocalDateTime startDateTime,
			LocalDateTime endDateTime, String description, LocalDate createdAt, LocalDate updatedAt) {
		this.idEmployee = idEmployee;
		this.licensePlate = licensePlate;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void deleteReservationDto() {
		int enabled = 0;
		LocalDate updatedAt = LocalDate.now();
	}
	
	
}
