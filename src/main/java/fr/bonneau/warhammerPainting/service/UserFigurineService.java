package fr.bonneau.warhammerPainting.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.repository.FigurineRepository;
import fr.bonneau.warhammerPainting.repository.PaintingReposirory;
import fr.bonneau.warhammerPainting.repository.UserFigurineRepository;

@Service
public class UserFigurineService {
	
	private UserFigurineRepository userFigurinerepository;
	private FigurineRepository figurineRepository;
	private PaintingReposirory paintingReposirory;

	public UserFigurineService(UserFigurineRepository userFigurinerepository, FigurineRepository figurineRepository, PaintingReposirory paintingReposirory) {
        this.userFigurinerepository = userFigurinerepository;
        this.figurineRepository = figurineRepository;
        this.paintingReposirory = paintingReposirory;
    }

    public List<UserFigurine> getAll() {	
		return userFigurinerepository.getALL();
	}

	public List<UserFigurine> getByUserId(int userId) {
		return userFigurinerepository.getByUserId(userId);
	}

	public List<UserFigurine> getByFigurineId(int figurineId) {
		return userFigurinerepository.getByFigurineId(figurineId);
	}

	@Transactional
	public UserFigurine create(UserFigurine userFigurine) throws ObjectNotFoundException {
	    verificationObject(userFigurine);
		return userFigurinerepository.saveOrUpdate(userFigurine);
	}

	@Transactional
	public UserFigurine update(UserFigurine userFigurine) throws ObjectNotFoundException {
	    verificationObject(userFigurine);
		return userFigurinerepository.saveOrUpdate(userFigurine);
	}

	@Transactional
	public UserFigurine delete(UserFigurine userFigurine) throws ObjectNotFoundException {
		return userFigurinerepository.delete(userFigurine);
	}
	
	private void verificationObject(UserFigurine userFigurine) throws ObjectNotFoundException {
	    figurineRepository.getById(userFigurine.getFigurine().getId());
	    List<Painting> paintings = paintingReposirory.getAll();
	    
	    if(!paintings.containsAll(userFigurine.getListPainting())) throw new ObjectNotFoundException("Painting");
	}

}
