package com.talktalkcare.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String loginId;

    private String password;

    private String token;

    private String name;

    private String birth;

    private String phone;

    private Date loginedAt;

    private MultipartFile s3Filename;

}