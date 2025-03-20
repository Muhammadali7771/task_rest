package epam.com.task_rest.dto;

public record ErrorDto(int status, String message, String path) {
}
