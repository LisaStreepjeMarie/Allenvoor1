package com.wemakeitwork.allenvooreen.repository;

import org.junit.Test;
import com.wemakeitwork.allenvooreen.model.Activity;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ActivityRepositoryIntegrationTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void whenFindByActivityName_thenReturnActivity() {
        // given
        Activity testactivity = new Activity();
        testactivity.setActivityId(2);
        testactivity.setActivityName("test");
        testactivity.setActivityCategory("test");
        activityRepository.save(testactivity);

        // when
        Activity gevonden = activityRepository.findByActivityName(testactivity.getActivityName());

        // then
        Assertions.assertThat(gevonden.getActivityName())
                .isEqualTo(testactivity.getActivityName());
    }

}
