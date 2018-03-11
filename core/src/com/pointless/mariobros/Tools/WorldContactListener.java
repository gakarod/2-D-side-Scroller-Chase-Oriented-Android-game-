package com.pointless.mariobros.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by brentaureli on 9/4/15.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case com.pointless.mariobros.MarioBros.MARIO_HEAD_BIT | com.pointless.mariobros.MarioBros.BRICK_BIT:
            case com.pointless.mariobros.MarioBros.MARIO_HEAD_BIT | com.pointless.mariobros.MarioBros.COIN_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.MARIO_HEAD_BIT)
                    ((com.pointless.mariobros.Sprites.TileObjects.InteractiveTileObject) fixB.getUserData()).onHeadHit((com.pointless.mariobros.Sprites.Mario) fixA.getUserData());
                else
                    ((com.pointless.mariobros.Sprites.TileObjects.InteractiveTileObject) fixA.getUserData()).onHeadHit((com.pointless.mariobros.Sprites.Mario) fixB.getUserData());
                break;
            case com.pointless.mariobros.MarioBros.ENEMY_HEAD_BIT | com.pointless.mariobros.MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.ENEMY_HEAD_BIT)
                    ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).hitOnHead((com.pointless.mariobros.Sprites.Mario) fixB.getUserData());
                else
                    ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData()).hitOnHead((com.pointless.mariobros.Sprites.Mario) fixA.getUserData());
                break;

            case com.pointless.mariobros.MarioBros.ENEMY_BIT | com.pointless.mariobros.MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.ENEMY_BIT) {
                    if (((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).jerry) {
                        ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).jump(false, true);
                    } else {
                          ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).hitByEnemy((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData());
                    }
                } else {
                    if (((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData()).jerry) {
                        ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData()).jump(false, true);
                    } else {
                        ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData()).hitByEnemy((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData());
                    }
                }
                break;

            case com.pointless.mariobros.MarioBros.MARIO_BIT | com.pointless.mariobros.MarioBros.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.MARIO_BIT)
                    ((com.pointless.mariobros.Sprites.Mario) fixA.getUserData()).hit((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData());
                else
                    ((com.pointless.mariobros.Sprites.Mario) fixB.getUserData()).hit((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData());
                break;
            case com.pointless.mariobros.MarioBros.ENEMY_BIT | com.pointless.mariobros.MarioBros.ENEMY_BIT:
                if (((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).jerry == false) {
                    ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).hitByEnemy((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData());
                    ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData()).hitByEnemy((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData());
                }
                break;

            case com.pointless.mariobros.MarioBros.ITEM_BIT | com.pointless.mariobros.MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.ITEM_BIT)
                    ((com.pointless.mariobros.Sprites.Items.Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((com.pointless.mariobros.Sprites.Items.Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case com.pointless.mariobros.MarioBros.ITEM_BIT | com.pointless.mariobros.MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.ITEM_BIT)
                    ((com.pointless.mariobros.Sprites.Items.Item) fixA.getUserData()).use((com.pointless.mariobros.Sprites.Mario) fixB.getUserData());
                else
                    ((com.pointless.mariobros.Sprites.Items.Item) fixB.getUserData()).use((com.pointless.mariobros.Sprites.Mario) fixA.getUserData());
                break;
            case com.pointless.mariobros.MarioBros.FIREBALL_BIT | com.pointless.mariobros.MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.FIREBALL_BIT)
                    ((com.pointless.mariobros.Sprites.Other.FireBall) fixA.getUserData()).setToDestroy();
                else
                    ((com.pointless.mariobros.Sprites.Other.FireBall) fixB.getUserData()).setToDestroy();
                break;
            case com.pointless.mariobros.MarioBros.FIREBALL_BIT | com.pointless.mariobros.MarioBros.ENEMY_BIT:
                if (((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).jerry == false) {
                    if (fixA.getFilterData().categoryBits == com.pointless.mariobros.MarioBros.FIREBALL_BIT) {
                        ((com.pointless.mariobros.Sprites.Other.FireBall) fixA.getUserData()).setToDestroy();
                        ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixB.getUserData()).hitOnHead(null);
                    } else {
                        ((com.pointless.mariobros.Sprites.Other.FireBall) fixB.getUserData()).setToDestroy();
                        ((com.pointless.mariobros.Sprites.Enemies.Enemy) fixA.getUserData()).hitOnHead(null);
                    }
                }
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
