import com.skypro.vik.User;

import com.skypro.vik.UserException;
import com.skypro.vik.UserRepository;
import com.skypro.vik.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User natalia;


    @BeforeEach
    public void setUp() {
        natalia = new User("Natalia", "1234");
    }

    @Test
    void getAllLogins() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Natalia", "1234")));
        userRepository.addUser(natalia);
        Assertions.assertEquals(List.of("Natalia"), userService.getAllLogins());
    }

    @Test
    void loginIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser(" ", "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Проверьте корректность ввода логина и пароля");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void userAddedThenAddUserIsCalledFromRepo() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        userService.createNewUser("Natalia", "1234");
        verify(userRepository)
                .addUser(any());
    }

    @Test
    void passwordIsNullThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser("Natalia", " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Проверьте корректность ввода логина и пароля");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void userThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("test", "1")));
        assertThatThrownBy(() -> userService.createNewUser("test", "1"))
                .isInstanceOf(UserException.class)
                .hasMessage("Такой пользователь уже существует");
    }

    @Test
    void loginAndPasswordValid() {
        when(userRepository.getAllUsers()).thenReturn(List.of(natalia));
        userService.userByLoginAndPassword("Natalia", "1234");
        Assertions.assertTrue(userService.userByLoginAndPassword("Natalia", "1234"));
    }

}