package br.com.a2tecnology.api.services.impl;

import br.com.a2tecnology.api.domain.User;
import br.com.a2tecnology.api.repositories.UserRepository;
import br.com.a2tecnology.api.services.UserService;
import br.com.a2tecnology.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
    }
}
