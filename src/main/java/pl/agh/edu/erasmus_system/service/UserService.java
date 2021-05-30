package pl.agh.edu.erasmus_system.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.erasmus_system.model.User;
import pl.agh.edu.erasmus_system.repository.UserRepository;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
