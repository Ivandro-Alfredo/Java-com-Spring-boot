package com.mrgenius.learningjava.ToDoList.task;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITaskRepo extends JpaRepository <TaskModel,UUID>{
    List<TaskModel> findByIdUser(UUID idUser);
}
