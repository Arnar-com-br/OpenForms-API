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

package br.com.arnar.openforms.api.request.user;


import br.com.arnar.openforms.api.exception.RequestValidationException;
import br.com.arnar.openforms.api.request.Request;
import br.com.arnar.openforms.api.request.RequestValidation;
import lombok.Getter;
import lombok.Setter;

import static br.com.arnar.openforms.api.request.ExceptionTemplate.*;
import static java.util.Objects.isNull;

@Getter
@Setter
public class UserEmailUpdateRequest implements Request {
    private String oldEmail;
    private String newEmail;
    private String password;

    public void validate() throws RequestValidationException {
        if (isNull(oldEmail) || oldEmail.isEmpty()) {
            throw emptyOrNull("oldEmail");
        }

        if (RequestValidation.invalidEmail(oldEmail)) {
            throw invalid("oldEmail");
        }

        if (oldEmail.length() > 128) {
            throw exceedsMaxSize("oldEmail", 128);
        }

        if (isNull(newEmail) || newEmail.isEmpty()) {
            throw emptyOrNull("newEmail");
        }

        if (RequestValidation.invalidEmail(newEmail)) {
            throw invalid("newEmail");
        }

        if (newEmail.length() > 128) {
            throw exceedsMaxSize("newEmail", 128);
        }

        if (oldEmail.equals(newEmail)) {
            throw new RequestValidationException("Emails cannot be equal");
        }

        if (isNull(password) || password.isEmpty()) {
            throw emptyOrNull("password");
        }

        if (password.length() > 128) {
            throw exceedsMaxSize("password", 128);
        }
    }
}
