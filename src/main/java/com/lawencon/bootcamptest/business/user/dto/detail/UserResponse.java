package com.lawencon.bootcamptest.business.user.dto.detail;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    
    private String username;
	private String fullName;
	private String placeOfBirth;
    private LocalDate dateOfBirth;
	private String email;
    private String phoneNumber;
    private String address;
    private Boolean isActive;

    private String role;
    private String roleName;

    private String profile;

    private String statusId;
    private Boolean enabled;
    private Boolean isLoginWeb;
    
}
