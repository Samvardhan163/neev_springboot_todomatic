package com.tw.todomatic.Repository;

import com.tw.todomatic.Entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
}
