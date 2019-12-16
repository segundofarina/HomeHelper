package testUtils;

import ar.edu.itba.paw.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class UserTestUtils {

    private UserTestUtils() {
    }

    public static User dummyUser() {
        int id = new Random().nextInt();
        return new User("florci" + id, "dulcedeleche" + id, "florencia", "cavallin", "florci" + id + "@hotmail.com", "1540910035", "Av Cabildo" + id, /*profilePictureFromEmail("florci" + id + "@hotmail.com"),*/ true);
    }

    public static List<User> dummyUserList(int size) {
        List<User> userList = new ArrayList<User>(size);

        for (int i = 0; i < size; i++)
            userList.add(dummyUser());

        return userList;
    }

    public static void assertEqualsUsers(User expected, User actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getAddress(), actual.getAddress());
        //assertEquals(expected.getImage(), actual.getImage());
    }

    public static byte[] profilePictureFromUser(User dummyUser) {
        return dummyUser.getEmail().getBytes();
    }

    public static byte[] profilePictureFromEmail(String email) {
        return email.getBytes();
    }
}