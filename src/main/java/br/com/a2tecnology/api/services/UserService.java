package br.com.a2tecnology.api.services;


import br.com.a2tecnology.api.domain.User;
import br.com.a2tecnology.api.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDto obj);
}
