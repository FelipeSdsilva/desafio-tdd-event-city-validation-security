package com.devsuperior.demo.dto;

import com.devsuperior.demo.services.validations.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO {

//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
//            message = " The password must have contain at least one uppercase letter, lowercase latter, numbers and special character")
//    @Size(min = 8, max = 16, message = "This space is obrigatoried min = 8 e max = 16")
    private String password;

    public UserUpdateDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}