package connectfour;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Connect Four Game");
        ConnectFour game = new ConnectFour();
        game.play();
    }
}

