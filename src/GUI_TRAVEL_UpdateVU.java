import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

//combine fight with travel?

public class GUI_TRAVEL_UpdateVU extends Application {
    int fightCount = 0;
    int counter = 2;
    int introCount = 0;
    int safety = 0;
    int mainCount = 0;
    int turn = 0;
    int vsCount = 0;
    int travelCount = 0;
    boolean traveling = false;
    boolean gotName = true;
    double xOffset = 0;
    double yOffset = 0;

    String playerName = "Player";
    Character mainChar;
    Narrator narrate = new Narrator();
    BorderPane mainPane = new BorderPane();
    TextField write = new TextField();
    ProgressBar progressBar;
    Text health;
    Animation animation;
    Scene mainScene;

    Character mouse = new Character("Mouse", 5, 20);
    Character wolf = new Character("Wolf", 10, 40);
    Character bigHonkers = new Character("Big Honkers", 25, 100);
    Character ent = new Character("Ent", 20, 3);
    Character enemy;

    ArrayList<Character> characters = new ArrayList<>(Arrays.asList(mouse, wolf, bigHonkers));
    ArrayList<Character> newChar = new ArrayList<>(Arrays.asList(ent));
    Location mainLocation = new Location("Main", new Coord(0, 0), characters);
    Location newLoc = new Location("New", new Coord(20, 10), newChar);
    Location nextLoc;
    ArrayList<Location> places = new ArrayList<>(Arrays.asList(mainLocation, newLoc));

