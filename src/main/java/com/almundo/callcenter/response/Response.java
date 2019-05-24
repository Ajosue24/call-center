package com.almundo.callcenter.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private boolean status;
}
