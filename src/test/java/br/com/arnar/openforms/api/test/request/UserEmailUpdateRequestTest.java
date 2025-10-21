/*
 * Copyright (c) 2025 Arnar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package br.com.arnar.openforms.api.test.request;

import br.com.arnar.openforms.api.exception.RequestValidationException;
import br.com.arnar.openforms.api.request.user.UserEmailUpdateRequest;
import org.junit.jupiter.api.Test;

import static br.com.arnar.openforms.api.request.ExceptionTemplate.emptyOrNull;
import static br.com.arnar.openforms.api.request.ExceptionTemplate.exceedsMaxSize;

public class UserEmailUpdateRequestTest extends RequestTest {
    @Test
    void noException() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur.araujo@gmail.com");
        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("password");

        assertValidationDoesNotThrow(request);
    }

    @Test
    void emailsEqual() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur.araujo@hotmail.com");
        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("password");

        assertValidationThrows(request, new RequestValidationException("Emails cannot be equal"));
    }

    @Test
    void oldEmailEmpty() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("");
        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("password");

        assertValidationThrows(request, emptyOrNull("oldEmail"));
    }

    @Test
    void oldEmailNull() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("password");

        assertValidationThrows(request, emptyOrNull("oldEmail"));
    }

    @Test
    void oldEmailExceedsSize() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur".repeat(128) + "@hotmail.com");
        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("password");

        assertValidationThrows(request, exceedsMaxSize("oldEmail", 128));
    }

    @Test
    void newEmailEmpty() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur.araujo@gmail.com");
        request.setNewEmail("");
        request.setPassword("password");

        assertValidationThrows(request, emptyOrNull("newEmail"));
    }

    @Test
    void newEmailNull() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur.araujo@gmail.com");
        request.setPassword("password");

        assertValidationThrows(request, emptyOrNull("newEmail"));
    }

    @Test
    void newEmailExceedsSize() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur@hotmail.com");
        request.setNewEmail("arthur.araujo".repeat(128) + "@hotmail.com");
        request.setPassword("password");

        assertValidationThrows(request, exceedsMaxSize("newEmail", 128));
    }

    @Test
    void passwordEmpty() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur.araujo@gmail.com");
        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("");

        assertValidationThrows(request, emptyOrNull("password"));
    }

    @Test
    void passwordNull() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur.araujo@gmail.com");
        request.setNewEmail("arthur.araujo@hotmail.com");

        assertValidationThrows(request, emptyOrNull("password"));
    }

    @Test
    void passwordExceedsSize() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("arthur@hotmail.com");
        request.setNewEmail("arthur.araujo@hotmail.com");
        request.setPassword("password".repeat(128) );

        assertValidationThrows(request, exceedsMaxSize("password", 64));
    }


    @Test
    void fieldsEmpty() {
        UserEmailUpdateRequest request = new UserEmailUpdateRequest();

        request.setOldEmail("");
        request.setNewEmail("");
        request.setPassword("");

        assertValidationThrows(request, emptyOrNull("oldEmail"));
    }

    @Test
    void fieldsNull() {
        assertValidationThrows(new UserEmailUpdateRequest(), emptyOrNull("oldEmail"));
    }
}
