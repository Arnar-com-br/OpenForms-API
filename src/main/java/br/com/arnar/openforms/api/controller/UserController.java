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

package br.com.arnar.openforms.api.controller;

import br.com.arnar.openforms.api.OpenFormsApplication;
import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.request.user.UserCreateRequest;
import br.com.arnar.openforms.api.request.user.UserEmailUpdateRequest;
import br.com.arnar.openforms.api.request.user.UserLoginRequest;
import br.com.arnar.openforms.api.request.user.UserUpdateRequest;
import br.com.arnar.openforms.api.response.NoCredentialsResponse;
import br.com.arnar.openforms.api.service.UserServiceInterface;
import br.com.arnar.openforms.api.util.ClassMerger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;

import static br.com.arnar.openforms.api.controller.response.Response.created;
import static br.com.arnar.openforms.api.controller.response.Response.ok;

@RestController
@CrossOrigin(origins = OpenFormsApplication.CORS_ORIGIN)
@RequestMapping(path = OpenFormsApplication.API_PATH + "/user")
public class UserController extends ServiceController<UserServiceInterface> {
    @PostMapping(path = "/register", consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody UserCreateRequest request) throws InvalidKeySpecException {
        String jwtToken = service.register(request.toEntity());
        return created(jwtToken);
    }

    @PostMapping(path = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) throws InvalidKeySpecException {
        String jwtToken = service.login(request.toEntity());
        return ok(jwtToken);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(HttpServletRequest request) {
        return ok(new NoCredentialsResponse(service.getMe(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ok(service.getByIdWithoutCredentials(id));
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@RequestBody UserUpdateRequest request, HttpServletRequest req) {
        User user = service.getMe(req);
        User userAlter = request.toEntity();

        User merged = ClassMerger.merge(user, userAlter);

        service.insert(merged);

        return ok();
    }

    @PutMapping("/me/email")
    public ResponseEntity<?> updateEmail(@RequestBody UserEmailUpdateRequest request, HttpServletRequest req) throws InvalidKeySpecException {
        request.validate();

        service.insertWithNewEmail(req, request.getNewEmail(), request.getOldEmail(), request.getPassword());

        return ok();
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> delete(HttpServletRequest request) {
        User user = service.getMe(request);
        service.delete(user);
        return ok();
    }
}
