package sec.project.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, String> accountDetails;

    @PostConstruct
    public void init() {
        // this data would typically be retrieved from a database
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode("seppo");
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("ted", password);
        this.accountDetails.put("matt", password);
        this.accountDetails.put("seppo", password);
        this.accountDetails.put("ismo", password);
        this.accountDetails.put("ulla", password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!this.accountDetails.containsKey(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        
        String role = "USER";
        if (username.equals("ted") || username.equals("seppo")) {
            role = "ADMIN";
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                this.accountDetails.get(username),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority(role)));
    }
}
