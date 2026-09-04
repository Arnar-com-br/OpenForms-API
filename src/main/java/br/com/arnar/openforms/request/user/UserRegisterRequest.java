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

package br.com.arnar.openforms.request.user;

import br.com.arnar.openforms.database.User;
import br.com.arnar.openforms.exception.RequestValidationException;
import br.com.arnar.openforms.request.Request;
import br.com.arnar.openforms.request.RequestValidation;
import lombok.Getter;
import lombok.Setter;

import static br.com.arnar.openforms.request.ExceptionTemplate.*;
import static br.com.arnar.openforms.request.ExceptionTemplate.emptyOrNull;
import static br.com.arnar.openforms.request.ExceptionTemplate.exceedsMaxSize;
import static java.util.Objects.isNull;

@Getter
@Setter
public class UserRegisterRequest implements Request {
    private String username;
    private String companyName;
    private String email;
    private String password;

    public User toEntity() throws RequestValidationException {
        this.validate();

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setCompanyName(companyName);
        newUser.setEmail(email);
        newUser.setPassword(password);

        return newUser;
    }


    @Override
    public void validate() throws RequestValidationException {
        if (isNull(email) || email.isEmpty()) {
            throw emptyOrNull("email");
        }
        if (isNull(username) || username.isEmpty()) {
            throw emptyOrNull("username");
        }
        if (isNull(companyName) || companyName.isEmpty()) {
            throw emptyOrNull("companyName");
        }
        if (RequestValidation.invalidEmail(email)) {
            throw invalid("email");
        }
        if (email.length() > 128) {
            throw exceedsMaxSize("email", 128);
        }
        if (username.length() > 128) {
            throw exceedsMaxSize("username", 128);
        }
        if (companyName.length() > 128) {
            throw exceedsMaxSize("companyName", 128);
        }
        if (isNull(password) || password.isEmpty()) {
            throw emptyOrNull("password");
        }
        if (password.length() > 128) {
            throw exceedsMaxSize("password", 128);
        }
    }
}
