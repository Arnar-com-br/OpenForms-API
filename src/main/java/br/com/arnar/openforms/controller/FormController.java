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
import br.com.arnar.openforms.request.form.FormSendRequest;
import br.com.arnar.openforms.service.FormServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.arnar.openforms.controller.response.Response.created;

@RestController
@CrossOrigin(origins = OpenformsApplication.CORS_ORIGIN)
@RequestMapping(path = OpenformsApplication.API_PATH + "/form")
public class FormController extends ServiceController<FormServiceInterface> {
    @PostMapping(consumes = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> create(@RequestBody FormSendRequest req, @RequestParam Long ownerId) throws Exception {
        Form form = req.toEntity();
        service.insert(form, ownerId);
        return created();
    }
}
