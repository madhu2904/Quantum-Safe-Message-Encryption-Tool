package com.quantum_safe.sercure_server.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class loginRequest
{
    public String getMailId() {
        return mailId;
    }

    public String getPassword() {
        return password;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String mailId;
    private String password;

}
