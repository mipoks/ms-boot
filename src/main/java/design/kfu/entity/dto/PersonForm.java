package design.kfu.entity.dto;


import design.kfu.helper.validation.AssertEquals;
import design.kfu.helper.validation.AssertOn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static design.kfu.service.SignUpService.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AssertEquals(
        fieldOne = "password",
        fieldTwo = "pwd2")
public class PersonForm {

    @NotBlank(message = "Enter your email")
    @Email(message = "Email is not correct")
    private String email;
    @Size(min=2, max=30, message = "Choose name between 2 and 30 symbols")
    private String name;
    @Size(min=6, max=30, message = "Choose password between 6 and 30 symbols")
    private String password;
    private String pwd2;

    @AssertOn
    private String agree;
    private int songCount = 5;

    public PersonForm(String email, String name, String pwd1, String pwd2, String agree) {
        this.email = email;
        this.name = name;
        this.password = pwd1;
        this.pwd2 = pwd2;
        if (agree.equals("on"))
            this.agree = agree;
    }

    public PersonForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}