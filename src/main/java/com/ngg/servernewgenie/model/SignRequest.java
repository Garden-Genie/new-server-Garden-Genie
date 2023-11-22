package com.ngg.servernewgenie.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignRequest {

    private String user_id;

    private String user_pwd;

    private String user_name;

    private String user_email;

}
