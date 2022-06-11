package com.tw.todomatic.Service;

import com.tw.todomatic.Entity.Todo;
import com.tw.todomatic.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    public TodoRepository todoRepository;

    public  Long  createNewTask(Todo todo)
    {
        todoRepository.save(todo);
        return todo.getId();
    }

    public List<Todo> getAllTodoTask()
    {
        List<Todo> todos=new ArrayList<>();
        todoRepository.findAll().forEach(todo -> todos.add(todo));
        return todos;
    }
}
