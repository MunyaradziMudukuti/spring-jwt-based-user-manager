package zw.co.munyasys.todo.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.munyasys.todo.context.CreateTodoCommand;
import zw.co.munyasys.todo.context.TodoDto;
import zw.co.munyasys.todo.context.TodoService;

import java.security.Principal;

@CrossOrigin
@RestController
@Api(tags = "Todo Management")
@RequestMapping("v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    @ApiOperation("Get My Todos")
    public ResponseEntity<Page<TodoDto>> getAll(Principal principal, @PageableDefault(sort = "title") Pageable pageable, @RequestParam(required = false) String searchTerm) {
        Page<TodoDto> todos = todoService.findAll(principal, pageable, searchTerm);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create a Todo")
    public ResponseEntity<TodoDto> addTodo(Principal currentUser, @RequestBody @Valid CreateTodoCommand createTodoCommand) {
        TodoDto todoDto = todoService.create(currentUser, createTodoCommand);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

}
