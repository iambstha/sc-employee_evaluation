package org.base.client.dto;

import lombok.Data;

@Data
public class ClientResDto {

    private String status;
    private int statusCode;
    private String message;
    private Object data;

}
