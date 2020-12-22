package fr.bonneau.warhammerPainting.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.transaction.TestTransaction;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;

public class PaintingReposiroryTest {
	
	Painting painting;
	Painting painting2;
	List<Painting> paintings;
	EntityManager entityManager;
	PaintingReposirory repository;
	
	@BeforeEach
	public void beforEach() {
		painting = new Painting();
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);
		painting2 = new Painting();
		painting2.setName("ruineLord brass");
		painting2.setType(PaintingTypes.BASE);
		paintings = new ArrayList<Painting>();
		paintings.add(painting);
		paintings.add(painting2);
	}

	//  -------getAll--------
	@Test
	public void getAllTest() {	
		TestTransaction.start();
		List<Painting> paintingsTest = repository.getAll();
		assertEquals(paintings, paintingsTest);
		TestTransaction.end();
	}
	
	//  ------create--------
	
	@Test
	public void creatTest() {
		TestTransaction.start();
		Painting paintingTest;
		try {
			paintingTest = repository.create(painting);
		} catch (AlreadyExistException e) {
			assertTrue(false);
		}
		TestTransaction.end();
		
		TestTransaction.start();
		TypedQuery<Painting> paintingQuery = entityManager.createQuery("FROM Painting",Painting.class);
		
	}
}
