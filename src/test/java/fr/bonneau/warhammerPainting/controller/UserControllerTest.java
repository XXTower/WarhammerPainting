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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.exception.UserNotFoundException;
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
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

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
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new CustomGlobalExceptionHandler())
                .build();
    }

    /*--------------------get all--------------------*/
    @Test
    void getAllShouldReturnStatusOkAndUsersList() throws Exception {
        List<User> users = Collections.singletonList(user);

        when(service.getAll()).thenReturn(users);
        when(mappeur.mapToDtos(users)).thenReturn(Collections.singletonList(userDto));

        String mockResult = mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> result = Arrays.asList(objectMapper.readValue(mockResult, UserDto[].class));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(userDto);
    }

    /*--------------------create--------------------*/
    @Test
    void createShouldReturnOkAndTheUser() throws Exception {
        userDto.setId(0);
        user.setId(0);
        when(mappeur.mapToUser(userDto)).thenReturn(user);
        when(service.create(user)).thenReturn(user);
        when(mappeur.mapToDto(user)).thenReturn(userDto);

        String mockResult = mockMvc
                .perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(service, times(1)).create(user);
        verifyNoMoreInteractions(service);

        UserDto result = objectMapper.readValue(mockResult, UserDto.class);
        assertThat(result).isEqualTo(userDto);
    }
    
    @Test
    void createShouldReturn400WhenIddifferent0() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenUsernameIsNull() throws Exception {
        userDto.setUsername(null);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenUsernameIsEmpty() throws Exception {
        userDto.setUsername("");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenUsernameIsBlank() throws Exception {
        userDto.setUsername(" ");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenPasswordIsNull() throws Exception {
        userDto.setPassword(null);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenPasswordIsEmpty() throws Exception {
        userDto.setPassword("");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void createShouldReturn400WhenPasswordIsBlank() throws Exception {
        userDto.setPassword(" ");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    /*----------------------getById------------------------------*/
    @Test
    void getByIdShouldReturnUserDto() throws Exception {
        int id = 1;

        when(service.getById(id)).thenReturn(user);
        when(mappeur.mapToDto(user)).thenReturn(userDto);

        String mockResult = mockMvc.perform(get("/api/v1/users/{id}", id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto result = objectMapper.readValue(mockResult, UserDto.class);
        assertThat(result).isEqualTo(userDto);
    }
    
    @Test
    void getByIdShouldReturn405WhenUserNotFound() throws Exception {
        int id = 1;

        when(service.getById(id)).thenThrow(UserNotFoundException.class);
        
        mockMvc.perform(get("/api/v1/users/{id}", id))
                .andExpect(status().isNotFound());
            
    }

    /*----------------------update ------------------------------*/
    @Test
    void updateShouldRetunUserDtoUpdate() throws Exception {
        int id = 1;

        when(mappeur.mapToUser(userDto)).thenReturn(user);
        when(service.update(user)).thenReturn(user);
        when(mappeur.mapToDto(user)).thenReturn(userDto);

        String mockResult = mockMvc.perform(put("/api/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto result = objectMapper.readValue(mockResult, UserDto.class);
        assertThat(result).isEqualTo(userDto);
    }

    @Test
    void updateShouldRetun400WhenIdIsDifferent() throws Exception {
        int id = 2;

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldRetun400WhenIdIsEqualToZero() throws Exception {
        int id = 0;
        userDto.setId(0);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateShouldReturn400WhenUsernameIsNull() throws Exception {
        int id = 1;
        userDto.setUsername(null);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenUsernameIsEmpty() throws Exception {
        int id = 1;
        userDto.setUsername("");

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenUsernameIsBlank() throws Exception {
        int id = 1;
        userDto.setUsername(" ");

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenPasswordIsNull() throws Exception {
        int id = 1;
        userDto.setPassword(null);

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenPasswordIsEmpty() throws Exception {
        int id = 1;
        userDto.setPassword("");

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturn400WhenPasswordIsBlank() throws Exception {
        int id = 1;
        userDto.setPassword(" ");

        mockMvc.perform(put("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userDto)))
        .andExpect(status().isBadRequest());
    }
}
