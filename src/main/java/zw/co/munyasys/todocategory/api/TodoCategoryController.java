package zw.co.munyasys.todocategory.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.munyasys.todocategory.context.CreateTodoCategoryCommand;
import zw.co.munyasys.todocategory.context.TodoCategoryDto;
import zw.co.munyasys.todocategory.context.TodoCategoryService;

import java.security.Principal;
import java.util.List;

@RestController
@Api(tags = "Todo Category Management")
@RequestMapping("/v1/todo-categories")
public class TodoCategoryController {

    private final TodoCategoryService todoCategoryService;

    public TodoCategoryController(TodoCategoryService todoCategoryService) {
        this.todoCategoryService = todoCategoryService;
    }

    @PostMapping
    @ApiOperation("Create A Todo Category")
    public ResponseEntity<TodoCategoryDto> addCategory(Principal currentUser, @RequestBody @Valid CreateTodoCategoryCommand createTodoCategoryCommand) {
        TodoCategoryDto todoCategoryDto = todoCategoryService.create(currentUser, createTodoCategoryCommand);
        return new ResponseEntity<>(todoCategoryDto, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get My Todo Categories")
    public ResponseEntity<List<TodoCategoryDto>> getMyCategories(Principal currentUser) {
        List<TodoCategoryDto> categories = todoCategoryService.getCategories(currentUser);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
