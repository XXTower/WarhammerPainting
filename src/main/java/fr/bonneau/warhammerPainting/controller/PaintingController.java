package fr.bonneau.warhammerPainting.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.bonneau.warhammerPainting.dto.PaintingDto;
import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.mappeur.PaintingMappeur;
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
	public ResponseEntity<PaintingDto> create(@Valid @RequestBody PaintingDto paintingDto) {
		System.out.println("creat name :" + paintingDto.getName());
		PaintingDto newPainting;
		try {
			newPainting = mappeur.paintingToDto(service.create(mappeur.dtoToPainting(paintingDto)));
		} catch (AlreadyExistException e) {
			return new ResponseEntity<PaintingDto>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<PaintingDto>(newPainting,HttpStatus.CREATED);
	}
	 
}
