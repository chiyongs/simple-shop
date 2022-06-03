package com.ssg.techrookie.backend.domain.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_성공() {
        //given
        String userName = "testUser";
        UserType userType = UserType.일반;
        User user = User.builder()
                .userName(userName)
                .userType(userType)
                .build();

        //when
        testEntityManager.persist(user);

        //then
        User findUser = userRepository.findById(user.getId()).get();

        assertThat(findUser).isEqualTo(user);
        assertThat(findUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(findUser.getUserType()).isEqualTo(user.getUserType());
        assertThat(findUser.getUserStat()).isEqualTo(user.getUserStat());
    }
}