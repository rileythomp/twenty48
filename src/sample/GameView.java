package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class GameView {
    private Stage stage;
    private Pane root;
    private Scene scene;

    private Game game;

    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 800;
    private final int FONT_SIZE = 30;
    private final String FONT_NAME = "verdana";
    private Color BACKGROUND_COLOR = Color.rgb(250, 248, 239);;

    private HashMap<Integer, Color> colorMap = new HashMap<Integer, Color>() {{
        put(0, Color.rgb(238, 228, 218, 0.35));
        put(2, Color.rgb(238, 228, 218));
        put(4, Color.rgb(237, 224, 200));
        put(8, Color.rgb(242, 177, 121));
        put(16, Color.rgb(245, 149, 99));
        put(32, Color.rgb(246, 124, 95));
        put(64, Color.rgb(246, 94, 59));
        put(128, Color.rgb(237, 206, 114));
        put(256, Color.rgb(237, 204, 97));
        put(512, Color.rgb(237, 200, 80));
        put(1024, Color.rgb(237, 197, 63));
        put(2048, Color.rgb(237, 194, 46));
    }};

    public GameView(Stage s, Game g) {
        stage = s;
        game = g;
    }

    public void displayGame() {
        root = new Pane();
        int[][] board = game.board;

        Rectangle background = new Rectangle(20, 20, 450, 450);
        background.setFill(Color.rgb(187, 173, 160));
        root.getChildren().add(background);

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                Rectangle cell = new Rectangle(110*x + 30, 110*y + 30, 100, 100);
                cell.setFill(colorMap.getOrDefault(board[y][x], Color.rgb(60, 58, 50)));

                root.getChildren().add(cell);
                Text num = Util.CreateTextNode(
                        Integer.toString(board[y][x]),
                        FONT_NAME, FontWeight.NORMAL, Color.rgb(119, 110, 101), FONT_SIZE/2,
                        110*x + 30, 110*y + 50
                );
                root.getChildren().add(num);
            }
        }

        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                if (pressed == KeyCode.Q) {
                    QuitGame(root);
                }
                else {
                    game.moveDir(pressed);
                }
            }
        });

        stage.setScene(scene);
    }

    public void QuitGame(Pane root) {
        Rectangle rect = new Rectangle(SCREEN_WIDTH/4, SCREEN_HEIGHT/4 - 25, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        rect.setFill(Color.WHITE);
        Text quitMessage = Util.CreateTextNode(
                "Thanks for playing!",
                FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                SCREEN_WIDTH/4 + 25, SCREEN_HEIGHT/4
        );
        root.getChildren().add(rect);
        root.getChildren().add(quitMessage);

        AnimationTimer quitDelay = new AnimationTimer() {
            int counter = 0;
            @Override
            public void handle(long now) {
                counter += 1;
                if (counter > 500) {
                    System.exit(0);
                }
            }
        };
        quitDelay.start();
    }
}
