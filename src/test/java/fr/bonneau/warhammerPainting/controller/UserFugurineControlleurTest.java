package fr.bonneau.warhammerPainting.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fr.bonneau.warhammerPainting.controller.exception.CustomGlobalExceptionHandler;
import fr.bonneau.warhammerPainting.dto.FigurineDto;
import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.dto.UserFigurineDto;
import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.mappeur.UserFigurineMappeur;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;
import fr.bonneau.warhammerPainting.service.UserFigurineService;

public class UserFugurineControlleurTest {
	private UserFigurineService service;
	private UserFigurineMappeur mappeur;
	private UserFigurine userFigurine;
	private UserFigurine newUserFigurine;
	private UserFigurineDto dto;
	private UserFigurineDto newDto;
	private UserFigurineController controller;
    private MockMvc mockMvc ;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

    @BeforeEach
	public void beforEach() {
		Painting painting = new Painting();
		painting.setId(1);
		painting.setName("abadon black");
		painting.setType(PaintingTypes.BASE);

		PaintingDto paintingDto = new PaintingDto();
		paintingDto.setId(1);
		paintingDto.setName("abadon black");
		paintingDto.setType(PaintingTypes.BASE);
		
		Set<Painting> paintings = Collections.singleton(painting);
		List<PaintingDto> paintingDtos = Collections.singletonList(paintingDto);
		
		User user = new User();
		user.setId(1);
        user.setUsername("test");
        user.setPassword("test");

        UserDto userDto = new UserDto();
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("test");
        userDto.setPassword("test");
        
        Figurine figurine = new Figurine();
        figurine.setId(1);
        figurine.setName("test");
        figurine.setFaction(Faction.SPACE_MARINS);
        figurine.setSubFaction(SubFaction.ULTRAMARINS);
        
        FigurineDto figurineDto = new FigurineDto();
        figurineDto.setId(1);
        figurineDto.setName("test");
        figurineDto.setFaction(Faction.SPACE_MARINS);
        figurineDto.setSubFaction(SubFaction.ULTRAMARINS);
        
        userFigurine = new UserFigurine();
        userFigurine.setId(1);
		userFigurine.setTitle("title");
		userFigurine.setDescription("Descripsion");
		userFigurine.setVisibility(true);
		userFigurine.setFigurine(figurine);
		userFigurine.setUser(user);
		userFigurine.setListPainting(paintings);
		
		dto = new UserFigurineDto();
		dto.setId(1);
		dto.setTitle("title");
		dto.setDescription("Descripsion");
		dto.setVisibility(true);
		dto.setFigurine(figurineDto);
		dto.setUser(userDto);
		dto.setListPainting(paintingDtos);
		
		newUserFigurine = new UserFigurine();
		newUserFigurine.setId(0);
		newUserFigurine.setTitle("title");
		newUserFigurine.setDescription("Descripsion");
		newUserFigurine.setVisibility(true);
		newUserFigurine.setFigurine(figurine);
		newUserFigurine.setUser(user);
		newUserFigurine.setListPainting(paintings);
		
		newDto = new UserFigurineDto();
		newDto.setId(0);
		newDto.setTitle("title");
		newDto.setDescription("Descripsion");
		newDto.setVisibility(true);
		newDto.setFigurine(figurineDto);
		newDto.setUser(userDto);
		newDto.setListPainting(paintingDtos);

		service = mock(UserFigurineService.class);
		mappeur = mock(UserFigurineMappeur.class);
		controller = new UserFigurineController(mappeur, service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new CustomGlobalExceptionHandler())
				.build();
	}
    
    
    //  -----------getAll----------
	
	@Test
	public void getAllTest() throws Exception {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		List<UserFigurineDto> dtos = Collections.singletonList(dto);

		when(mappeur.userFigurinesToDtos(userFigurines)).thenReturn(dtos);
		when(service.getAll()).thenReturn(userFigurines);

		mockMvc.perform(get("/api/v1/userFigurines"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(dtos)));
	}
	
    //  -----------getByUserId----------
	
	@Test
	public void getByUserIdTest() throws Exception {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		List<UserFigurineDto> dtos = Collections.singletonList(dto);

		when(mappeur.userFigurinesToDtos(userFigurines)).thenReturn(dtos);
		when(service.getByUserId(1)).thenReturn(userFigurines);

		mockMvc.perform(get("/api/v1/userFigurines/user/1"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(dtos)));
	}
	
    //  -----------getByFigurineId----------
	
