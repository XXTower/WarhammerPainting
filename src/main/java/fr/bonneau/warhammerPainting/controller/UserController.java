package fr.bonneau.warhammerPainting.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.exception.UserNotFoundException;
import fr.bonneau.warhammerPainting.mappeur.UserMappeur;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final UserMappeur userMappeur;
    private final UserService userService;
    
    public UserController(UserMappeur userMappeur, UserService userService) {
        this.userMappeur = userMappeur;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        return ResponseEntity.ok(userMappeur.mapToDtos(userService.getAll()));
    }
    
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto){
        User user = userMappeur.mapToUser(userDto);
        UserDto userDtoCreate = userMappeur.mapToDto(userService.create(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoCreate);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable int id) throws UserNotFoundException{
        return ResponseEntity.ok(userMappeur.mapToDto(userService.getById(id)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody UserDto userDto){
        if(id != userDto.getId()) {
            return ResponseEntity.badRequest().body("The Id in parameter must be the same in the body of the request");
        }
        User user = userMappeur.mapToUser(userDto);
        UserDto userDtoUpdate = userMappeur.mapToDto(userService.update(user));
        return ResponseEntity.ok(userDtoUpdate);
    }
}
