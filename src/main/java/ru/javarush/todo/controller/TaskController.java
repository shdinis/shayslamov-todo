package ru.javarush.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javarush.todo.dto.TaskDto;
import ru.javarush.todo.entity.Task;
import ru.javarush.todo.exception.InvalidIdException;
import ru.javarush.todo.service.TaskService;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Controller
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") String limit) {
        if (!page.matches("\\d+") || page.matches("0+") || page.isEmpty()) {
            page = "1";
        }
        if (!limit.matches("\\d+") || limit.matches("0+") || limit.isEmpty()) {
            limit = "10";
        }
        int pageInt = Integer.parseInt(page);
        int limitInt = Integer.parseInt(limit);
        List<Task> tasks = taskService.getAll((pageInt - 1) * limitInt, limitInt);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limitInt);
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestBody TaskDto info) {
        try {
            if (isNull(id) || id <= 0) {
                throw new InvalidIdException("Wrong id=%d ".formatted(id));
            }
            taskService.edit(id, info.getDescription(), info.getStatus());
            return tasks(model, "1", "10");
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "error_page";
        }
    }

    @PostMapping("/")
    public String add(Model model,
                      @RequestBody TaskDto info) {
        taskService.create(info.getDescription(), info.getStatus());
        return tasks(model, "1", "10");
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable Integer id) {
        try {
            if (isNull(id) || id <= 0) {
                throw new InvalidIdException("Wrong id=%d ".formatted(id));
            }
            taskService.delete(id);
            return tasks(model, "1", "10");
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            return "error_page";
        }
    }
}
