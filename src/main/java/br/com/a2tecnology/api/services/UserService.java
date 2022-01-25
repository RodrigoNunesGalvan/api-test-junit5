package br.com.a2tecnology.api.services;


import br.com.a2tecnology.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();
}
