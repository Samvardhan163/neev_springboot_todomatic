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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void ShouldAbleToCreateNewTodoTask() throws Exception {
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
}

