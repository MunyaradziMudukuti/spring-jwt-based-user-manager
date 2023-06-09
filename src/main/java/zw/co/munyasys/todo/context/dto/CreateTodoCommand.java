package zw.co.munyasys.todo.context.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import zw.co.munyasys.common.utils.BasicFormats;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTodoCommand(
        @NotBlank(message = "Todo title should be provided")
        String title,
        String comment,
        @NotNull(message = "Todo Category should be provided")
        UUID todoCategoryId,

        @FutureOrPresent
        @JsonFormat(pattern = BasicFormats.FULL_DATE_TIME)
        @NotNull(message = "Due Date Time should be provided") LocalDateTime dueDateTime
) {
}
