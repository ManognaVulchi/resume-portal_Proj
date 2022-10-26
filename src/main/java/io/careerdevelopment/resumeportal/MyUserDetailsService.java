package io.careerdevelopment.resumeportal;

import io.careerdevelopment.resumeportal.models.MyUserDetails;
import io.careerdevelopment.resumeportal.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
//Spring @Autowired annotation is used for automatic dependency injection. Spring framework is built on dependency
// injection and we inject the class dependencies through spring bean configuration file.
    @Autowired
    UserRepository userRepository;

    @Override
    //user details is the bean that spring security returns
    //loaduserbyusername is default spring security method
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException // this expection is also sprin
    //security code exception
    {
        Optional<Users> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(MyUserDetails::new).get();//if the user is found covert it into user details class
    }


}
