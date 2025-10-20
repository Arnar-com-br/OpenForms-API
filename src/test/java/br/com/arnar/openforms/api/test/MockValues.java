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

package br.com.arnar.openforms.api.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.arnar.openforms.api.test.controller.mockentity.MockUser;

public class MockValues {
	public static String getUserJwt(MockMvc request) throws Exception {
		MockUser mock = new MockUser("mock.user@gmail.com", "S_enha64");
		MvcResult result = request
				.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
						.contentType("application/json")
						.content(mock.toJson()))
				.andExpect(status().isOk()).andReturn();

		return result.getResponse().getContentAsString();
	}

	public static String getDisposableJwt(MockMvc request) throws Exception {
		MockUser mock = new MockUser("mock.disposable@gmail.com", "S_enha64");
		MvcResult result = request
				.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
						.contentType("application/json")
						.content(mock.toJson()))
				.andExpect(status().isOk()).andReturn();

		return result.getResponse().getContentAsString();
	}

    public static String getDisposableEmailJwt(MockMvc request) throws Exception {
        MockUser mock = new MockUser("mock.disposable.email@gmail.com", "S_enha64");
        MvcResult result = request
                .perform(MockMvcRequestBuilders.post("/api/v1/user/login")
                        .contentType("application/json")
                        .content(mock.toJson()))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

	public static String getAdmJwt(MockMvc request) throws Exception {
		MockUser mock = new MockUser("mock.admin@gmail.com", "S_enha64");
		MvcResult result = request
				.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
						.contentType("application/json")
						.content(mock.toJson()))
				.andExpect(status().isOk()).andReturn();

		return result.getResponse().getContentAsString();
	}
}
