package fr.bonneau.warhammerPainting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.repository.PaintingReposirory;

public class PaintingServiceTest {

	private Painting painting;
	private PaintingService service;
	private PaintingReposirory reposirory;
	
	@BeforeEach
	public void beforAll() {
		painting = new Painting();
		painting.setId(1);
		painting.setName("black");
		painting.setType(PaintingTypes.BASE);
		
		reposirory = mock(PaintingReposirory.class);
		service = new PaintingService(reposirory);
	}
	
	//  ---------getAll----------
	
	@Test
	public void getAllTest() {
		List<Painting> paintings = new ArrayList<Painting>();
		paintings.add(painting);
		when(reposirory.getAll()).thenReturn(paintings);
		
		List<Painting> paintingsTest = service.getAll();
		assertEquals(paintings, paintingsTest);
	}
	
	//  ---------create----------
	
	@Test
	public void createTest() throws AlreadyExistException {
		when(reposirory.checkIfExiste(painting)).thenReturn(false);
		when(reposirory.saveOrUpdate(painting)).thenReturn(painting);
		
		Painting paintingTest = service.create(painting);
		assertEquals(painting, paintingTest);
	}
	
	@Test
	public void createTestAlreadyExiste() {
		when(reposirory.checkIfExiste(painting)).thenReturn(true);
		
		try {
			service.create(painting);
		} catch (AlreadyExistException e) {
			assert(true);
		}
	}
	
	//  ---------update----------
	
	@Test
	public void updateTest() throws AlreadyExistException {
		when(reposirory.saveOrUpdate(painting)).thenReturn(painting);
		
		Painting paintingTest = service.update(painting);
		assertEquals(painting, paintingTest);
	}
}
