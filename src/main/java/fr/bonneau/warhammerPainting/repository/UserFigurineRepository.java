package fr.bonneau.warhammerPainting.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.models.UserFigurine;

@Repository
public class UserFigurineRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	private static final String ALL = "FROM UserFigurine";
	private static final String WHERE_USERID = "FROM UserFigurine WHERE user.id = :userId";
	private static final String WHERE_FIGURINEID = "FROM UserFigurine WHERE figurine.id = :figurineId";
	private static final String WHERE_ID = "FROM UserFigurine WHERE id = :id";

	
	public UserFigurineRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	public List<UserFigurine> getALL() {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<UserFigurine> userFigurineQuery = session.createQuery(ALL,UserFigurine.class);
		
		return userFigurineQuery.getResultList();
	}

	public List<UserFigurine> getByUserId(int userId) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<UserFigurine> userFigurineQuery = session.createQuery(WHERE_USERID,UserFigurine.class);
		userFigurineQuery.setParameter("userId", userId);
		return userFigurineQuery.getResultList();
	}

	public List<UserFigurine> getByFigurineId(int figurineId) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<UserFigurine> userFigurineQuery = session.createQuery(WHERE_FIGURINEID,UserFigurine.class);
		userFigurineQuery.setParameter("figurineId", figurineId);
		return userFigurineQuery.getResultList();
	}

	public UserFigurine saveOrUpdate(UserFigurine userFigurine) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(userFigurine);
		return userFigurine;
	}

	public UserFigurine delete(UserFigurine userFigurine) throws ObjectNotFoundException {
		Session session = entityManager.unwrap(Session.class);
		try {
		TypedQuery<UserFigurine> userFigurineQuery = session.createQuery(WHERE_ID,UserFigurine.class);
		userFigurineQuery.setParameter("id", userFigurine.getId());
		userFigurine = userFigurineQuery.getSingleResult();
		} catch (NoResultException e) {
			throw new ObjectNotFoundException("UserFigurine");
		}
		session.delete(userFigurine);
		return userFigurine;
	}

}
