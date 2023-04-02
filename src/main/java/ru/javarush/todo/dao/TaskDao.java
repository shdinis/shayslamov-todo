package ru.javarush.todo.dao;

import ru.javarush.todo.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {

    public List<Task> getAll(int offset, int limit);

    public int getAllCount();

    public Optional<Task> getById(int id);

    public void saveOrUpdate(Task task);

    public void delete(Task task);

}
