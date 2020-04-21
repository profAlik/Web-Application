package com.example.web.app.controllers;

import com.example.web.app.api.request.*;
import com.example.web.app.dao.DbSqlite;
import com.example.web.app.dao.model.User;
import com.example.web.app.services.ValidationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api")
public class DBUserController {
    private final DbSqlite dbSqlite;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    private Logger log = Logger.getLogger(getClass().getName());

    public DBUserController(DbSqlite dbSqlite, PasswordEncoder passwordEncoder, ValidationService validationService) {
        this.dbSqlite = dbSqlite;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    @ApiOperation(value = "Выборка User по id")
    @RequestMapping(value = "/select/user/by/id", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> selectUserById(@RequestBody UserByIdRequest id) {
        User user = new User();
        try {
            user = dbSqlite.selectUserById(id.getId());
        } catch (NullPointerException ex) {
            log.info("Запрашиваемые данные о пользователе отсутствуют.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Создание нового User")
    @RequestMapping(value = "/create/new/user", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createNewUser (@RequestBody User user) {
        String passwordEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncode);
        user.setName(user.getName().toUpperCase());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<String> errors = new ArrayList<>(validationService.checkInputData(user));
        if (errors.isEmpty()) {
            return new ResponseEntity<>(dbSqlite.createNewUser(user), headers, HttpStatus.OK);
        } else return new ResponseEntity<>(errors, headers, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Получение всех id пользователей")
    @RequestMapping(value = "/get/all/user/id", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> getAllUserID () {
        List<Integer> allUserID = dbSqlite.getAllUserID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(allUserID, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Проверка на правельность имени пользователя и пароля")
    @RequestMapping(value = "/is/user/with/password", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> isUserWithPassword (@RequestBody UserByPasswordRequest user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (dbSqlite.isUserWithPassword(user.getName(), user.getPassword())){
            return new ResponseEntity<>("Данные верны", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Неверное имя пользователя или/и пароль", headers, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Выбор User по имени пользователя")
    @RequestMapping(value = "/select/user/by/name", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> selectUserByName (@RequestBody UserByNameRequest user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(dbSqlite.selectUserByName(username), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Проверка существования имени в БД")
    @RequestMapping(value = "/is/name/exist", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isNameExist (@RequestBody UserNameRequest name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (validationService.isUserNameNotExisting(name.getName())) {
            return new ResponseEntity<>(true, headers, HttpStatus.OK);
        } else return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Изменить информацию о пользователе")
    @RequestMapping(value = "/update/user/info", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> updateUserInfo (@RequestBody UserInfoRequest userInfoRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (dbSqlite.updateUserInfo(userInfoRequest.getInfo(), userInfoRequest.getName()) != null) {
            return new ResponseEntity<>(true, headers, HttpStatus.OK);
        } else return new ResponseEntity<>(false, headers, HttpStatus.BAD_REQUEST);
    }
}
