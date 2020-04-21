package com.example.web.app.dao.model;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private String numberPhone;
    private Date birthday;
    private String vk;
    private String about;
    private String hobby;
    private String marriage;
    private String haveChildren;
    private String gender;
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public void setHaveChildren(String haveChildren) {
        this.haveChildren = haveChildren;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarriage() {
        return marriage;
    }

    public String getHaveChildren() {
        return haveChildren;
    }

    public String getGender() {
        return gender;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getVk() {
        return vk;
    }

    public String getAbout() {
        return about;
    }

    public String getHobby() {
        return hobby;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Long getTimeOfBirthday() {
        return birthday.getTime();
    }
}