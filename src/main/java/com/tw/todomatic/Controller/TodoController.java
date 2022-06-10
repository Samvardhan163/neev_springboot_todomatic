package com.tw.todomatic.Controller;

import com.tw.todomatic.Entity.Todo;
import com.tw.todomatic.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    public TodoService todoService;


    @PostMapping
    public ResponseEntity<Void> createNewTask(@RequestBody Todo todolist, UriComponentsBuilder uriComponentsBuilder) {
        Long primaryKey = todoService.createNewTask(todolist);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/todo/{id}").buildAndExpand(primaryKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
