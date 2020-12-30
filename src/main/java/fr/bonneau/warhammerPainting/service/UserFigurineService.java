package fr.bonneau.warhammerPainting.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.bonneau.warhammerPainting.models.UserFigurine;
import fr.bonneau.warhammerPainting.repository.UserFigurineRepository;

@Service
public class UserFigurineService {
	
	private UserFigurineRepository repository;
	
	public UserFigurineService(UserFigurineRepository repository) {
		super();
		this.repository = repository;
	}

	public List<UserFigurine> getAll() {	
		return repository.getALL();
	}

	public List<UserFigurine> getByUserId(int userId) {
		return repository.getByUserId(userId);
	}

	public List<UserFigurine> getByFigurineId(int figurineId) {
		return repository.getByFigurineId(figurineId);
	}

	@Transactional
	public UserFigurine create(UserFigurine userFigurine) {
		return repository.saveOrUpdate(userFigurine);
	}

	@Transactional
	public UserFigurine update(UserFigurine userFigurine) {
		return repository.saveOrUpdate(userFigurine);
	}

	@Transactional
	public UserFigurine delete(UserFigurine userFigurine) {
		return repository.delete(userFigurine);
	}

}
