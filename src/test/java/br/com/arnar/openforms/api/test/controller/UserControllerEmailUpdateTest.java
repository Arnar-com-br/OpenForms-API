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
import br.com.arnar.openforms.api.test.controller.mockentity.MockEmailUpdateRequest;
import br.com.arnar.openforms.api.test.controller.mockentity.MockUser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerEmailUpdateTest extends ControllerTest {
    @Test
    @Order(1)
    void login() throws Exception {
        MockUser user = new MockUser("mock.disposable.email@gmail.com", "S_enha64");

        req.post("/user/login", user.toJson()).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void updateExistingEmail() throws Exception {
        String jwt = MockValues.getDisposableEmailJwt(mockMvc);
        MockEmailUpdateRequest request = new MockEmailUpdateRequest("mock.disposable.email@gmail.com", "mock.admin@gmail.com", "S_enha64");

        req.put("/user/me/email", request.toJson(), jwt).andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    void updateEmail() throws Exception {
        String jwt = MockValues.getDisposableEmailJwt(mockMvc);
        MockEmailUpdateRequest request = new MockEmailUpdateRequest("mock.disposable.email@gmail.com", "new.email@gmail.com", "S_enha64");

        req.put("/user/me/email", request.toJson(), jwt).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void loginNewEmail() throws Exception {
        MockUser user = new MockUser("new.email@gmail.com", "S_enha64");

        req.post("/user/login", user.toJson()).andExpect(status().isOk());
    }
}