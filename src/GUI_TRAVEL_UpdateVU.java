import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

//combine fight with travel?

public class GUI_TRAVEL_UpdateVU extends Application {
    int lock = 0;
    int fightCount = 0;
    int counter = 4;
    int introCount = 0;
    int safety = 0;
    int mainCount = 0;
    int turn = 0;
    int vsCount = 0;
    int travelCount = 0;
    int distance = 0;
    boolean traveling = false;
    boolean gotName = true;
    boolean dead = false;
    double xOffset = 0;
    double yOffset = 0;
    int push = 40;

    String playerName = "Player";
    Character mainChar;
    Narrator narrate = new Narrator();
    BorderPane mainPane = new BorderPane();
    TextField write = new TextField();
    ProgressBar progressBar;
    Text health;
    Animation animation;
    Scene mainScene;

    Character mouse = new Character("Mouse", 5, 1,
            "\n" + "           .-.(c)\n" + "   (__( )     , \" - .\n" + "          `~  ~\"\"`");
    Character wolf = new Character("Wolf", 10, 4, "             /\n" + "      ,~~   /\n" + "  _  <=)  _/_\n"
            + " /I\\.=\"==.{>\n" + " \\I/-\\T/-'\n" + "     /_\\\n" + "    // \\\\_\n" + "   _I    /\n");
    Character bigHonkers = new Character("Big Honkers", 25, 1,
            "             /\n" + "      ,~~   /\n" + "  _  <=)  _/_\n" + " /I\\.=\"==.{>\n" + " \\I/-\\T/-'\n"
                    + "     /_\\\n" + "    // \\\\_\n" + "   _I    /\n");
    Character ent = new Character("Ent", 20, 3, "             /\n" + "      ,~~   /\n" + "  _  <=)  _/_\n"
            + " /I\\.=\"==.{>\n" + " \\I/-\\T/-'\n" + "     /_\\\n" + "    // \\\\_\n" + "   _I    /\n");
    Character enemy;

    ArrayList<Character> characters = new ArrayList<>(Arrays.asList(mouse, wolf, bigHonkers));
    ArrayList<Character> newChar = new ArrayList<>(Arrays.asList(ent));
    Location mainLocation = new Location("Main", new Coord(0, 0), characters);
    Location newLoc = new Location("New", new Coord(20, 10), newChar);
    Location nextLoc;
    ArrayList<Location> places = new ArrayList<>(Arrays.asList(mainLocation, newLoc));

