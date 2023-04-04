package com.yeyou.yeapiclientsdk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TranslateRequest implements Serializable {

    private static final long serialVersionUID = -3615846736205273410L;
    private String from;
    private String to;
    private String query;
}
