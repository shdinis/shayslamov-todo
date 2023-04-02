package ru.javarush.todo.dto;

import lombok.Getter;
import lombok.Setter;
import ru.javarush.todo.entity.Status;

@Getter
@Setter
public class TaskDto {
    private Integer id;
    private String description;
    private Status status;
}
