package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.PictureDto;

public class PictureDao implements ICrud<PictureDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(PictureDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	@Override
	public void update(PictureDto obj) {
		entityManager.getTransaction().begin();
		PictureDto pictureDto = entityManager.find(PictureDto.class,obj.getPicturePath());
		if (pictureDto!=null) {
			pictureDto.updatePictureDto(obj.getLicensePlate()
			);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		PictureDto pictureDto = entityManager.find(PictureDto.class, id);
		if (pictureDto!=null) {
			pictureDto.deletePictureDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public PictureDto findById(Object id) {
		PictureDto pictureDto = entityManager.find(PictureDto.class, id);
		entityManager.close();
		factory.close();
		return pictureDto;
	}

	@Override
	public List<PictureDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PictureDto> criteriaQuery = criteriaBuilder.createQuery(PictureDto.class);
		Root<PictureDto> root = criteriaQuery.from(PictureDto.class);
		criteriaQuery.select(root);
		List<PictureDto> picture = entityManager.createQuery(criteriaQuery).getResultList();
		return picture;
	}
}
