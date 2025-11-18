package com.jobportal.jobportal.dto;

import com.jobportal.jobportal.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    private String id;
    @NotBlank(message="Name should not be empty or blank")
    private String name;
    @NotBlank(message="Email should not be empty or blank")
    private String email;
    @NotBlank(message="Password should not be empty or blank")
    private String password;
    private AccountType accountType;

    public User toEntity() {
        return new User(this.id,this.name,this.email,this.password,this.accountType);
    }
}
