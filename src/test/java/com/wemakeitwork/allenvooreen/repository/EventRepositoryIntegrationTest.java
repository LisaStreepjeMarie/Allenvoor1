package com.wemakeitwork.allenvooreen.repository;
import com.wemakeitwork.allenvooreen.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
class EventRepositoryIntegrationTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void whenFindByActivityName_thenReturnActivity() {
        // given
        Event testEvent = new Event();
        testEvent.setEventId(2);
        testEvent.setEventName("test");
        eventRepository.save(testEvent);

        // when
        Optional<Event> gevonden = eventRepository.findById(2);

        // then
        gevonden.ifPresent(event -> Assertions.assertThat(event.getEventName())
                .isEqualTo(testEvent.getEventName()));
    }

}