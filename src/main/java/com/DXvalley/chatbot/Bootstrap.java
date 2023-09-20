package com.DXvalley.chatbot;

import com.DXvalley.chatbot.models.*;
import com.DXvalley.chatbot.repository.OfficeRepository;
import com.DXvalley.chatbot.repository.RoleRepository;
import com.DXvalley.chatbot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
public class Bootstrap {
    Role admin = new Role("admin", "System Administrator");
    Role sysAdmin = new Role("System Admin", "Highest Level System Administrator");
    Role marketAnalyst = new Role("Market Analyst", "Works on analysis of market");
    Role Employee = new Role("employee", "officer");
    Role TourOperator = new Role("Tour Operator", "Create Package");
    Office adminOffice = new Office("Around Bole", "Visit Oromia Info Centre", 38.78404357187282F, 8.990634168076502F,"Head Office");
    Address address = new Address("ethiopia", "Addis Ababa", "Bole Noh Real State", "03", "457");
    Users user = new Users("@mesay", "@mesay123", "Mesay Kebede Lemma", "fikirawaldi7@gmail.com", true, "MALE", "1997-11-27", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgBhcplevwUKGRs1P-Ps8Mwf2wOwnW_R_JIA&usqp=CAU", "https://pbs.twimg.com/media/FmQODdMXEAEvUao?format=jpg&name=large", "194.12.1.200", "2023-31-08", null, 1, 0, false, true);

    Collection<Role> roles = new ArrayList<>();

    void setUp() {
        roles.add(sysAdmin);
        user.setAddress(address);
        user.setRoles(roles);
    }
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, OfficeRepository officeRepository) {
        setUp();
        return args -> {
            log.info("Preloading " + officeRepository.save(adminOffice));
            log.info("Preloading " + roleRepository.save(sysAdmin));
            log.info("Preloading " + roleRepository.save(marketAnalyst));
            log.info("Preloading " + roleRepository.save(Employee));
            log.info("Preloading " + roleRepository.save(admin));
            log.info("Preloading " + userRepository.save(user));
            log.info("preloading"+roleRepository.save(TourOperator));
        };
    }
}