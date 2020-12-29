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

import fr.bonneau.warhammerPainting.dto.FigurineDto;
import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.mappeur.FigurineMappeur;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.service.FigurineService;

@RestController
@RequestMapping("/api/v1/figurines")
public class FigurineController {

    private final FigurineMappeur figurineMappeur;
    private final FigurineService figurineService;
    
    public FigurineController(FigurineMappeur figurineMappeur, FigurineService figurineService) {
        this.figurineMappeur = figurineMappeur;
        this.figurineService = figurineService;
    }
    
    @GetMapping
    public ResponseEntity<List<FigurineDto>> getAll(){
        return ResponseEntity.ok(figurineMappeur.mapToDtos(figurineService.getAll()));
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FigurineDto figurineDto) throws AlreadyExistException{
        if (figurineDto.getId() != 0) {
            return ResponseEntity.badRequest().body("The Id in body must be equal to 0 or not presente");
        }
        Figurine figurine = figurineMappeur.mapToFigurine(figurineDto);
        FigurineDto figurineDtoCreate = figurineMappeur.mapToDto(figurineService.create(figurine));
        return ResponseEntity.status(HttpStatus.CREATED).body(figurineDtoCreate);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody FigurineDto figurineDto) throws AlreadyExistException{
        if(id != figurineDto.getId()) {
            return ResponseEntity.badRequest().body("The Id in parameter must be the same in the body of the request");
        }
        if(figurineDto.getId() == 0) {
            return ResponseEntity.badRequest().body("The Id in body must be diferente to 0");
        }
        Figurine figurine = figurineMappeur.mapToFigurine(figurineDto);
        FigurineDto figurineDtoUpdate = figurineMappeur.mapToDto(figurineService.update(figurine));
        return ResponseEntity.ok(figurineDtoUpdate);
    }
    
}
