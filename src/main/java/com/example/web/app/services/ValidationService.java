package com.example.web.app.services;

import com.example.web.app.dao.DbSqlite;
import com.example.web.app.dao.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    private Logger log = Logger.getLogger(getClass().getName());
    private DbSqlite dbSqlite = new DbSqlite();

    public List<String> checkInputData (User user) {
        List<String> errors = new ArrayList<>(checkNonEmptyData(user));
        if (!isInputNumberValid(user.getNumberPhone())) errors.add("Номер телефона заполнен некорректно.");
        if (!isInputBirthValid(user.getBirthday())) errors.add("Дата заполнена некорректно");
        if (!isInputVkValid(user.getVk())) errors.add("Ссылка на vk заполнена некорректно");
        if (!isUserNameNotExisting(user.getName())) errors.add("Данное имя пользователя уже существует в БД.");
        return errors;
    }

    public Boolean isUserNameNotExisting (String name) {
        if (dbSqlite.isNameExistRequest(name)) {
            log.log(Level.WARNING, "Данное имя пользователя уже существует в БД.");
            return false;
        } else {
            return true;
        }
    }

    public Boolean isInputNumberValid (String numberPhone) {
        Pattern p = Pattern.compile("^[-+()0-9]*[^A-Za-zА-ЯА-я]$");
        Matcher m = p.matcher(numberPhone);
        if (!m.matches()) {
            log.log(Level.WARNING, "Номер телефона заполнен некорректно.");
            return false;
        } else {
            return true;
        }
    }

    public Boolean isInputBirthValid (Date date) {
        Date current_date = new Date();
        Date min_date = new Date();
        min_date.setYear(min_date.getYear() - 150);
        if (!date.before(current_date) && !date.after(min_date)) {
            log.log(Level.WARNING, "Дата заполнена некорректно.");
            return false;
        } else {
            return true;
        }
    }

    public Boolean isInputVkValid (String vk) {
        Pattern p = Pattern.compile("^https://vk\\.com/.*");
        Matcher m = p.matcher(vk);
        if (!m.matches()) {
            log.log(Level.WARNING, "Ссылка на vk заполнена некорректно");
            return false;
        } else {
            return true;
        }
    }

    public List<String> checkNonEmptyData (User user) {
        List<String> errors = new ArrayList<>();
        if (user.getName() == null || user.getName().trim().isEmpty())
            errors.add("Имя не указано");
        if (user.getNumberPhone() == null || user.getNumberPhone().trim().isEmpty())
            errors.add("Номер телефона не указан");
        if (user.getBirthday().toString() == null || user.getBirthday().toString().isEmpty())
            errors.add("Дата рождения не указана");
        if (user.getVk() == null || user.getVk().trim().isEmpty())
            errors.add("Ссылка на vk не указана");
        if (user.getAbout() == null || user.getAbout().trim().isEmpty())
            errors.add("Информация о себе не заполнена");
        if (user.getHobby() == null || user.getHobby().trim().isEmpty())
            errors.add("Хобби не указано");
        if (user.getMarriage() == null || user.getMarriage().trim().isEmpty())
            errors.add("Информация о браке не указана");
        if (user.getHaveChildren() == null || user.getHaveChildren().trim().isEmpty())
            errors.add("Информация о наличии детей не указана");
        if (user.getGender() == null || user.getGender().trim().isEmpty())
            errors.add("Информация о поле не указана");
        if (user.getPassword() == null || user.getPassword().trim().isEmpty())
            errors.add("Пароль не заполнен");
        return errors;
    }
}
