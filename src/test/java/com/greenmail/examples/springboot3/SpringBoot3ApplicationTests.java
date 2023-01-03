package com.greenmail.examples.springboot3;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBoot3ApplicationTests {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP_IMAP)
            .withConfiguration(GreenMailConfiguration
                    .aConfig()
                    .withUser("foo@localhost", "foo", "foo-pwd"));


    // See application.properties for SMPT host and user configuration
    @Autowired
    private JavaMailSender mailSender;

    @Test
    void testSendAndReceiveMail() throws MessagingException {
        // Send email ...
        final String subject = "Hello Spring 3: testReceiveMailViaImap";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("foo@localhost");
        message.setTo("foo@localhost");
        message.setSubject(subject);
        message.setText("I'm a test");
        mailSender.send(message);

        // .. wait for delivery ...
        greenMail.waitForIncomingEmail(1);

        // ... verify by using IMAPx
        try (final Store store = greenMail.getImap().createStore()) {
            store.connect("foo", "foo-pwd");
            final Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            assertThat(inbox.getMessageCount()).isEqualTo(1);
            assertThat(inbox.getMessages()[0].getSubject()).isEqualTo(subject);
        }
    }
}