	@Test
	public void getByFigurineId() throws Exception {
		List<UserFigurine> userFigurines = Collections.singletonList(userFigurine);
		List<UserFigurineDto> dtos = Collections.singletonList(dto);

		when(mappeur.userFigurinesToDtos(userFigurines)).thenReturn(dtos);
		when(service.getByFigurineId(1)).thenReturn(userFigurines);

		mockMvc.perform(get("/api/v1/userFigurine/figurines/1"))
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(dtos)));
	}
	
	//  -----------createTest----------
	
	@Test
	public void createTest() throws Exception {
		when(mappeur.dtoToUserFigurine(newDto)).thenReturn(newUserFigurine);
		when(mappeur.userFigurineToDto(userFigurine)).thenReturn(dto);
		when(service.create(newUserFigurine)).thenReturn(userFigurine);
		
		String result = mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isCreated())
		.andReturn()
        .getResponse()
        .getContentAsString();
		
		UserFigurineDto dtoTest = objectMapper.readValue(result, UserFigurineDto.class);
		assertEquals(dto, dtoTest);
		verify(service).create(newUserFigurine);
	}
	
	@Test
	public void createTestIdNot0() throws Exception {
		mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestUserNull() throws Exception {
		newDto.setUser(null);
		mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestFigurineNull() throws Exception {
		newDto.setFigurine(null);
		mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestTitleNull() throws Exception {
		newDto.setTitle(null);
		mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestTitleEmpty() throws Exception {
		newDto.setTitle("");
		mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void createTestTitleBlank() throws Exception {
		newDto.setTitle("  ");
		mockMvc.perform(post("/api/v1/userFigurines")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	//  -----------updateTest----------
	
	@Test
	public void updateTest() throws Exception {
		when(mappeur.dtoToUserFigurine(dto)).thenReturn(userFigurine);
		when(mappeur.userFigurineToDto(userFigurine)).thenReturn(dto);
		when(service.update(userFigurine)).thenReturn(userFigurine);
		
		String result = mockMvc.perform(put("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isOk())
		.andReturn()
        .getResponse()
        .getContentAsString();
		
		UserFigurineDto dtoTest = objectMapper.readValue(result, UserFigurineDto.class);
		assertEquals(dto, dtoTest);
		verify(service).update(userFigurine);
	}
	
	@Test
	public void updateTestIdDtfferenteBodyParam() throws Exception {
		mockMvc.perform(put("/api/v1/userFigurines/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestId0() throws Exception {
		dto.setId(0);
		mockMvc.perform(put("/api/v1/userFigurines/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestUserNull() throws Exception {
		dto.setUser(null);
		mockMvc.perform(put("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestFigurineNull() throws Exception {
		dto.setFigurine(null);
		mockMvc.perform(put("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestTitleNull() throws Exception {
		dto.setTitle(null);
		mockMvc.perform(put("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestTitleEmpty() throws Exception {
		dto.setTitle("");
		mockMvc.perform(put("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateTestTitleBlank() throws Exception {
		dto.setTitle("  ");
		mockMvc.perform(put("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
//  -----------deleteTest----------
	
	@Test
	public void deleteTest() throws Exception {
		when(mappeur.dtoToUserFigurine(dto)).thenReturn(userFigurine);
		when(mappeur.userFigurineToDto(userFigurine)).thenReturn(dto);
		when(service.delete(userFigurine)).thenReturn(userFigurine);
		
		String result = mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isOk())
		.andReturn()
        .getResponse()
        .getContentAsString();
		
		assertEquals("the userFigurine 1 has been daleted", result);
		verify(service).delete(userFigurine);
	}
	
	@Test
	public void deleteTestIdDtfferenteBodyParam() throws Exception {
		mockMvc.perform(delete("/api/v1/userFigurines/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestId0() throws Exception {
		dto.setId(0);
		mockMvc.perform(delete("/api/v1/userFigurines/0")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestUserNull() throws Exception {
		dto.setUser(null);
		mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestFigurineNull() throws Exception {
		dto.setFigurine(null);
		mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestTitleNull() throws Exception {
		dto.setTitle(null);
		mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestTitleEmpty() throws Exception {
		dto.setTitle("");
		mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestTitleBlank() throws Exception {
		dto.setTitle("  ");
		mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteTestUserFigurineNotFound() throws Exception {
		when(mappeur.dtoToUserFigurine(dto)).thenReturn(userFigurine);
		when(service.delete(userFigurine)).thenThrow(new ObjectNotFoundException("userFigurine"));
		
		mockMvc.perform(delete("/api/v1/userFigurines/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isNotFound());
	}

}
