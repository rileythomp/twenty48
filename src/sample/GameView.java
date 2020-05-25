package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
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
    private final Color BACKGROUND_COLOR = Color.rgb(250, 248, 239);;
    private final Color TEXT_COLOR = Color.rgb(119, 110, 101);

    private final int CELL_LEN = 100;
    private final int CELL_GAP = 10;
    private final int BOARD_LEN = CELL_GAP + 4*(CELL_LEN+CELL_GAP);
    private final int TOPLEFT_X = (SCREEN_WIDTH - BOARD_LEN)/2;
    private final int TOPLEFT_Y = 100;

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

    private int shiftBack(int val) {
        int x = val/10;

        if (x < 1) {
            return 0;
        }
        else if (x < 10) {
            return 5;
        }
        else if (x < 100) {
            return 10;
        }
        else {
            return 15;
        }
    }

    public void displayGame() {
        root = new Pane();
        int[][] board = game.board;

        Rectangle background = new Rectangle(TOPLEFT_X, TOPLEFT_Y, BOARD_LEN, BOARD_LEN);
        background.setFill(Color.rgb(187, 173, 160));
        background.setArcHeight(15);
        background.setArcWidth(15);
        root.getChildren().add(background);

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                Rectangle cell = new Rectangle((CELL_LEN+CELL_GAP)*x + TOPLEFT_X + CELL_GAP, (CELL_LEN+CELL_GAP)*y + TOPLEFT_Y + CELL_GAP, CELL_LEN, CELL_LEN);
                cell.setFill(colorMap.getOrDefault(board[y][x], Color.rgb(60, 58, 50)));
                cell.setArcHeight(5);
                cell.setArcWidth(5);

                root.getChildren().add(cell);
                Text num = Util.CreateTextNode(
                        Integer.toString(board[y][x]),
                        FONT_NAME, FontWeight.NORMAL, TEXT_COLOR, (int)(FONT_SIZE*0.75),
                        (CELL_LEN+CELL_GAP)*x + TOPLEFT_X  + CELL_LEN/2 - shiftBack(board[y][x]), (CELL_LEN+CELL_GAP)*y + TOPLEFT_Y + CELL_GAP + CELL_LEN/2 + 5
                );
                root.getChildren().add(num);
            }
        }

        Text gameTitle = Util.CreateTextNode(
                "2048",
                FONT_NAME, FontWeight.BOLD, TEXT_COLOR, FONT_SIZE,
                350, 64
        );
        root.getChildren().add(gameTitle);

        Text scoreDisplay = Util.CreateTextNode(
                "Score: " + Integer.toString(game.score),
                FONT_NAME, FontWeight.NORMAL, TEXT_COLOR, FONT_SIZE/2,
                TOPLEFT_X, 600
        );
        root.getChildren().add(scoreDisplay);

        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND_COLOR);

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                if (pressed == KeyCode.Q) {
                    System.exit(0);
                }
                else {
                    game.moveDir(pressed);
                }
            }
        });

        stage.setScene(scene);
    }

    public void QuitGame(int score) {
        Rectangle rect = new Rectangle(SCREEN_WIDTH/4, SCREEN_HEIGHT/4 - 70, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        rect.setFill(BACKGROUND_COLOR);
        Text quitMessage = Util.CreateTextNode(
                "Game over! Final score: " + Integer.toString(score) + "\n\n" +
                "q - Quit Game\n\n" +
                "r - Play again\n\n",
                FONT_NAME, FontWeight.NORMAL, Color.BLACK, FONT_SIZE/2,
                SCREEN_WIDTH/4 + 25, SCREEN_HEIGHT/4 - 40
        );
        root.getChildren().add(rect);
        root.getChildren().add(quitMessage);

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                if (pressed == KeyCode.Q) {
                    System.exit(0);
                }
                else if (pressed == KeyCode.R) {
                    Game twenty48 = null;
                    try {
                        twenty48 = new Game(stage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    twenty48.play();
                }
            }
        });
    }
}
