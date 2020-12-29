package fr.bonneau.warhammerPainting.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.bonneau.warhammerPainting.exception.UserNotFoundException;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;

@SpringBootTest
@Transactional
public class FigurineRepositoryTest {
    private Figurine figurine;
    private FigurineRepository figurineRepository;
    @PersistenceContext
    private EntityManager entityManager;
    
    @BeforeEach
    void setUp() {
        figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        
        figurineRepository = new FigurineRepository(entityManager);
    }
    
    /*-------------------- getAll --------------------*/
    @Test
    void getAllShouldReturnUserList() {
        List<Figurine> fetchedFigurines = figurineRepository.getAll();
        
        assertThat(fetchedFigurines).hasSize(2);
        assertThat(fetchedFigurines.get(0)).isEqualTo(figurine);
    }
    
    /*-------------------- saveOrUpdate --------------------*/
    
    @Test
    void saveOrUpdateShouldSaveNewFigurine() {
        Figurine newFigurine = new Figurine();
        newFigurine.setName("toto");
        newFigurine.setFaction(Faction.NECRON);
        newFigurine.setSubFaction(SubFaction.CANOPTECK);
        
        Figurine excepteFigurine = new Figurine();
        excepteFigurine.setId(3);
        excepteFigurine.setName("toto");
        excepteFigurine.setFaction(Faction.NECRON);
        excepteFigurine.setSubFaction(SubFaction.CANOPTECK);
        
        Figurine fetchFigurine = figurineRepository.saveOrUpdate(newFigurine);
        
        assertThat(fetchFigurine).isEqualTo(excepteFigurine);
        assertThat(figurineRepository.getAll()).hasSize(3);
    }
    
    @Test
    void saveOrUpdateShouldUpdateOldFigurine() throws UserNotFoundException {
        Figurine figurineToUpdate = new Figurine();
        figurineToUpdate.setId(2);
        figurineToUpdate.setName("toto");
        figurineToUpdate.setFaction(Faction.NECRON);
        figurineToUpdate.setSubFaction(SubFaction.CANOPTECK);
        
        Figurine excepteFigurine = new Figurine();
        excepteFigurine.setId(2);
        excepteFigurine.setName("toto");
        excepteFigurine.setFaction(Faction.NECRON);
        excepteFigurine.setSubFaction(SubFaction.CANOPTECK);
        
        Figurine fetchFigurine = figurineRepository.saveOrUpdate(figurineToUpdate);
        
        assertThat(fetchFigurine).isEqualTo(excepteFigurine);
        assertThat(figurineRepository.getAll().get(1)).isEqualTo(excepteFigurine);
    }
    
    /*-------------------- checkIfExiste --------------------*/
    
    @Test
    void checkIfExisteShouldReturnTrue() {
        assertThat(figurineRepository.checkIfExiste("test")).isTrue();
    }
    
    @Test
    void checkIfExisteShouldReturnFalse() {
        assertThat(figurineRepository.checkIfExiste("toto")).isFalse();
    }

}
