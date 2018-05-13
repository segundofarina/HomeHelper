package ar.edu.itba.paw.homehelper.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * Validates that {@link User} with the form's Email has the
 * same Username registered as the one in the form
 */
@Component
public class EqualsUsernameValidator implements Validator {

    public static UserNamePair buildUserNamePair(String expectedUserName, String retrievedUserName) {
        return new UserNamePair(expectedUserName, retrievedUserName);
    }

    public boolean supports(Class<?> clazz) {
        return UserNamePair.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        UserNamePair userPair = (UserNamePair) target;
        String expectedUserName = userPair.expectedUserName;
        String retrievedUserName = userPair.retrievedUserName;

        if (expectedUserName.equals(retrievedUserName))
            errors.rejectValue("username", "EqualsUsernameValidator", new Object[]{retrievedUserName}, "EqualsUsernameValidator");
    }

    private static class UserNamePair {
        private String expectedUserName;
        private String retrievedUserName;

        public UserNamePair(String expectedUserName, String retrievedUserName) {
            this.expectedUserName = expectedUserName;
            this.retrievedUserName = retrievedUserName;
        }
    }
}
