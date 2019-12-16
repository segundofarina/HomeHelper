package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.UserTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void findByIdTest() {
        Optional<User> expected = Optional.ofNullable(dummyUser());
        when(userDaoMock.findById(expected.get().getId())).thenReturn(expected).thenReturn(Optional.empty());

        User actual = userService.findById(expected.get().getId()).orElse(null);

        assertEqualsUsers(expected.get(), actual);
        assertFalse(userService.findById(2).isPresent());

        verify(userDaoMock, times(2)).findById(anyInt());
    }

    @Test
    public void findByUsernameTest() {
        Optional<User> expected = Optional.ofNullable(dummyUser());
        when(userDaoMock.findByUsername(expected.get().getUsername())).thenReturn(expected).thenReturn(Optional.empty());

        User actual = userService.findByUsername(expected.get().getUsername()).orElse(null);

        assertEqualsUsers(expected.get(), actual);
        assertFalse(userService.findByUsername("lala").isPresent());
        verify(userDaoMock, times(2)).findByUsername(anyString());
    }

//    @Test
//    public void createTest() {
//        User expected = dummyUser();
//        byte[] picture = profilePictureFromUser(expected);
//        when(userDaoMock.create(expected.getUsername(), expected.getPassword(), expected.getFirstname(), expected.getLastname(),
//                expected.getEmail(), expected.getPhone(), expected.getAddress(), expected.getAddress().getBytes()))
//                .thenReturn(expected)
//                .thenReturn(null);
//
//        User actual = userService.create(expected.getUsername(), expected.getPassword(), expected.getFirstname(), expected.getLastname(),
//                expected.getEmail(), expected.getPhone(), expected.getAddress(), picture);
//        assertEqualsUsers(expected, actual);
//
//        User shouldBeNull = userService.create(expected.getUsername(), expected.getPassword(), expected.getFirstname(), expected.getLastname(),
//                expected.getEmail(), expected.getPhone(), expected.getAddress(), picture);
//        assertNull(shouldBeNull);
//
//        verify(userDaoMock, times(2)).create(expected.getUsername(), expected.getPassword(), expected.getFirstname(),
//                expected.getLastname(), expected.getEmail(), expected.getPhone(), expected.getAddress(), picture);
//    }

    @Test
    public void loginTest() {
        Optional<User> expected = Optional.ofNullable(dummyUser());
        when(userDaoMock.findByUsername(expected.get().getUsername())).thenReturn(expected);

        boolean login = userService.login(expected.get().getUsername(), expected.get().getPassword());

        assertTrue(login);
        login = userService.login(expected.get().getUsername(), "lala");
        assertFalse(login);
        verify(userDaoMock, times(2)).findByUsername(anyString());
    }

    @Test
    public void updatePasswordOfUserTest() {

        User dummyUser = dummyUser();
        String expectedPassword = "sucutrule";
        User expected = new User(dummyUser.getUsername(), dummyUser.getPassword(), dummyUser.getFirstname(), dummyUser.getLastname(), dummyUser.getEmail(), dummyUser.getPhone(), dummyUser.getAddress(),/* profilePictureFromEmail(dummyUser.getEmail()),*/ true);
        when(userDaoMock.updatePasswordOfUser(dummyUser.getId(), expectedPassword)).thenReturn(true);

        boolean changed = userService.updatePasswordOfUser(expected.getId(), expectedPassword);

        assertTrue(changed);

        verify(userDaoMock, times(1)).updatePasswordOfUser(anyInt(), anyString());
    }
}
