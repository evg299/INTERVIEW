package ru.bs.interview.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bs.interview.demo.model.entities.User;
import ru.bs.interview.demo.model.repo.UserRepository;

import javax.validation.Valid;

/**
 * Необходимо разработать REST-сервис на базе Spring/Spring Boot Framework для работы с БД и вывода результата.
 * Требования:
 * 1.  Сервис представляет из себя "обертку" для создания, удаления и отображения пользователей.
 * 2.  Пользователь должен иметь минимум 5 параметров разных типов.
 * 3.  Сервис должен работать с JSON или XML данными.
 * 4.  Сервис должен валидировать данные в зависимости от их типа.
 * 5. Исходый код на gitHub или подобные
 * <p>
 * Желательно:
 * <p>
 * 1.  Сервис должен обрабатывать ошибки и корректно отвечать клиенту.
 * 2.  Сервис должен производить логирование.
 * 3.  Наличие интеграционых тестов для REST endpoints.
 */

@RestController
@RequestMapping("/api/users")
public class UserApiController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Iterable<User> readUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "limit", defaultValue = "30") int limit) {
        return userRepository.findAll(PageRequest.of(page, limit));
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public User readUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@Valid @RequestBody User user) {
        user.setId(null);
        userRepository.save(user);
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
