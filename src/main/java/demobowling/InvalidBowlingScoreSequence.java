package demobowling;

public class InvalidBowlingScoreSequence  extends Exception{


    private static final long serialVersionUID = 1L;
    public InvalidBowlingScoreSequence(String message){
        super(message);
    }
}
