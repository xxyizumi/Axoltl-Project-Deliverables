package com.example.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO管理用コントローラー
 */
@Controller
@RequestMapping("/todos")
public class TodoController {
    
    private final TodoRepository todoRepository;
    
    // コンストラクタインジェクション
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    /**
     * TODO一覧を表示
     */
    @GetMapping
    public String list(Model model, @RequestParam(required = false) String filter) {
        List<Todo> todos = findTodos(filter);
        long completedCount = todos.stream().filter(Todo::isCompleted).count();
        long incompleteCount = todos.size() - completedCount;

        model.addAttribute("todos", todos);
        model.addAttribute("totalCount", todos.size());
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("incompleteCount", incompleteCount);

        return "todos/list";
    }

    private List<Todo> findTodos(String filter) {
        if (filter == null) {
            return todoRepository.findAllByOrderByCreatedAtDesc();
        } else {
            return todoRepository.findByCompleted("completed".equals(filter) ? true : false);
        }
    }

    /**
     * TODO新規作成フォームを表示
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("todoForm", new TodoForm());
        return "todos/form";
    }
    
    /**
     * TODOを新規作成
     */
    @PostMapping
    public String create(
            @Valid @ModelAttribute("todoForm") TodoForm form,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return "todos/form";
        }
        
        Todo todo = new Todo();
        todo.setTitle(form.getTitle());
        todo.setDescription(form.getDescription());
        todoRepository.save(todo);
        
        return "redirect:/todos";
    }
    
    /**
     * TODOの完了状態を切り替え
     */
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        todoRepository.findById(id).ifPresent(todo -> {
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        });
        return "redirect:/todos";
    }
    
    /**
     * TODOを削除
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/todos";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        // idでTODOを取得してフォームに設定
        Todo todo = todoRepository.findById(id).orElse(null);
        model.addAttribute("id", id);
        model.addAttribute("todoForm", todo);

        return "todos/editForm";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable String id, 
        @Valid @ModelAttribute("todoForm") TodoForm form, 
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todos/editForm";
        }

        Todo todo = todoRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new IllegalArgumentException("指定したIDが間違っています"));

        todo.setTitle(form.getTitle());
        todo.setDescription(form.getDescription());
        todo.setUpdatedAt(LocalDateTime.now());
        todoRepository.save(todo);

        return "redirect:/todos";
    }
}
