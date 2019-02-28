package fr.laerce.thymesecurity.security.dao;

import fr.laerce.thymesecurity.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
public interface UserDao extends JpaRepository<User, Long>{
    User findByName(String name);
    User findById(long id);
    List<User> findAllByOrderById();
}
