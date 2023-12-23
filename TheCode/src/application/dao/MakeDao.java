package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import application.dto.MakeDto;

public class MakeDao implements ICrud<MakeDto> {
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("carfleet_manager");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(MakeDto obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}
	
	@Override
	public void update(MakeDto obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MakeDto findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MakeDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
