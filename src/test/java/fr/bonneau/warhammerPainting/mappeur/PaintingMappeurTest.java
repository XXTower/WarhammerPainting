package fr.bonneau.warhammerPainting.mappeur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;


public class PaintingMappeurTest {
	
	Painting painting;
	PaintingDto dto;
	List<Painting> paintings;
	List<PaintingDto> dtos;
	PaintingMappeur mapper = new PaintingMappeur();
	
	@BeforeEach
	public void beforEach() {
		painting = new Painting();
		painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);
		dto = new PaintingDto();
		dto.setId(1);
		dto.setName("abadon black");
		dto.setType(PaintingTypes.BASE);
		paintings = new ArrayList<Painting>();
		paintings.add(painting);
		dtos = new ArrayList<PaintingDto>();
		dtos.add(dto);
	}

	//   ------paintingToDto------

	@Test
	public void paintingToDtoTest() {
		PaintingDto dtoTest = mapper.paintingToDto(painting);
		
		assertEquals(dtoTest,dto);
	}
	
	@Test
	public void paintingToDtoTestNull() {
		PaintingDto dtoTest = mapper.paintingToDto(null);
		assertNull(dtoTest);
	}

	//   ------dtoToPainting------

	@Test
	public void dtoToPaintingTest() {
		Painting paintingTest = mapper.dtoToPainting(dto);
		
		assertEquals(paintingTest,painting);
	}
	
	@Test
	public void dtoToPaintingTestNull() {
		Painting paintingTest = mapper.dtoToPainting(null);
		assertNull(paintingTest);
	}

	//  ------paintingsToDtos------

	@Test
	public void paintingsToDtosTest() {
		List<PaintingDto> dtosTest = mapper.paintingsToDtos(paintings);
		assertEquals(dtos, dtosTest);
	}
	
	@Test
	public void paintingsToDtosTestEmptyList() {
		List<PaintingDto> dtosTest = mapper.paintingsToDtos(new ArrayList<Painting>());
		assert(dtosTest.isEmpty());
	}
	
	@Test
	public void paintingsToDtosTestNull() {
		List<PaintingDto> dtosTest = mapper.paintingsToDtos(null);
		assert(dtosTest.isEmpty());
	}
	
	//  ------dtosToPaintings------
	
	@Test
	public void dtosToPaintingsTest() {
		List<Painting> paintingsTest = mapper.dtosToPaintings(dtos);
		assertEquals(paintings, paintingsTest);
	}
	
	@Test
	public void dtosToPaintingsTestEmptyList() {
		List<Painting> paintingsTest = mapper.dtosToPaintings(new ArrayList<PaintingDto>());
		assert(paintingsTest.isEmpty());
	}
	
	@Test
	public void dtosToPaintingsTestNull() {
		List<Painting> paintingsTest = mapper.dtosToPaintings(null);
		assert(paintingsTest.isEmpty());
	}
}
