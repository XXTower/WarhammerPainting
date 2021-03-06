package fr.bonneau.warhammerPainting.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.models.Figurine;

@Repository
public class FigurineRepository {

    private static final String HQL_ALL = "FROM Figurine";
    private static final String HQL_CHECK_IF_EXCISTE = "FROM Figurine WHERE NAME LIKE :name";
    private static final String HQL_GET_BY_ID = "FROM Figurine WHERE id = :id";

    
    @PersistenceContext
    private EntityManager entityManager;

    public FigurineRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public List<Figurine> getAll(){
        Session session = entityManager.unwrap(Session.class);
        Query<Figurine> query = session.createQuery(HQL_ALL, Figurine.class);
        return query.getResultList();
    }
    
    public Figurine saveOrUpdate(Figurine figurine) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(figurine);
        return figurine;
    }
    
    public boolean checkIfExiste(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query<Figurine> query = session.createQuery(HQL_CHECK_IF_EXCISTE, Figurine.class);
        query.setParameter("name", name);
        return !query.getResultList().isEmpty();
    }
    
    public Figurine getById(int id) throws ObjectNotFoundException {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<Figurine> query = session.createQuery(HQL_GET_BY_ID, Figurine.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("Figurine");
        }
        
    }
    
}
