package com.study.spring.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectorManager.getDataSource());
    }

    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();
        userDao.create(new User("woo","password", "name", "email"));

        User user = userDao.findByUserId("woo");
        assertThat(user).isEqualTo(new User("woo", "password", "name", "email"));
    }
}