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

package br.com.arnar.openforms.controller;

import br.com.arnar.openforms.OpenformsApplication;
import br.com.arnar.openforms.database.Form;
import br.com.arnar.openforms.database.User;
import br.com.arnar.openforms.exception.NoSuchEntryException;
import br.com.arnar.openforms.exception.RequestValidationException;
import br.com.arnar.openforms.request.form.FormSendRequest;
import br.com.arnar.openforms.service.FormServiceInterface;
import br.com.arnar.openforms.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static br.com.arnar.openforms.controller.response.Response.created;
import static br.com.arnar.openforms.controller.response.Response.noContent;

@RestController
@CrossOrigin(origins = OpenformsApplication.CORS_ORIGIN)
@RequestMapping(path = OpenformsApplication.API_PATH + "/form")
public class FormController extends ServiceController<FormServiceInterface> {
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private FormServiceInterface formService;

    @PostMapping(consumes = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> create(@RequestBody FormSendRequest req, @RequestParam String campaign) throws NoSuchEntryException, RequestValidationException {
        Form form = req.toEntity();
        User owner = userService.getByCampaignId(campaign);

        service.insert(form, owner.getId());

        return created();
    }

    @GetMapping("/visualize/{id}")
    public ResponseEntity<?> home(@PathVariable Long id, Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
        Form form = formService.getById(id);

        if (!form.getOwner().getId().equals(user.getId())) {
            throw new NoSuchEntryException("Unable to find any form with this id");
        }

        form.setVisualized(true);
        formService.insert(form);

        return noContent();
    }
}
