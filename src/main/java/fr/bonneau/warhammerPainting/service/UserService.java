package fr.bonneau.warhammerPainting.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import fr.bonneau.warhammerPainting.exception.ObjectNotFoundException;
import fr.bonneau.warhammerPainting.models.User;
import fr.bonneau.warhammerPainting.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> getAll(){
        return userRepository.getAll();
    }
    
    @Transactional
    public User create(User user) {
        return userRepository.saveOrUpdate(user);
    }
    
    @Transactional
    public User getById(int id) throws ObjectNotFoundException {
        return userRepository.getById(id);
    }
    
    @Transactional
    public User update(User user) {
        return userRepository.saveOrUpdate(user);
    }
}
