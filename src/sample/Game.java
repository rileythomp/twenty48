package sample;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Game {
    public int[][] board;

    public int score;

    private GameView view;

    public Game(Stage stage) throws FileNotFoundException {
        board = new int[4][4];
        score = 0;
        view = new GameView(stage, this);

        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                board[y][x] = 0;
            }
        }

        int y = (int)(Math.random()*4);
        int x = (int)(Math.random()*4);
        board[y][x] = 2;

        while (true) {
            y = (int)(Math.random()*4);
            x = (int)(Math.random()*4);
            if (board[y][x] != 2) {
                board[y][x] = 2;
                break;
            }
        }
    }

    public void play() {
        view.displayGame();
    }

    public void moveDir(KeyCode pressed) {
        if (pressed == KeyCode.UP) {
            boolean didMove = moveUp();
            if (didMove) {
                addNew();
                view.displayGame();
            }
        }
        else if (pressed == KeyCode.RIGHT) {
            boolean didMove = moveRight();
            if (didMove) {
                addNew();
                view.displayGame();
            };
        }
        else if (pressed == KeyCode.DOWN) {
            boolean didMove = moveDown();
            if (didMove) {
                addNew();
                view.displayGame();
            }
        }
        else if (pressed == KeyCode.LEFT) {
            boolean didMove = moveLeft();
            if (didMove) {
                addNew();
                view.displayGame();
            }
        }
        checkGameOver();
    }

    private boolean moveLeft() {
        boolean didMove = false;
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (board[y][x] == 0) {
                    continue;
                }
                int curx = x;
                while (curx > 0 && board[y][curx-1] == 0) {
                    didMove = true;
                    board[y][curx-1] = board[y][curx];
                    board[y][curx] = 0;
                    curx--;
                }
                if (curx > 0 && board[y][curx-1] != 0 && board[y][curx-1] == board[y][curx]) {
                    didMove = true;
                    board[y][curx-1] = 2*board[y][curx];
                    score += board[y][curx-1];
                    board[y][curx] = 0;
                }
            }
        }
        return didMove;
    }

    private boolean moveRight() {
        boolean didMove = false;
        for (int y = 0; y < 4; ++y) {
            for (int x = 3; x >= 0; --x) {
                if (board[y][x] == 0) {
                    continue;
                }
                int curx = x;
                while (curx < 3 && board[y][curx+1] == 0) {
                    didMove = true;
                    board[y][curx+1] = board[y][curx];
                    board[y][curx] = 0;
                    curx++;
                }
                if (curx < 3 && board[y][curx+1] != 0 && board[y][curx+1] == board[y][curx]) {
                    didMove = true;
                    board[y][curx+1] = 2*board[y][curx];
                    score += board[y][curx+1];
                    board[y][curx] = 0;
                }
            }
        }
        return didMove;
    }

    private boolean moveUp() {
        boolean didMove = false;
        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                if (board[y][x] == 0) {
                    continue;
                }
                int cury = y;
                while (cury > 0 && board[cury-1][x] == 0) {
                    didMove = true;
                    board[cury-1][x] = board[cury][x];
                    board[cury][x] = 0;
                    cury--;
                }
                if (cury > 0 && board[cury-1][x] != 0 && board[cury-1][x] == board[cury][x]) {
                    didMove = true;
                    board[cury-1][x] = 2*board[cury][x];
                    score += board[cury-1][x];
                    board[cury][x] = 0;
                }
            }
        }
        return didMove;
    }

    private boolean moveDown() {
        boolean didMove = false;
        for (int x = 0; x < 4; ++x) {
            for (int y = 3; y >= 0; --y) {
                if (board[y][x] == 0) {
                    continue;
                }
                int cury = y;
                while (cury < 3 && board[cury+1][x] == 0) {
                    didMove = true;
                    board[cury+1][x] = board[cury][x];
                    board[cury][x] = 0;
                    cury++;
                }
                if (cury < 3 && board[cury+1][x] != 0 && board[cury+1][x] == board[cury][x]) {
                    didMove = true;
                    board[cury+1][x] = 2*board[cury][x];
                    score += board[cury+1][x];
                    board[cury][x] = 0;
                }
            }
        }
        return didMove;
    }

    private void checkGameOver() {
        boolean isFull = true;
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 4; ++x) {
                if (board[y][x] == 0) {
                    isFull = false;
                }
            }
        }

        if (isFull) {
            view.QuitGame(score);
        }
    }

    private void addNew() {
        while (true) {
            int y = (int)(Math.random()*4);
            int x = (int)(Math.random()*4);

            if (board[y][x] == 0) {
                board[y][x] = 2*(1 + (int)(Math.random()*2));
                break;
            }

        }
    }
}
