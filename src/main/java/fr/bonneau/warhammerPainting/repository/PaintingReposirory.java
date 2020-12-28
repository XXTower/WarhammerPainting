package fr.bonneau.warhammerPainting.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import fr.bonneau.warhammerPainting.models.Painting;

@Repository
public class PaintingReposirory {
	
	@PersistenceContext
	private EntityManager entityManager;
	private static final String ALL = "FROM Painting";
	private static final String WHERE_NAME_AND_TYPE = "FROM Painting WHERE name = :name AND type = :type";
	
	
	public PaintingReposirory(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public List<Painting> getAll() {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<Painting> paintingQuery = session.createQuery(ALL,Painting.class);
		return paintingQuery.getResultList();
	}

	
	public Painting saveOrUpdate(Painting painting) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(painting);
		return painting;
	}

	public boolean checkIfExiste(Painting painting) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<Painting> paintingQuery = session.createQuery(WHERE_NAME_AND_TYPE,Painting.class);
		paintingQuery.setParameter("name", painting.getName());
		paintingQuery.setParameter("type", painting.getType());
		return !paintingQuery.getResultList().isEmpty();
	}
}
