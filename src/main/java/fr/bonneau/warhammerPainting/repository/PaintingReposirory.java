package fr.bonneau.warhammerPainting.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.models.Painting;

@Repository
public class PaintingReposirory {
	
	EntityManager entityManager;
	private final String SELECT_ALL = "FROM Painting";
	private final String SELECT_ONE = "FROM Painting WHERE name = :name AND type = :type";
	
	
	public PaintingReposirory(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public List<Painting> getAll() {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<Painting> paintingQuery = session.createQuery(SELECT_ALL,Painting.class);
		return paintingQuery.getResultList();
	}

	
	public Painting create(Painting painting) throws AlreadyExistException {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(painting);
		return painting;
	}

	public boolean checkIfExiste(Painting painting) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<Painting> paintingQuery = session.createQuery(SELECT_ONE,Painting.class);
		paintingQuery.setParameter("name", painting.getName());
		paintingQuery.setParameter("type", painting.getType());
		return !paintingQuery.getResultList().isEmpty();
	}
}
