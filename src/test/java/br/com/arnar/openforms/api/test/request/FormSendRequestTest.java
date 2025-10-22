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

import br.com.arnar.openforms.api.request.form.FormSendRequest;
import org.junit.jupiter.api.Test;

import static br.com.arnar.openforms.api.request.ExceptionTemplate.emptyOrNull;
import static br.com.arnar.openforms.api.request.ExceptionTemplate.exceedsMaxSize;

public class FormSendRequestTest extends RequestTest {
    @Test
    void noException() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationDoesNotThrow(request);
    }

    @Test
    void nameEmpty() {
        FormSendRequest request = new FormSendRequest();

        request.setName("");
        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, emptyOrNull("username"));
    }

    @Test
    void nameNull() {
        FormSendRequest request = new FormSendRequest();

        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, emptyOrNull("username"));
    }

    @Test
    void nameExceedsSize() {
        FormSendRequest request = new FormSendRequest();

        request.setName("a".repeat(129));
        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, exceedsMaxSize("name", 128));
    }

    @Test
    void phoneNumberEmpty() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("");
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, emptyOrNull("phoneNumber"));
    }

    @Test
    void phoneNumberNull() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, emptyOrNull("phoneNumber"));
    }

    @Test
    void phoneNumberExceedsSize() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1".repeat(129));
        request.setEmail("arthur@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, exceedsMaxSize("phoneNumber", 128));
    }

    @Test
    void emailEmpty() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setEmail("");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void emailNull() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void emailExceedsSize() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setEmail("a".repeat(120) + "@gmail.com");
        request.setMessage("This is a test message.");

        assertValidationThrows(request, exceedsMaxSize("email", 128));
    }

    @Test
    void messageEmpty() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");
        request.setMessage("");

        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void messageNull() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");

        assertValidationThrows(request, emptyOrNull("email"));
    }

    @Test
    void messageExceedsSize() {
        FormSendRequest request = new FormSendRequest();

        request.setName("Arthur");
        request.setPhoneNumber("1234567890");
        request.setEmail("arthur@gmail.com");
        request.setMessage("a".repeat(1281));

        assertValidationThrows(request, exceedsMaxSize("message", 1280));
    }
}
