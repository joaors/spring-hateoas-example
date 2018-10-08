package com.joao.example.resource;

import com.joao.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class StudentResourceMapper {

    private final EntityLinks entityLinks;
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";

    @Autowired
    public StudentResourceMapper(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    public StudentResource toResource(Student student) {
        StudentResource resource = new StudentResource(student.getId(), student.getName());
        final Link selfLink = entityLinks.linkToSingleResource(student);
        resource.add(selfLink.withSelfRel());
        resource.add(selfLink.withRel(UPDATE));
        resource.add(selfLink.withRel(DELETE));
        return resource;
    }

    public Collection<StudentResource> toResourceCollection(Collection<Student> domainObjects) {
        return domainObjects.stream()
                .map(t -> toResource(t))
                .collect(Collectors.toList());
    }

}
