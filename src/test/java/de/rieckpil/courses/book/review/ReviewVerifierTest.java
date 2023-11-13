package de.rieckpil.courses.book.review;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ReviewVerifierTest {

  private ReviewVerifier reviewVerifier;

  @BeforeEach
  public void beforeEach(){
    System.out.println("Before each");
    reviewVerifier = new ReviewVerifier();
  }
  @Test
  void shouldFailWhenUseSwearWords() {
    String review = "I don't like this shit";
    boolean result = reviewVerifier.doesMeetQualityStandards(review);
    Assert.assertFalse(result);
  }

}
