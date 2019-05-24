package com.almundo.callcenter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class Users implements Comparable<Users>{

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @NotNull
    private String name;
    @Getter
    @Setter
    private String  lastName;

    @Email
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Roles roleId;


    @Override
    public int compareTo(Users users) {
        return 0;
    }
}