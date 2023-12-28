package application.dto;

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
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_employee", nullable = false)
	private EmployeeDto employee;

	@ManyToOne
	@JoinColumn(name = "license_plate", nullable = false)
	private CarDto car;

	@Column(name = "start_date_time", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "end_date_time", nullable = false)
	private LocalDateTime endDate;
	
    @Column(name = "description", length = 100)
	private String description;
	
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name="is_deleted")
    private boolean isDeleted;






	public ReservationDto() {
		super();
	}

	

	public ReservationDto(int id, EmployeeDto employee, CarDto car, LocalDateTime startDate, LocalDateTime endDate, String description,
			LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
		super();
		this.id = id;
		this.employee = employee;
		this.car = car;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted=isDeleted;
	}






	@Override
	public String toString() {
		return "ReservationDto [id=" + id + ", employee=" + employee + ", car=" + car + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", isDeleted=" + isDeleted + "]";
	}



	public String toStringWithNames() {
		return "Reservation number: "+id+", employee name: "+employee.getLastName()+", car: "+car.getLicensePlate(); 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
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
		return isDeleted;
	}



	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String isDeletedToString() {
		return isDeleted ? "törölve":"aktív";
	}

	

}
