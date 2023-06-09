package zw.co.munyasys.todo.context.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.munyasys.common.jpa.BaseDao;
import zw.co.munyasys.todo.context.model.Todo;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepository extends BaseDao<Todo> {

    Optional<Todo> findByIdAndUser_Username(UUID uuid, String username);

    Page<Todo> findByTitleContainingIgnoreCaseOrTodoCategory_NameContainingIgnoreCase(String title, String category, Pageable pageable);
}