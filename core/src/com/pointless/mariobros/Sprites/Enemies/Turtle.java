package com.pointless.mariobros.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.pointless.mariobros.Sprites.Mario;

/**
 * Created by brentaureli on 10/10/15.
 */
public class Turtle extends Enemy {
    public static final int KICK_LEFT = -2;
    public static final int KICK_RIGHT = 2;
    public enum State {WALKING, MOVING_SHELL, STANDING_SHELL}
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion normal;
    private TextureRegion shell;
    private boolean setToDestroy;
    private boolean destroyed;
    public  float pos ;
    private Mario tom ;
    public boolean down = false  ;
    public float timer = 0 ;


    public Turtle(com.pointless.mariobros.Screens.PlayScreen screen, float x, float y, Mario player) {
        super(screen, x, y);
        this.tom = player;
        this.pos = y ;
        normal = new TextureRegion(screen.getAtlas().findRegion("jerry"), 0, 0, 30, 27);
        shell = new TextureRegion(screen.getAtlas().findRegion("para"), 0, 0, 22, 30);
        currentState = previousState = State.WALKING;
        velocity =  new Vector2(42f , 0f); ;
        jerry = true ;

        setBounds(getX(), getY(), 30 / com.pointless.mariobros.MarioBros.PPM, 27 / com.pointless.mariobros.MarioBros.PPM);

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1500 ;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / com.pointless.mariobros.MarioBros.PPM);
        fdef.filter.categoryBits = com.pointless.mariobros.MarioBros.ENEMY_BIT;
        fdef.filter.maskBits = //MarioBros.GROUND_BIT |
           //     MarioBros.COIN_BIT |
          //      MarioBros.BRICK_BIT |
          //      MarioBros.ENEMY_BIT |
           //     MarioBros.OBJECT_BIT |
                com.pointless.mariobros.MarioBros.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 16).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        vertice[1] = new Vector2(5, 16).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 1.8f;
        fdef.filter.categoryBits = com.pointless.mariobros.MarioBros.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case MOVING_SHELL:
            case STANDING_SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = normal;
                break;
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;
    }

    @Override
    public void update(float dt) {

        setRegion(getFrame(dt));

        if (currentState == State.STANDING_SHELL && stateTime > 5) {
            currentState = State.WALKING;
            velocity =  new Vector2(45f , 0f); ;
            System.out.println("WAKE UP SHELL");
        }
        if(b2body.getPosition().y >= 170/ com.pointless.mariobros.MarioBros.PPM) {
            velocity.x += 5;
            down = true;
        }
        if(b2body.getPosition().y <= 100/ com.pointless.mariobros.MarioBros.PPM) {
            down = false;
        }
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 / com.pointless.mariobros.MarioBros.PPM);

        if(tom.getX() + 150 >= getX() && down == false){
            b2body.applyForceToCenter(new Vector2(0, 300f), true);
        }
        else if( down == true   ) {
             b2body.applyForceToCenter(new Vector2(0, -200f), true);
        }

        if(velocity.x > 35f)
           velocity.x = 35f ;

        b2body.setLinearVelocity(velocity);
        b2body.applyForceToCenter(new Vector2(0, 12f), true);
    }

    @Override
    public void hitOnHead(Mario mario) {
        if(currentState == State.STANDING_SHELL) {
            currentState = State.MOVING_SHELL;
              tom.marioWon = true;
            System.out.println("Set to moving shell");
        }
        else {
            currentState = State.STANDING_SHELL;
            mario.b2body.applyForceToCenter(new Vector2(-200f , -100f),true );
        }
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
      if(velocity.x > 0) {
          b2body.applyLinearImpulse(new Vector2(10f, 10f), b2body.getWorldCenter(), true);
      }
      else
        {
        b2body.applyLinearImpulse(new Vector2(-10f, 10f), b2body.getWorldCenter(), true);
        }

    }

    public void kick(int direction){
        velocity.x = direction;
        currentState = State.MOVING_SHELL;
    }
}