    public void start(Stage primaryStage) {
        mainLocation.canGo(newLoc);
        mainLocation.getEuclidDistance(newLoc);
        mainScene = new Scene(mainPane, 600, 900);

        Text press = new Text("PRESS ENTER TO START");
        press.setLayoutX(50);
        press.setLayoutY(400);
        mainPane.getChildren().add(press);
        press.setStyle("-fx-fill: white;" + "-fx-font-size: 40px;" + "-fx-font-family: Verdana;");

        mainPane.setPadding(new Insets(20, 20, 20, 20));
        mainPane.setStyle("-fx-background-color: #3e3e3e;");
        mainPane.getStylesheets().add("idk.css");

        mainPane.setBottom(write);
        write.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 28));
        write.setStyle("-fx-background-color: #494949;" + "-fx-text-fill: white;");
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
                }
                if (((int) narrate.think(in).getValue() == 2
                        && mainChar.getLocation().getDistance(narrate.getNewLoc()) > 0) || traveling) {
                    if (travelCount == 0) {
                        // not universal
                        nextLoc = newLoc;
                        write.clear();
                        Text travel = new Text("");
                        typeWrite("Narrator: " + narrate.think(in).getKey(), travel, 0, 40);
                        mainPane.getChildren().add(travel);
                        counter++;
                        travelCount++;
                        traveling = true;
                    } else if (isFight() || fightCount == 1) {
                        if (vsCount == 0) {
                            write.clear();
                            for (int i = 0; i < places.size(); i++) {
                                if (places.get(i).equals(mainChar.getLocation())) {
                                    enemy = places.get(i).getLives()
                                            .get((int) (Math.random() * places.get(i).getLives().size()));
                                }
                            }
                            Text yvm = new Text("");
                            typeWrite("Narrator: You vs " + enemy.getName(), yvm, 0, 40);
                            mainPane.getChildren().add(yvm);
                            counter++;
                            vsCount++;
                        } else {
                            fight(mainChar, enemy);
                        }
                    } else {
                        Text travelDist = new Text("");
                        typeWrite("Narrator: You have " + mainChar.getLocation().getDistance(nextLoc) + " steps left.",
                                travelDist, 0, 40);
                        mainPane.getChildren().add(travelDist);
                        counter++;
                        mainChar.getLocation().reducDistance(1);
                        if (mainChar.getLocation().getDistance(nextLoc) == 0) {
                            traveling = false;
                            mainChar.setLocation(nextLoc);
                            Text arrived = new Text("");
                            typeWrite("You have arrived at " + nextLoc.getName(), arrived, 0, 40);
                            counter++;
                            mainPane.getChildren().add(arrived);
                        }
                    }

                } else {
                    mainPane.getChildren().add((new Text(playerName + ":  " + write.getText())));
                    setLayout(mainPane.getChildren().get(counter), 0, 40);
                    mainPane.getChildren().get(counter)
                            .setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
                    counter++;

                    Text text = new Text("");
                    String content = "Narrator: " + narrate.think(in).getKey();
                    typeWrite(content, text, 0, 0);
                    text.setLayoutX(mainPane.getChildren().get(counter - 1).getLayoutX());
                    text.setLayoutY(mainPane.getChildren().get(counter - 1).getLayoutY() + 40);
                    write.clear();
                    counter++;
                    mainPane.getChildren().add(text);
                    check();

                    if (mainPane.getChildren().get(counter - 1).getLayoutY() > 700) {
                        down();
                    }
                }

            } else if (introCount < 4 && e.getCode() == KeyCode.ENTER) {
                if (introCount == 0 && gotName) {
                    mainPane.getChildren().remove(0);
                    counter--;
                    String introText = "Narrator: Hello, and welcome to the land of iphigenaia.\nThis will "
                            + "hopefully be an amazing experience.\nWhat is your name? "
                            + "(Please pick something wild)";
                    Text intro = new Text("");
                    typeWrite(introText, intro, 5, -740);
                    mainPane.getChildren().add(intro);
                    counter++;
                    gotName = true;
                    if(in.length() == 0){
                        gotName = false;
                    }
                    else{introCount++;}
                }else if(introCount == 0 && !gotName){
                    if(in.length()> 0){
                        introCount++;
                        Text name = new Text("");
                    typeWrite("Narrator: So your name is " + in + "? What a loser.", name, 0, 80);

                    mainChar = new Character(in, 10, 0, mainLocation, 100);
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
                    progressBar.setTranslateY(-2);
                    counter++;

                    Text money = new Text("Money: " + mainChar.getMoney());
                    money.setId("money");
                    money.setStyle("-fx-fill: white;" + "-fx-font-size: 14px;" + "-fx-font-family: Verdana;");
                    money.setLayoutX(mainPane.getChildren().get(counter - 2).getLayoutX() + 370);
                    money.setLayoutY(mainPane.getChildren().get(counter - 2).getLayoutY());
                    mainPane.getChildren().add(money);
                    counter++;
                    introCount++;
                    }
                }else if (introCount == 2) {
                    Text explain = new Text("");
                    String explainString = "Narrator: You might have realized that you can now \nsee your health and money."
                            +"You can lose health during \nbattle. Let me show you.";
                    typeWrite(explainString, explain, 0, 0);
                    explain.setLayoutX(mainPane.getChildren().get(counter - 4).getLayoutX());
                    explain.setLayoutY(mainPane.getChildren().get(counter - 4).getLayoutY() + 40);
                    mainPane.getChildren().add(explain);
                    counter++;
                    introCount++;
                }

                else if (introCount == 3) {
                    if (mainCount == 0) {
                        Text yvm = new Text("");
                        typeWrite("Narrator: You vs Mouse", yvm, 0, 85);
                        mainPane.getChildren().add(yvm);
                        counter++;
                        mainCount++;
                    } else if (mainCount == 1) {
                        fight(mainChar, mouse);
                    }
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
        if (turn == 0) {
            BorderPane fightPane = new BorderPane();
            fightPane.setStyle("-fx-background-color: #242424;");
            Stage fightStage = new Stage();
            Scene fightScene = new Scene(fightPane, 400, 300);
            Text mouseArt = new Text("           .-.(c)\n"+
                                     "  (___( )     , \" - .\n"+
                                     "          `~  ~\"\"`");
            mouseArt.setStyle("-fx-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-family: Verdana;");
            mouseArt.setLayoutX(10);
            mouseArt.setLayoutY(125);
            fightPane.getChildren().add(mouseArt);

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
            fightStage.initStyle(StageStyle.UNDECORATED);
            fightStage.setScene(fightScene);
            fightStage.show();


            double mainAtk = attacker.attack();
            Text mainAttack = new Text("");
            attackee.damageTaken((int) mainAtk);
            String mainAtkString = "Narrator: You've done " + (int) (mainAtk) + " damage";
            typeWrite(mainAtkString, mainAttack, 0, 40);
            mainPane.getChildren().add(mainAttack);
            counter++;
            turn++;
            if (attackee.getHealth() < 1) {
                introCount++;
                fightCount = 0;
                turn = 0;
                vsCount = 0;
                attackee.reset();
            }
        } else if (turn == 1) {
            double enemAtk = attackee.attack();
            Text enemAttack = new Text("");
            attacker.damageTaken((int) enemAtk);
            String enemString = "Narrator: " + attackee.getName() + "(" + (int) attackee.getHealth() + ")"
                    + " has done " + (int) enemAtk + " damage";
            typeWrite(enemString, enemAttack, 0, 40);
            mainPane.getChildren().add(enemAttack);
            progressBar.setProgress((mainChar.getHealth() - enemAtk) / mainChar.getMaxHealth());
            counter++;
            turn = 0;
            if (mainPane.getChildren().get(counter - 1).getLayoutY() > 700) {
                down();
            }
        }
    }

    public boolean isFight() {
        if (Math.random() > 0.95) {
            fightCount = 1;
            return true;
        }
        return false;
    }

    //some line divider thingy
    public void paragraphBreaker(String s){
        int lines = (int)((s.length()/45)+0.5);
        ArrayList<String> sentences = new ArrayList<>();

        
    }

    public static void main(String[] args) {
        launch(args);
    }
}