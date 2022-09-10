package com.github.driversti.nuimpressionbot.notifiers

import com.github.driversti.nuimpressionbot.UnitTest
import com.github.driversti.nuimpressionbot.features.start.StartCommandBroadcaster
import com.github.driversti.nuimpressionbot.events.StartCommandReceivedEvent
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Subject

class StartCommandBroadcasterUT extends UnitTest {

    private ApplicationEventPublisher applicationEventPublisher = Mock(ApplicationEventPublisher)
    @Subject
    private StartCommandBroadcaster publisher = Spy(new StartCommandBroadcaster(applicationEventPublisher))

    def "should send event when received '/start' command"() {
        given:
        Update update = new Update(message: new Message(text: "/start"))

        when:
        publisher.handle(update)

        then:
        1 * publisher.broadcast(_ as StartCommandReceivedEvent)
    }

    def "should not send event when command is #command"(String command) {
        given:
        Update update = new Update(message: new Message(text: command))

        when:
        publisher.handle(update)

        then:
        0 * publisher.broadcast(_ as StartCommandReceivedEvent)

        where:
        command      | _
        "/not_start" | _
        null         | _
    }

    def "should not send event when Message is not present"() {
        given:
        Update update = new Update()

        when:
        publisher.handle(update)

        then:
        0 * publisher.broadcast(_ as StartCommandReceivedEvent)
    }
}
