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
import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
public class UserUpdateRequest implements Request {
    private String username;

    public User toEntity() throws RequestValidationException {
        this.validate();

        User user = new User();

        user.setUsername(username);

        return user;
    }

    public void validate() throws RequestValidationException {
        if (!isNull(username)) {
            if (!username.isEmpty())
                return;
        }

        throw new RequestValidationException("Specify at least 1 field");
    }
}
