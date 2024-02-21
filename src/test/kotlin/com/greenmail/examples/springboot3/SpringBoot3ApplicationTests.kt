package com.greenmail.examples.springboot3

import com.icegreen.greenmail.configuration.GreenMailConfiguration
import com.icegreen.greenmail.junit5.GreenMailExtension
import com.icegreen.greenmail.util.ServerSetupTest
import jakarta.mail.Folder
import jakarta.mail.MessagingException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender


@SpringBootTest
internal class SpringBoot3ApplicationTests {
    // See application.properties for SMTP host and user configuration
    @Autowired
    private val mailSender: JavaMailSender? = null

    @Test
    @Throws(MessagingException::class)
    fun testSendAndReceiveMail() {
        mailSender!!

        // Send email ...
        val subject = "Hello Spring 3: testReceiveMailViaImap"
        val message = SimpleMailMessage()
        message.from = "foo@localhost"
        message.setTo("foo@localhost")
        message.subject = subject
        message.text = "I'm a test"
        mailSender.send(message)

        // ... wait for delivery ...
        greenMail.waitForIncomingEmail(1)

        greenMail.imap.createStore().use { store ->
            store.connect("foo", "foo-pwd")
            val inbox = store.getFolder("INBOX")!!
            inbox.open(Folder.READ_ONLY)
            Assertions.assertThat(inbox.messageCount).isEqualTo(1)
            Assertions.assertThat(inbox.messages[0].subject).isEqualTo(subject)
        }
    }

    companion object {
        @JvmField
        @RegisterExtension
        protected val  greenMail: GreenMailExtension = GreenMailExtension(ServerSetupTest.SMTP_IMAP)
                .withConfiguration(
                    GreenMailConfiguration
                        .aConfig()
                        .withUser("foo@localhost", "foo", "foo-pwd")
                )
    }
}