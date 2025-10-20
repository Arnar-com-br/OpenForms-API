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

package br.com.arnar.openforms.api.test.controller;

import br.com.arnar.openforms.api.test.MockValues;
import br.com.arnar.openforms.api.test.controller.mockentity.MockUser;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest extends ControllerTest {
	@Test
	@Order(1)
	void create() throws Exception {
		MockUser user = new MockUser("arthur", "arthur.araujo@gmail.com", "S_enha64");

		req.post("/user/register", user.toJson()).andExpect(status().isCreated());
	}

	@Test
	@Order(2)
	void createUserExistingEmail() throws Exception {
		MockUser user = new MockUser("aaaaaaaa", "arthur.araujo@tutanota.com", "S_enha64");

		req.post("/user/register", user.toJson()).andExpect(status().isForbidden());
	}

	@Test
	@Order(3)
	void getById() throws Exception {
		req.get("/user/1").andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void getMe() throws Exception {
		String jwt = MockValues.getUserJwt(mockMvc);
		req.get("/user/me", jwt).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void getByInexistentId() throws Exception {
		req.get("/user/64").andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	void login() throws Exception {
		MockUser user = new MockUser("arthur.araujo@gmail.com", "S_enha64");

		req.post("/user/login", user.toJson()).andExpect(status().isOk());
	}

	@Test
	@Order(7)
	void loginIncorrectCredentials() throws Exception {
		MockUser user = new MockUser("arthur.araujo@gmail.com", "senha");

		req.post("/user/login", user.toJson()).andExpect(status().isUnauthorized());
	}

	@Test
	@Order(8)
	void updateMeUsername() throws Exception {
		String jwt = MockValues.getUserJwt(mockMvc);
		req.put("/user/me", "{\"username\": \"uuu\"}", jwt).andExpect(status().isOk());
	}

	@Test
	@Order(9)
	void deleteMe() throws Exception {
		String jwt = MockValues.getDisposableJwt(mockMvc);
		req.delete("/user/me", jwt).andExpect(status().isOk());
	}
}