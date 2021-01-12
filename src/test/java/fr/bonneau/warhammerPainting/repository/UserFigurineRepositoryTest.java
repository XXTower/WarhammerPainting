package fr.bonneau.warhammerPainting.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;

@SpringBootTest
@Transactional
public class UserFigurineRepositoryTest {

	private UserFigurine userFigurine1;
	private UserFigurine userFigurine2;
	private UserFigurine userFigurine3;
	private UserFigurine userFigurine4;
	List<UserFigurine> userFigurines;
	private UserFigurineRepository repository;
	@PersistenceContext
    private EntityManager em;
	
	@BeforeEach
	public void beforAll() {
		Painting painting = new Painting();
		painting.setId(1);
		painting.setName("red");
		painting.setType(PaintingTypes.BASE);
		
		Set<Painting> paintings = Collections.singleton(painting);
		
		User user = new User();
		user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        User user2 = new User();
		user2.setId(2);
        user2.setUsername("test2");
        user2.setPassword("test2");
        
        Figurine figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        Figurine figurine2 = new Figurine();
        figurine2.setId(2);
        figurine2.setName("test2");
        figurine2.setFaction(Faction.NECRON);
        figurine2.setSubFaction(SubFaction.CANOPTECK);
        
        userFigurine1 = new UserFigurine();
        userFigurine1.setId(1);
		userFigurine1.setTitle("title");
		userFigurine1.setDescription("Descripsion");
		userFigurine1.setVisibility(true);
		userFigurine1.setFigurine(figurine);
		userFigurine1.setUser(user);
		userFigurine1.setListPainting(paintings);
		userFigurine2 = new UserFigurine();
		userFigurine2.setId(2);
		userFigurine2.setTitle("title");
		userFigurine2.setDescription("Descripsion");
		userFigurine2.setVisibility(true);
		userFigurine2.setFigurine(figurine2);
		userFigurine2.setUser(user);
		userFigurine2.setListPainting(paintings);
		userFigurine3 = new UserFigurine();
		userFigurine3.setId(3);
		userFigurine3.setTitle("title");
		userFigurine3.setDescription("Descripsion");
		userFigurine3.setVisibility(true);
		userFigurine3.setFigurine(figurine);
		userFigurine3.setUser(user2);
		userFigurine3.setListPainting(paintings);
		userFigurine4 = new UserFigurine();
		userFigurine4.setId(0);
		userFigurine4.setTitle("title");
		userFigurine4.setDescription("Descripsion");
		userFigurine4.setVisibility(true);
		userFigurine4.setFigurine(figurine2);
		userFigurine4.setUser(user2);
		userFigurine4.setListPainting(paintings);
		repository = new UserFigurineRepository(em);
		
	}
	
	//  -------getAll--------
	@Test
	public void getAllTest() {
		List<UserFigurine> userFigurinesTest = repository.getALL();
		userFigurines = new ArrayList<UserFigurine>();
		userFigurines.add(userFigurine1);
		userFigurines.add(userFigurine2);
		userFigurines.add(userFigurine3);
		assertEquals(userFigurine1, userFigurinesTest.get(0));
	}
	
	//  -------getByUserId--------
	@Test
	public void getByUserIdTest() {
		List<UserFigurine> userFigurinesTest = repository.getByUserId(1);
		userFigurines = new ArrayList<UserFigurine>();
		userFigurines.add(userFigurine1);
		userFigurines.add(userFigurine2);
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	//  -------getByFigurineId--------
	@Test
	public void getByFigurineIdTest() {
		List<UserFigurine> userFigurinesTest = repository.getByFigurineId(1);
		userFigurines = new ArrayList<UserFigurine>();
		userFigurines.add(userFigurine1);
		userFigurines.add(userFigurine3);
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	//  ------saveOrUpdate--------
	
	@Test
	public void saveOrUpdateTestSave() {
		UserFigurine userFigurineTest;
		userFigurineTest = repository.saveOrUpdate(userFigurine4);
		userFigurine4.setId(4);
		assertEquals(userFigurine4, userFigurineTest);

		List<UserFigurine> userFigurinesTest = repository.getALL();
		assertEquals(userFigurine4, userFigurinesTest.get(3));
	}
	
	@Test
	public void saveOrUpdateTestUpdate() {
		UserFigurine userFigurineTest;
		userFigurine1.setTitle("new title");
		userFigurineTest = repository.saveOrUpdate(userFigurine1);
		assertEquals(userFigurine1, userFigurineTest);
		
		List<UserFigurine> userFigurinesTest = repository.getALL();
		assertEquals(userFigurine1, userFigurinesTest.get(0));
	}
	
	@Test
	public void deleteTest() throws ObjectNotFoundException {
		UserFigurine userFigurineTest;
		userFigurineTest = repository.delete(userFigurine1);
		assertEquals(userFigurine1, userFigurineTest);

		List<UserFigurine> userFigurinesTest = repository.getALL();
		userFigurines = new ArrayList<UserFigurine>();
		userFigurines.add(userFigurine2);
		userFigurines.add(userFigurine3);
		assertEquals(userFigurines, userFigurinesTest);
	}
	
	@Test
	public void deleteTestUserFigurineNotFound() throws ObjectNotFoundException {
		userFigurine1.setId(100);
		assertThrows(ObjectNotFoundException.class,() -> repository.delete(userFigurine1));
	}
}
