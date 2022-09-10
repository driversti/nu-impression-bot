package com.github.driversti.nuimpressionbot.features.registration.cage

import static com.github.driversti.nuimpressionbot.common.Constants.REG_CAGE_PREFIX

import com.github.driversti.nuimpressionbot.UnitTest
import com.github.driversti.nuimpressionbot.building.Cage
import com.github.driversti.nuimpressionbot.features.registration.apartment.cage.RegisterCageBroadcaster
import com.github.driversti.nuimpressionbot.features.registration.apartment.cage.RegisterCageReceivedEvent
import com.pengrad.telegrambot.model.CallbackQuery
import com.pengrad.telegrambot.model.Update
import com.pengrad.telegrambot.model.User
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Subject

class RegisterCageBroadcasterUT extends UnitTest {

    private ApplicationEventPublisher publisher = Mock()
    @Subject
    private RegisterCageBroadcaster notifier = new RegisterCageBroadcaster(publisher)

    def "should not send event if expected prefixed cage was not received"(CallbackQuery callback) {
        given:
        Update update = new Update(callback_query: callback)

        when:
        notifier.handle(update)

        then:
        0 * publisher.publishEvent(_)

        where:
        callback                                                                    | _
        new CallbackQuery()                                                         | _
        new CallbackQuery(data: "")                                                 | _
        new CallbackQuery(from: new User(1L), data: REG_CAGE_PREFIX + "x") | _
    }

    def "should send event if #givenData received"(String givenData, Cage expectedCage) {
        given:
        Long ID = 12345L
        Update update = new Update(callback_query: new CallbackQuery(from: new User(ID), data: givenData))

        when:
        notifier.handle(update)

        then:
        1 * publisher.publishEvent(_ as RegisterCageReceivedEvent) >> { RegisterCageReceivedEvent e ->
            with(e) {
                chatId() == ID
                cage() == expectedCage
            }
        }

        where:
        givenData                         || expectedCage
        REG_CAGE_PREFIX + Cage.A || Cage.A
        REG_CAGE_PREFIX + Cage.B || Cage.B
        REG_CAGE_PREFIX + Cage.C || Cage.C
        REG_CAGE_PREFIX + Cage.D || Cage.D
        REG_CAGE_PREFIX + Cage.E || Cage.E
        REG_CAGE_PREFIX + Cage.F || Cage.F
        REG_CAGE_PREFIX + Cage.G || Cage.G
    }
}
