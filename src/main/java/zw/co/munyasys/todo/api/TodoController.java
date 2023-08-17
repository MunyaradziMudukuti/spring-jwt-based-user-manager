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
import zw.co.munyasys.todo.context.TodoService;
import zw.co.munyasys.todo.context.dto.CreateTodoCommand;
import zw.co.munyasys.todo.context.dto.TodoDto;
import zw.co.munyasys.todo.context.dto.UpdateTodoCommand;

import java.security.Principal;
import java.util.UUID;

@CrossOrigin
@RestController
@Api(tags = "Todo Management")
@RequestMapping("v1/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping
    @ApiOperation("Create a Todo")
    public ResponseEntity<TodoDto> addTodo(Principal currentUser, @RequestBody @Valid CreateTodoCommand createTodoCommand) {
        TodoDto todoDto = todoService.create(currentUser, createTodoCommand);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @PutMapping("{todoId}")
    @ApiOperation("Update a Todo")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable UUID todoId, Principal currentUser, @RequestBody @Valid UpdateTodoCommand updateTodoCommand) {
        TodoDto todoDto = todoService.update(todoId, currentUser, updateTodoCommand);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @DeleteMapping("{todoId}")
    @ApiOperation("Delete a Todo")
    public ResponseEntity<?> deleteTodo(@PathVariable UUID todoId, Principal currentUser) {
        todoService.delete(todoId, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{todoId}")
    @ApiOperation("Get Todo By ID")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable UUID todoId, Principal currentUser) {
        return new ResponseEntity<>(todoService.findMyTodoById(todoId, currentUser), HttpStatus.OK);
    }


    @GetMapping("/search")
    @ApiOperation("Search Todos")
    public ResponseEntity<Page<TodoDto>> searchCustomer(Principal principal, @RequestParam("searchTerm") String searchTerm, @PageableDefault(sort = "createdDate") Pageable pageable) {
        return new ResponseEntity<>(todoService.search(principal, searchTerm, pageable), HttpStatus.OK);
    }

}
