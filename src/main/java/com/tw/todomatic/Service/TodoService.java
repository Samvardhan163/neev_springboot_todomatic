package com.tw.todomatic.Service;

import com.tw.todomatic.Entity.Todo;
import com.tw.todomatic.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    public TodoRepository todoRepository;

    public  Long  createNewTask(Todo todo)
    {
        todoRepository.save(todo);
        return todo.getId();
    }

}
