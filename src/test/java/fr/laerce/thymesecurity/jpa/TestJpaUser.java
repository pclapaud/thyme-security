package fr.laerce.thymesecurity.jpa;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fr.laerce.thymesecurity.security.dao.UserDao;
import fr.laerce.thymesecurity.security.domain.Groups;
import fr.laerce.thymesecurity.security.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.*;


/**
 * Projet thyme-security
 * Pour LAERCE SAS
 * <p>
 * Créé le  22/03/2017.
 *
 * @author fred
 */
@RunWith(SpringRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextBeforeModesTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
@DataJpaTest
@Transactional
@SpringBootTest
@TestPropertySource(value = "classpath:test.properties")
//@ComponentScan(value = {"fr.gmte94.lieux.service"})
@DatabaseSetup(value = {"classpath:/dbunit/db-init.xml"},
        type = DatabaseOperation.CLEAN_INSERT)
public class TestJpaUser {
    private UserDao userDao;

    //@PersistenceContext
    //EntityManager em;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Test
    @Rollback
    public void testGetAdmin(){

        User user = userDao.findOne(1L);
        System.out.println(user.getPassword());
        assertEquals("L'utilisateur est Admin",user.getName(),"Admin");
        user = userDao.findByName("Admin");
        assertNotNull("Admin est bien récupéré en minucsules", user);
        log.info("Nom utilisateur : "+ user.getName());
        Collection<Groups> groupss = user.getGroups();
        assertEquals("Admin appartient exactement à 2 groupes",
                groupss.size(),2);
        for(Groups grp: groupss){
            log.info("Groupe : "+ grp.getName()+" | Rôle : "+ grp.getRole());
        }
        log.info("Mot de passe : "+user.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("p@ssword :"+passwordEncoder.encode("p@ssw0rd"));

        System.out.println(user.getName());
        assertTrue("Mot de passe est 'p@ssw0rd' :",
                passwordEncoder.matches("p@ssw0rd", user.getPassword()));
    }
}
