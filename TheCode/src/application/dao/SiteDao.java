package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.dto.SiteDto;

public class SiteDao implements ICrud<SiteDto>{
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(SiteDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
		
	}

	
	public void update(SiteDto obj) {
		entityManager.getTransaction().begin();
		SiteDto siteDto = entityManager.find(SiteDto.class,obj.getNameSite());
		if (siteDto!=null) {
			siteDto.updateSiteDto(obj.getLocation(),
					obj.getCapacity(),
					obj.getContactPerson(),
					obj.getContactEmail(),
					obj.getContactPhone(),
					obj.getDescription(),
					obj.getCreatedAt(),
					obj.getUpdatedAt(),
					obj.isEnabled()
			);
	    }
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}

	@Override
	public void deleteById(Object id) {
		entityManager.getTransaction().begin();
		SiteDto siteDto = entityManager.find(SiteDto.class, id);
		if (siteDto!=null) {
			siteDto.deleteSiteDto();
			entityManager.getTransaction().commit();
		}
		entityManager.close();
		factory.close();
	}

	@Override
	public SiteDto findById(Object id) {
		SiteDto siteDto = entityManager.find(SiteDto.class, id);
		entityManager.close();
		factory.close();
		return siteDto;
	}

	@Override
	public List<SiteDto> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SiteDto> criteriaQuery = criteriaBuilder.createQuery(SiteDto.class);
		Root<SiteDto> root = criteriaQuery.from(SiteDto.class);
		criteriaQuery.select(root);
		List<SiteDto> site = entityManager.createQuery(criteriaQuery).getResultList();
		return site;
	}
}
