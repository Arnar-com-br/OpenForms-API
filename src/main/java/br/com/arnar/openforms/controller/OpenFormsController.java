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

import br.com.arnar.openforms.database.Form;
import br.com.arnar.openforms.database.User;
import br.com.arnar.openforms.exception.NoSuchEntryException;
import br.com.arnar.openforms.exception.ValueTakenException;
import br.com.arnar.openforms.request.user.UserLoginRequest;
import br.com.arnar.openforms.request.user.UserRegisterRequest;
import br.com.arnar.openforms.service.FormServiceInterface;
import br.com.arnar.openforms.service.UserServiceInterface;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Controller
public class OpenFormsController {
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private FormServiceInterface formService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        UserLoginRequest loginRequest = new UserLoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        model.addAttribute("registerRequest", registerRequest);
        return "register";
    }

    @SneakyThrows
    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterRequest registerRequest) {
        try {
            User newUser = registerRequest.toEntity();
            userService.register(newUser);
        } catch (ValueTakenException e) {
            return "redirect:/register?e=emailTaken";
        }
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
        List<Form> forms = formService.getByOwner(user);

        model.addAttribute("companyName", user.getCompanyName());
        model.addAttribute("forms", forms.reversed());

        return "home";
    }
}
