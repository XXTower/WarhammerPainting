package fr.bonneau.warhammerPainting.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TestTransaction;

import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;

@SpringBootTest
@Transactional
public class PaintingReposiroryTest {
	
	private Painting painting;
	private Painting painting2;
	private Painting painting3;
	private List<Painting> paintings;
	private PaintingReposirory repository;
	@PersistenceContext
    private EntityManager em;
	
	@BeforeEach
	public void beforEach() {
		painting = new Painting();
		painting.setId(1);
		painting.setName("red");
		painting.setType(PaintingTypes.BASE);
		painting2 = new Painting();
		painting2.setId(2);
		painting2.setName("green");
		painting2.setType(PaintingTypes.LAYER);
		painting3 = new Painting();
		painting3.setName("red");
		painting3.setType(PaintingTypes.BASE);
		paintings = new ArrayList<Painting>();
		paintings.add(painting);
		paintings.add(painting2);
		repository = new PaintingReposirory(em);
	}

	//  -------getAll--------
	@Test
	public void getAllTest() {	
		List<Painting> paintingsTest = repository.getAll();
		assertEquals(paintings, paintingsTest);
	}
	
	//  ------saveOrUpdate--------
	
	@Test
	public void saveOrUpdateTestSave() {
		Painting paintingTest;
		paintingTest = repository.saveOrUpdate(painting3);
		painting3.setId(3);
		assertEquals(painting3, paintingTest);

		List<Painting> paintingsTest = repository.getAll();
		assertEquals(painting3, paintingsTest.get(2));
	}
	
	@Test
	public void saveOrUpdateTestUpdate() {
		Painting paintingTest;
		painting.setName("black");
		paintingTest = repository.saveOrUpdate(painting);
		assertEquals(painting, paintingTest);
		
		List<Painting> paintingsTest = repository.getAll();
		assertEquals(painting, paintingsTest.get(0));
	}
	
	//  ------checkIfExiste--------
	
	@Test
	public void checkIfExisteTestTrue() {
		assertTrue(repository.checkIfExiste(painting));
	}
	
	@Test
	public void checkIfExisteTestFalse() {
		assertTrue(repository.checkIfExiste(painting3));
	}
}
