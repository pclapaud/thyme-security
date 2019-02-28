package fr.laerce.thymesecurity.web;


import fr.laerce.thymesecurity.security.dao.GroupDao;
import fr.laerce.thymesecurity.security.domain.User;
import fr.laerce.thymesecurity.security.service.JpaUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("")
public class UserController {


    GroupDao groupDao;
    JpaUserService jpaUserService;

    public UserController(GroupDao groupDao,JpaUserService jpaUserService) {
        this.jpaUserService = jpaUserService;
        assert(jpaUserService != null);
        this.groupDao = groupDao;
        assert(groupDao != null);
    }

    @GetMapping("/profil/{name}")
    public String home2(Model model, @PathVariable("name")String name){
        model.addAttribute("userU",jpaUserService.findByUserName(name));
        model.addAttribute("users",jpaUserService.findAll());
        return "profil";
    }
    @GetMapping("/editpassword/{id}")
    public String modpasswordForm(Model model, @PathVariable("id")long id){
        model.addAttribute("user",jpaUserService.findByUserId(id));
        model.addAttribute("idUser",id);
        return "editpassword";
    }
    @GetMapping("/mod")
    public String modpassword(@RequestParam("id") long id,@RequestParam("password") String password,@RequestParam("password1") String password1,@RequestParam("password2") String password2){
    if (password1.equals(password2)){

        User user = jpaUserService.findByUserId(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, user.getPassword())){
            user.setPassword(password1);
            jpaUserService.save(user);
        }

    }
        return "redirect:/editpassword/"+id;
    }
    @GetMapping("/mod2")
    public String modpasswordAdmin(@RequestParam("id") long id,@RequestParam("password1") String password1,@RequestParam("password2") String password2){
        if (password1.equals(password2)){
            User user = jpaUserService.findByUserId(id);
            user.setPassword(password1);
            jpaUserService.save(user);
        }
        return "redirect:/profil";
    }

}


