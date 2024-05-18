package drukmakori_sivatag;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;

/**
 * A MainMenu osztály a felhasználói felület főmenüjét valósítja meg.
 * Itt lehetőség van új játék indítására vagy a játékból való kilépésre.
 * A felhasználó beállíthatja a csapatok létszámát, a források és ciszternák számát,
 * valamint a turnosok számát.
 */
public class MainMenu extends JPanel {
    private JLabel lGameName; // Játék címke
    private JPanel pinputbox; // Bemeneti doboz panel
    private Game game; // Játék objektum
    private JLabel label_team_count; // Csapatok létszámának címke
    private JLabel label_source_drain_count; // Források és ciszternák számának címke
    private JLabel label_turn_count; // Turnosok számának címke
    private JTextField tfTeamCount; // Csapatok létszámának szövegmezője
    private JTextField tfSourceAndDrain; // Források és ciszternák számának szövegmezője
    private JTextField tfMaxRound; // Turnosok számának szövegmezője
    private JButton bNewGame; // Új játék indítása gomb
    private JButton bExit; // Kilépés a játékból gomb
    private JPanel panel; // Panel
    private String alapertek;
    /**
     * MainMenu osztály konstruktora.
     *
     *Létrehozzuk a különböző komponenseket (címkék, szövegmezők, gombok)
     * Beállítjuk a megfelelő tulajdonságokat (méret, szöveg, stílus, háttérszín stb.)
     * Hozzáadjuk őket a megfelelő panelhez vagy a főmenühöz
     * Eseménykezelők beállítása a gombokhoz és a szövegmezőkhöz
     *
     * Elmenti a játék objektumot a game változóban.
     * Beállítja az alapértékét az alapertek változónak, amely jelenleg "0".
     * Beállítja a főmenü elrendezését a GridLayout segítségével, ahol 3 sor és 1 oszlop van.
     * Beállítja a háttérszínt a Color.decode("#F8C06D") segítségével.
     * Létrehozza a címkéket, a paneleket, a szövegmezőket és a gombokat.
     * Beállítja a komponensek megfelelő tulajdonságait (méret, szöveg, stílus, háttérszín stb.).
     * Hozzáadja a komponenseket a megfelelő panelhez vagy a főmenühöz.
     * Beállítja az eseménykezelőket a gombokhoz és a szövegmezőkhöz.
     * @param _game a játék objektum, amelyhez a főmenü tartozik
     */
    public MainMenu(Game _game) {
        game = _game;
        alapertek = "0";
        setLayout(new GridLayout(3, 1));
        setBackground(Color.decode("#F8C06D"));
        Font font = new Font("Arial", Font.BOLD, 20);

        lGameName = new JLabel("A drukmákori sivatag vízhálózata"); // Játék címke
        panel = new JPanel(); // Panel
        pinputbox = new JPanel() { // Bemeneti doboz panel
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(5, 5, getWidth() - 11, getHeight() - 11, 10, 10);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(5, 5, getWidth() - 11, getHeight() - 11, 10, 10);
            }
        };
        pinputbox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pinputbox.setBackground(Color.decode("#F8C06D"));
        label_team_count = new JLabel("Csapatok létszáma:"); // Csapatok létszámának címke
        label_team_count.setBackground(Color.WHITE);
        label_team_count.setFont(new Font("Serif", Font.PLAIN, 20));
        tfTeamCount = new JTextField(); // Csapatok létszámának szövegmezője
        tfTeamCount.setText(alapertek);
        tfTeamCount.setFont(font);
        tfTeamCount.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 2),
                new LineBorder(Color.WHITE, 2)
        ));
        tfTeamCount.setPreferredSize(new Dimension(pinputbox.getWidth() / 2, tfTeamCount.getHeight()));
        label_source_drain_count = new JLabel("Források és ciszternák száma:"); // Források és ciszternák számának címke
        label_source_drain_count.setBackground(Color.WHITE);
        label_source_drain_count.setFont(new Font("Serif", Font.PLAIN, 20));
        tfSourceAndDrain = new JTextField(); // Források és ciszternák számának szövegmezője
        tfSourceAndDrain.setText(alapertek);
        tfSourceAndDrain.setFont(font);
        tfSourceAndDrain.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 2),
                new LineBorder(Color.WHITE, 2)
        ));
        tfSourceAndDrain.setPreferredSize(new Dimension(pinputbox.getWidth() / 2, tfSourceAndDrain.getHeight()));
        label_turn_count = new JLabel("Turnosok száma:"); // Turnosok számának címke
        label_turn_count.setBackground(Color.WHITE);
        label_turn_count.setFont(new Font("Serif", Font.PLAIN, 20));
        tfMaxRound = new JTextField(); // Turnosok számának szövegmezője
        tfMaxRound.setText(alapertek);
        tfMaxRound.setFont(font);
        tfMaxRound.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 2),
                new LineBorder(Color.WHITE, 2)
        ));
        tfMaxRound.setPreferredSize(new Dimension(pinputbox.getWidth() / 2, tfMaxRound.getHeight()));

        setNumberOnlyFilter(tfTeamCount);
        setNumberOnlyFilter(tfSourceAndDrain);
        setNumberOnlyFilter(tfMaxRound);

        bNewGame = new JButton("Új játék indítása"); // Új játék indítása gomb
        bExit = new JButton("Kilépés a játékból"); // Kilépés a játékból gomb

        lGameName.setFont(new Font("Serif", Font.PLAIN, 45));
        lGameName.setForeground(Color.white);
        lGameName.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension buttonSize = new Dimension(200, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        bNewGame.setFont(buttonFont);
        bNewGame.setBackground(Color.WHITE);
        bNewGame.setForeground(Color.BLACK);
        bNewGame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bNewGame.setFocusPainted(false);
        bNewGame.setPreferredSize(buttonSize);

        bExit.setFont(buttonFont);
        bExit.setBackground(Color.WHITE);
        bExit.setForeground(Color.BLACK);
        bExit.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bExit.setFocusPainted(false);
        bExit.setPreferredSize(buttonSize);

        bExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        bNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBackground(Color.decode("#F8C06D"));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0); // Elhelyezési térköz beállítása
        panel.add(bNewGame, gbc);

        gbc.gridy = 1;
        panel.add(bExit, gbc);

        pinputbox.setPreferredSize(new Dimension(0, 0));
        pinputbox.setLayout(new BoxLayout(pinputbox, BoxLayout.Y_AXIS));

        pinputbox.add(label_team_count);
        pinputbox.add(tfTeamCount);
        pinputbox.add(label_source_drain_count);
        pinputbox.add(tfSourceAndDrain);
        pinputbox.add(label_turn_count);
        pinputbox.add(tfMaxRound);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        add(lGameName);
        add(pinputbox);
        add(panel);

        bNewGame.addActionListener((ActionEvent e) -> newGame());
        tfTeamCount.addActionListener((ActionEvent e) -> newGame());
        tfMaxRound.addActionListener((ActionEvent e) -> newGame());
        tfSourceAndDrain.addActionListener((ActionEvent e) -> newGame());

        tfMaxRound.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfMaxRound.getText().equals(alapertek)) {
                    tfMaxRound.setText(" ");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfMaxRound.getText().isEmpty()) {
                    tfMaxRound.setText(alapertek);
                }
            }
        });
        ((AbstractDocument) tfTeamCount.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                currentText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (currentText.isEmpty()) {
                    alapertek = "0";
                    tfTeamCount.setText(alapertek);
                    return;
                }
                try {
                    int value = Integer.parseInt(currentText);
                    if (value >= 0 && value <= 99) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
        tfTeamCount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfTeamCount.getText().equals(alapertek)) {
                    tfTeamCount.setText(" ");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfTeamCount.getText().isEmpty()) {
                    tfTeamCount.setText(alapertek);
                }
            }
        });
        ((AbstractDocument) tfMaxRound.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                currentText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (currentText.isEmpty()) {
                    alapertek = "0";
                    tfMaxRound.setText(alapertek);
                    return;
                }
                try {
                    int value = Integer.parseInt(currentText);
                    if (value >= 0 && value <= 99) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
        tfSourceAndDrain.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (tfSourceAndDrain.getText().equals(alapertek)) {
                    tfSourceAndDrain.setText(" ");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfSourceAndDrain.getText().isEmpty()) {
                    tfSourceAndDrain.setText(alapertek);
                }
            }
        });
        ((AbstractDocument) tfSourceAndDrain.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                currentText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (currentText.isEmpty()) {
                    alapertek = "0";
                    tfSourceAndDrain.setText(alapertek);
                    return;
                }
                try {
                    int value = Integer.parseInt(currentText);
                    if (value >= 0 && value <= 99) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                } catch (NumberFormatException e) {
                }
            }
        });
        bExit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }

    /**
     * Az új játék indítását végrehajtó metódus.
     * Ellenőrzi a beviteli mezőkből kapott adatok helyességét, majd meghívja a játék objektum
     * StartGame() metódusát az új játék indításához a megadott paraméterekkel.
     * Ha a beviteli mezők érvénytelen adatokat tartalmaznak, hibaüzenetek jelennek meg.
     *
     * Beolvassa a beviteli mezőkből kapott adatokat, és átalakítja őket egész számokká (teamCount, sourceAndDrainCount, maxRound).
     * Ha a beviteli mezők érvénytelen adatot tartalmaznak (nem szám), akkor megjelenik egy hibaüzenet a felhasználónak, és a metódus véget ér.
     * Ellenőrzi, hogy a teamCount értéke 2 és 5 között van-e. Ha nem, megjelenik egy hibaüzenet a felhasználónak.
     * Ellenőrzi, hogy a sourceAndDrainCount értéke 2 és 4 között van-e. Ha nem, megjelenik egy hibaüzenet a felhasználónak.
     * Ellenőrzi, hogy a maxRound értéke legalább 5. Ha nem, megjelenik egy hibaüzenet a felhasználónak.
     * Ha minden ellenőrzés sikeres, me
     */
    private void newGame(){
        {
            int teamCount ;
            int sourceAndDrainCount;
            int maxRound ;

            try {
                teamCount = Integer.parseInt(tfTeamCount.getText());
                sourceAndDrainCount = Integer.parseInt(tfSourceAndDrain.getText());
                maxRound = Integer.parseInt(tfMaxRound.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Hibás adatbevitel. Kérlek, adj meg érvényes számokat.", "Hibaüzenet", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(teamCount > 5 || teamCount < 2){
                JOptionPane.showMessageDialog(this,"A csapatok létszáma 2 és 5 között kell lennie","Hibaüzenet",JOptionPane.ERROR_MESSAGE);
            } else if (sourceAndDrainCount>4 || sourceAndDrainCount < 2){
                JOptionPane.showMessageDialog(this,"A ciszterna és a forrás számának 2 és 4 között kell lennie","Hibaüzenet",JOptionPane.ERROR_MESSAGE);
            }else if (maxRound < 5 ){
                JOptionPane.showMessageDialog(this,"A körök számának 5 és 99 között kell lennie","Hibaüzenet",JOptionPane.ERROR_MESSAGE);
            }else{
                game.StartGame(teamCount, sourceAndDrainCount, maxRound);
            }
        }
    }
    /**
     * A megadott JTextField komponenshez hozzárendeli a számokat és a ":" karaktert fogadó dokumentumszűrőt.
     * Ez azt jelenti, hogy a mezőben csak számok és ":" karakter szerepelhetnek, minden más karakter beillesztése vagy cseréje
     * el lesz utasítva.
     *
     * Létrehoz egy AbstractDocument objektumot, ami az adott JTextField dokumentumát reprezentálja.
     * Beállítja a dokumentumszűrőt a DocumentFilter anonim osztály egy példányával, ami felülírja az insertString és a replace metódusokat.
     * Az insertString metódusban ellenőrzi, hogy a beillesztett string csak számokat és a ":" karaktert tartalmazza. Ha igen, hívja meg az ősosztály insertString metódusát.
     * A replace metódusban ellenőrzi, hogy a cserélt szöveg csak számokat és a ":" karaktert tartalmazza. Ha igen, hívja meg az ősosztály replace metódusát.
     * @param textField a JTextField komponens, amelyhez a dokumentumszűrőt hozzá kell rendelni
     */
    private void setNumberOnlyFilter(JTextField textField) {
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Csak számokat és ":" karaktert fogadunk el az insertString metódusban
                if (string.matches("[\\d:]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Csak számokat és ":" karaktert fogadunk el a replace metódusban
                if (text.matches("[\\d:]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }
}