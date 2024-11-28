package com.equaled.to;

import lombok.Data;

@Data
public class PassageTO extends BaseTO {

    private static final long serialVersionUID = -7907149288395121499L;
    private String title;
    private String content;
    private Integer author;
    private String publicationDate;

}
