package com.example.user.senioritaandroid.User;

public class UserDto {

    private String login;
    private String password;
    private String email;
    private Long roleId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public UserDto(String login, String password, String email, Long roleId) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }
}
