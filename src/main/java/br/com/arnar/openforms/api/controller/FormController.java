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
import br.com.arnar.openforms.api.database.Form;
import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.exception.NoSuchEntryException;
import br.com.arnar.openforms.api.request.form.FormSendRequest;
import br.com.arnar.openforms.api.response.SimpleFormsResponse;
import br.com.arnar.openforms.api.response.SimplifiedForm;
import br.com.arnar.openforms.api.service.FormServiceInterface;
import br.com.arnar.openforms.api.service.UserServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.arnar.openforms.api.controller.response.Response.created;
import static br.com.arnar.openforms.api.controller.response.Response.ok;

@RestController
@CrossOrigin(origins = OpenFormsApplication.CORS_ORIGIN)
@RequestMapping(path = OpenFormsApplication.API_PATH + "/form")
public class FormController extends ServiceController<FormServiceInterface> {
    @Autowired
    private UserServiceInterface userService;

    // TODO: This should be public, anyone should be able to make this request without specifying a token.
    @PostMapping(consumes = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> create(@RequestBody FormSendRequest req, @RequestParam Long ownerId) {
        Form form = req.toEntity();
        service.insert(form, ownerId);
        return created();
    }

    @Transactional
    @GetMapping(path = "/me/listAll", produces = "application/json")
    public ResponseEntity<?> listAllForMe(HttpServletRequest request) {
        User me = userService.getMe(request);

        List<Form> forms = service.getByOwner(me);

        return ok(new SimpleFormsResponse(forms));
    }

    @Transactional
    @GetMapping(path = "/me/{id}", produces = "application/json")
    public ResponseEntity<?> getByIdForMe(@PathVariable Long id, HttpServletRequest request) {
        User me = userService.getMe(request);

        Form forms = service.getById(id);

        if (!forms.getOwner().getId().equals(me.getId())) {
            throw new NoSuchEntryException("Unable to find a form with this id");
        }

        return ok(new SimplifiedForm(forms));
    }

    @Transactional
    @GetMapping(path = "/me/{id}/view")
    public ResponseEntity<?> setFormVisualized(@PathVariable Long id, HttpServletRequest request) {
        User me = userService.getMe(request);

        Form form = service.getById(id);

        if (!form.getOwner().getId().equals(me.getId())) {
            throw new NoSuchEntryException("Unable to find a form with this id");
        }

        form.setVisualized(true);
        service.insert(form);

        return ok(new SimplifiedForm(form));
    }
}
