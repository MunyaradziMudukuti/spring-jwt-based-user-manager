package zw.co.munyasys.todocategory.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.munyasys.todocategory.context.CreateTodoCategoryCommand;
import zw.co.munyasys.todocategory.context.TodoCategoryDto;
import zw.co.munyasys.todocategory.context.TodoCategoryService;

import java.security.Principal;

@RestController
@Api(tags = "Todo Category Management")
@RequestMapping("/v1/todo-categories")
public class TodoCategoryController {

    private final TodoCategoryService todoCategoryService;

    public TodoCategoryController(TodoCategoryService todoCategoryService) {
        this.todoCategoryService = todoCategoryService;
    }

    @PostMapping
    @ApiOperation("Get My Todo Categories")
    public ResponseEntity<TodoCategoryDto> getAll(Principal principal, @RequestBody @Valid CreateTodoCategoryCommand createTodoCategoryCommand) {
        TodoCategoryDto todoCategoryDto = todoCategoryService.create(principal, createTodoCategoryCommand);
        return new ResponseEntity<>(todoCategoryDto, HttpStatus.OK);
    }

}
