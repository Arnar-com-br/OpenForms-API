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

import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.exception.RequestValidationException;
import br.com.arnar.openforms.api.request.Request;
import br.com.arnar.openforms.api.request.RequestValidation;
import lombok.Getter;
import lombok.Setter;

import static br.com.arnar.openforms.api.request.ExceptionTemplate.*;
import static java.util.Objects.isNull;

@Getter
@Setter
public class UserLoginRequest implements Request {
    private String email;
    private String password;

    public User toEntity() throws RequestValidationException {
        this.validate();

        User loginUser = new User();

        loginUser.setEmail(email);
        loginUser.setPassword(password);

        return loginUser;
    }

    public void validate() throws RequestValidationException {
        if (isNull(email) || email.isEmpty()) {
            throw emptyOrNull("email");
        }
        if (RequestValidation.invalidEmail(email)) {
            throw invalid("email");
        }
        if (email.length() > 128) {
            throw exceedsMaxSize("email", 128);
        }

        if (isNull(password) || password.isEmpty()) {
            throw emptyOrNull("password");
        }
        if (password.length() > 128) {
            throw exceedsMaxSize("password", 64);
        }
    }
}
