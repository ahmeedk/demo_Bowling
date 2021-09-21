package demobowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoring {

    public int getScore(String[] rollsSequence) throws InvalidBowlingScoreSequence {

        int totalScore = 0;

        List<Frame> frames = extractFrames(rollsSequence);
        System.out.println("Roll sequence is valid");

        int i = 0;
        while (i < frames.size()) {

            Frame f = frames.get(i);
            int frameScore = f.getFirstThrow() + f.getSecondThrow();

            if (i < 9) {
                if (f.getFrameType().equals(FrameTypeEnum.SPARE)) {
                    frameScore = frameScore + frames.get(i + 1).getFirstThrow();
                } else if (f.getFrameType().equals(FrameTypeEnum.STRIKE)) {
                    if (frames.get(i + 1).getFrameType().equals(FrameTypeEnum.STRIKE)) {
                        frameScore = frameScore + frames.get(i + 1).getFirstThrow() + frames.get(i + 2).getFirstThrow();
                    } else {
                        frameScore = frameScore + frames.get(i + 1).getFirstThrow() + frames.get(i + 1).getSecondThrow();
                    }
                }
            }

            System.out.println("Score at frame " + i + " = " + frameScore);
            totalScore = totalScore + frameScore;
            i++;
        }
        return totalScore;
    }

    private List<Frame> extractFrames(String[] seq) throws InvalidBowlingScoreSequence {

        List<Frame> frames = new ArrayList<>();

        int i = 0;
        while (i < seq.length) {
            Frame f;
            String s = seq[i];
            if (s.equals("X")) {
                f = new Frame(FrameTypeEnum.STRIKE, 10, 0);
            } else {
                i++;
                f = getFrame(s, seq[i]);
            }

            frames.add(f);
            i++;

            if (frames.size() == 10) {
                break;
            }
        }

        if (frames.size() < 10) {
            throw new InvalidBowlingScoreSequence("Bowling score sequence not valid, sequence should contains at least 10 frames");
        }

        boolean strikeCondition = frames.get(9).getFrameType().equals(FrameTypeEnum.STRIKE) && i + 2 != seq.length;
        boolean spareCondition = frames.get(9).getFrameType().equals(FrameTypeEnum.SPARE) && i + 1 != seq.length;
        boolean openCondition = frames.get(9).getFrameType().equals(FrameTypeEnum.OPEN) && i != seq.length;

        if (strikeCondition || spareCondition || openCondition) {
            throw new InvalidBowlingScoreSequence("Bowling score sequence length is not valid, ");
        }

        while (i < seq.length) {
            int score = getThrowScore(seq[i]);
            frames.add(new Frame(FrameTypeEnum.OPEN, score, 0));
            i++;
        }

        return frames;
    }

    private Frame getFrame(String s1, String s2) throws InvalidBowlingScoreSequence {

        // check first string
        int firstThrow = 0;
        int secondThrow = 0;
        FrameTypeEnum frameType;
        if (s1.equals("-")) {
            firstThrow = 0;
        } else if (isNumericBetweenOneAndNine(s1)) {
            firstThrow = Integer.parseInt(s1);
        } else {
            throw new InvalidBowlingScoreSequence("Character not expected : " + s1);
        }
        // check second string
        if (s2.equals("-")) {
            frameType = FrameTypeEnum.OPEN;
        } else if (isNumericBetweenOneAndNine(s2)) {
            secondThrow = Integer.parseInt(s2);
            frameType = FrameTypeEnum.OPEN;
        } else if (s2.equals("/")) {
            secondThrow = 10 - firstThrow;
            frameType = FrameTypeEnum.SPARE;
        } else {
            throw new InvalidBowlingScoreSequence("Unexpected character : " + s2);
        }

        int sumOfTwoThrow = firstThrow + secondThrow;
        if (!s2.equals("/") && sumOfTwoThrow > 9) {
            throw new InvalidBowlingScoreSequence("Sum of the two throw bigger than 9 : " + sumOfTwoThrow);
        }
        return new Frame(frameType, firstThrow, secondThrow);
    }

    private int getThrowScore(String s) throws InvalidBowlingScoreSequence {
        int score;
        if (s.equals("-")) {
            score = 0;
        } else if (s.equals("X")) {
            score = 10;
        } else if (isNumericBetweenOneAndNine(s)) {
            score = Integer.parseInt(s);
        } else {
            throw new InvalidBowlingScoreSequence("Unexpected character : " + s);
        }
        return score;
    }

    private boolean isNumericBetweenOneAndNine(String str) {
        try {
            int n = Integer.parseInt(str);
            if (n > 0 && n < 10) {
                return true;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return false;
    }
}
