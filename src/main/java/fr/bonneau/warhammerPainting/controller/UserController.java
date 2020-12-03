package fr.bonneau.warhammerPainting.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.bonneau.warhammerPainting.dto.UserDto;
import fr.bonneau.warhammerPainting.mappeur.UserMappeur;
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
}
