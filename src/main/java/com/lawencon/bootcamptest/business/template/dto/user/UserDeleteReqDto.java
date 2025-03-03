package com.lawencon.bootcamptest.business.template.dto.user;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDeleteReqDto {
    
    @NotNull(message = "Id is must not null")
    private String id;
    private Boolean isActive;
}
