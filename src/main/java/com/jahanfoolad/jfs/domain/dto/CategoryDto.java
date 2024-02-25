package com.jahanfoolad.jfs.domain.dto;

import com.jahanfoolad.jfs.domain.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private File thumbnail;
}
