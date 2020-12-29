package fr.bonneau.warhammerPainting.mappeur;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fr.bonneau.warhammerPainting.dto.FigurineDto;
import fr.bonneau.warhammerPainting.models.Figurine;

@Component
public class FigurineMappeur {

    public FigurineDto mapToDto(Figurine figurine) {
        if(figurine == null) return null;
        
        FigurineDto figurineDto = new FigurineDto();
        figurineDto.setId(figurine.getId());
        figurineDto.setName(figurine.getName());
        figurineDto.setFaction(figurine.getFaction());
        figurineDto.setSubFaction(figurine.getSubFaction());
        return figurineDto;
    }
    
    public List<FigurineDto> mapToDtos(List<Figurine> figurines){
        if(figurines == null) return new ArrayList<FigurineDto>();
        return figurines.stream().filter(Objects::nonNull).map(figurine -> mapToDto(figurine))
                .collect(Collectors.toList());
    }
    
    public Figurine mapToFigurine(FigurineDto figurineDto) {
        if(figurineDto == null) return null;
        
        Figurine figurine = new Figurine();
        figurine.setId(figurineDto.getId());
        figurine.setName(figurineDto.getName());
        figurine.setFaction(figurineDto.getFaction());
        figurine.setSubFaction(figurineDto.getSubFaction());
        return figurine;
    }
    
    public List<Figurine> mapToFigurines(List<FigurineDto> figurineDtos){
        if(figurineDtos == null) return new ArrayList<Figurine>();
        return figurineDtos.stream().filter(Objects::nonNull).map(figurineDto -> mapToFigurine(figurineDto))
                .collect(Collectors.toList());
    }
}
