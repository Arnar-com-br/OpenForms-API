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

import lombok.Getter;
import lombok.Setter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.arnar.openforms.api.OpenFormsApplication.API_PATH;

public class ControllerTestRequester {
        @Getter
        @Setter
        private MockMvc mockMvc;

        /**
         * Performs a GET request in an API endpoint that uses JWT authentication.
         * @param url The endpoint
         * @param jwt The authentication token
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions get(String url, String jwt) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + url).header("Authorization", "Bearer " + jwt));
        }

        /**
         * Performs a GET request in an API endpoint.
         * @param url The endpoint
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions get(String url) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + url));
        }

        /**
         * Performs a PUT request in an API endpoint that uses JWT authentication.
         * @param url The endpoint
         * @param content The content of the request
         * @param jwt The authentication token
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions put(String url, String content, String jwt) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + url).header("Authorization", "Bearer " + jwt).contentType("application/json").content(content));
        }

        /**
         * Performs a PUT request in an API endpoint that uses JWT authentication.
         * @param url The endpoint
         * @param jwt The authentication token
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions put(String url, String jwt) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + url).header("Authorization", "Bearer " + jwt));
        }

        /**
         * Performs a DELETE request in an API endpoint that uses JWT authentication.
         * @param url The endpoint
         * @param jwt The authentication token
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions delete(String url, String jwt) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + url).header("Authorization", "Bearer " + jwt));
        }

        /**
         * Performs a DELETE request in an API endpoint.
         * @param url The endpoint
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions delete(String url) throws Exception {
            return mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + url));
        }

        /**
         * Performs a POST request in an API endpoint.
         * @param url The endpoint
         * @param content The content of the request
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions post(String url, String content) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.post(API_PATH + url).contentType("application/json").content(content));
        }

        /**
         * Performs a POST request in an API endpoint that uses JWT authentication.
         * @param url The endpoint
         * @param content The content of the request
         * @param jwt The authentication token
         * @return The result of the request
         * @throws Exception If the request fails
         */
        public ResultActions post(String url, String content, String jwt) throws Exception {
                return mockMvc.perform(MockMvcRequestBuilders.post(API_PATH + url).header("Authorization", "Bearer " + jwt).contentType("application/json").content(content));
        }
}
