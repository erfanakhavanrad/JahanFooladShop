package com.jahanfoolad.jfs.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class FileDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1469773962182414207L;

    private Long   id;
    private Long  name;
    private String extension;
    private String title;
    private String url;
    private boolean isMain = false;
    private long companyId;

}
