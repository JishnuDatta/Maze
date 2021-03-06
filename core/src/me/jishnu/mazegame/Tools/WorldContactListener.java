package me.jishnu.mazegame.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import me.jishnu.mazegame.InteractiveObjects.Key;
import me.jishnu.mazegame.InteractiveObjects.Player;
import me.jishnu.mazegame.Tiles.Ground;
import me.jishnu.mazegame.Tiles.LadderDown;
import me.jishnu.mazegame.Tiles.LadderUp;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //Adding the bits together to make it faster?
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //Switch statement controls all collisions
        switch (cDef) {
            //For collision between a player and a ladder going up
            case Constants.PLAYER_BIT | Constants.LADDER_UP_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderUp)(fixB).getUserData()).teleportUp((Player) fixA.getUserData());
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderUp)(fixA).getUserData()).teleportUp((Player) fixB.getUserData());
                }
                break;
            //For collision between a player and a ladder going down
            case Constants.PLAYER_BIT | Constants.LADDER_DOWN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderDown)(fixB).getUserData()).teleportDown((Player) fixA.getUserData());
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderDown)(fixA).getUserData()).teleportDown((Player) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.KEY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player)(fixA).getUserData()).pickedUpKey((Key) fixB.getUserData());
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Player)(fixB).getUserData()).pickedUpKey((Key) fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                         if(((Player) fixA.getUserData()).trailOn){
                             ((Ground) (fixB).getUserData()).steppedOn(((Player) fixA.getUserData()).getTeam());
                         }
                    if(((Player)fixA.getUserData()).getHasKey()){

                    if(((Ground)(fixB).getUserData()).teamLastStepped ==(((Player)fixA.getUserData()).getTeam())){
                        System.out.println("on PATH");
                        ((Player)fixA.getUserData()).setOnPath(true);
                    }
                    else{
                        ((Player)fixA.getUserData()).setOnPath(false);
                    }
                    }
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT ) {
                    if(((Player) fixB.getUserData()).trailOn){
                        ((Ground)(fixA).getUserData()).steppedOn(((Player)fixB.getUserData()).getTeam());
                    }
                    if(((Player)fixB.getUserData()).getHasKey()){

                        if(((Ground)(fixA).getUserData()).teamLastStepped ==(((Player)fixB.getUserData()).getTeam())){
                            System.out.println("on PATH");
                            ((Player)fixB.getUserData()).setOnPath(true);
                        }
                        else{
                            ((Player)fixB.getUserData()).setOnPath(false);
                        }
                    }
                }
                break;
            case Constants.PLAYER_BIT | Constants.BASE_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT && ((Player) fixA.getUserData()).getHasKey() && ((Player) fixA.getUserData()).getReturnPoint() == fixB.getUserData()) {
                    ((Player) fixA.getUserData()).setWinner();
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT && ((Player) fixB.getUserData()).getHasKey() && ((Player) fixB.getUserData()).getReturnPoint() == fixA.getUserData()) {
                    ((Player) fixB.getUserData()).setWinner();
                }
                break;
            default:
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