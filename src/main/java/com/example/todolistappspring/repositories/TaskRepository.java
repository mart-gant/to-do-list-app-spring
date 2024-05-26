package com.example.todolistappspring.repositories;

import com.example.todolistappspring.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

