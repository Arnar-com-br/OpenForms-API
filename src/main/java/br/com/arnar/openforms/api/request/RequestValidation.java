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

package br.com.arnar.openforms.api.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestValidation {
    public static boolean hasSpecialCharacters(String string) {
        String padrao = "[a-zA-Z\\s\\u00C0-\\u00FF]+";
        Pattern pattern = Pattern.compile(padrao);
        Matcher matcher = pattern.matcher(string);

        return !matcher.matches();
    }

    public static boolean invalidEmail(String email) {
        String padrao = "[a-zA-Z0-9._%+-]+@(gmail|hotmail|outlook|yahoo|tutanota|tuta)\\.(com|br|net)";
        Pattern pattern = Pattern.compile(padrao);
        Matcher matcher = pattern.matcher(email);

        return !matcher.matches();
    }
}
