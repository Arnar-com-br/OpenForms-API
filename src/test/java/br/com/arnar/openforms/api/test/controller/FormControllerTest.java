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
import br.com.arnar.openforms.api.test.controller.mockentity.MockForm;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormControllerTest extends ControllerTest {
    @Test
    void create() throws Exception {
        MockForm form = new MockForm(
                "Arthur",
                "21921342391",
                "mock.user@gmail.com",
                "hello world"
        );

        req.post("/form?ownerId=3", form.toJson()).andExpect(status().isCreated());
    }

    @Test
    void inexistentOwnerId() throws Exception {
        MockForm form = new MockForm(
                "Arthur",
                "21921342391",
                "mock.user@gmail.com",
                "hello world"
        );

        req.post("/form?ownerId=3022", form.toJson()).andExpect(status().isNotFound());
    }

    @Test
    void getAllForMe() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/listAll", jwt).andExpect(status().isOk());
    }

    @Test
    void getAllForMeWithoutAny() throws Exception {
        String jwt = MockValues.getAdmJwt(mockMvc);
        req.get("/form/me/listAll", jwt).andExpect(status().isNotFound());
    }

    @Test
    void getAllForMeNotAuthenticated() throws Exception {
        req.get("/form/me/listAll").andExpect(status().isForbidden());
    }

    @Test
    void getByIdForMe() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/1", jwt).andExpect(status().isOk());
    }

    @Test
    void getByIdExistentButNotMine() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/2", jwt).andExpect(status().isNotFound());
    }

    @Test
    void getByIdInexistent() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/23", jwt).andExpect(status().isNotFound());
    }

    @Test
    void visualize() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/1/view", jwt).andExpect(status().isOk());
    }

    @Test
    void visualizeExistentBotNotMine() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/2/view", jwt).andExpect(status().isNotFound());
    }

    @Test
    void visualizeInexistent() throws Exception {
        String jwt = MockValues.getUserJwt(mockMvc);
        req.get("/form/me/2231/view", jwt).andExpect(status().isNotFound());
    }
}
