package fr.bonneau.warhammerPainting.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.bonneau.warhammerPainting.exception.UserNotFoundException;
import fr.bonneau.warhammerPainting.models.User;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
    
    private User user;
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        
        userRepository = new UserRepository(em);
    }
    
    /*-------------------- getAll --------------------*/
    @Test
    void getAllShouldReturnUserList() {
        List<User> fetchedUsers = userRepository.getAll();
        
        assertThat(fetchedUsers).hasSize(2);
        assertThat(fetchedUsers.get(0)).isEqualTo(user);
    }
    
    /*-------------------- getById --------------------*/
    
    @Test
    void getByIdReturnUser() throws UserNotFoundException {
        User fetchedUser = userRepository.getById(1);
        
        assertThat(fetchedUser).isEqualTo(user);
    }
    
    @Test
    void getByIdShouldThrowUserNotFound() {
        Exception exception = assertThrows(UserNotFoundException.class, ()->userRepository.getById(13));
        
        String excpectMessage = "User not found.";
        assertThat(exception.getMessage()).contains(excpectMessage);
    }
    
    /*-------------------- saveOrUpdate --------------------*/
    
    @Test
    void saveOrUpdateShouldSaveNewUser() {
        User newUser = new User();
        newUser.setUsername("toto");
        newUser.setPassword("toto");
        
        User excepteUser = new User();
        excepteUser.setId(3);
        excepteUser.setUsername("toto");
        excepteUser.setPassword("toto");
        
        User fetchUser = userRepository.saveOrUpdate(newUser);
        
        assertThat(fetchUser).isEqualTo(excepteUser);
        assertThat(userRepository.getAll()).hasSize(3);
    }
    
    @Test
    void saveOrUpdateShouldUpdateOldUser() {
        User userToUpdate = new User();
        userToUpdate.setId(2);
        userToUpdate.setUsername("toto");
        userToUpdate.setPassword("toto");
        
        User excepteUser = new User();
        excepteUser.setId(2);
        excepteUser.setUsername("toto");
        excepteUser.setPassword("toto");
        
        User fetchUser = userRepository.saveOrUpdate(userToUpdate);
        
        assertThat(fetchUser).isEqualTo(excepteUser);
    }
}
