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

package br.com.arnar.openforms.request;

import static br.com.arnar.openforms.request.ExceptionTemplate.*;
import org.junit.jupiter.api.Test;
import br.com.arnar.openforms.request.user.UserRegisterRequest;

public class UserRegisterRequestTest extends RequestTest {

    private UserRegisterRequest validRequest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("arthur");
        request.setCompanyName("Company");
        request.setEmail("arthur@gmail.com");
        request.setPassword("senha23213");
        return request;
    }

    @Test
    void noException() {
        UserRegisterRequest request = validRequest();
        assertValidationDoesNotThrow(request);
    }

    @Test
    void emailEmpty() {
        UserRegisterRequest request = validRequest();
        request.setEmail("");
        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void emailNull() {
        UserRegisterRequest request = validRequest();
        request.setEmail(null);
        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void emailInvalid() {
        UserRegisterRequest request = validRequest();
        request.setEmail("invalidemail.com");
        assertValidationThrows(request, invalid("email"));
    }

    @Test
    void emailExceedsSize() {
        UserRegisterRequest request = validRequest();
        request.setEmail("arthur".repeat(128) + "@gmail.com");
        assertValidationThrows(request, exceedsMaxSize("email", 128));
    }

    @Test
    void usernameEmpty() {
        UserRegisterRequest request = validRequest();
        request.setUsername("");
        assertValidationThrows(request, emptyOrNull("username"));
    }

    @Test
    void usernameNull() {
        UserRegisterRequest request = validRequest();
        request.setUsername(null);
        assertValidationThrows(request, emptyOrNull("username"));
    }

    @Test
    void usernameExceedsSize() {
        UserRegisterRequest request = validRequest();
        request.setUsername("arthur".repeat(129));
        assertValidationThrows(request, exceedsMaxSize("username", 128));
    }

    @Test
    void companyNameEmpty() {
        UserRegisterRequest request = validRequest();
        request.setCompanyName("");
        assertValidationThrows(request, emptyOrNull("companyName"));
    }

    @Test
    void companyNameNull() {
        UserRegisterRequest request = validRequest();
        request.setCompanyName(null);
        assertValidationThrows(request, emptyOrNull("companyName"));
    }

    @Test
    void companyNameExceedsSize() {
        UserRegisterRequest request = validRequest();
        request.setCompanyName("Company".repeat(129));
        assertValidationThrows(request, exceedsMaxSize("companyName", 128));
    }

    @Test
    void passwordEmpty() {
        UserRegisterRequest request = validRequest();
        request.setPassword("");
        assertValidationThrows(request, emptyOrNull("password"));
    }

    @Test
    void passwordNull() {
        UserRegisterRequest request = validRequest();
        request.setPassword(null);
        assertValidationThrows(request, emptyOrNull("password"));
    }

    @Test
    void passwordExceedsSize() {
        UserRegisterRequest request = validRequest();
        request.setPassword("senha23213".repeat(129));
        assertValidationThrows(request, exceedsMaxSize("password", 128));
    }
}