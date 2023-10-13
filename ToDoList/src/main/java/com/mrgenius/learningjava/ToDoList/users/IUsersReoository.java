package com.mrgenius.learningjava.ToDoList.users;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;


public interface IUsersReoository extends JpaRepository <UserModel, UUID>{
    UserModel findByUsername(String username);
}
