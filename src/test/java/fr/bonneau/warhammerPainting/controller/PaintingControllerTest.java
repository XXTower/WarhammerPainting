package fr.bonneau.warhammerPainting.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.mappeur.PaintingMappeur;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.service.PaintingService;

public class PaintingControllerTest {

	private PaintingService service = Mockito.mock(PaintingService.class);
	private PaintingMappeur mappeur = Mockito.mock(PaintingMappeur.class);
	private Painting painting;
	private PaintingDto dto;
	private List<Painting> paintings;
	private List<PaintingDto> dtos;
	private PaintingController controller = new PaintingController(service, mappeur);
    private MockMvc mockMvc ;
	private ObjectMapper oblectMapper = new ObjectMapper();
	
	
	@BeforeEach
	public void beforEach() {
		painting = new Painting();
		//painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);
		dto = new PaintingDto();
		//dto.setId(1);
		dto.setName("abadon black");
		dto.setType(PaintingTypes.BASE);
		paintings = new ArrayList<Painting>();
		paintings.add(painting);
		dtos = new ArrayList<PaintingDto>();
		dtos.add(dto);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	
	@Test
	public void getAllTest() throws Exception {
		Mockito.when(mappeur.paintingsToDtos(paintings)).thenReturn(dtos);
		Mockito.when(service.getAll()).thenReturn(paintings);
		mockMvc.perform(post("/api/v1/paintings"))
		.andExpect(status().isOk())
		.andExpect(content().json(oblectMapper.writeValueAsString(dtos)));
	}
	
	@Test
	public void createTest() throws Exception {
		Mockito.when(mappeur.dtoToPainting(dto)).thenReturn(painting);
		Mockito.when(mappeur.paintingToDto(painting)).thenReturn(dto);
		Mockito.when(service.create(painting)).thenReturn(painting);
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oblectMapper.writeValueAsString(dto)))
		.andExpect(status().isCreated())
		.andExpect(content().json(oblectMapper.writeValueAsString(dto)));
	}
	
	@Test
	public void createTestAlredyExist() {
		assert(false);
	}
	
	@Test
	public void createTestNameNull() throws Exception {
		dto.setName(null);
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oblectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestNameEmpty() throws Exception {
		dto.setName("");
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oblectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestNameBlank() throws Exception {
		dto.setName("   ");
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oblectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestTypeNull() throws Exception {
		dto.setType(null);
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(oblectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
}
