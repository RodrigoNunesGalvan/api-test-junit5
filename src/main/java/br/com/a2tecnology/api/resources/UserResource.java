package br.com.a2tecnology.api.resources;


import br.com.a2tecnology.api.domain.dto.UserDto;
import br.com.a2tecnology.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    public static final String ID = "/{id}";

    private final UserService service;

    private final ModelMapper mapper;

    public UserResource(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = ID)
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDto.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
         return ResponseEntity.ok()
                 .body(service.findAll()
                         .stream().map(x -> mapper.map(x, UserDto.class))
                         .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto obj) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/id").buildAndExpand(service.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody UserDto obj) {
        obj.setId(id);
        return  ResponseEntity.ok().body(mapper.map(service.update(obj), UserDto.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}