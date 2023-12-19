package de.rieckpil.courses.book.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryNoInMemoryTest {

  static PostgreSQLContainer<?> container = (PostgreSQLContainer) new PostgreSQLContainer<>("postgres:12.3")
    .withDatabaseName("test")
    .withUsername("duke")
    .withPassword("s3cret")
    .withReuse(true);

  static {
    container.start();
  }

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Autowired
  private ReviewRepository cut;

  @BeforeEach
  void beforeEach() {
    assertEquals(0, cut.count());
  }

  @Test
  @Sql(scripts = "/scripts/INIT_REVIEW_EACH_BOOK.sql")
  void shouldGetTwoReviewStatisticsWhenDatabaseContainsTwoBooksWithReview() {

  }
}
