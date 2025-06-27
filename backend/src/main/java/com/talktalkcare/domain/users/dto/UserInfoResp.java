package com.talktalkcare.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResp {

    private String name;

    private Integer age;

    private String loginId;

    private String phone;

    private String s3Filename;

}
