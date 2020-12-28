package fr.bonneau.warhammerPainting.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.models.Figurine;
import fr.bonneau.warhammerPainting.repository.FigurineRepository;

@Service
public class FigurineService {
    
    private FigurineRepository figurineRepository;
    
    public FigurineService(FigurineRepository figurineRepository) {
        this.figurineRepository = figurineRepository;
    }

    public List<Figurine> getAll(){
        return figurineRepository.getAll();
    }
    
    @Transactional
    public Figurine create(Figurine figurine) throws AlreadyExistException {
        if(figurineRepository.checkIfExiste(figurine.getName())) {
            throw new AlreadyExistException("figurine");
        }
        return figurineRepository.saveOrUpdate(figurine);
    }
    
    @Transactional
    public Figurine update(Figurine figurine) throws AlreadyExistException {
        if(figurineRepository.checkIfExiste(figurine.getName())) {
            throw new AlreadyExistException("figurine");
        }
        return figurineRepository.saveOrUpdate(figurine);
    }
    
}
