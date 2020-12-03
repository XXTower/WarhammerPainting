package fr.bonneau.warhammerPainting.mappeur;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.models.User;

@Component
public class UserMappeur {

    public UserDto mapToDto(User user) {
        if(user == null) return null;
        
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    
    public List<UserDto> mapToDtos(List<User> users) {
        if(users == null) return null;
        return users.stream().map(user -> mapToDto(user)).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    public User mapToUser(UserDto userDto) {
        if(userDto == null) return null;
        
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(user.getPassword());
        return user;
    }
    
    public List<User> mapToUsers(List<UserDto> userDtos){
        if(userDtos == null) return null;
        return userDtos.stream().map(userDto -> mapToUser(userDto)).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
