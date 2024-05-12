import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGTH;
    int player1;
    int player2;

    score(int GAME_WIDTH,int GAME_HEIGTH){
        score.GAME_HEIGTH=GAME_HEIGTH;
        score.GAME_WIDTH=GAME_WIDTH;

    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Concolas",Font.PLAIN,60));

        g.drawLine(GAME_WIDTH/2,0,GAME_WIDTH/2,GAME_WIDTH);
        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10),(GAME_WIDTH/2)-85,50 );
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10),(GAME_WIDTH/2)+20,50 );
    }
}
