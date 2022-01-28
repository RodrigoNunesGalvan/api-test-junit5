package br.com.a2tecnology.api.config;

import br.com.a2tecnology.api.domain.User;
import br.com.a2tecnology.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User u1 = new User(null, "Rodrigo", "rodrigo@email.com", "123");
        User u2 = new User(null, "Juliana", "juliana@email.com", "123");
        User u3 = new User(null, "Alice", "alice@email.com", "123");
        User u4 = new User(null, "Arthur", "arthur@email.com", "123");

        repository.saveAll(List.of(u1, u2, u3, u4));
    }
}
