package com.pointless.mariobros.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.pointless.mariobros.Screens.PlayScreen;
import com.pointless.mariobros.Sprites.Mario;

/**
 * Created by brentaureli on 9/14/15.
 */
public class Goomba extends Enemy
{
    private float stateTime;
    Animation walkAnimation;
    private Array<TextureRegion> frames;
    public boolean setToDestroy;
    private boolean destroyed;
    public boolean jerry = false ;
    float angle;


    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), 0, 0, 19, 25));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), 20, 0, 20, 25));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), 40, 0, 21, 25));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / com.pointless.mariobros.MarioBros.PPM, 16 / com.pointless.mariobros.MarioBros.PPM);
        jerry = false ;
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 61, 0, 19, 25));
            stateTime = 0;
        }
        else if(!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 5 ;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / com.pointless.mariobros.MarioBros.PPM);
        fdef.filter.categoryBits = com.pointless.mariobros.MarioBros.ENEMY_BIT;
        fdef.filter.maskBits = com.pointless.mariobros.MarioBros.GROUND_BIT |
                com.pointless.mariobros.MarioBros.COIN_BIT |
                com.pointless.mariobros.MarioBros.BRICK_BIT |
                com.pointless.mariobros.MarioBros.ENEMY_BIT |
                com.pointless.mariobros.MarioBros.OBJECT_BIT |
                com.pointless.mariobros.MarioBros.FIREBALL_BIT |
                com.pointless.mariobros.MarioBros.MARIO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        vertice[2] = new Vector2(-5, 3).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / com.pointless.mariobros.MarioBros.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = com.pointless.mariobros.MarioBros.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }



    @Override
    public void hitOnHead(Mario mario) {
        setToDestroy = true;
        com.pointless.mariobros.MarioBros.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }

    @Override
    public void hitByEnemy(Enemy enemy) {

            walkAnimation.getKeyFrame(stateTime).flip(true,false);
            reverseVelocity(true, false);
    }
}
