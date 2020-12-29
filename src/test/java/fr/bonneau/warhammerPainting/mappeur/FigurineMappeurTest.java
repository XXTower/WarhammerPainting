package fr.bonneau.warhammerPainting.mappeur;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.bonneau.warhammerPainting.dto.FigurineDto;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;

public class FigurineMappeurTest {
    
    private FigurineMappeur mappeur = new FigurineMappeur();
    private Figurine figurine;
    private FigurineDto figurineDto;
    
    @BeforeEach
    void serUp() {
        figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        
        figurineDto = new FigurineDto();
        figurineDto.setId(1);
        figurineDto.setName("test");
        figurineDto.setFaction(Faction.SPACE_MARINS);
        figurineDto.setSubFaction(SubFaction.ULTRAMARINS);
    }
    
    /*------------------ mapToFigurine ----------------------------------*/
    @Test
    void mapToFigurineWithNullShouldReturnNull() {
        Figurine figurineMapper = mappeur.mapToFigurine(null);
        assertThat(figurineMapper).isNull();
    }
    
    @Test
    void mapToFigurineWithDtoShouldReturnFigurineMappedFieldByField() {
        Figurine figurineMapper = mappeur.mapToFigurine(figurineDto);
        assertThat(figurineMapper).isEqualTo(figurine);
    }
    
    /*------------------ mapToFigurines ----------------------------------*/
    @Test
    void mapToFigurinesWithNullListShouldReturnEmptyList() {
        
        List<Figurine> figurinesMapper = mappeur.mapToFigurines(null);
        assertThat(figurinesMapper).isEmpty();
    }
    
    @Test
    void mapToFigurinesWithEmptyListShouldReturnEmptyList() {
        List<FigurineDto> figurinesDtos = new ArrayList<FigurineDto>();
        List<Figurine> figurinesMapper = mappeur.mapToFigurines(figurinesDtos);
        assertThat(figurinesMapper).isEmpty();
    }
    
    @Test
    void mapToFigurinesWithNoEmptyListShouldReturnNoEmptyListWithSameSize() {
        List<FigurineDto> figurinesDtos = Collections.singletonList(figurineDto);
        
        List<Figurine> figurinesMapper = mappeur.mapToFigurines(figurinesDtos);
        assertThat(figurinesMapper).hasSize(1);
        Figurine figurineMapper = figurinesMapper.get(0);
        assertThat(figurineMapper).isEqualTo(figurine);
    }
    
    /*------------------ mapToDto ----------------------------------*/
    @Test
    void mapToDtoWihtNullShouldReturnEmptyList() {
        FigurineDto figurineDtoMapper = mappeur.mapToDto(null);
        assertThat(figurineDtoMapper).isNull();
    }
    
    @Test
    void mapToDtoWihthUserShouldRetunDtoMappedFieldByField() {
        FigurineDto figurineDtoMapper = mappeur.mapToDto(figurine);
        assertThat(figurineDtoMapper).isEqualTo(figurineDto);
    }
    
    /*------------------ mapToDtos ----------------------------------*/
    @Test
    void mapToDtosWithNullListShouldReturnEmptyList() {
        
        List<FigurineDto> figurinesDtoMapper = mappeur.mapToDtos(null);
        assertThat(figurinesDtoMapper).isEmpty();
    }
    
    @Test
    void mapToDtosWithEmptyListShouldReturnEmptyList() {
        List<Figurine> figurines = new ArrayList<Figurine>();
        List<FigurineDto> figurinesDtoMapper = mappeur.mapToDtos(figurines);
        assertThat(figurinesDtoMapper).isEmpty();
    }
    
    @Test
    void mapToDtosWithNoEmptyListShouldReturnNoEmptyListWithSameSize() {
        List<Figurine> figurinesDtos = Collections.singletonList(figurine);
        
        List<FigurineDto> figurinesDtoMapper = mappeur.mapToDtos(figurinesDtos);
        assertThat(figurinesDtoMapper).hasSize(1);
        FigurineDto figurineDtoMapper = figurinesDtoMapper.get(0);
        assertThat(figurineDtoMapper).isEqualTo(figurineDto);
    }
}
