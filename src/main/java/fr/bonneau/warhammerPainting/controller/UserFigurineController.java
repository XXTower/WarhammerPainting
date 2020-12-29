package fr.bonneau.warhammerPainting.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.dto.UserFigurineDto;
import fr.bonneau.warhammerPainting.mappeur.UserFigurineMappeur;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.service.UserFigurineService;

@RestController
@RequestMapping("api/v1/userFigurine")
public class UserFigurineController {

	private UserFigurineMappeur mappeur;
	private UserFigurineService service;
	
	public UserFigurineController(UserFigurineMappeur mappeur, UserFigurineService service) {
		super();
		this.mappeur = mappeur;
		this.service = service;
	}
	
	@GetMapping()
	public ResponseEntity<List<UserFigurineDto>> getAll() {
		List<UserFigurineDto> userFigurineDtos = mappeur.userFigurinesToDtos(service.getAll());
		return ResponseEntity.ok(userFigurineDtos);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<UserFigurineDto>> getAllForUser(@PathVariable int userId) {
		List<UserFigurineDto> userFigurineDtos = mappeur.userFigurinesToDtos(service.getByUserId(userId));
		return ResponseEntity.ok(userFigurineDtos);
	}
	
	@GetMapping("/figurine/{figurineId}")
	public ResponseEntity<List<UserFigurineDto>> getAllForidFigurine(@PathVariable int figurineId) {
		List<UserFigurineDto> userFigurineDtos = mappeur.userFigurinesToDtos(service.getByFigurineId(figurineId));
		return ResponseEntity.ok(userFigurineDtos);
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@Valid @RequestBody UserFigurineDto userFigurineDto) {
		if(userFigurineDto.getId() != 0) {
			return ResponseEntity.badRequest().body("The Id in body must be equal to 0 or not presente");
		}
		UserFigurine UserFigurine = mappeur.dtoToUserFigurine(userFigurineDto);
		UserFigurineDto newUserFigurineDto = mappeur.userFigurineToDto(service.create(UserFigurine));
		return new ResponseEntity<UserFigurineDto>(newUserFigurineDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody UserFigurineDto userFigurineDto) {
		if(id != userFigurineDto.getId()) {
			 return ResponseEntity.badRequest().body("The Id in parameter must be the same in the body of the request");
		 }
		 if(userFigurineDto.getId() == 0) {
			 return ResponseEntity.badRequest().body("The Id in body must be diferente to 0");
		 }
		 
		 UserFigurine UserFigurine = mappeur.dtoToUserFigurine(userFigurineDto);
		 UserFigurineDto UserFigurineUpdate = mappeur.userFigurineToDto(service.update(UserFigurine));
		 return new ResponseEntity<UserFigurineDto>(UserFigurineUpdate,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id, @Valid @RequestBody UserFigurineDto userFigurineDto) {
		if(id != userFigurineDto.getId()) {
			 return ResponseEntity.badRequest().body("The Id in parameter must be the same in the body of the request");
		 }
		 if(userFigurineDto.getId() == 0) {
			 return ResponseEntity.badRequest().body("The Id in body must be diferente to 0");
		 }
		 
		 UserFigurine UserFigurine = mappeur.dtoToUserFigurine(userFigurineDto);
		 UserFigurineDto userFigurineDtoDelete = mappeur.userFigurineToDto(service.dalete(UserFigurine));
		 return ResponseEntity.ok(String.format("the userFigurine %s has been daleted", userFigurineDtoDelete));
	}
}
