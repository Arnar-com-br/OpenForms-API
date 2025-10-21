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

import static br.com.arnar.openforms.api.request.ExceptionTemplate.*;

import org.junit.jupiter.api.Test;

import br.com.arnar.openforms.api.request.user.UserLoginRequest;

public class UserLoginRequestTest extends RequestTest {
    @Test
    void noException() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("arthur@gmail.com");
        request.setPassword("senha23213");

        assertValidationDoesNotThrow(request);
    }

    @Test
    void emailEmpty() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("");
        request.setPassword("senha23213");

        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void emailNull() {
        UserLoginRequest request = new UserLoginRequest();

        request.setPassword("senha23213");

        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void emailInvalid() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("invalidemail.com");
        request.setPassword("senha23213");

        assertValidationThrows(request, invalid("email"));
    }

    @Test
    void emailExceedsSize() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("arthur".repeat(128) + "@gmail.com");
        request.setPassword("senha23213");

        assertValidationThrows(request, exceedsMaxSize("email", 128));
    }

    @Test
    void passwordEmpty() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("arthur@gmail.com");
        request.setPassword("");

        assertValidationThrows(request, emptyOrNull("password"));
    }

    @Test
    void passwordNull() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("arthur@gmail.com");

        assertValidationThrows(request, emptyOrNull("password"));
    }

    @Test
    void passwordExceedsSize() {
        UserLoginRequest request = new UserLoginRequest();

        request.setEmail("arthur@gmail.com");
        request.setPassword("senha23213".repeat(128));

        assertValidationThrows(request, exceedsMaxSize("password", 64));
    }
}
