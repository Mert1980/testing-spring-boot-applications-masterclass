package de.rieckpil.courses.book.review;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(RandomReviewParameterResolverExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class ReviewVerifierTest {

  private ReviewVerifier reviewVerifier;

  @BeforeEach
  public void beforeEach(){
    reviewVerifier = new ReviewVerifier();
  }
  @Test
  void shouldFailWhenUseSwearWords() {
    String review = "I don't like this shit";
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect swear word");
  }

  @Test
  @DisplayName("Should fail when review contains 'lorem ipsum'")
  void testLoremIpsum(){
    String review = """
      Lorem ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken.
      """;
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect lorem ipsum");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/badReview.csv")
  void shouldFailWhenReviewIsOfBadQuality(String review) {
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect bad review");
  }

  @RepeatedTest(5)
  void shouldFailWhenRandomReviewQualityIsBad(@RandomReviewParameterResolverExtension.RandomReview String review){
    System.out.println(review);
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertFalse(result, "ReviewVerifier did not detect random bad review");
  }

  @Test
  void shouldPassWhenReviewIsGood(){
    String review = """
      It is slechts een proeftekst uit het drukkerij- en zetterijwezen. It is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken.
      """;

    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    assertTrue(result, "ReviewVerifier did not pass a good review");
  }

  @Test
  void shouldPassWhenReviewIsGoodHamcrest(){
    String review = """
      It is slechts een proeftekst uit het drukkerij- en zetterijwezen. It is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken.
      """;
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    MatcherAssert.assertThat("ReviewVerifier did not pass a good review", result, Matchers.equalTo(true));
    MatcherAssert.assertThat("Lorem Ipsum", Matchers.endsWith("Ipsum"));
    MatcherAssert.assertThat(List.of(1, 2, 3, 4, 5), Matchers.hasSize(5));
    MatcherAssert.assertThat(List.of(1, 2, 3, 4, 5), Matchers.anyOf(Matchers.hasSize(5), Matchers.emptyIterable()));

  }
}
