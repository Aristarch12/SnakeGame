package oop.snakegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import oop.snakegame.cells.Cell;
import oop.snakegame.controllers.IGameController;
import oop.snakegame.controllers.KeyboardPlayerController;
import oop.snakegame.controllers.MouseRedactorController;
import oop.snakegame.primitives.Direction;

import java.util.*;

public class Main extends Application {

    private final static int tickTime = 400;
    private final static String levelFileName = "level.txt";
    private final static List<Paint> colors = new ArrayList<Paint>() {{
        add(Color.BLUE);
        add(Color.RED);
        add(Color.YELLOW);
    }};

    private static PlayerAction getSetDirectionAction(Direction direction){
        return (player) -> player.getSnake().setNextHeadDirection(direction);
    }

    private static final PlayerAction reverseAction = (player) -> player.getSnake().reverse();

    private final static HashMap<KeyCode, PlayerAction> arrowsKeyMap = new HashMap<KeyCode, PlayerAction>() {
        {
            put(KeyCode.LEFT, getSetDirectionAction(Direction.Left));
            put(KeyCode.RIGHT, getSetDirectionAction(Direction.Right));
            put(KeyCode.UP, getSetDirectionAction(Direction.Up));
            put(KeyCode.DOWN, getSetDirectionAction(Direction.Down));
            put(KeyCode.ENTER, reverseAction);
        }
    };

    private final static HashMap<KeyCode, PlayerAction> adwsKeyMap = new HashMap<KeyCode, PlayerAction>() {
        {
            put(KeyCode.A, getSetDirectionAction(Direction.Left));
            put(KeyCode.D, getSetDirectionAction(Direction.Right));
            put(KeyCode.W, getSetDirectionAction(Direction.Up));
            put(KeyCode.S, getSetDirectionAction(Direction.Down));
            put(KeyCode.Q, reverseAction);
        }
    };

    private final static HashMap<KeyCode, PlayerAction> jlikKeyMap = new HashMap<KeyCode, PlayerAction>() {
        {
            put(KeyCode.J, getSetDirectionAction(Direction.Left));
            put(KeyCode.L, getSetDirectionAction(Direction.Right));
            put(KeyCode.I, getSetDirectionAction(Direction.Up));
            put(KeyCode.K, getSetDirectionAction(Direction.Down));
            put(KeyCode.U, reverseAction);
        }
    };

    private final static List<HashMap<KeyCode, PlayerAction>> collectionKeyMap = new ArrayList<HashMap<KeyCode, PlayerAction>>() {{
        add(adwsKeyMap);
        add(arrowsKeyMap);
        add(jlikKeyMap);
    }};

    private final static int playersCount = 3;
    private int getMaxCountPlayers() {
        return collectionKeyMap.size();
    }
    private final static int cellSize = 15;
    private Game game;
    private GraphicsContext gc;
    private Timer timer = new Timer();
    private IGameController[] controllers;
    private Painter painter;

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game(playersCount);
        controllers = createControllers(game.players);
        game.setControllers(controllers);
        game.loadLevel(LevelCreator.create(levelFileName));
        Field field = game.getLevel().field;
        setUpStage(primaryStage, field.width * cellSize, field.height * cellSize);
        scheduleGameTimer();
    }

    private IGameController[] createControllers(Player[] players) {
        List<IGameController> controllers = new ArrayList<>(playersCount);
        for (int i = 0; i < playersCount; i++) {
            KeyboardPlayerController controller = new KeyboardPlayerController(players[i]);
            controller.setKeyMap(collectionKeyMap.get(i));
            controllers.add(controller);
        }
        controllers.add(new MouseRedactorController(cellSize));
        return controllers.toArray(new IGameController[controllers.size()]);
    }

    private void setUpStage(Stage primaryStage, int width, int height) {
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        painter = new Painter(gc, createSnakeIdToColorMap(), cellSize);
        Scene scene = new Scene(root);
        addHandlers(scene, controllers);
        primaryStage.setTitle("Змейка");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addHandlers(Scene scene, IGameController[] controllers) {
        for(IGameController controller: controllers)  {
            if (controller instanceof  KeyboardPlayerController)
                    scene.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler<? super KeyEvent>) controller);
            if (controller instanceof MouseRedactorController)
                    scene.addEventHandler(MouseEvent.MOUSE_PRESSED, (EventHandler<? super MouseEvent>) controller);
        }
    }


    private HashMap<Integer, Paint> createSnakeIdToColorMap() {
        HashMap<Integer, Paint> idToColor = new HashMap<>();
        Snake[] snakes =  game.getLevel().getSnakes();
        for (int i = 0; i < snakes.length; i++) {
            idToColor.put(snakes[i].id, colors.get(i));
        }
        return idToColor;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void scheduleGameTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                game.tick();
                Platform.runLater(() -> repaint());
                if (game.getState() == GameState.Finished) {
                    timer.cancel();
                }
            }
        }, 0, tickTime);
    }

    private void repaint() {
        Canvas canvas = gc.getCanvas();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (game.getState() == GameState.Finished) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font(40));
            gc.fillText("Game Over", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2);
            return;
        }
        for (Cell cell : game.getLevel()) {
            cell.accept(painter);
        }
    }
}
