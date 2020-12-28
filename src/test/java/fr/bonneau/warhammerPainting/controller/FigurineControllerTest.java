package fr.bonneau.warhammerPainting.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import fr.bonneau.warhammerPainting.mappeur.FigurineMappeur;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;
import fr.bonneau.warhammerPainting.service.FigurineService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class FigurineControllerTest {

    private Figurine figurine;
    private FigurineDto figurineDto;
    private MockMvc mockMvc;
    private FigurineMappeur mappeur;
    private FigurineService service;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
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
        
        service = mock(FigurineService.class);
        mappeur = mock(FigurineMappeur.class);
        FigurineController controller = new FigurineController(mappeur, service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .build();
    }
    
    /*--------------------get all--------------------*/
    @Test
    void getAllShouldReturnStatusOkAndFigurineList() throws Exception {
        List<Figurine> figurines = Collections.singletonList(figurine);
        
        when(service.getAll()).thenReturn(figurines);
        when(mappeur.mapToDtos(figurines)).thenReturn(Collections.singletonList(figurineDto));
        
        String mockResult = mockMvc.perform(get("/api/v1/figurines"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        List<FigurineDto> result = Arrays.asList(objectMapper.readValue(mockResult, FigurineDto[].class));
        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(figurineDto);
    }
    
    /*--------------------create--------------------*/
    @Test
    void  createShouldReturnOkAndTheFigurine() throws Exception {
        figurineDto.setId(0);
        figurine.setId(0);
        when(mappeur.mapToFigurine(figurineDto)).thenReturn(figurine);
        when(service.create(figurine)).thenReturn(figurine);
        when(mappeur.mapToDto(figurine)).thenReturn(figurineDto);
        
        String mockResult = mockMvc
                .perform(post("/api/v1/figurines")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(figurineDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        verify(service, times(1)).create(figurine);
        verifyNoMoreInteractions(service);
        
        FigurineDto result = objectMapper.readValue(mockResult, FigurineDto.class);
        assertThat(result).isEqualTo(figurineDto);
    }
    
    @Test
    void  createShouldReturn400WhenIdNotZero() throws Exception {
        mockMvc.perform(post("/api/v1/figurines")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(figurineDto)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void createShouldReturn400WhenNameIsNull() throws Exception {
        figurineDto.setName(null);

        mockMvc.perform(post("/api/v1/figurines")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void createShouldReturn400WhenNameIsEmpty() throws Exception {
        figurineDto.setName("");

        mockMvc.perform(post("/api/v1/figurines")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void createShouldReturn400WhenNameIsBlank() throws Exception {
        figurineDto.setName(" ");

        mockMvc.perform(post("/api/v1/figurines")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void createShouldReturn400WhenFactionIsNull() throws Exception {
        figurineDto.setFaction(null);

        mockMvc.perform(post("/api/v1/figurines")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void createShouldReturn400WhenSubFactionIsNull() throws Exception {
        figurineDto.setSubFaction(null);

        mockMvc.perform(post("/api/v1/figurines")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    /*----------------------update ------------------------------*/
    @Test
    void updateShouldRetunFigurineDtoUpdate() throws Exception {
        int id = 1;

        when(mappeur.mapToFigurine(figurineDto)).thenReturn(figurine);
        when(service.update(figurine)).thenReturn(figurine);
        when(mappeur.mapToDto(figurine)).thenReturn(figurineDto);

        String mockResult = mockMvc.perform(put("/api/v1/figurines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(figurineDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        FigurineDto result = objectMapper.readValue(mockResult, FigurineDto.class);
        assertThat(result).isEqualTo(figurineDto);
    }
    
    @Test
    void updateShouldRetun400WhenIdIsDifferent() throws Exception {
        int id = 2;

        mockMvc.perform(put("/api/v1/figurines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenNameIsNull() throws Exception {
        int id = 1;
        figurineDto.setName(null);

        mockMvc.perform(put("/api/v1/figurines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenNameIsEmpty() throws Exception {
        int id = 1;
        figurineDto.setName("");

        mockMvc.perform(put("/api/v1/figurines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenNameIsBlank() throws Exception {
        int id = 1;
        figurineDto.setName(" ");

        mockMvc.perform(put("/api/v1/figurines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenFactionIsNull() throws Exception {
        int id = 1;
        figurineDto.setFaction(null);

        mockMvc.perform(put("/api/v1/figurines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenSubFactionIsNull() throws Exception {
        int id = 1;
        figurineDto.setSubFaction(null);

        mockMvc.perform(put("/api/v1/figurines/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(figurineDto)))
        .andExpect(status().isBadRequest());
    }
}
