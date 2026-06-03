import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Color;
public class MinesweeperGame extends JFrame {
    private final int ROWS = 15;
    private final int COLS = 15;
    private ImageIcon[] iconArray = new ImageIcon[] { null, one, two, three, four, five, six, seven, eight };
    private static boolean firstClick = true;
    private static boolean startGame = true;
    private static final ImageIcon one = new ImageIcon("one.png");
    private static final ImageIcon two = new ImageIcon("two.png");
    private static final ImageIcon three = new ImageIcon("three.png");
    private static final ImageIcon four = new ImageIcon("four.png");
    private static final ImageIcon five = new ImageIcon("five.png");
    private static final ImageIcon six = new ImageIcon("six.png");
    private static final ImageIcon seven = new ImageIcon("seven.png");
    private static final ImageIcon eight = new ImageIcon("eight.png");
    private static final ImageIcon flag = new ImageIcon("flag.png");
    private static final ImageIcon mine = new ImageIcon("mine.png");
    private static MineButton[][] buttons;

    public MinesweeperGame(){
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(ROWS, COLS));
        buttons = new MineButton[ROWS][COLS];
        int q = 0;
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                buttons[row][col] = new MineButton(row, col);
                if(q == 0){
                    buttons[row][col].setBackground(Color.CYAN);
                    q++;
                }else if(q == 1){
                    buttons[row][col].setBackground(Color.GREEN);
                    q = 0;
                }
                final int r = row;
                final int c = col; 
                buttons[row][col].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    if(e.getButton() == MouseEvent.BUTTON1){
                            handleLeftClick(r, c);
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        handleRightClick(r, c);
                    }
                }
                });
                buttons[row][col].setPreferredSize(new Dimension(35, 35));
                mainPanel.add(buttons[row][col]);
            }
        }
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void placeMines(int safeRow, int safeCol) {
        int minesToPlace = 60;
        int count = 0;
        while(count < minesToPlace){
            int r = (int)(Math.random() * ROWS);
            int c = (int)(Math.random() * COLS);
            boolean safeZone = (Math.abs(r - safeRow) <= 1 && Math.abs(c - safeCol) <= 1);
            if(!buttons[r][c].isBomb() && !safeZone){
                buttons[r][c].setBomb(true);
                count++;
            }
        }
        calculateAllBombCounts();
    }
    private void handleLeftClick(int row, int col){
        if(!startGame) return;
            if(firstClick){
                placeMines(row, col);
                firstClick = false;
            }
            if(buttons[row][col].isBomb()){
                gameOver(); 
                return;
            }
        reveal(row, col);
    }
    private void calculateAllBombCounts(){
        for(int r = 0; r < ROWS; r++){
            for(int c = 0; c < COLS; c++){
                if(!buttons[r][c].isBomb()){
                    int count = countAdjacentBombs(r, c);
                    buttons[r][c].setBombCount(count);
                }
            }
        }
    }
    private void reveal(int r, int c){
        if(r < 0 || r >= ROWS || c < 0 || c >= COLS) return;
        if(buttons[r][c].isRevealed() || buttons[r][c].isBomb()) return;
        
        buttons[r][c].setRevealed(true);
        buttons[r][c].setBackground(Color.LIGHT_GRAY);
        buttons[r][c].setOpaque(true);
        if(buttons[r][c].getBombCount() == 0){
            for(int i = r - 1; i <= r + 1; i++) {
                for(int j = c - 1; j <= c + 1; j++) {
                reveal(i, j);
                }
            }
        }else{
            buttons[r][c].setIcon(iconArray[buttons[r][c].getBombCount()]);
        }
    }
    private void gameOver(){
        startGame = false;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                buttons[i][j].setEnabled(false);
                if(buttons[i][j].isBomb()){
                    buttons[i][j].setIcon(mine);
                }
            }
        }
    }
    private void handleRightClick(int row, int col){
        buttons[row][col].setIcon(flag);
    }
    private int countAdjacentBombs(int row, int col){
        int count = 0;
        for(int i = row - 1; i <= row + 1; i++){
            for(int j = col - 1; j <= col + 1; j++){
                if(i >= 0 && i < ROWS && j >= 0 && j < COLS){
                    if(buttons[i][j].isBomb()){
                        count++;
                    }
                }
            }
        }return count;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MinesweeperGame());
    }
}