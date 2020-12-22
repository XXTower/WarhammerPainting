package fr.bonneau.warhammerPainting.mappeur;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.models.User;

public class UserMappeurTest {

    private UserMappeur mappeur = new UserMappeur();
    private User user;
    private UserDto userDto;
    
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
    }
    
    /*------------------ mapToUser ----------------------------------*/
    @Test
    void mapToUserWithNullShouldReturnNull() {
        
        User userMapper = mappeur.mapToUser(null);
        assertThat(userMapper).isNull();
    }
    
    @Test
    void mapToUserWithDtoShouldRetunUserMappedFieldByField() {
        User userMapper = mappeur.mapToUser(userDto);
        assertThat(userMapper).isEqualTo(user);
    }
    
    /*------------------ mapToUsers ----------------------------------*/
    @Test
    void mapToUsersWithNullListShouldReturnEmptyList() {
        
        List<User> usersMapper = mappeur.mapToUsers(null);
        assertThat(usersMapper).isEmpty();
    }
    
    @Test
    void mapToUsersWithEmptyListListShouldReturnEmptyList() {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        
        List<User> usersMapper = mappeur.mapToUsers(userDtos);
        assertThat(usersMapper).isEmpty();
    }
    
    @Test
    void mapToUsersWithNoEmptyListListShouldReturnNoEmptyListWithSameSize() {
        List<UserDto> userDtos = Collections.singletonList(userDto);
        
        List<User> usersMapper = mappeur.mapToUsers(userDtos);
        assertThat(usersMapper).hasSize(1);
        User userMapper = usersMapper.get(0);
        assertThat(userMapper).isEqualTo(user);
    }
    
    /*------------------ mapToDto ----------------------------------*/
    @Test
    void mapToDtoWihtNullShouldReturnEmptyList() {
        UserDto userDtoMapper = mappeur.mapToDto(null);
        assertThat(userDtoMapper).isNull();
    }
    
    @Test
    void mapToDtoWihthUserShouldRetunDtoMappedFieldByField() {
        UserDto userDtoMapper = mappeur.mapToDto(user);
        assertThat(userDtoMapper).isEqualTo(userDto);
    }
    
    /*------------------ mapToDtos ----------------------------------*/
    @Test
    void mapToDtosWithNullListShouldReturnEmptyList() {
        List<UserDto> userDtosMapper = mappeur.mapToDtos(null);
        assertThat(userDtosMapper).isEmpty();
    }
    
    @Test
    void mapToDtosWithEmptyListListShouldReturnEmptyList() {
        List<User> users = new ArrayList<User>();
        
        List<UserDto> userDtosMapper = mappeur.mapToDtos(users);
        assertThat(userDtosMapper).isEmpty();
    }
    
    @Test
    void mapToDtosWithNoEmptyListListShouldReturnNoEmptyListWithSameSize() {
        List<User> users = Collections.singletonList(user);
        
        List<UserDto> userDtosMapper = mappeur.mapToDtos(users);
        assertThat(userDtosMapper).hasSize(1);
        UserDto userDtoMapper = userDtosMapper.get(0);
        assertThat(userDtoMapper).isEqualTo(userDto);
    }
}
