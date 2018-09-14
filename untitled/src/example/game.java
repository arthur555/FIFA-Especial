package example;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.nio.file.Paths;

public class game extends Application {
    private Level myLevel;
    private int score1;
    private int score2;
    private Text scoreBoard;
    private Stage myStage;
    private int level = 0;
    private double directionX = 1;
    private double directionY = 2;
    private int[] playerDir;
    private ImageView[] npcs;
    private ImageView[] buffs;
    private Rectangle[] doors;
    private ImageView[] players;
    private ImageView[] enermies;
    private Group content;
    private MediaView bgm;
    private ImageView ball;
    private int[] enermySpeeds;
    private Timeline myAni;

    @Override
    public void start(Stage stage) {
        //set up stage
        myStage = stage;
        stage.setScene(setup( Default.WIDTH, Default.HEIGHT));
        stage.setTitle(Default.TITLE);
        stage.show();
        //set up animation
        var frame = new KeyFrame(Duration.millis(Default.MILLISECOND_DELAY), e -> step(Default.SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        myAni = animation;
        animation.play();
    }

    private Scene setup( int width, int height) {
        content = new Group();
        var scene = new Scene(content, width, height);
        //make a new level object
        Level level1 = new Level(level, Default.NUMOFPLAYERS, level + 2);
        myLevel = level1;
        //set up score board
        score1 = 0;
        score2 = 0;
        scoreBoard = new Text(Default.WIDTH / 2 - Default.TEXT_SPACING, Default.GENERALPADDING + Default.UPPERPADDING / 2, score1 + " : " + score2);
        scoreBoard.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        content.getChildren().add(scoreBoard);
        //setup field
        ImagePattern pattern = new ImagePattern(new Image("file:resources/field"+level+".png"));
        Rectangle field = new Rectangle(0, Default.UPPERPADDING + Default.GENERALPADDING, Default.WIDTH, Default.HEIGHT - Default.UPPERPADDING - Default.GENERALPADDING);
        field.setFill(pattern);
        content.getChildren().add(field);
        //setup ball
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(Default.BALLIMAGE));
        directionX = directionX+1/Default.MAXLEVEL;
        directionY = directionY+2/Default.MAXLEVEL;
        ball = new ImageView(image);
        ball.setX(Default.WIDTH / 2);
        ball.setY(Default.HEIGHT - Default.GENERALPADDING - Default.MIDLENGTH);
        content.getChildren().add(ball);
        //set up doors
        doors = new Rectangle[2];
        doors[0] = level1.getDoor1().getBody();
        doors[1] = level1.getDoor2().getBody();
        content.getChildren().add(doors[1]);
        content.getChildren().add(doors[0]);
        //setup players, enermies and npcs
        players = new ImageView[Default.NUMOFPLAYERS];
        enermies = new ImageView[Default.NUMOFPLAYERS+level];
        playerDir = new int[Default.NUMOFPLAYERS];
        enermySpeeds = new int[Default.NUMOFPLAYERS+level];
        npcs = new ImageView[level + 2];
        buffs = new ImageView[level + 2];
        for (int i = 0; i < Default.NUMOFPLAYERS; i++) {
            playerDir[i] = 0;
            players[i] = level1.getPlayers()[i].getBody();
            content.getChildren().add(players[i]);
        }

        for(int i = 0; i<level+Default.NUMOFPLAYERS;i++)
        {
            enermySpeeds[i] = level+2;
            enermies[i] = level1.getOpponents()[i].getBody();
            content.getChildren().add(enermies[i]);
        }

        for (int i = 0; i < level+2; i++) {
            npcs[i] = level1.getNpcs()[i].getBody();
            content.getChildren().add(npcs[i]);
        }
        //set up background musics
        Media pick = new Media(Paths.get("resources/song"+level+".mp3").toUri().toString());
        MediaPlayer player = new MediaPlayer(pick);
        bgm = new MediaView(player);
        content.getChildren().add(bgm);
        player.play();

        scene.setOnKeyPressed((e -> handleKeyInput(e.getCode())));
        scene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
        return scene;
    }

    private void step(double elapsedTime) {
        //update ball and buff(power ups) positions
        for (int i = 0; i < npcs.length; i++) {
            if (buffs[i] != null) {
                buffs[i].setY(buffs[i].getY() + 1);
            }
        }
        ball.setX(ball.getX() + Default.BALLSPEED * directionX);
        ball.setY(ball.getY() + Default.BALLSPEED * directionY);
        // detect collision with borders
        if (ball.getX() > Default.WIDTH || ball.getX() < 0) {
            directionX = 0 - directionX;
        }
        if (ball.getY() < 0 + Default.UPPERPADDING + Default.GENERALPADDING || ball.getY() > Default.HEIGHT-Default.DOORWIDTH/2) {
            directionY = 0 - directionY;
        }
        // if ball touches an npc, take away the npc and add a new buff
        for (int i = 0; i < npcs.length; i++) {
            if (npcs[i] != null && npcs[i].getBoundsInParent().intersects(ball.getBoundsInParent())) {
                buffs[i] = myLevel.getBuff((int) npcs[i].getX(), (int) npcs[i].getY(), Default.PLAYERSIZE / 3, i).getBody();
                npcs[i].setX(Default.WIDTH * 2);
                npcs[i] = null;
                content.getChildren().add(buffs[i]);
                directionY = 0- directionY;
            }
        }
        // update enermy positions and detect enermy-ball collision
        for (int i = 0; i < enermies.length; i++) {
            enermies[i].setX(enermies[i].getX() + enermySpeeds[i]);
            if (enermies[i].getX() > Default.WIDTH || enermies[i].getX() < 0) {
                enermySpeeds[i] = 0 - enermySpeeds[i];
            }
            if (enermies[i].getBoundsInParent().intersects(ball.getBoundsInParent())) {
                directionY = 0 - directionY;
            }
        }
        // update player positions
        for (int i = 0; i < Default.NUMOFPLAYERS; i++) {
            if (players[i].getX() + Default.PLAYERSPEED * playerDir[i] > 0 && players[i].getX() + Default.PLAYERSPEED * playerDir[i] < Default.WIDTH) {
                players[i].setX(players[i].getX() + Default.PLAYERSPEED * playerDir[i]);
            }
        // player-ball collision
            if (buffs[0] != null && players[i].getBoundsInParent().intersects(buffs[0].getBoundsInParent())) {
                players[i].setFitWidth(players[i].getFitWidth() + 20);
                buffs[0].setY(Default.HEIGHT + 1);
            }
        // player-power-ups detection
            if (buffs[1] != null && players[i].getBoundsInParent().intersects(buffs[1].getBoundsInParent())) {
                players[i].setFitWidth(players[i].getFitWidth() - 20);
                buffs[1].setY(Default.HEIGHT + 1);
            }

            if (players[i].getBoundsInParent().intersects(ball.getBoundsInParent())) {
                directionY = 0 - directionY;
                if(ball.getY()<players[i].getY())
                {
                    ball.setY(players[i].getY()-Default.PLAYERSIZE/2);
                }
                else{
                    ball.setY(players[i].getY()+Default.PLAYERSIZE/2);
                }

                if(playerDir[i]!=0 &&(playerDir[i]>0 ^ directionX >0)==false){
                    directionX*=1.1;
                    directionY*=1.1;
                }
                else{
                    if(playerDir[i]!= 0)
                        directionX = 0-directionX;
                }

                // myStage.setScene(new Scene(new Pane(), Color.OLIVE));
            }

            if (buffs.length >=3 && buffs[2] != null && players[i].getBoundsInParent().intersects(buffs[2].getBoundsInParent())) {
                directionX*= 0.5;
                directionX*= 0.5;
                buffs[2].setY(Default.HEIGHT + 1);
            }

            if (buffs.length >=4 &&buffs[3] != null && players[i].getBoundsInParent().intersects(buffs[3].getBoundsInParent())) {
                directionX*= 1.5;
                directionX*= 1.5;
                players[i].setFitWidth(players[i].getFitWidth() - 20);
                buffs[3].setY(Default.HEIGHT + 1);
            }


        }

        incrementScores(0);
        incrementScores(1);

        if (score1 >= Default.TARGETSCORE) {
            myAni.stop();
            interim(true);
        }
        if (score2 >= Default.TARGETSCORE)
        {
            interim(false);
        }
    }

    private void incrementScores(int index) {
        if (doors[index].getBoundsInParent().intersects(ball.getBoundsInParent())) {
            if(index == 0)
                ball.setY(doors[index].getY()+Default.DOORWIDTH);
            else
                ball.setY(doors[index].getY()-Default.DOORWIDTH);
            directionY = 0 - directionY;
            if(index == 0)
                score1++;
            else
                score2++;
            scoreBoard.setText(score1 + " : " + score2);
        }
    }

    private void pause(int sec, EventHandler e){
        PauseTransition pause = new PauseTransition(Duration.seconds(sec));
        pause.setOnFinished(e);
        pause.play();
    }

    private void interim(boolean win) {
        bgm.getMediaPlayer().stop();
        myAni.stop();
        reaction(win, level);

        pause(Default.PAUSESEC, event -> {
            if(win){
                level+=1;
                myStage.setScene(setup(Default.WIDTH,Default.HEIGHT));
                myAni.play();}
            else
            {myStage.close();}
        });
    }

    private void reaction(boolean win, int level)
    {
        Group words = new Group();
        String cong = new String();
        if(win && level < Default.MAXLEVEL)
            cong = new String("  Congratulations!! You've survived Round " + (level+1));
        else if(win)
            cong = new String("  We are the champions~ my friend");
        else
            cong = new String(new String("  oh no, you lost..."));

        Text congWords = new Text(0, Default.HEIGHT / 2, cong);
        congWords.setTextAlignment(TextAlignment.CENTER);
        congWords.setFont(Font.font("Verdana", FontWeight.BOLD, Default.GENERALPADDING));
        words.getChildren().add(congWords);

        myStage.setScene(new Scene(words, Default.WIDTH, Default.HEIGHT));
        if(win && level >= Default.MAXLEVEL)
            pause(Default.PAUSESEC, e->{myStage.close();});
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            playerDir[0] = 1;
        } else if (code == KeyCode.LEFT) {
            playerDir[0] = -1;
        } else if (code == KeyCode.DIGIT1) {
            playerDir[1] = -1;
        } else if (code == KeyCode.DIGIT2) {
            playerDir[1] = 1;
        } else if (code == KeyCode.A) {
            playerDir[2] = -1;
        } else if (code == KeyCode.D) {
            playerDir[2] = 1;
        } else if (code == KeyCode.DIGIT9)
        {
            for(int i=0; i <enermySpeeds.length;i++){
                enermySpeeds[i]=0;
            }
        } else if (code == KeyCode.DIGIT8 && enermies.length>0)
        {
            enermies[enermies.length-1].setX(Default.WIDTH*2);
            ImageView[] enermiesNew = new ImageView[enermies.length-1];
            for(int i =0; i < enermiesNew.length;i++)
            {
                enermiesNew[i] = enermies[i];
            }
            enermies = enermiesNew;
        } else if(code ==KeyCode.DIGIT7) interim(true);
    }

    private void handleKeyReleased(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            playerDir[0] = 0;
        } else if (code == KeyCode.LEFT) {
            playerDir[0] = 0;
        } else if (code == KeyCode.A) {
            playerDir[2] = 0;
        } else if (code == KeyCode.D) {
            playerDir[2] = 0;
        } else if (code == KeyCode.DIGIT1) {
            playerDir[1] = 0;
        } else if (code == KeyCode.DIGIT2) {
            playerDir[1] = 0;
        } else if (code == KeyCode.DIGIT9)
        {
            for(int i=0; i <enermySpeeds.length;i++){
                enermySpeeds[i]=level+2;
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

    //
