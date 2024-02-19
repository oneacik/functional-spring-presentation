package com.ksidelta.presentation.functional;

import org.springframework.data.annotation.Id;

public class SimpleEntity {
    public SimpleEntity(String id, String contents) {
        this.id = id;
        this.contents = contents;
    }

    @Id
    public String id;

    public String contents;
}
