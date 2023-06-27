package inventarios.com.Sistema.Inventarios.Configurations;

import inventarios.com.Sistema.Inventarios.Models.UserInventory;
import inventarios.com.Sistema.Inventarios.Models.UserType;
import inventarios.com.Sistema.Inventarios.Repositories.UserInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {
            UserInventory userInventory = userInventoryRepository.findByLogin(inputName);
            if (userInventory != null) {
                if(userInventory.getUserType().equals(UserType.ADMIN)){
                    return new User(userInventory.getLogin(), userInventory.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }if(userInventory.getUserType().equals(UserType.SUPERVISOR)){
                    return new User(userInventory.getLogin(), userInventory.getPassword(),
                            AuthorityUtils.createAuthorityList("SUPERVISOR"));
                }if((userInventory.getUserType().equals(UserType.CASHIER))){
                    return new User(userInventory.getLogin(), userInventory.getPassword(),
                            AuthorityUtils.createAuthorityList("  CASHIER"));
                }else{
                    return new User(userInventory.getLogin(), userInventory.getPassword(),
                    AuthorityUtils.createAuthorityList("WORKER"));
                }

            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }

        });
    }

}
