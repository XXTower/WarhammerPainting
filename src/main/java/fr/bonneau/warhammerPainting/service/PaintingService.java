package fr.bonneau.warhammerPainting.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bonneau.warhammerPainting.exception.AlreadyExistException;
import fr.bonneau.warhammerPainting.models.Painting;
import fr.bonneau.warhammerPainting.repository.PaintingReposirory;

@Service
public class PaintingService {
	
	private PaintingReposirory reposirory;

	public PaintingService(PaintingReposirory reposirory) {
		super();
		this.reposirory = reposirory;
	}

	public List<Painting> getAll() {
		 
		return reposirory.getAll();
	}

	@Transactional
	public Painting create(Painting painting) throws AlreadyExistException {
		if(reposirory.checkIfExiste(painting)) {
			AlreadyExistException exception = new AlreadyExistException();
			exception.setMessage("this painting already existe");
			throw exception;
		}
		return reposirory.saveOrUpdate(painting);
	}

	public Painting update(Painting painting) {
		return reposirory.saveOrUpdate(painting);
	}
}
