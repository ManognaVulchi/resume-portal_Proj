package io.careerdevelopment.resumeportal;

import io.careerdevelopment.resumeportal.models.Education;
import io.careerdevelopment.resumeportal.models.Job;
import io.careerdevelopment.resumeportal.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
       job1.getResponsibilities().add("blow people's mind");
    //
        //
        //
        //
        //
        //   job1.setEndDate(LocalDate.of(2020,3,1));

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
    public String edit(Model model, Principal principal, @RequestParam(required = false) String add)
    {
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        if("job".equals(add)) {
            userProfile.getJobs().add(new Job());
        } else if ("education".equals(add)) {
            userProfile.getEducations().add(new Education());
        }
        else if ("skill".equals(add)) {
            userProfile.getSkills().add("");
        }
        model.addAttribute("userProfile", userProfile);

        return "profile-edit";
    }
    @GetMapping("/delete")
    public String delete(Model model, Principal principal, @RequestParam String type,@RequestParam int index)
    {
        String userId = principal.getName();
        model.addAttribute("userId", userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();

        if("job".equals(type)) {
            userProfile.getJobs().remove(index);
        } else if ("education".equals(type)) {
            userProfile.getEducations().remove(index);
        }
        else if ("skill".equals(type)) {
            userProfile.getSkills().remove(index);
        }
        userProfileRepository.save(userProfile);
        return "redirect:/edit";

    }

    @PostMapping("/edit")
    public String postEdit(Model model, Principal principal, @ModelAttribute UserProfile userProfile)
    {
        String userName = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userName);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userName));
        UserProfile savedUserProfile = userProfileOptional.get();
        userProfile.setId(savedUserProfile.getId());
        userProfile.setUserName(userName);
      //  userProfile.setTheme(savedUserProfile.getTheme());
        userProfileRepository.save(userProfile);
       //save the updated values in the form
        return "redirect:/view/" + userName;
    }
    @GetMapping("/view/{userId}")
    public String view(Principal principal, @PathVariable String userId, Model model)
    {
        if(principal != null && principal.getName() != "")
        {
            boolean currentUsersProfile = principal.getName().equals(userId);
            model.addAttribute("currentUsersProfile", currentUsersProfile);
        }
        String userName = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);
        System.out.println(userProfile.getJobs());
        return "profile-templates/" + userProfile.getTheme() +"/index";
    }
}
