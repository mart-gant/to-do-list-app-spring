package com.example.todolistappspring.controllers;

import com.example.todolistappspring.models.Task;
import com.example.todolistappspring.models.User;
import com.example.todolistappspring.services.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static java.nio.file.Paths.get;
//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(new Task("Task 1", "Description 1", false),
                new Task("Task 2", "Description 2", false));
        when(taskService.findAll()).thenReturn(tasks);

        mockMvc.perform((RequestBuilder) get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attributeExists("tasks"));

        verify(taskService, times(1)).findAll();
    }

    private TaskService verify(TaskService taskService, VerificationMode times) {
        return taskService;
    }

    @Test
    public void testAddTask() throws Exception {
        Task task = new Task("Task 1", "Description 1", false);
        when(taskService.save((User) any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .param("title", "Task 1")
                        .param("description", "Description 1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService, times(1)).save((User) any(Task.class));
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tasks"));

        verify(taskService, times(1)).deleteById(1L);
    }
}

