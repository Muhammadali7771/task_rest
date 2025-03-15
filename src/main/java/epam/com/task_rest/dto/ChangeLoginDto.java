package epam.com.task_rest.dto;

public record ChangeLoginDto(String username,
                             String oldPassword,
                             String newPassword) {
}
