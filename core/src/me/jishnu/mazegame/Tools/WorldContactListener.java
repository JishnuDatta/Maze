package me.jishnu.mazegame.Tools;

        import com.badlogic.gdx.physics.box2d.Contact;
        import com.badlogic.gdx.physics.box2d.ContactImpulse;
        import com.badlogic.gdx.physics.box2d.ContactListener;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.Manifold;

        import me.jishnu.mazegame.InteractiveObjects.*;
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
                    ((LadderUp)(fixB).getUserData()).teleportUp((me.jishnu.mazegame.InteractiveObjects.Player) fixA.getUserData());
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderUp)(fixA).getUserData()).teleportUp((me.jishnu.mazegame.InteractiveObjects.Player) fixB.getUserData());
                }
                break;
            //For collision between a player and a ladder going down
            case Constants.PLAYER_BIT | Constants.LADDER_DOWN_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderDown)(fixB).getUserData()).teleportDown((me.jishnu.mazegame.InteractiveObjects.Player) fixA.getUserData());
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((LadderDown)(fixA).getUserData()).teleportDown((me.jishnu.mazegame.InteractiveObjects.Player) fixB.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.KEY_BIT:
                if (fixA.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Key)(fixB).getUserData()).pickedUp((me.jishnu.mazegame.InteractiveObjects.Player) fixA.getUserData());
                }
                else if (fixB.getFilterData().categoryBits == Constants.PLAYER_BIT) {
                    ((Key)(fixA).getUserData()).pickedUp((me.jishnu.mazegame.InteractiveObjects.Player) fixB.getUserData());
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