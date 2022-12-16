package io.careerdevelopment.resumeportal;

import io.careerdevelopment.resumeportal.models.Education;
import io.careerdevelopment.resumeportal.models.Job;
import io.careerdevelopment.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserProfileRepository userProfileRepository;
    @GetMapping("/")
    public String home()
    {
     Optional<UserProfile> profileOptional = userProfileRepository.findByUserName("einstein");
        profileOptional.orElseThrow(() -> new RuntimeException("Not found: "));

        UserProfile profile1 = profileOptional.get();

       Job job1 = new Job();
       job1.setCompany("Company 1");
       job1.setDesignation("Designation");
       job1.setId(1);
       job1.setStartDate(LocalDate.of(2020,1,1));
       job1.getResponsibilities().add("come up the theory of relativity");
       job1.getResponsibilities().add("come up the advance quantum mechanics");
       job1.getResponsibilities().add("blow people's mind");job1.setEndDate(LocalDate.of(2020,3,1));

        job1.setCurrentJob(true);
        Job job2 = new Job();
        job2.setCompany("Company 1");
        job2.setDesignation("Designation");
        job2.setId(2);
        job2.setStartDate(LocalDate.of(2019,1,1));
        job2.setEndDate(LocalDate.of(2019,3,1));
        job2.getResponsibilities().add("come up the theory of relativity");
        job2.getResponsibilities().add("come up the advance quantum mechanics");
        job2.getResponsibilities().add("blow people's mind");

        profile1.getJobs().clear();
        profile1.getJobs().add(job1);
        profile1.getJobs().add(job2);

        Education e1 = new Education();
        e1.setCollege("Awesome college");
        e1.setQualification("useless degree");
        e1.setSummary("studied a lot");
        e1.setStartDate(LocalDate.of(2019,1,1));
        e1.setEndDate(LocalDate.of(2019,3,1));

        Education e2 = new Education();
        e2.setCollege("Awesome college");
        e2.setQualification("useless degree");
        e2.setSummary("studied a lot");
        e2.setStartDate(LocalDate.of(2019,1,1));
        e2.setEndDate(LocalDate.of(2019,3,1));

        profile1.getEducations().clear();
        profile1.getEducations().add(e1);
        profile1.getEducations().add(e2);

        profile1.getSkills().clear();
        profile1.getSkills().add("quantum physics");
        profile1.getSkills().add("modern physics");
        profile1.getSkills().add("violin");
        profile1.getSkills().add("philosophy");
       userProfileRepository.save(profile1);


        return "profile";
    }

    @GetMapping("/edit")
    public String edit(Model model, Principal principal)
    {
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);

        return "profile-edit";
    }
    @PostMapping("/edit")
    public String postEdit(Model model, Principal principal)
    {
        String userId = principal.getName();
       //save the updated values in the form
        return "redirect:/view/" + userId;
    }
    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model)
    {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);
        System.out.println(userProfile.getJobs());
        return "profile-templates/" + userProfile.getTheme() +"/index";
    }
}
