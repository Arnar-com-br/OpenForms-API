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

import br.com.arnar.openforms.configuration.TestMailSenderConfiguration;
import br.com.arnar.openforms.controller.mockentity.MockForm;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = { TestMailSenderConfiguration.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormControllerTest extends ControllerTest {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

    @BeforeEach
    void loginEmail() {
        greenMail.setUser("test@example.com", "password");
    }

    @Test
    void create() throws Exception {
        MockForm form = new MockForm(
                "Arthur",
                "21921342391",
                "mock.user@gmail.com",
                "hello world"
        );

        req.post("/form?campaign=cb147f1", form.toJson()).andExpect(status().isCreated());

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);

        MimeMessage message = receivedMessages[0];

        assertThat(message.getFrom()[0].toString()).isEqualTo("test@example.com");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("mock.admin@gmail.com");
        assertThat(message.getSubject()).isEqualTo("Você recebeu um novo contato!");
    }

    @Test
    void inexistentCampaign() throws Exception {
        MockForm form = new MockForm(
                "Arthur",
                "21921342391",
                "mock.user@gmail.com",
                "hello world"
        );

        req.post("/form?campaign=2led7f2", form.toJson()).andExpect(status().isNotFound());
    }

    @Test
    void visualizeUnlogged() throws Exception {
        req.get("/form/visualize/1")
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "http://localhost/login"));
    }


    @Test
    @WithMockUser(username = "mock.admin@gmail.com")
    void visualize() throws Exception {
        req.get("/form/visualize/1").andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "mock.admin@gmail.com")
    void visualizationOfUnauthorizedUser() throws Exception {
        req.get("/form/visualize/7").andExpect(status().isNotFound());
    }
}
