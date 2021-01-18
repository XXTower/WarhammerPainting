package fr.bonneau.warhammerPainting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;
import fr.bonneau.warhammerPainting.repository.FigurineRepository;
import fr.bonneau.warhammerPainting.repository.PaintingReposirory;
import fr.bonneau.warhammerPainting.repository.UserFigurineRepository;

public class UserFigurineServiceTest {

	private UserFigurine userFigurine;
	private UserFigurineService service;
	private UserFigurineRepository reposirory;
	private FigurineRepository figurineRepository;
	private Figurine figurine;
	private PaintingReposirory paintingReposirory;
	private List<Painting> paintings;

	@BeforeEach
	public void beforAll() {
		Painting painting = new Painting();
		painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);
		
		paintings = Collections.singletonList(painting);
		Set<Painting> paintingsSet = Collections.singleton(painting);
		
		User user = new User();
		user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        
        figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        
        userFigurine = new UserFigurine();
        userFigurine.setId(1);
		userFigurine.setTitle("title");
		userFigurine.setDescription("Descripsion");
		userFigurine.setVisibility(true);
		userFigurine.setFigurine(figurine);
		userFigurine.setUser(user);
		userFigurine.setListPainting(paintingsSet);
		
		reposirory = mock(UserFigurineRepository.class);
		figurineRepository = mock(FigurineRepository.class);
		paintingReposirory = mock(PaintingReposirory.class);
		service = new UserFigurineService(reposirory, figurineRepository, paintingReposirory);
	}
	
	//  ---------getAll----------
	
	@Test
	public void getAllTest() {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		when(reposirory.getALL()).thenReturn(userFigurines);
		
		List<UserFigurine> userFigurinesTest = service.getAll();
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	//  ---------getByUserId----------
	
	@Test
	public void getByUserIdTest() {
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
	public void createTest() throws ObjectNotFoundException {
	    when(figurineRepository.getById(1)).thenReturn(figurine);
	    when(paintingReposirory.getAll()).thenReturn(paintings);
		when(reposirory.saveOrUpdate(userFigurine)).thenReturn(userFigurine);
		
		UserFigurine userFigurineTest = service.create(userFigurine);
		assertEquals(userFigurine, userFigurineTest);
		verify(reposirory).saveOrUpdate(userFigurine);
	}
	
	@Test
    public void createTestThrowsObjectNotFound() throws ObjectNotFoundException {
	    Painting painting = new Painting();
        painting.setId(2);
        painting.setName("test");
        painting.setType(PaintingTypes.BASE);
        when(figurineRepository.getById(1)).thenReturn(figurine);
        when(paintingReposirory.getAll()).thenReturn(Collections.singletonList(painting));
        
        Exception exception = assertThrows(ObjectNotFoundException.class, ()->service.create(userFigurine));
        
        String excpectMessage = "Painting not found.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
	
	//  ---------update----------
	
	@Test
	public void updateTest() throws ObjectNotFoundException {
	    when(figurineRepository.getById(1)).thenReturn(figurine);
	    when(paintingReposirory.getAll()).thenReturn(paintings);
		when(reposirory.saveOrUpdate(userFigurine)).thenReturn(userFigurine);
		
		UserFigurine userFigurineTest = service.update(userFigurine);
		assertEquals(userFigurine, userFigurineTest);
		verify(reposirory).saveOrUpdate(userFigurine);
	}
	
	@Test
    public void updateTestThrowsObjectNotFound() throws ObjectNotFoundException {
        Painting painting = new Painting();
        painting.setId(2);
        painting.setName("test");
        painting.setType(PaintingTypes.BASE);
        when(figurineRepository.getById(1)).thenReturn(figurine);
        when(paintingReposirory.getAll()).thenReturn(Collections.singletonList(painting));
        
        Exception exception = assertThrows(ObjectNotFoundException.class, ()->service.update(userFigurine));
        
        String excpectMessage = "Painting not found.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
	
	//  ---------delete----------
	
	@Test
	public void deleteTest() throws ObjectNotFoundException  {
		when(reposirory.delete(userFigurine)).thenReturn(userFigurine);
		
		UserFigurine userFigurineTest = service.delete(userFigurine);
		assertEquals(userFigurine, userFigurineTest);
		verify(reposirory).delete(userFigurine);
	}
}
