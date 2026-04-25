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

package br.com.arnar.openforms.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class OpenFormsControllerTest extends ControllerTest {
    @Test
    void loginRenders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest"));
    }

    @Test
    void performLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("email", "mock.admin@gmail.com")
                .param("password", "S_enha64")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/home"));
    }

    @Test
    void performLoginWithBadPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "mock.admin@gmail.com")
                        .param("password", "64")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login?failed=true"));
    }

    @Test
    void performLoginInexistentEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "mock@gmail.com")
                        .param("password", "S_enha64")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/login?failed=true"));
    }

    @Test
    void homeRedirectsToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"));

    }

    @Test
    @WithMockUser(username = "mock.admin@gmail.com")
    void homeRendersWhenAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("forms"))
                .andExpect(model().attributeExists("companyName"));
    }
}
