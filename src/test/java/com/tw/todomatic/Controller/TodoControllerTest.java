package com.tw.todomatic.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.todomatic.Entity.Todo;
import com.tw.todomatic.Service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TodoController.class)
@ExtendWith(SpringExtension.class)
public class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Captor
    private ArgumentCaptor<Todo> todoArgumentCaptor;

    @Test
    void shouldAbleToCreateNewTodoTask() throws Exception {
        Todo todo = new Todo();
        todo.setName("Sam");
        todo.setDescription("Sleeping");
        when(todoService.createNewTask(todoArgumentCaptor.capture())).thenReturn(1L);

        this.mockMvc
                .perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/todo/1"));

        assertThat(todoArgumentCaptor.getValue().getName(), is("Sam"));
        assertThat(todoArgumentCaptor.getValue().getDescription(), is("Sleeping"));

    }

    @Test
    void shouldReturnListOfTodoTask() throws Exception {
        Todo todo = new Todo();
        todo.setName("Sam");
        todo.setDescription("Sleeping");
        todo.setId(1L);
        Todo todo1 = new Todo();
        todo1.setName("Sam");
        todo1.setDescription("");
        todo1.setId(2L);
        when(todoService.getAllTodoTask()).thenReturn(List.of(todo, todo1));

        this.mockMvc
                .perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name",is("Sam")))
                .andExpect(jsonPath("$[0].description",is("Sleeping")));



    }
}

