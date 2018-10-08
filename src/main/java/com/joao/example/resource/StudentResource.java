package com.joao.example.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class StudentResource extends ResourceSupport {

    private final Long id;
    private final String name;

    public StudentResource(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    @JsonProperty("id")
    public Long getResourceId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
