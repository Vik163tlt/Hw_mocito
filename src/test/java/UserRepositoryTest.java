import com.skypro.vik.User;
import com.skypro.vik.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();
    User natalia;
    User egor;
    User natalia2;

    @BeforeEach
    public void setUp() {
        natalia = new User("Natalia", "1234");
        egor = new User("Egor", "1234");
        natalia2 = new User("Natalia", "5678");
    }

    @Test
    public void getEmptyUsers() {
        Assertions.assertEquals(userRepository.getAllUsers(), Collections.EMPTY_LIST);
    }

    @Test
    public void getNotEmptyUsers() {
        userRepository.addUser(natalia);
        Assertions.assertEquals(List.of(natalia), userRepository.getAllUsers());
    }

    @Test
    public void findUserByLoginIfSuchUserExists() {
        userRepository.addUser(natalia);
        Assertions.assertEquals(userRepository.findUserByLogin("Natalia"), natalia);
    }

    @Test
    public void findUserByLoginIfSuchUserDoesNotExist() {
        userRepository.addUser(natalia);
        Assertions.assertNull(userRepository.findUserByLogin("Andrey"));
    }

    @Test
    public void findUserByLoginAndPasswordIfSuchUserExists() {
        userRepository.addUser(natalia);
        Assertions.assertEquals(userRepository.findUserByLoginAndPassword("Natalia", "1234"), natalia);
    }

    @Test
    public void findUserByLoginAndPasswordIfLoginMatchesWithOneExistingAndPasswordNotMatches() {
        userRepository.addUser(natalia);
        userRepository.addUser(natalia2);
        Assertions.assertNotEquals(userRepository.findUserByLoginAndPassword("Natalia", "1234"), natalia2);
    }

    @Test
    public void findUserByLoginAndPasswordIfPasswordMatchesWithOneExistingAndLoginNotMatches() {
        userRepository.addUser(natalia);
        userRepository.addUser(egor);
        Assertions.assertNotEquals(userRepository.findUserByLoginAndPassword("Natalia", "1234"), egor);
    }
}