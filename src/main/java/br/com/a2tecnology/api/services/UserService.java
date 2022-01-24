package br.com.a2tecnology.api.services;


import br.com.a2tecnology.api.domain.User;

public interface UserService {

    User findById(Integer id);
}