    public void start(Stage primaryStage) {
        String musicFile = "bard.mp3"; // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        mainLocation.canGo(newLoc);
        mainScene = new Scene(mainPane, 600, 900);

        Text press = new Text("PRESS ENTER TO START");
        press.setLayoutX(50);
        press.setLayoutY(400);
        mainPane.getChildren().add(press);
        press.setStyle("-fx-fill: white;" + "-fx-font-size: 40px;" + "-fx-font-family: Verdana;");

        Button closeButton = new Button("✕");
        closeButton.setTranslateX(10);
        closeButton.setTranslateY(-40);
        closeButton.setStyle("-fx-background-color: #313131; -fx-text-fill: red; -fx-background-radius: 36em;"
                + "-fx-min-width: 30px; -fx-min-height: 30px; -fx-max-width: 30px;-fx-max-height: 30px; -fx-font-size: 10px;");
        closeButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                closeButton.setStyle("-fx-background-color: #c30808; -fx-text-fill: black; -fx-background-radius: 30em;"
                        + "-fx-min-width: 30px; -fx-min-height: 30px; -fx-max-width: 30px;-fx-max-height: 30px; -fx-font-size: 10px;");
            } else {
                closeButton.setStyle("-fx-background-color: #313131; -fx-text-fill: red; -fx-background-radius: 30em;"
                        + "-fx-min-width: 30px; -fx-min-height: 30px; -fx-max-width: 30px;-fx-max-height: 30px; -fx-font-size: 10px;");
            }
        });
        closeButton.setOnMousePressed(e -> {
            System.exit(0);
        });
        mainPane.setRight(closeButton);

        Button minimizeButton = new Button("−");
        minimizeButton.setTranslateX(500);
        minimizeButton.setTranslateY(-10);
        minimizeButton.setStyle("-fx-background-color: #313131; -fx-text-fill: yellow; -fx-background-radius: 36em;"
                + "-fx-min-width: 30px; -fx-min-height: 30px; -fx-max-width: 30px;-fx-max-height: 30px; -fx-font-size: 14px;");
        minimizeButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                minimizeButton
                        .setStyle("-fx-background-color: #cacc00; -fx-text-fill: black; -fx-background-radius: 30em;"
                                + "-fx-min-width: 30px; -fx-min-height: 30px; -fx-max-width: 30px;-fx-max-height: 30px; -fx-font-size: 10px;");
            } else {
                minimizeButton
                        .setStyle("-fx-background-color: #313131; -fx-text-fill: yellow; -fx-background-radius: 30em;"
                                + "-fx-min-width: 30px; -fx-min-height: 30px; -fx-max-width: 30px;-fx-max-height: 30px; -fx-font-size: 10px;");
            }
        });
        minimizeButton.setOnMousePressed(e -> {
            primaryStage.setIconified(true);
        });
        mainPane.setTop(minimizeButton);

        mainPane.setPadding(new Insets(20, 20, 20, 20));
        mainPane.setStyle("-fx-background-color: #3e3e3e;");
        mainPane.getStylesheets().add("idk.css");

        mainPane.setBottom(write);
        write.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 28));
        write.setStyle("-fx-background-color: #494949;" + "-fx-text-fill: white;");
        write.requestFocus();
        write.setOnKeyPressed(e -> {
            String in = write.getText();
            if (e.getCode() == KeyCode.UP) {
                if (mainPane.getChildren().get(1).getLayoutY() - mainPane.getChildren().get(5).getLayoutY() < 60) {
                    mainPane.getChildren().forEach(a -> {
                        if (a.getClass() == Text.class && a.getId() == null) {
                            a.setLayoutY(a.getLayoutY() + 85);
                            check();
                        }
                    });
                }

            } else if (safety > 0 && animation.getStatus() == Status.RUNNING) {
                animation.jumpTo(animation.getTotalDuration());

            } else if (e.getCode() == KeyCode.DOWN) {
                if (mainPane.getChildren().get(counter - 1).getLayoutY()
                        - mainPane.getChildren().get(5).getLayoutY() > 300) {
                    mainPane.getChildren().forEach(a -> {
                        if (a.getClass() == Text.class && a.getId() == null) {
                            a.setLayoutY(a.getLayoutY() - 85);
                            check();
                        }
                    });
                }

            } else if (e.getCode() == KeyCode.ENTER && introCount >= 4) {
                if (mainPane.getChildren().get(counter - 1).getLayoutY() > 700) {
                    down();
                } else if ((int) narrate.think(in).getValue() == 2 && !traveling) {
                    if (narrate.getNewLoc().toLowerCase().equals(mainChar.getLocation().getName().toLowerCase())) {
                        Text travel = new Text("");
                        typeWrite("Narrator: You're already here simp :/", travel, 0, 40);
                        mainPane.getChildren().add(travel);
                        counter++;
                    } else {
                        for (Location a : places) {
                            if (a.getName().toLowerCase().equals(narrate.getNewLoc().toLowerCase())) {
                                nextLoc = a;
                            }
                        }
                        Text travel = new Text("");
                        typeWrite("Narrator: " + narrate.think(in).getKey(), travel, 0, 40);
                        mainPane.getChildren().add(travel);
                        counter++;
                        write.clear();
                        traveling = true;
                        distance = mainChar.getLocation().getDistance(narrate.getNewLoc());
                    }
                } else if (traveling) {
                    write.clear();
                    travel(distance);
                } else {
                    mainPane.getChildren().add((new Text(playerName + ":  " + write.getText())));
                    setLayout(mainPane.getChildren().get(counter), 0, push);
                    mainPane.getChildren().get(counter)
                            .setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
                    counter++;
                    String content = "Narrator: " + narrate.think(in).getKey();
                    // typeWrite(content, text, 0, 0);
                    paragraphBreaker(content);
                    check();

                    if (mainPane.getChildren().get(counter - 1).getLayoutY() > 700) {
                        down();
                    }
                }

            } else if (introCount < 4 && e.getCode() == KeyCode.ENTER) {
                if (introCount == 0 && gotName) {
                    mainPane.getChildren().remove(0);
                    counter--;
                    mediaPlayer.play();
                    String introText = "Narrator: Hello, and welcome to the land of iphigenaia.\nThis will "
                            + "hopefully be an amazing experience.\nWhat is your name? "
                            + "(Please pick something wild)";
                    Text intro = new Text("");
                    typeWrite(introText, intro, 5, -740);
                    mainPane.getChildren().add(intro);
                    counter++;
                    gotName = false;
                } else if (introCount == 1) {
                    Text name = new Text("");
                    typeWrite("Narrator: So your name is " + in + "? What a loser.", name, 0, 80);

                    mainChar = new Character(in, 10, 0, mainLocation, 100,
                            "                   /\n" + "         ,~~   /\n" + "  _  <=)   _/_\n" + " /I\\.=\"==.{>\n"
                                    + " \\I/-\\T/-'\n" + "      /_\\\n" + "     // \\\\_\n" + "   _I    /\n");
                    playerName = mainChar.getName();

                    mainPane.getChildren().add(name);
                    write.clear();
                    counter++;

                    health = new Text(" Health: ");
                    health.setId("health");
                    health.setStyle("-fx-fill: white;" + "-fx-font-size: 14px;" + "-fx-font-family: Verdana;");
                    setLayout(health, 0, -130);
                    mainPane.getChildren().add(health);
                    counter++;

                    progressBar = new ProgressBar(1);
                    progressBar.setPrefWidth(200);
                    mainPane.setLeft(progressBar);
                    progressBar.setTranslateX(64);
                    progressBar.setTranslateY(-30);
                    counter++;

                    Text money = new Text("Money: " + mainChar.getMoney());
                    money.setId("money");
                    money.setStyle("-fx-fill: white;" + "-fx-font-size: 14px;" + "-fx-font-family: Verdana;");
                    money.setLayoutX(mainPane.getChildren().get(counter - 2).getLayoutX() + 370);
                    money.setLayoutY(mainPane.getChildren().get(counter - 2).getLayoutY());
                    mainPane.getChildren().add(money);
                    counter++;
                    introCount++;
                } else if (introCount == 2) {
                    Text explain = new Text("");
                    String explainString = "Narrator: You might have realized that you can now \nsee your health and money."
                            + "You can lose health during \nbattle. Let me show you.";
                    typeWrite(explainString, explain, 0, 0);
                    explain.setLayoutX(mainPane.getChildren().get(counter - 4).getLayoutX());
                    explain.setLayoutY(mainPane.getChildren().get(counter - 4).getLayoutY() + 40);
                    mainPane.getChildren().add(explain);
                    counter++;
                    introCount++;
                } else if (introCount == 3) {
                    if (mainCount == 0) {
                        Text yvm = new Text("");
                        typeWrite("Narrator: You vs Mouse", yvm, 0, 85);
                        mainPane.getChildren().add(yvm);
                        counter++;
                        mainCount++;
                    } else if (mainCount == 1) {
                        fight(mainChar, mouse);
                    }
                } else if (in.length() > 0) {
                    introCount++;
                }
            }
        });
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // moves text down
    public void down() {
        mainPane.getChildren().forEach(a -> {
            if (a.getClass() == Text.class && a.getId() == null) {
                a.setLayoutY(a.getLayoutY() - 85);
                check();
            }
        });
    }

    // save?
    public void save() {
    }

    public void check() {
        mainPane.getChildren().forEach(a -> {
            if (a.getClass() == Text.class) {
                if ((a.getLayoutY() >= write.getLayoutY() - 30 || a.getLayoutY() <= health.getLayoutY() + 55)
                        && a.getId() == null) {
                    a.setVisible(false);
                    ;
                } else {
                    if (a.getId() == null) {
                        a.setVisible(true);
                        ;
                    }
                }
            }
        });
    }

    public void typeWrite(String s, Text t, int x, int y) {
        animation = new Transition() {
            {
                setCycleDuration(Duration.millis(30 * s.length()));
            }

            public void interpolate(double frac) {
                int length = s.length();
                int n = Math.round(length * (float) frac);
                t.setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
                t.setText(s.substring(0, n));
            }

        };
        setLayout(t, x, y);
        animation.play();
        safety++;
    }

    public void setLayout(Node n, int x, int y) {
        n.setLayoutX(mainPane.getChildren().get(counter - 1).getLayoutX() + x);
        n.setLayoutY(mainPane.getChildren().get(counter - 1).getLayoutY() + y);
    }

    public void fight(Character attacker, Character attackee) {
        dead = false;
        write.setEditable(false);
        BorderPane fightPane = new BorderPane();
        fightPane.setStyle("-fx-background-color: #242424;");
        Stage fightStage = new Stage();
        Scene fightScene = new Scene(fightPane, 400, 300);
        fightScene.setFill(Color.TRANSPARENT);
        Text enemArt = new Text(attackee.getDesign());
        enemArt.setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
        enemArt.setLayoutX(10);
        enemArt.setLayoutY((fightScene.getHeight() - enemArt.getBoundsInLocal().getHeight()) / 2);
        fightPane.getChildren().add(enemArt);

        Text mainArt = new Text(attacker.getDesign());
        mainArt.setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
        mainArt.setLayoutX(230);
        mainArt.setLayoutY(60);
        fightPane.getChildren().add(mainArt);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), mainArt);
        TranslateTransition enemtt = new TranslateTransition(Duration.millis(500), enemArt);

        fightPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        fightPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fightStage.setX(event.getScreenX() - xOffset);
                fightStage.setY(event.getScreenY() - yOffset);
            }
        });
        Button fightButton = new Button("ATTACK!");
        fightButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-background-radius: 15;");
        fightButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fightButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 15;");
            } else {
                fightButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-background-radius: 15;");
            }
        });

        fightPane.setBottom(fightButton);
        fightButton.setTranslateX(150);
        fightButton.setTranslateY(-20);
        fightStage.initStyle(StageStyle.UNDECORATED);
        fightStage.initStyle(StageStyle.TRANSPARENT);
        fightStage.setScene(fightScene);
        fightStage.show();

        fightButton.setOnMouseClicked(e -> {
            if (tt.getStatus() == Status.STOPPED && enemtt.getStatus() == Status.STOPPED) {
                double mainAtk = attacker.attack();
                attackee.damageTaken((int) mainAtk);
                Text text = new Text((int) mainAtk + "");
                text.setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
                text.setLayoutX(170);
                text.setLayoutY(70);
                fightPane.getChildren().add(text);
                tt.setByX(-100);
                tt.setCycleCount(2);
                tt.setAutoReverse(true);
                tt.play();
                TranslateTransition damgeAnim = new TranslateTransition(Duration.millis(3000), text);
                damgeAnim.setByY(-100);
                damgeAnim.setCycleCount(1);
                damgeAnim.play();
                FadeTransition ft = new FadeTransition(Duration.millis(1000), text);
                ft.setFromValue(1.0);
                ft.setToValue(0);
                ft.setCycleCount(1);
                ft.play();
                lock++;
                tt.statusProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Status.STOPPED && lock == 1) {
                        if (attackee.getHealth() < 1) {
                            FadeTransition enemDead = new FadeTransition(Duration.millis(200), enemArt);
                            enemDead.setFromValue(1.0);
                            enemDead.setToValue(0);
                            enemDead.setCycleCount(1);
                            enemDead.play();
                            Timeline timeline = new Timeline();
                            KeyFrame key = new KeyFrame(Duration.millis(2000),
                                    new KeyValue(fightStage.getScene().getRoot().opacityProperty(), 0));
                            timeline.getKeyFrames().add(key);
                            timeline.setOnFinished((ae) -> fightStage.close());
                            timeline.play();
                            introCount++;
                            fightCount = 0;
                            turn = 0;
                            vsCount = 0;
                            attackee.reset();
                            write.setEditable(true);
                            dead = true;
                        }
                        if (!dead) {
                            double enemAtk = attackee.attack();
                            attacker.damageTaken((int) enemAtk);
                            progressBar.setProgress((mainChar.getHealth() - enemAtk) / mainChar.getMaxHealth());
                            Text enemText = new Text((int) enemAtk + "");
                            enemText.setStyle(
                                    "-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
                            enemText.setLayoutX(230);
                            enemText.setLayoutY(70);
                            fightPane.getChildren().add(enemText);
                            enemtt.setByX(100);
                            enemtt.setCycleCount(2);
                            enemtt.setAutoReverse(true);
                            enemtt.play();
                            TranslateTransition enemDamgeAnim = new TranslateTransition(Duration.millis(3000),
                                    enemText);
                            enemDamgeAnim.setByY(-100);
                            enemDamgeAnim.setCycleCount(1);
                            enemDamgeAnim.play();
                            FadeTransition enemFt = new FadeTransition(Duration.millis(1000), enemText);
                            enemFt.setFromValue(1.0);
                            enemFt.setToValue(0);
                            enemFt.setCycleCount(1);
                            enemFt.play();
                            lock--;
                        }
                    }
                });
            }
        });
    }

    public void travel(int remainingDist) {
        if (remainingDist == 0) {
            mainChar.setLocation(nextLoc);
            Text travel = new Text("");
            typeWrite("Narrator: You've reached your destination", travel, 0, 40);
            mainPane.getChildren().add(travel);
            counter++;
            traveling = false;
        } else {
            Text travel = new Text("");
            typeWrite("Narrator: " + remainingDist + " left!", travel, 0, 40);
            mainPane.getChildren().add(travel);
            counter++;
            if (isFight()) {
                for (Location a : places) {
                    if (a.equals(mainChar.getLocation())) {
                        enemy = a.getLives().get((int) (Math.random() * a.getLives().size()));
                    }
                }
                fight(mainChar, enemy);
            }
            distance--;
        }
    }

    public boolean isFight() {
        if (Math.random() > 0.75) {
            fightCount = 1;
            return true;
        }
        return false;
    }

    // some line divider thingy
    public void paragraphBreaker(String s) {
        String temp = s;
        Text narratorText = new Text("");
        String newString = "";
        int lines = (int) ((s.length() / 45) + 0.5);
        if (lines > 0) {
            for (int i = 1; i <= lines; i++) {
                newString = temp.substring((i - 1) * 52, 52 * i) + "\n" + temp.substring(52 * i, temp.length());
                temp = newString;
            }
            typeWrite(newString, narratorText, 0, 40);
            mainPane.getChildren().add(narratorText);
            write.clear();
            push = 32 * (lines + 1);
            counter++;
        } else {
            typeWrite(s, narratorText, 0, 40);
            mainPane.getChildren().add(narratorText);
            write.clear();
            push = 40;
            counter++;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}