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

import org.junit.jupiter.api.Test;

import br.com.arnar.openforms.api.request.user.UserCreateRequest;

import static br.com.arnar.openforms.api.request.ExceptionTemplate.*;

class UserRequestTest extends RequestTest {

	@Test
	void noException() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("arthur");
		request.setPassword("senha23213");
		request.setEmail("arthur@gmail.com");

		assertValidationDoesNotThrow(request);
	}

	@Test
	void usernameWithSpecialChars() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("asda@!#!@");
		request.setPassword("senha23213");
		request.setEmail("arthur@gmail.com");

		assertValidationThrows(request, specialChars("username"));
	}

	@Test
	void usernameEmpty() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("");
		request.setPassword("senha23213");
		request.setEmail("arthur@gmail.com");

		assertValidationThrows(request, emptyOrNull("username"));
	}

	@Test
	void usernameNull() {
		UserCreateRequest request = new UserCreateRequest();

		request.setPassword("senha23213");
		request.setEmail("arthur@gmail.com");

		assertValidationThrows(request, emptyOrNull("username"));
	}

	@Test
	void emailInvalid() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("Arthur");
		request.setPassword("senha23213");
		request.setEmail("arthur@cmake.com");

		assertValidationThrows(request, invalid("email"));
	}

	@Test
	void emailEmpty() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("Arthur");
		request.setPassword("senha23213");
		request.setEmail("");

		assertValidationThrows(request, emptyOrNull("email"));
	}

	@Test
	void emailNull() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("Arthur");
		request.setPassword("senha23213");

		assertValidationThrows(request, emptyOrNull("email"));
	}

	@Test
	void passwordEmpty() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("Arthur");
		request.setPassword("");
		request.setEmail("arthur@gmail.com");

		assertValidationThrows(request, emptyOrNull("password"));
	}

	@Test
	void passwordNull() {
		UserCreateRequest request = new UserCreateRequest();

		request.setUsername("Arthur");
		request.setEmail("arthur@gmail.com");

		assertValidationThrows(request, emptyOrNull("password"));
	}
}
