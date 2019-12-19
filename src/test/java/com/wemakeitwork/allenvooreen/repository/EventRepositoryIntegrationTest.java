package com.wemakeitwork.allenvooreen.repository;
import com.wemakeitwork.allenvooreen.model.Activity;
import com.wemakeitwork.allenvooreen.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryIntegrationTest {
    Event testEvent = new Event();

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void createEvent(){
        testEvent.setEventId(2);
        testEvent.setEventName("test");
    }

    @Test
    public void whenFindByEventName_thenReturnEvent() {
        // given
        eventRepository.save(testEvent);

        // when
        Optional<Event> gevonden = eventRepository.findById(2);

        // then
        gevonden.ifPresent(event -> Assertions.assertThat(event.getEventName())
                .isEqualTo(testEvent.getEventName()));
    }


    @Test
    public void deleteEventTest(){
        // given
        eventRepository.save(testEvent);

        // when
        eventRepository.delete(testEvent);

        // then
        Assertions.assertThat(eventRepository.findById(2))
                .isEqualTo(Optional.empty());
    }

}