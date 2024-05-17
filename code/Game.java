import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private MainMenu mmenu;
    private GameView gview;
    /**
     * A játék osztály konstruktora.
     * Létrehozza a főmenüt (`mmenu`) és a játék nézetét (`gview`).
     * Beállítja az ablak átméretezhetőségét és láthatóságát.
     * Elindítja a játékot a start() metódus meghívásával.
     */
    public Game(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mmenu = new MainMenu(Game.this);
                gview = new GameView(Game.this);
                setResizable(false);
                setVisible(true);
                start();
            }
        });

    }
    CardLayout cardLayout = new CardLayout();
    JPanel containerPanel = new JPanel(cardLayout);
    /**
     * A játék indítása.
     * Beállítja az ablak címét, a kilépési műveletet, és a méretet.
     * Hozzáadja a konténer panelt az ablakhoz.
     * Hozzáadja a főmenüt a konténer panelhez és megjeleníti azt.
     */
    public void start() {
        setTitle("A drukmákori sivatag vízhálózta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        add(containerPanel);
        containerPanel.add(mmenu, "menu");
        cardLayout.show(containerPanel, "menu");
    }
    /**
     * Visszatérés a főmenühöz.
     * Eltávolítja a jelenlegi tartalmat a konténer panelből, és hozzáadja a főmenüt.
     * A konténer panel újrarajzolása a változások érvényesítéséhez.
     */
    public void BackToMenu(){
        containerPanel.removeAll();
        containerPanel.add(mmenu, "menu");
        containerPanel.revalidate();
        containerPanel.repaint();
    }
    /**
     * Új játék indítása a megadott paraméterek alapján.
     *
     * @param _team_count             a csapatok létszáma
     * @param _source_and_drain_count   a források és ciszternák száma
     * @param _max_turns              a maximális körök száma
     */
    public void StartGame(int _team_count, int _source_and_drain_count, int _max_turns){
        gview.Initialize(_team_count, _source_and_drain_count, _max_turns);
        containerPanel.removeAll();
        containerPanel.add(gview, "gview");
        cardLayout.show(containerPanel, "gview");
        gview.DrawScene();
        containerPanel.repaint();
        containerPanel.revalidate();
    }

}
