package fr.bonneau.warhammerPainting.mappeur;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;

@Component
public class PaintingMappeur {
	
	public PaintingDto paintingToDto(Painting painting) {
		if(painting == null) {
			return null;
		}
		PaintingDto dto = new PaintingDto();
		dto.setId(painting.getId());
		dto.setName(painting.getName());
		dto.setType(painting.getType());
		return dto;
	}
	
	public Painting dtoToPainting(PaintingDto dto) {
		if (dto == null) {
			return null;
		}
		Painting painting = new Painting();
		painting.setId(dto.getId());
		painting.setName(dto.getName());
		painting.setType(dto.getType());
		return painting;
	}

	public List<PaintingDto> paintingsToDtos(List<Painting> listPaintings) {
		if (listPaintings == null) {
			return new ArrayList<PaintingDto>();
		}
		return listPaintings.stream()
				.filter(painting -> painting != null)
				.map(painting -> paintingToDto(painting))
				.collect(Collectors.toList());
	}
	
	public List<Painting> dtosToPaintings(List<PaintingDto> listDtos) {
		if (listDtos == null) {
			return new ArrayList<Painting>();
		}
		return listDtos.stream()
				.filter(dto -> dto!=null)
				.map(dto -> dtoToPainting(dto))
				.collect(Collectors.toList());
	}
}
