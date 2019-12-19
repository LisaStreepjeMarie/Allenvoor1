package com.wemakeitwork.allenvooreen.repository;

import org.junit.Test;
import com.wemakeitwork.allenvooreen.model.Activity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ActivityRepositoryIntegrationTest {
    Activity testactivity = new Activity();

    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void createActivity(){
        testactivity.setActivityId(2);
        testactivity.setActivityName("test");
        testactivity.setActivityCategory("test");
    }

    @Test
    public void whenFindByActivityName_thenReturnActivity() {
        // given
        activityRepository.save(testactivity);

        // when
        Activity gevonden = activityRepository.findByActivityName(testactivity.getActivityName());

        // then
        Assertions.assertThat(gevonden.getActivityName())
                .isEqualTo(testactivity.getActivityName());
    }

    @Test
    public void deleteActivityTest(){
        // given
        activityRepository.save(testactivity);

        // when
        activityRepository.delete(testactivity);

        // then
        Assertions.assertThat(activityRepository.findByActivityName(testactivity.getActivityName()))
                .isEqualTo(null);
    }

}
