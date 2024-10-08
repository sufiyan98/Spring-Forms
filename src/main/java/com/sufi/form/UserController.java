package com.sufi.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model){
//        User user = new User();
//        user.setFirstName("Dan");
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/save")
    public String saveUser(User user , Model model) {
        userRepository.save(user);
        model.addAttribute("message", "User information saved successfully!");
        return "index";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException exception, Model model){
        User user = (User) exception.getBindingResult().getTarget();
        model.addAttribute("user",user);
        model.addAttribute("error","Please fill out all the required fields");
        log.info("User Validation failed for: {}",user);
        return "index";
    }

}
