package ru.javarush.controller;

import lombok.Getter;
import lombok.Setter;
import ru.javarush.domain.Status;

@Getter
@Setter
public class TaskInfo {
    private String description;
    private Status status;
}
