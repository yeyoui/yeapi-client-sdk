package com.yeyou.yeapiclientsdk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailMsg implements Serializable {
    private static final long serialVersionUID = -3868735527674992955L;
    private String receiver;
    private String title;
    private String msg;
    private Integer codeNum;
}
