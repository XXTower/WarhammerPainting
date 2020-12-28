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

import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.mappeur.PaintingMappeur;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.service.PaintingService;


@RestController
@RequestMapping("api/v1/paintings")
public class PaintingController {
	
	private PaintingService service;
	private PaintingMappeur mappeur;
	
	public PaintingController(PaintingService service, PaintingMappeur mappeur) {
		super();
		this.service = service;
		this.mappeur = mappeur;
	}

	@GetMapping()
	public ResponseEntity<List<PaintingDto>> getAll() { 
		List<PaintingDto> paintingDtos = mappeur.paintingsToDtos(service.getAll());	
		return ResponseEntity.ok(paintingDtos);
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@Valid @RequestBody PaintingDto paintingDto) throws AlreadyExistException {
		if(paintingDto.getId() != 0) {
			return ResponseEntity.badRequest().body("The Id in body must be equal to 0 or not presente");
		}
		Painting painting = mappeur.dtoToPainting(paintingDto);
		PaintingDto newPaintingDto = mappeur.paintingToDto(service.create(painting));
		return new ResponseEntity<PaintingDto>(newPaintingDto,HttpStatus.CREATED);
	}
	
	 @PutMapping("/{id}")
	 public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody PaintingDto paintingDto){
		 if(id != paintingDto.getId()) {
			 return ResponseEntity.badRequest().body("The Id in parameter must be the same in the body of the request");
		 }
		 if(paintingDto.getId() == 0) {
			 return ResponseEntity.badRequest().body("The Id in body must be diferente to 0");
		 }
		 Painting painting = mappeur.dtoToPainting(paintingDto);
		 PaintingDto paintingUpdate = mappeur.paintingToDto(service.update(painting));
		 return ResponseEntity.ok(paintingUpdate);
	 }
}
