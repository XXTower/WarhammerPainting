package fr.bonneau.warhammerPainting.controller;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.bonneau.warhammerPainting.controller.exception.CustomGlobalExceptionHandler;
import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.mappeur.PaintingMappeur;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.service.PaintingService;

public class PaintingControllerTest {

	private PaintingService service;
	private PaintingMappeur mappeur;
	private Painting painting;
	private Painting newPainting;
	private PaintingDto dto;
	private PaintingDto newDto;
	private PaintingController controller;
    private MockMvc mockMvc ;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
	
	
	@BeforeEach
	public void beforEach() {
		newPainting = new Painting();
		newPainting.setId(0);
		newPainting.setName("abadon black");
		newPainting.setType(PaintingTypes.BASE);
		painting = new Painting();
		painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);

		newDto = new PaintingDto();
		newDto.setId(0);
		newDto.setName("abadon black");
		newDto.setType(PaintingTypes.BASE);
		dto = new PaintingDto();
		dto.setId(1);
		dto.setName("abadon black");
		dto.setType(PaintingTypes.BASE);

		service = mock(PaintingService.class);
		mappeur = mock(PaintingMappeur.class);
		controller = new PaintingController(service, mappeur);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new CustomGlobalExceptionHandler())
				.build();
	}
	
	//  -----------getAll----------
	
	@Test
	public void getAllTest() throws Exception {
		List<Painting> paintings = Collections.singletonList(painting);
		List<PaintingDto> dtos = Collections.singletonList(dto);

		when(mappeur.paintingsToDtos(paintings)).thenReturn(dtos);
		when(service.getAll()).thenReturn(paintings);

		mockMvc.perform(get("/api/v1/paintings"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(dtos)));
	}
	
	//  -----------createTest----------
	
	@Test
	public void createTest() throws Exception {
		when(mappeur.dtoToPainting(newDto)).thenReturn(newPainting);
		when(mappeur.paintingToDto(painting)).thenReturn(dto);
		when(service.create(newPainting)).thenReturn(painting);
		
		String result = mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isCreated())
		.andReturn()
        .getResponse()
        .getContentAsString();
		
		PaintingDto dtoTest = objectMapper.readValue(result, PaintingDto.class);
		assertEquals(dto, dtoTest);
	}
	
	@Test
	public void createTestAlredyExist() throws Exception {
		when(mappeur.dtoToPainting(newDto)).thenReturn(newPainting);
		when(service.create(newPainting)).thenThrow(AlreadyExistException.class);
		
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestNameNull() throws Exception {
		newDto.setName(null);
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestNameEmpty() throws Exception {
		newDto.setName("");
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestNameBlank() throws Exception {
		newDto.setName("   ");
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestTypeNull() throws Exception {
		newDto.setType(null);
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestIdNot0() throws Exception {
		mockMvc.perform(post("/api/v1/paintings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	//  -----------updateTest----------
	
	@Test
	public void updateTest() throws Exception {
		when(mappeur.dtoToPainting(dto)).thenReturn(painting);
		when(mappeur.paintingToDto(painting)).thenReturn(dto);
		when(service.update(painting)).thenReturn(painting);
		
		String result = mockMvc.perform(put("/api/v1/paintings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isOk())
		.andReturn()
        .getResponse()
        .getContentAsString();
		
		PaintingDto dtoTest = objectMapper.readValue(result, PaintingDto.class);
		assertEquals(dto, dtoTest);
	}
	
	@Test
	public void updateTestNameNull() throws Exception {
		dto.setName(null);
		mockMvc.perform(put("/api/v1/paintings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestNameEmpty() throws Exception {
		dto.setName("");
		mockMvc.perform(put("/api/v1/paintings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestNameBlank() throws Exception {
		dto.setName("   ");
		mockMvc.perform(put("/api/v1/paintings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestIdDtfferenteBodyParam() throws Exception {
		mockMvc.perform(put("/api/v1/paintings/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestId0() throws Exception {
		dto.setId(0);
		mockMvc.perform(put("/api/v1/paintings/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestTypeNull() throws Exception {
		dto.setType(null);
		mockMvc.perform(put("/api/v1/paintings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
}
