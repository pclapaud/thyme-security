package fr.laerce.thymesecurity.security.dao;

import fr.laerce.thymesecurity.security.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  21/03/2017.
 *
 * @author fred
 */
public interface GroupDao extends JpaRepository<Groups, Long>{
}
