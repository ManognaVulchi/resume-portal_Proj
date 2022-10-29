package io.careerdevelopment.resumeportal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home()
    {
        return "Hello";
    }

    @GetMapping("/edit")
    public String edit()
    {
        return "Edit page";
    }
    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model)
    {
        model.addAttribute("userId", userId);
        return "profile";
    }
}
