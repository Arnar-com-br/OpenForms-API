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

import br.com.arnar.openforms.api.request.Request;
import org.assertj.core.api.SoftAssertionsProvider;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class RequestTest {
    public static void assertThrows(SoftAssertionsProvider.ThrowingRunnable runnable, String expectedMessage) {
        try {
            runnable.run();
        } catch (Exception exception) {
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    /**
     * Assert that the validation of the specified request throws the desired exception.
     * @param request The request to be validated.
     * @param exception The expected Exception.
     */
    public void assertValidationThrows(Request request, Exception exception) {
        assertThrows(request::validate, exception.getMessage());
    }

    /**
     * Assert that the validation of the specified request throws the desired message.
     * @param request The request to be validated.
     */
    public void assertValidationThrows(Request request, String message) {
        assertThrows(request::validate, message);
    }

    /**
     * Assert that the validation of the specified request will not throw any exceptions.
     * @param request The request to be validated
     */
    public void assertValidationDoesNotThrow(Request request) {
        assertDoesNotThrow(request::validate);
    }
}
