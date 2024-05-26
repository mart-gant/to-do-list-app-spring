package com.example.todolistappspring.services;

import com.example.todolistappspring.models.Task;
import com.example.todolistappspring.repositories.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testFindAll() {
        List<Task> tasks = Arrays.asList(new Task("Task 1", "Description 1", false),
                new Task("Task 2", "Description 2", false));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.findAll();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        Task task = new Task("Task 1", "Description 1", false);
        when(taskRepository.save(argThat(argument -> argument.getTitle().equals("Task 1")))).thenReturn(task);

        Task result = taskService.save(task);

        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testDeleteById() {
        taskService.deleteById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
