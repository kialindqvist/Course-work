package sec.project.controller;

import static java.util.Collections.list;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("/")
    public String defaultMapping() {
       return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String loadAdmin(Model model) {
        List<Signup> entries = signupRepository.findAll();
        model.addAttribute("entries", entries);
        return "admin";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        model.addAttribute("name", address);
        return "done";
    }

}
