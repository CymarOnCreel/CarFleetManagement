package application.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date_time", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
    @Column(name = "description", length = 100)
	private String description;
	
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;



	public ReservationDto() {
		super();
	}

	

	public ReservationDto(int id, EmployeeDto employee, CarDto car, Date startDate, Date endDate, String description,
			Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.employee = employee;
		this.car = car;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	@Override
	public String toString() {
		return "ReservationDto [id=" + id + ", employee=" + employee + ", car=" + car + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

    
	

}
