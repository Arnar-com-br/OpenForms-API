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

package br.com.arnar.openforms.api.request;

import br.com.arnar.openforms.api.exception.RequestValidationException;

public class ExceptionTemplate {
    public static RequestValidationException exceedsMaxSize(String field, Integer maxSize) {
        return new RequestValidationException(field + " exceeds the maximum size of " + maxSize.toString());
    }

    public static RequestValidationException emptyOrNull(String field) {
        return new RequestValidationException(field + " cannot be empty or null");
    }

    public static RequestValidationException specialChars(String field) {
        return new RequestValidationException(field + " cannot contain special characters");
    }

    public static RequestValidationException invalid(String field) {
        return new RequestValidationException(field + " is invalid");
    }
}
