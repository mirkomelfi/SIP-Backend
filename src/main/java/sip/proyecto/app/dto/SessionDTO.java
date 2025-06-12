package sip.proyecto.app.dto;

import sip.proyecto.app.model.entity.UserDTO;

public class SessionDTO {
    UserDTO user;
    String token;

    public SessionDTO() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
