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

package br.com.arnar.openforms.api.util;

import br.com.arnar.openforms.api.database.User;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static java.util.Objects.isNull;

public class HashedPassword {
    private String hashed;

    private String email;

    private String password;

    public HashedPassword(User entity) throws InvalidKeySpecException {
        this.email = entity.getEmail();
        this.password = entity.getPassword();

        KeySpec hashSpecifications = getKeySpecifications();

        String generatedHashedPassword = generateHash("PBKDF2WithHmacSHA512", hashSpecifications);

        this.setHashedPassword(generatedHashedPassword);
    }

    public HashedPassword(String email, String password) throws InvalidKeySpecException {
        this.email = email;
        this.password = password;

        KeySpec hashSpecifications = getKeySpecifications();

        String generatedHashedPassword = generateHash("PBKDF2WithHmacSHA512", hashSpecifications);

        this.setHashedPassword(generatedHashedPassword);
    }

    private PBEKeySpec getKeySpecifications() throws InvalidKeySpecException {
        if (isNull(email) || isNull(password))
            throw new InvalidKeySpecException("Neither email or password should be null");

        if (email.isEmpty() || password.isEmpty())
            throw new InvalidKeySpecException("Neither email or password should be empty");

        byte[] salt = (email + password).getBytes();

        return new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    }

    private String generateHash(String algorithm, KeySpec hashSpecifications) {
        try {
            SecretKeyFactory sKFactory = SecretKeyFactory.getInstance(algorithm);

            byte[] hashedPassword = sKFactory.generateSecret(hashSpecifications).getEncoded();

            return Hex.encodeHexString(hashedPassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();

            return null;
        }
    }

    public String getValue() {
        return hashed;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashed = hashedPassword;
    }
}
