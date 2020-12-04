package fr.bonneau.warhammerPainting.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.mappeur.UserMappeur;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.service.UserService;

public class UserControllerTest {

    private User user;
    private UserDto userDto;
    private MockMvc mockMvc;
    private UserMappeur mappeur;
    private UserService service;
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("test");
        userDto.setPassword("test");
        
        service = mock(UserService.class);
        mappeur = mock(UserMappeur.class);
        UserController controller = new UserController(mappeur, service);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }
    
    /*--------------------get all--------------------*/
    @Test
    void getAllShouldReturnStatusOkAndUsersList() throws Exception {
        List<User> users = Collections.singletonList(user);
        
        when(service.getAll()).thenReturn(users);
        when(mappeur.mapToDtos(users)).thenReturn(Collections.singletonList(userDto));
        
        String result = mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        List<UserDto> dtos = Arrays.asList(objectMapper.readValue(result, UserDto[].class));
        
        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
        assertThat(dtos).hasSize(1);
        assertThat(dtos.get(0)).isEqualTo(userDto);
    }
    
    /*--------------------create--------------------*/
    @Test
    void createShouldReturnOkAndTheUser() throws Exception {
        when(mappeur.mapToUser(userDto)).thenReturn(user);
        when(service.create(user)).thenReturn(user);
        when(mappeur.mapToDto(user)).thenReturn(userDto);
        
        String result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        verify(service, times(1)).create(user);
        verifyNoMoreInteractions(service);

        assertThat(result).isEqualTo(userDto);
    }
}
