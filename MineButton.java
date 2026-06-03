import javax.swing.*;
import java.awt.*;

public class MineButton extends JButton{
    private boolean revealed = false;
    private int adjacentBombs = 0;
    private int row;
    private int col;
    private final int probability = (int)(Math.random() * 101) +1;
    private int bombChecker;
    private boolean bomb;
    private int rowChecker = 0;
    private int colChecker = 0;
    
    public MineButton(int row, int col){
        this.row = row;
        this.col = col;
        this.bomb = false;
    }
    public boolean isRevealed() {
    return revealed;
    }

    public void setRevealed(boolean revealed) {
    this.revealed = revealed;
    }   

    public int getBombCount() {
    return adjacentBombs;
    }   
    
    public void setBombCount(int count) {
    this.adjacentBombs = count;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getProbability(){
        return probability;
    }
    
    public boolean isBomb(){
        return bomb;
    }
    public int getNumberOfBombs(){
        return bombChecker;
    }
    public void setBomb(boolean sets){
        this.bomb = sets;
    }

}