package epam.com.task_rest.dto.user;

public record UserUpdateDto(String firstName,
                            String lastName,
                            boolean isActive) {
}
