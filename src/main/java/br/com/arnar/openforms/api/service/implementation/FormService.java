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

package br.com.arnar.openforms.api.service.implementation;


import br.com.arnar.openforms.api.database.Form;
import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.exception.NoSuchEntryException;
import br.com.arnar.openforms.api.repository.FormRepository;
import br.com.arnar.openforms.api.repository.UserRepository;
import br.com.arnar.openforms.api.service.FormServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService implements FormServiceInterface {
    @Autowired
    private FormRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Form> getByOwner(User owner) {
        List<Form> forms = repository.findByOwnerId(owner.getId());

        if (forms.isEmpty()) {
            throw new NoSuchEntryException("There are no forms for you yet");
        }

        return forms;
    }

    @Override
    public Form getById(Long id) {
        Optional<Form> form = repository.findById(id);

        if (form.isEmpty()) {
            throw new NoSuchEntryException("Unable to find a form with this id");
        }

        return form.get();
    }

    @Override
    public Form insert(Form entity, Long ownerId) {
        Optional<User> addresseeOpt = userRepository.findById(ownerId);

        if (addresseeOpt.isEmpty()) {
            throw new NoSuchEntryException("Unable to find a user with this id");
        }

        User addressee = addresseeOpt.get();

        entity.setOwner(addressee);

        return repository.save(entity);
    }
}
