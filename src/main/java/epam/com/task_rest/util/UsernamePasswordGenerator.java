package epam.com.task_rest.util;

import epam.com.task_rest.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UsernamePasswordGenerator {
    private final UserRepository userRepository;
    private static int counter = 1;

    public UsernamePasswordGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateUsername(String firstName, String lastName) {
        String username = firstName.concat(".").concat(lastName);
        if (userRepository.isUsernameTaken(username)) {
            int number = counter++;
            username = username.concat(String.valueOf(number));
        }
        return username;
    }

    public String generatePassword(){
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++){
            int a = random.nextInt(33, 127);
            password.append((char) a);
        }
        return password.toString();
    }
}
