package zw.co.munyasys.todo.context;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.munyasys.common.jpa.BaseDao;

public interface TodoRepository extends BaseDao<Todo> {
    Page<Todo> findByTitleContainingIgnoreCaseOrTodoCategory_NameContainingIgnoreCase(String title, String category, Pageable pageable);
}
