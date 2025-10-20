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

package br.com.arnar.openforms.api.test.service.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.arnar.openforms.api.test.request.RequestTest;
import org.junit.Test;

import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.util.HashedPassword;

public class HashedPasswordTest {
        @Test
        public void hashPassword() throws Exception {
                User mockUser = new User();

                mockUser.setEmail("test@gmail.com");
                mockUser.setPassword("123456789");

                HashedPassword password = new HashedPassword(mockUser);

                assertEquals("ff13706a956c3ac97560f7ba6e1f0a0d", password.getValue());
        }

        @Test
        public void nullEmail() throws Exception {
                RequestTest.assertThrows(() -> {
                        User mockUser = new User();

                        mockUser.setPassword("123456789");

                        new HashedPassword(mockUser);
                }, "Neither email or password should be null");
        }

        @Test
        public void emptyEmail() throws Exception {
                RequestTest.assertThrows(() -> {
                        User mockUser = new User();

                        mockUser.setEmail("");
                        mockUser.setPassword("123456789");

                        new HashedPassword(mockUser);
                }, "Neither email or password should be empty");
        }

        @Test
        public void nullPassword() throws Exception {
                RequestTest.assertThrows(() -> {
                        User mockUser = new User();

                        mockUser.setEmail("test@gmail.com");

                        new HashedPassword(mockUser);
                }, "Neither email or password should be null");
        }

        @Test
        public void emptyPassword() throws Exception {
                RequestTest.assertThrows(() -> {
                        User mockUser = new User();

                        mockUser.setEmail("test@gmail.com");
                        mockUser.setPassword("");

                        new HashedPassword(mockUser);
                }, "Neither email or password should be empty");
        }
}
