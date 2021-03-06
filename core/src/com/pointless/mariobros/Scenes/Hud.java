package com.pointless.mariobros.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pointless.mariobros.MarioBros;

/**
 * Created by brentaureli on 8/17/15.
 */
public class Hud implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    public Integer getWorldTimer() {
        return worldTimer;
    }

    //Mario score/time Tracking Variables
    public Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;

    public Integer getScore() {
        return score;
    }

    public static Integer score;
    BitmapFont font ;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label marioLabel;

    public Hud(SpriteBatch sb){
        //define our tracking variables
        worldTimer = 150;
        timeCount = 0;
        score = 0;
        font = new BitmapFont(Gdx.files.internal("jelly.fnt"));

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = font ;
        String timeAsString = Integer.toString(worldTimer);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label( timeAsString, label1Style);
        scoreLabel =new Label(String.format("%06d", score),label1Style);
        timeLabel = new Label("TIME", label1Style);
        marioLabel = new Label("TOM", label1Style);

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(marioLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        //add a second row to our table
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}
