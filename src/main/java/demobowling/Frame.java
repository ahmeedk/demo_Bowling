package demobowling;

public class Frame {

    private FrameTypeEnum frameType;
    private int firstThrow ;
    private int secondThrow ;


    public Frame(FrameTypeEnum frameType, int firstThrow, int secondThrow) {
        this.frameType = frameType;
        this.firstThrow = firstThrow;
        this.secondThrow = secondThrow;
    }


    public FrameTypeEnum getFrameType() {
        return frameType;
    }

    public void setFrameType(FrameTypeEnum frameType) {
        this.frameType = frameType;
    }

    public int getFirstThrow() {
        return firstThrow;
    }

    public void setFirstThrow(int firstThrow) {
        this.firstThrow = firstThrow;
    }

    public int getSecondThrow() {
        return secondThrow;
    }

    public void setSecondThrow(int secondThrow) {
        this.secondThrow = secondThrow;
    }
}
