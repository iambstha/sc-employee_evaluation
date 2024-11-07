package org.base.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoResDto {

    private String userId;
    private String username;
    private List<String> roles;
    private List<String> permissions;

}
