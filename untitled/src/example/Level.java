package example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import example.Default;
import java.util.Random;

public class Level {
    private int difficulty;
    private Door door1;
    private Door door2;
    private Player[] players;
    private Player[] enermies;
    private NPC[] npcs;



    Level(int difficulty, int num_players, int num_npc)
    {
        this.difficulty = difficulty;
        Random random = new Random();

        players = new Player[num_players];
        enermies = new Player[num_players+difficulty];
        npcs = new NPC[num_npc];

        door1 = new Door(Default.WIDTH/2,Default.UPPERPADDING+Default.GENERALPADDING,Default.DOORLENGTH-difficulty*Default.SHRINKAGE);
        door2 = new Door(Default.WIDTH/2,Default.HEIGHT-Default.GENERALPADDING/2-1,Default.DOORLENGTH-difficulty*Default.SHRINKAGE);

        for(int i = 0; i<num_players+difficulty; i++){
            enermies[i] = new Player(random.nextInt(Default.WIDTH),Default.UPPERPADDING+Default.GENERALPADDING+Default.MIDLENGTH/(1+num_players+difficulty)*(i+1), Default.PLAYERSIZE,i+Default.NUMOFPLAYERS);
        }
        for(int i = 0; i<num_players; i++){
            players[i] = new Player(Default.WIDTH/2,Default.HEIGHT/2+Default.HEIGHT/2/(num_players+1)*(i+1), Default.PLAYERSIZE,i);
        }

        for(int i = 0; i<num_npc; i++){
            npcs[i] = new NPC(random.nextInt(Default.WIDTH),Default.MIDLENGTH/num_npc*(i+1), Default.BUFFSIZE,i);
        }
    }

    Player[] getPlayers(){
        return players;
    }

    Player[] getOpponents(){
        return enermies;
    }

    Door getDoor1(){
        return door1;
    }

    Door getDoor2(){
        return door2;
    }

    NPC[] getNpcs(){
        return npcs;
    }

    protected class Door{
        Rectangle body;
        Door(int center, int y, double length)
        {
            body = new Rectangle();
            body.setX((double)center - length/2);
            body.setY(y);
            body.setWidth(length);
            body.setHeight(Default.DOORWIDTH);
        }

        Rectangle getBody(){
            return body;
        }
    }

    protected class Player{
        ImageView body;
        Player(int x, int y, int size, int order)
        {
            var image = new Image(this.getClass().getClassLoader().getResourceAsStream("player"+order+".png"));
            this.body = new ImageView(image);
            this.body.setX(x);
            this.body.setY(y);
            body.setFitHeight(size/3);
            body.setFitWidth(size);
        }

        ImageView getBody(){
            return body;
        }

    }

    protected class NPC{
        ImageView body;
        NPC(int x, int y, int size,int order)
        {
            var image = new Image(this.getClass().getClassLoader().getResourceAsStream("npc"+order+".png"));
            this.body = new ImageView(image);
            this.body.setX(x);
            this.body.setY(y);
            body.setFitHeight(size);
            body.setFitWidth(size);
        }
        ImageView getBody(){
            return body;
        }

    }

    Buff getBuff(int x, int y, int size, int order)
    {
        return new Buff(x,y,size, order);
    }

    protected class Buff{
        ImageView body;
        Buff(int x, int y, int size,int order)
        {
            var image = new Image(this.getClass().getClassLoader().getResourceAsStream("buff"+(0+order)+".gif"));
            this.body = new ImageView(image);
            this.body.setX(x);
            this.body.setY(y);
            body.setFitHeight(size);
            body.setFitWidth(size);
        }
        ImageView getBody(){
            return body;
        }

    }

}






