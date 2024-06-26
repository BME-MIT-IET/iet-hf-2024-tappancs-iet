package UITests;

import drukmakori_sivatag.Game;
import drukmakori_sivatag.MainMenu;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainMenuTest {

    @Test
    public void testNewGameButtonActionPerformed() {
        Game game = new Game();
        MainMenu mainMenu = new MainMenu(game);

        mainMenu.tfTeamCount.setText("3");
        mainMenu.tfSourceAndDrain.setText("3");
        mainMenu.tfMaxRound.setText("10");
        mainMenu.bNewGame.doClick();

        int expectedTeamCount = Integer.parseInt(mainMenu.tfTeamCount.getText());
        int expectedSourceAndDrainCount = Integer.parseInt(mainMenu.tfSourceAndDrain.getText());
        int expectedMaxRound = Integer.parseInt(mainMenu.tfMaxRound.getText());

        assertEquals(3, expectedTeamCount);
        assertEquals(3, expectedSourceAndDrainCount);
        assertEquals(10, expectedMaxRound);
    }




    @Test
    public void testTextFieldInputValidation() {
        MainMenu mainMenu = new MainMenu(new Game());

        mainMenu.tfTeamCount.setText("-2");
        mainMenu.tfSourceAndDrain.setText("abc");
        mainMenu.tfMaxRound.setText("1000");

        mainMenu.bNewGame.doClick();

        assertTrue(mainMenu.isVisible());
        assertTrue(mainMenu.getComponents()[1].isVisible());
    }
}
