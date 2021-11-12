package csc331.sorry;

public class GamePiece {

    private Color color;
    private int space;
    private boolean inHome;

    public Color getColor() {
        return color;
    }

    public int getSpace() {
        return space;
    }

    public boolean isInHome() {
        return inHome;
    }

    public void setSpace(int space) { this.space = space; }

    public void setInHome(boolean inHome) { this.inHome = inHome; }

}

enum Color {
    YELLOW, GREEN, RED, BLUE;
}