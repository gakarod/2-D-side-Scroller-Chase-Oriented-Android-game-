package com.pointless.mariobros.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pointless.mariobros.Scenes.Controller;
import com.pointless.mariobros.Sprites.Enemies.Enemy;
import com.pointless.mariobros.Sprites.Items.ItemDef;
import com.pointless.mariobros.Sprites.Mario;
import com.pointless.mariobros.Tools.B2WorldCreator;
import com.pointless.mariobros.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by brentaureli on 8/14/15.
 */
public class PlayScreen extends ApplicationAdapter implements Screen {
    //Reference to our Game, used to set Screens
    private Game game;
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;
    public static final float SHOOT_WAIT_TIME = 0.3f;
    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private com.pointless.mariobros.Scenes.Hud hud;
    private com.pointless.mariobros.Tools.Adhandler adhandler ;
    float shootTimer;
    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;


    //sprites
    private Mario player;

    private Music music;

    private Array<com.pointless.mariobros.Sprites.Items.Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    Controller controller;

    public PlayScreen(Game game, com.pointless.mariobros.Tools.Adhandler adhandler   ){
        atlas = new TextureAtlas("tom_jerry.pack");
        this.adhandler = adhandler;
        this.game = game;
        //create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect rati o despite screen size
        gamePort = new FitViewport(com.pointless.mariobros.MarioBros.V_WIDTH / com.pointless.mariobros.MarioBros.PPM, com.pointless.mariobros.MarioBros.V_HEIGHT / com.pointless.mariobros.MarioBros.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new com.pointless.mariobros.Scenes.Hud(com.pointless.mariobros.MarioBros.batch);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / com.pointless.mariobros.MarioBros.PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        controller = new Controller(com.pointless.mariobros.MarioBros.batch);


        //create mario in our game world
        player = new Mario(this);
        creator = new B2WorldCreator(this,player);
        world.setContactListener(new WorldContactListener());

        items = new Array<com.pointless.mariobros.Sprites.Items.Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }


    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == com.pointless.mariobros.Sprites.Items.Mushroom.class){
                items.add(new com.pointless.mariobros.Sprites.Items.Mushroom(this, idef.position.x, idef.position.y));
            }
        }
    }


    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {


    }

    public void handleInput(float dt){
        //control our player using immediate impulses
        if(player.currentState != Mario.State.DEAD) {
            shootTimer += dt ;
            if (controller.isUpPressed())
                player.jump();
            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isLeftPressed()&& player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isDownPressed() && shootTimer >= SHOOT_WAIT_TIME) {
                player.fire();
                shootTimer = 0;
            }
        }

    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);
        handleSpawningItems();

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.jerry)
                if (enemy.getX() < player.getX() + 200 / com.pointless.mariobros.MarioBros.PPM || enemy.getX() <= player.getX()) {
                    if (enemy.getX() < gamecam.position.x + 200 / com.pointless.mariobros.MarioBros.PPM) {
                    enemy.b2body.setActive(true);
               //     enemy.b2body.getLinearVelocity().x = 0 ;
                 //  enemy.b2body.getLinearVelocity().y = 0 ;
                }
            }
            else
                enemy.b2body.setActive(false);
        }
        for(com.pointless.mariobros.Sprites.Items.Item item : items)
            item.update(dt);

        hud.update(dt);

        //attach our gamecam to our players.x coordinate
        if(player.currentState != Mario.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }
        if (player.currentState != Mario.State.DEAD) {


                      // move camera only forward


                               if (gamecam.position.x < player.b2body.getPosition().x) {


                gamecam.position.x = player.b2body.getPosition().x;


                           // stop mario when he is running out of the screen


            } else if (player.b2body.getPosition().x - player.b2body.getFixtureList().get(0).getShape().getRadius() <= gamecam.position.x - gamecam.viewportWidth / 2) {


                player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);


               player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getLocalCenter(), true);


                           }


        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }


    @Override
    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        com.pointless.mariobros.MarioBros.batch.setProjectionMatrix(gamecam.combined);
        com.pointless.mariobros.MarioBros.batch.begin();
        player.draw(com.pointless.mariobros.MarioBros.batch);
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(com.pointless.mariobros.MarioBros.batch);
        for (com.pointless.mariobros.Sprites.Items.Item item : items)
            item.draw(com.pointless.mariobros.MarioBros.batch);
        com.pointless.mariobros.MarioBros.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        com.pointless.mariobros.MarioBros.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();
        if(gameWon()){
            game.setScreen(new CongratsScreen(game, hud.getScore(), adhandler ));

            dispose();
        }

        if(gameOver()){
            game.setScreen(new GameOverScreen(game, hud.getScore() , adhandler ));

            dispose();
        }

    }



    public boolean gameOver(){
        if(player.currentState == Mario.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    public boolean gameWon(){
        if(player.marioWon){
            return true;
        }
            return false;

    }

    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);
        controller.resize(width, height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public com.pointless.mariobros.Scenes.Hud getHud(){ return hud; }
}
