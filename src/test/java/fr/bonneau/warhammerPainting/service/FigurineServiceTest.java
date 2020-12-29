package fr.bonneau.warhammerPainting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;
import fr.bonneau.warhammerPainting.repository.FigurineRepository;

public class FigurineServiceTest {

    private Figurine figurine;
    private FigurineService service;
    private FigurineRepository repository;
    
    @BeforeEach
    public void setUp() {
        figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("toto");
        figurine.setFaction(Faction.NECRON);
        figurine.setSubFaction(SubFaction.SKORPEKH);
        
        repository = mock(FigurineRepository.class);
        service = new FigurineService(repository);
    }
    
    /*-------------getAll---------------*/
    
    @Test
    public void getAllShouldRetunFigurineList() {
        List<Figurine> figurines = Collections.singletonList(figurine);
        
        when(repository.getAll()).thenReturn(figurines);
        
        List<Figurine> results = service.getAll();
        assertEquals(results,figurines);
    }
    
    /*----------create-----------------------*/
    
    @Test
    public void createShouldReturnFigurine() throws AlreadyExistException {
        when(repository.checkIfExiste("toto")).thenReturn(false);
        when(repository.saveOrUpdate(figurine)).thenReturn(figurine);
        
        Figurine result = service.create(figurine);
        
        assertThat(result).isEqualTo(figurine);
    }
    
    @Test
    public void createShouldThrowAlreadyExiste() {
        when(repository.checkIfExiste("toto")).thenReturn(true);
        Exception exception = assertThrows(AlreadyExistException.class, ()-> service.create(figurine));
        String excpectMessage = "This figurine already existe.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
    
    /*----------update-----------------------*/
    
    @Test
    public void updateShouldReturnFigurine() throws AlreadyExistException {
        when(repository.checkIfExiste("toto")).thenReturn(false);
        when(repository.saveOrUpdate(figurine)).thenReturn(figurine);
        
        Figurine result = service.update(figurine);
        
        assertThat(result).isEqualTo(figurine);
    }
    
    @Test
    public void updateShouldThrowAlreadyExiste() {
        when(repository.checkIfExiste("toto")).thenReturn(true);
        Exception exception = assertThrows(AlreadyExistException.class, ()-> service.update(figurine));
        String excpectMessage = "This figurine already existe.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
}
