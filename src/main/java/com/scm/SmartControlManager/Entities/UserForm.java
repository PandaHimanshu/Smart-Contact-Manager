package com.scm.SmartControlManager.Entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message ="username is required")
    @Size(min = 3,message = "Min 3 Character is Required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6,message = "min 6 characters is required")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    @NotBlank(message = "phone no is required")
    @Size(min = 8,max = 12,message = "min 8 and max 12 character required")
    private String phoneNumber;
}
