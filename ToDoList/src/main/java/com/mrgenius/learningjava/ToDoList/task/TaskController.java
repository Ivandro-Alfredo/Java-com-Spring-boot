package com.mrgenius.learningjava.ToDoList.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrgenius.learningjava.ToDoList.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tesks")
public class TaskController {
    @Autowired
    private ITaskRepo taskRepo;

    @PostMapping("")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var task = this.taskRepo.save(taskModel);
        taskModel.setIdUser((UUID)request.getAttribute("userId"));
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartedAt()) || currentDate.isAfter(taskModel.getEndedAt()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de incio deve se maior que a data atual");
        if(taskModel.getStartedAt().isAfter(taskModel.getEndedAt()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de incio deve se maior que a data atual");
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var tasks = this.taskRepo.findByIdUser((UUID)request.getAttribute("userId"));
        return tasks;
    }
    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody TaskModel task, HttpServletRequest request, @PathVariable UUID id){
        var idUser = request.getAttribute("userId");
        var taskTarget = this.taskRepo.findById(id).orElse(null);
        if(taskTarget==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa nao existe");
        if(!task.getIdUser().equals(idUser))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario nao tem permissao");
        
        Utils.copyNoNullProperties(task, taskTarget);
        var taskUpdated = this.taskRepo.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
