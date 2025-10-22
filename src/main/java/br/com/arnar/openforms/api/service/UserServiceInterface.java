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

package br.com.arnar.openforms.api.service;

import br.com.arnar.openforms.api.database.User;
import br.com.arnar.openforms.api.exception.IncorrectCredentialsException;
import br.com.arnar.openforms.api.exception.NoSuchEntryException;
import br.com.arnar.openforms.api.exception.ValueTakenException;
import br.com.arnar.openforms.api.response.NoCredentialsResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.security.spec.InvalidKeySpecException;

public interface UserServiceInterface {
    /**
     * Registers a new user account with a hashed password and default role.
     *
     * @param entity the user entity to register.
     * @return a JWT token for the newly registered user.
     * @throws ValueTakenException     if the email is already registered.
     * @throws InvalidKeySpecException if password hashing fails.
     */
    String register(User entity) throws ValueTakenException, InvalidKeySpecException;

    /**
     * Retrieves the current authenticated user based on the JWT in the request.
     *
     * @param request the HTTP request containing the JWT token.
     * @return the authenticated {@link User}.
     * @throws NoSuchEntryException if the user corresponding to the token cannot be found.
     */
    User getMe(HttpServletRequest request) throws NoSuchEntryException;

    /**
     * Retrieves a user entity by their email address.
     *
     * @param email the email of the user.
     * @return the corresponding {@link User}.
     * @throws NoSuchEntryException if no user is found with the given email.
     */
    User getByEmail(String email) throws NoSuchEntryException;

    /**
     * Updates the current user's email and password, after verifying the current credentials.
     * Fails if the new email is already taken or if credentials do not match.
     *
     * @param request  the request containing the current user's JWT.
     * @param newEmail the new email to set.
     * @param oldEmail the user's current email (for verification).
     * @param password the current password (for verification).
     * @return the updated {@link User} entity.
     * @throws ValueTakenException           if the new email is already registered.
     * @throws InvalidKeySpecException       if password hashing fails.
     * @throws NoSuchEntryException          if the current user cannot be found.
     * @throws IncorrectCredentialsException if the old credentials are incorrect.
     */
    @SuppressWarnings("UnusedReturnValue")
    User insertWithNewEmail(HttpServletRequest request, String newEmail, String oldEmail, String password) throws ValueTakenException, InvalidKeySpecException, NoSuchEntryException, IncorrectCredentialsException;

    /**
     * Authenticates a user by their credentials.
     *
     * @param entity the user entity containing email and password to authenticate.
     * @return a JWT token if authentication succeeds.
     * @throws NoSuchEntryException          if no user with the given email exists.
     * @throws IncorrectCredentialsException if the password is incorrect.
     * @throws InvalidKeySpecException       if password hashing fails.
     */
    String login(User entity) throws NoSuchEntryException, IncorrectCredentialsException, InvalidKeySpecException;

    /**
     * Deletes a user from the system.
     *
     * @param entity the user entity to delete.
     * @throws NoSuchEntryException if the user does not exist.
     */
    void delete(User entity) throws NoSuchEntryException;

    User insert(User entity);
}
