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

package br.com.arnar.openforms.service;

import br.com.arnar.openforms.database.User;
import br.com.arnar.openforms.exception.NoSuchEntryException;
import br.com.arnar.openforms.exception.ValueTakenException;

import java.security.NoSuchAlgorithmException;
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
    User register(User entity) throws ValueTakenException, InvalidKeySpecException, NoSuchAlgorithmException;

    /**
     * Retrieves a user entity by their email address.
     *
     * @param email the email of the user.
     * @return the corresponding {@link User}.
     * @throws NoSuchEntryException if no user is found with the given email.
     */
    User getByEmail(String email) throws NoSuchEntryException;

    User getByCampaignId(String campaignId) throws NoSuchEntryException;

    /**
     * Deletes a user from the system.
     *
     * @param entity the user entity to delete.
     * @throws NoSuchEntryException if the user does not exist.
     */
    void delete(User entity) throws NoSuchEntryException;

    User insert(User entity);
}
