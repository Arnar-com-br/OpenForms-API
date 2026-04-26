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

package br.com.arnar.openforms.service.implementation;

import br.com.arnar.openforms.database.User;
import br.com.arnar.openforms.exception.NoSuchEntryException;
import br.com.arnar.openforms.exception.ValueTakenException;
import br.com.arnar.openforms.repository.UserRepository;
import br.com.arnar.openforms.service.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(User entity) {
        Optional<User> userWithSameEmail = repository.findByEmail(entity.getEmail());

        if (userWithSameEmail.isPresent()) {
            throw new ValueTakenException("This email is already in use");
        }

        String hash = passwordEncoder.encode(entity.getPassword());

        entity.setPassword(hash);
        return repository.save(entity);
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NoSuchEntryException("Unable to find a user with this email");
        }

        return user.get();
    }

    @Override
    public User getByCampaignId(String campaignId) throws NoSuchEntryException {
        Optional<User> user = repository.findByCampaignId(campaignId);

        if (user.isEmpty()) {
            throw new NoSuchEntryException("This campaign does not exist");
        }

        return user.get();
    }

    @Override
    public User insert(User entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(User entity) {
        repository.delete(entity);
    }
}
