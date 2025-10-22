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

import br.com.arnar.openforms.api.authentication.JwtTokenProvider;
import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.exception.IncorrectCredentialsException;
import br.com.arnar.openforms.api.exception.NoSuchEntryException;
import br.com.arnar.openforms.api.exception.ValueTakenException;
import br.com.arnar.openforms.api.repository.UserRepository;
import br.com.arnar.openforms.api.response.NoCredentialsResponse;
import br.com.arnar.openforms.api.service.UserServiceInterface;
import br.com.arnar.openforms.api.util.HashedPassword;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository repository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(User entity) throws InvalidKeySpecException {
        Optional<User> userWithSameEmail = repository.findByEmail(entity.getEmail());

        if (userWithSameEmail.isPresent()) {
            throw new ValueTakenException("This email is already in use");
        }

        String hash = new HashedPassword(entity).getValue();

        entity.setPassword(hash);
        repository.save(entity);

        return jwtTokenProvider.createToken(entity.getEmail(), "DEFAULT");
    }

    @Override
    public User getMe(HttpServletRequest request) {
        return getByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(request)));
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
    public User insertWithNewEmail(HttpServletRequest request, String newEmail, String oldEmail, String password) throws InvalidKeySpecException {
        Optional<User> existingUserWithNewEmail = repository.findByEmail(newEmail);

        if (existingUserWithNewEmail.isPresent()) {
            throw new ValueTakenException("newEmail is already taken by another account.");
        }

        User user = getMe(request);

        String oldHash = new HashedPassword(oldEmail, password).getValue();
        String newHash = new HashedPassword(newEmail, password).getValue();

        if (user.getPassword().equals(oldHash)) {
            user.setEmail(newEmail);
            user.setPassword(newHash);
        } else {
            throw new IncorrectCredentialsException("Email or password did not match");
        }

        return insert(user);
    }

    @Override
    public User insert(User entity) {
        return repository.save(entity);
    }

    public String login(User entity) throws  InvalidKeySpecException {
        String hashedPasswd = new HashedPassword(entity).getValue();
        User user = this.getByEmail(entity.getEmail());

        if (!user.getPassword().equals(hashedPasswd)) {
            throw new IncorrectCredentialsException("Bad credentials");
        }

        return jwtTokenProvider.createToken(entity.getEmail(), "DEFAULT");
    }

    @Override
    public void delete(User entity) {
        repository.delete(entity);
    }
}
