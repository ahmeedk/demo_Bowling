package demobowling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class BowlingScoringTest {


    BowlingScoring bowlingScoring = new BowlingScoring();

    /**
     * testing invalid sequence
     *
     * @param seq
     */
    @ParameterizedTest
    @MethodSource("invalidScoreSequences")
    public void inValidSequence(String[] seq) {
        Assertions.assertThrows(InvalidBowlingScoreSequence.class, () -> bowlingScoring.getScore(seq));
    }

    static Stream<Arguments> invalidScoreSequences() {
        return Stream.of(
                Arguments.arguments((Object) new String[]{"X", "X", "X", "X", "X"}), // less than 10
                Arguments.arguments((Object) new String[]{"X", "X", "X", "X", "X", "X", "X", "X", "X", "X"}), // 10 but should be 12
                Arguments.arguments((Object) new String[]{"X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"}), // 11 but should be 12
                Arguments.arguments((Object) new String[]{"X", "X", "X", "X", "X", "X", "X", "Z", "X", "X", "X", "X"}), // contains Z
                Arguments.arguments((Object) new String[]{"X", "X", "X", "X", "X", "X", "X", "13", "4", "2", "/", "3", "/", "X"}), // contains 13
                Arguments.arguments((Object) new String[]{"X", "X", "X", "X", "X", "X", "X", "3", "2", "/", "3", "/", "X"}), // contains D
                Arguments.arguments((Object) new String[]{"1", "6", "4", "/", "X", "3", "6", "X", "4", "4", "8", "/", "5", "/", "5", "/", "5", "/", "/"}), // 11 and ends by /
                Arguments.arguments((Object) new String[]{"1", "6", "0", "/", "X", "3", "6", "X", "4", "4", "8", "/", "5", "/", "5", "/", "5", "/", "7"}), // contains 0 /
                Arguments.arguments((Object) new String[]{"1", "1", "2", "3", "4", "5", "6", "7", "8", "9", "1", "1", "2", "3", "4", "5", "6", "7", "8", "9"}), // sum of frame throws bigger than 10
                Arguments.arguments((Object) new String[]{"-", "-", "-", "/", "-", "/"})
        );
    }

    /**
     * testing valid sequence
     *
     * @throws Exception
     */
    @ParameterizedTest
    @MethodSource("validScoreSequences")
    public void validSequence(int score, String[] seq) throws Exception {
        System.out.println(Arrays.toString(seq));
        Assertions.assertEquals(score, bowlingScoring.getScore(seq));
    }

    static Stream<Arguments> validScoreSequences() {
        return Stream.of(
                Arguments.arguments(300, new String[]{"X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"}),
                Arguments.arguments(278, new String[]{"X", "X", "X", "X", "X", "X", "X", "X", "X", "8", "/", "X"}),
                Arguments.arguments(218, new String[]{"X", "X", "X", "X", "-", "-", "X", "X", "X", "X", "8", "/", "X"}),
                Arguments.arguments(90, new String[]{"9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-"}),
                Arguments.arguments(150, new String[]{"5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5"}),
                Arguments.arguments(0, new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}),
                Arguments.arguments(4, new String[]{"4", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}),
                Arguments.arguments(6, new String[]{"-", "-", "-", "6", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"}),
                Arguments.arguments(9, new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "9"}),
                Arguments.arguments(30, new String[]{"1", "3", "-", "-", "-", "-", "-", "6", "-", "8", "-", "-", "5", "-", "-", "-", "-", "-", "-", "7"}),
                Arguments.arguments(95, new String[]{"5", "/", "X", "-", "/", "1", "6", "-", "8", "4", "3", "5", "-", "X", "-", "-", "2", "5"}),
                Arguments.arguments(103, new String[]{"5", "/", "X", "-", "/", "1", "6", "-", "8", "4", "3", "5", "-", "X", "-", "-", "2", "/", "5"}),
                Arguments.arguments(107, new String[]{"5", "/", "X", "-", "/", "1", "6", "-", "8", "4", "3", "5", "-", "X", "-", "-", "X", "5", "4"})
        );
    }

    @Test
    public void simpleTest_score() throws Exception {
        String[] seq = {"-", "1", "2", "3", "4", "5", "1", "7", "-", "9", "-", "1", "2", "3", "4", "5", "6", "2", "8", "-"};
        Assertions.assertEquals(63, bowlingScoring.getScore(seq));
    }

    @Test
    public void shouldReturnScore_300() throws Exception {
        String[] seq = {"X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"};
        Assertions.assertEquals(300, bowlingScoring.getScore(seq));
    }

    @Test
    public void shouldReturnScore_90() throws Exception {
        String[] seq = {"9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-", "9", "-"};
        Assertions.assertEquals(90, bowlingScoring.getScore(seq));
    }

    @Test
    public void shouldReturnScore_150() throws Exception {
        String[] seq = {"5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5", "/", "5"};
        Assertions.assertEquals(150, bowlingScoring.getScore(seq));
    }

    @Test
    public void simpleTest_invalidSequence() throws Exception {
        // sum of two throw is bigger than 10 , frame 4 : 6+7=13
        String[] seq = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Assertions.assertThrows(InvalidBowlingScoreSequence.class, () -> bowlingScoring.getScore(seq));
    }

}
