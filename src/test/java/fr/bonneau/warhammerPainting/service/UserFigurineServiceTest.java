package fr.bonneau.warhammerPainting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;
import fr.bonneau.warhammerPainting.repository.UserFigurineRepository;

public class UserFigurineServiceTest {

	private UserFigurine userFigurine;
	private UserFigurineService service;
	private UserFigurineRepository reposirory;

	@BeforeEach
	public void beforAll() {
		Painting painting = new Painting();
		painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);
		
		List<Painting> paintings = Collections.singletonList(painting);
		
		User user = new User();
		user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        
        Figurine figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        
        userFigurine = new UserFigurine();
        userFigurine.setId(1);
		userFigurine.setTitle("title");
		userFigurine.setDescripsion("Descripsion");
		userFigurine.setVisibility(true);
		userFigurine.setFigurine(figurine);
		userFigurine.setUser(user);
		userFigurine.setListPainting(paintings);
		
		reposirory = mock(UserFigurineRepository.class);
		service = new UserFigurineService(reposirory);
	}
	
	//  ---------getAll----------
	
	@Test
	public void getAllTest() {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		when(reposirory.getALL()).thenReturn(userFigurines);
		
		List<UserFigurine> userFigurinesTest = service.getAll();
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	//  ---------getAll----------
	
	@Test
	public void getByUderIdTest() {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		when(reposirory.getByUserId(1)).thenReturn(userFigurines);
		
		List<UserFigurine> userFigurinesTest = service.getByUserId(1);
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	//  ---------getByFigurineId----------
	
	@Test
	public void getByFigurineIdTest() {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		when(reposirory.getByFigurineId(1)).thenReturn(userFigurines);
		
		List<UserFigurine> userFigurinesTest = service.getByFigurineId(1);
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	//  ---------create----------
	
	@Test
	public void createTest() {
		when(reposirory.saveOrUpdate(userFigurine)).thenReturn(userFigurine);
		
		UserFigurine userFigurineTest = service.create(userFigurine);
		assertEquals(userFigurine, userFigurineTest);
		verify(reposirory.saveOrUpdate(userFigurine));
	}
	
	//  ---------update----------
	
	@Test
	public void updateTest() {
		when(reposirory.saveOrUpdate(userFigurine)).thenReturn(userFigurine);
		
		UserFigurine userFigurineTest = service.update(userFigurine);
		assertEquals(userFigurine, userFigurineTest);
		verify(reposirory.saveOrUpdate(userFigurine));
	}
	
	//  ---------delete----------
	
	@Test
	public void deleteTest() {
		when(reposirory.delete(userFigurine)).thenReturn(userFigurine);
		
		UserFigurine userFigurineTest = service.delete(userFigurine);
		assertEquals(userFigurine, userFigurineTest);
		verify(reposirory.delete(userFigurine));
	}
}
