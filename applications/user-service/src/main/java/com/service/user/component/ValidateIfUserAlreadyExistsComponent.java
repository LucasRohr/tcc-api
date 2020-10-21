package com.service.user.component;

import com.service.common.domain.User;
import com.service.user.exceptions.UserAlreadyExistsException;
import com.service.user.service.GetUserByEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfUserAlreadyExistsComponent {
    @Autowired
    private GetUserByEmailService getUserByEmailService;

    public void validateUser(String email) throws UserAlreadyExistsException {
        User userSearchedByEmail = getUserByEmailService.getUserByEmail(email);

        boolean hasUser = userSearchedByEmail != null;

        if(hasUser) {
            throw new UserAlreadyExistsException();
        }
    }
}
