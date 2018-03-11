package com.pointless.mariobros.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pointless.mariobros.Sprites.Enemies.Enemy;
import com.pointless.mariobros.Sprites.Enemies.Turtle;
import com.pointless.mariobros.Sprites.Mario;

/**
 * Created by brentaureli on 8/28/15.
 */
public class B2WorldCreator {
    private Array<com.pointless.mariobros.Sprites.Enemies.Goomba> goombas;
    private Array<Turtle> turtles;

    public B2WorldCreator(com.pointless.mariobros.Screens.PlayScreen screen, Mario player){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / com.pointless.mariobros.MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / com.pointless.mariobros.MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / com.pointless.mariobros.MarioBros.PPM, rect.getHeight() / 2 / com.pointless.mariobros.MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / com.pointless.mariobros.MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / com.pointless.mariobros.MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / com.pointless.mariobros.MarioBros.PPM, rect.getHeight() / 2 / com.pointless.mariobros.MarioBros.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = com.pointless.mariobros.MarioBros.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new com.pointless.mariobros.Sprites.TileObjects.Brick(screen, object);
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            new com.pointless.mariobros.Sprites.TileObjects.Coin(screen, object);
        }

        //create all goombas
        goombas = new Array<com.pointless.mariobros.Sprites.Enemies.Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new com.pointless.mariobros.Sprites.Enemies.Goomba(screen, rect.getX() / com.pointless.mariobros.MarioBros.PPM, rect.getY() / com.pointless.mariobros.MarioBros.PPM));
        }
        turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / com.pointless.mariobros.MarioBros.PPM, rect.getY() / com.pointless.mariobros.MarioBros.PPM, player ));
        }
    }

    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }
}
