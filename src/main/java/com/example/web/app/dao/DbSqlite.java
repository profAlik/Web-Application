package com.example.web.app.dao;

import com.example.web.app.dao.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DbSqlite implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());

    private String dbPath = "db.db";

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    public void initDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База не подключена", ex);
        }
    }

    public Boolean execute(String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    public User selectUserById(int id) {
        String query = "select * from USER where id = " + id;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setName(resultSet.getString("name"));
            user.setNumberPhone(resultSet.getString("phone_number"));
            user.setAbout(resultSet.getString("about"));
            user.setHobby(resultSet.getString("hobby"));
            user.setVk(resultSet.getString("vk"));
            user.setMarriage(resultSet.getString("marriage"));
            user.setHaveChildren(resultSet.getString("have_children"));
            user.setGender(resultSet.getString("gender"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            return user;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public User selectUserByName (String name) {
        String query = "select * from USER where NAME = '%s'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format(query, name));
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setName(resultSet.getString("name"));
            user.setNumberPhone(resultSet.getString("number_phone"));
            user.setAbout(resultSet.getString("about"));
            user.setHobby(resultSet.getString("hobby"));
            user.setVk(resultSet.getString("vk"));
            user.setMarriage(resultSet.getString("marriage"));
            user.setHaveChildren(resultSet.getString("have_children"));
            user.setGender(resultSet.getString("gender"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            return user;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new User();
        }
    }

    public Integer createNewUser(User user) {
        StringBuilder query = new StringBuilder();
        query.append("insert into USER (name, number_phone, birthday, vk, about, hobby, marriage, have_children, gender, password, role) ")
                .append("values ('")
                .append(user.getName()).append("','")
                .append(user.getNumberPhone()).append("','")
                .append(user.getTimeOfBirthday()).append("','")
                .append(user.getVk()).append("','")
                .append(user.getAbout()).append("','")
                .append(user.getHobby()).append("','")
                .append(user.getMarriage()).append("','")
                .append(user.getHaveChildren()).append("','")
                .append(user.getGender()).append("','")
                .append(user.getPassword()).append("','")
                .append(user.getRole())
                .append("');");
        log.info(query.toString());
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            Statement stat = conn.createStatement();
            return stat.executeUpdate(query.toString());
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return null;
        }
    }

    public List<Integer> getAllUserID () {
        String query = "SELECT ID FROM USER";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery(query);
            List<Integer> list_id = new ArrayList<>();
            while (resultSet.next()) {
                list_id.add(resultSet.getInt("ID"));
            }
            return list_id;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return new ArrayList<>();
        }
    }

    public Boolean isUserWithPassword(String name, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format("select PASSWORD from USER where NAME = '%s'", name));
            return resultSet.getString("password").equals(password);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    public Boolean isNameExistRequest(String name) {
        log.log(Level.INFO, "Запрос: " + String.format("select NAME from USER where NAME = '%s'", name));
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(String.format("select NAME from USER where NAME = '%s'", name));
            return resultSet.next();
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос isNameExistRequest", ex);
            return false;
        }
    }

    public Integer updateUserInfo(String info, String name) {
        log.log(Level.INFO, "Запрос: " + String.format("UPDATE USER set ABOUT = '%s' where NAME = '%s'", info, name));
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.executeUpdate(String.format("UPDATE USER set ABOUT = '%s' where NAME = '%s'", info, name));
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос isNameExistRequest", ex);
            return null;
        }
    }

}