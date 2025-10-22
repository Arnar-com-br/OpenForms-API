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

package br.com.arnar.openforms.api.request.form;

import br.com.arnar.openforms.api.database.Form;
import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.exception.RequestValidationException;
import br.com.arnar.openforms.api.request.Request;
import lombok.Getter;
import lombok.Setter;

import static br.com.arnar.openforms.api.request.ExceptionTemplate.emptyOrNull;
import static br.com.arnar.openforms.api.request.ExceptionTemplate.exceedsMaxSize;
import static java.util.Objects.isNull;

@Getter
@Setter
public class FormSendRequest implements Request {
    private String name;
    private String phoneNumber;
    private String email;
    private String message;

    public Form toEntity() throws RequestValidationException {
        this.validate();

        Form form = new Form();

        form.setName(name);
        form.setPhoneNumber(phoneNumber);
        form.setEmail(email);
        form.setMessage(message);

        return form;
    }


    @Override
    public void validate() throws RequestValidationException {
        if (isNull(name) || name.isEmpty()) {
            throw emptyOrNull("username");
        }
        if (name.length() > 128) {
            throw exceedsMaxSize("name", 128);
        }

        if (isNull(phoneNumber) || phoneNumber.isEmpty()) {
            throw emptyOrNull("phoneNumber");
        }
        if (phoneNumber.length() > 128) {
            throw exceedsMaxSize("phoneNumber", 128);
        }

        if (isNull(email) || email.isEmpty()) {
            throw emptyOrNull("email");
        }
        if (email.length() > 128) {
            throw exceedsMaxSize("email", 128);
        }

        if (isNull(message) || message.isEmpty()) {
            throw emptyOrNull("email");
        }
        if (message.length() > 1280) {
            throw exceedsMaxSize("message", 1280);
        }
    }
}
