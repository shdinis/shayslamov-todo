package ru.javarush.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.todo.dao.TaskDao;
import ru.javarush.todo.entity.Status;
import ru.javarush.todo.entity.Task;
import ru.javarush.todo.exception.NotFoundException;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {
    private final TaskDao taskDao;

    public List<Task> getAll(int offset, int limit) {
        return taskDao.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDao.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
        Task task = taskDao.getById(id).orElseThrow(() -> new NotFoundException("Not found task with id=%d".formatted(id)));
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }

    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDao.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDao.getById(id).orElseThrow(() -> new NotFoundException("Not found task with id=%d".formatted(id)));
        taskDao.delete(task);
    }
}
