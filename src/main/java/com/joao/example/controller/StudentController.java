package com.joao.example.controller;

import com.joao.example.model.Student;
import com.joao.example.repository.StudentRepository;
import com.joao.example.resource.StudentResource;
import com.joao.example.resource.StudentResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@ExposesResourceFor(Student.class)
@RequestMapping(value = "/student", produces = "application/json")
public class StudentController {

    private final StudentRepository repository;
    private final StudentResourceMapper mapper;

    @Autowired
    public StudentController(StudentRepository repository, StudentResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<StudentResource>> findAll() {
        List<Student> students = repository.findAll();
        Collection<StudentResource> resource = mapper.toResourceCollection(students);
        return ResponseEntity.ok(resource);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<StudentResource> findById(@PathVariable Long id) {
        Optional<Student> found = repository.findById(id);

        if (found.isPresent()) {
            return ResponseEntity.ok(mapper.toResource(found.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<StudentResource> create(@RequestBody Student student) {
        Student createdStudent = repository.save(student);
        StudentResource resource = mapper.toResource(createdStudent);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<StudentResource> update(@PathVariable Long id, @RequestBody Student toUpdate) {
        Optional<Student> found = repository.findById(id);
        if (found.isPresent()) {
            Student updated = repository.save(toUpdate);
            return ResponseEntity.ok(mapper.toResource(updated));
        }
        return ResponseEntity.notFound().build();
    }
}
