package me.jishnu.mazegame.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import box2dLight.ConeLight;
import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Tools.Constants;
import me.jishnu.mazegame.Tools.Coordinates;

public class Player extends Sprite{
    private PlayScreen playScreen;
    private World world;
    public Body body;
    private ConeLight torch;
    private Body oldBody;
    private TextureRegion completeTextures;
    private TextureRegion standTexture;
    private Animation walkingAnimation;
    private float stateTimer;
    private Constants.teams team;
    private boolean hasKey;
    private boolean winner;

    private float maxVelocity;
    private boolean torchOn;
    public boolean trailOn;

    private Constants.tiles returnPoint;

    //Torch related stuff
    private float torchBattery;

    public Player(PlayScreen playScreen, Coordinates c , Constants.teams team) {
        super(playScreen.getAtlas().findRegion("Player"),0,0,16,16);
        completeTextures = playScreen.getAtlas().findRegion("Player");
        standTexture = new TextureRegion(completeTextures,0,0,16,16);
        this.team = team;
        setRegion(standTexture);
        setSize(getWidth() * Constants.SCALING / 4, getHeight() * Constants.SCALING / 4);
        setOriginCenter();
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();

        winner = false;
        hasKey = false;
        maxVelocity = 10;
        torchOn = true;
        trailOn = false;

        stateTimer = 0;

        createBody(c);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 3; i ++){
            frames.add(new TextureRegion(completeTextures,i*16, 0, 16, 16));
        }
        walkingAnimation = new Animation(0.2f, frames);

    }
    public void createBody(Coordinates c){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(((Constants.TILE_SIZE * c.f * playScreen.getMaze().getSizeX()) + Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.x) * Constants.SCALING, (Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.y) * Constants.SCALING);
        body = world.createBody(bdef);

        body.setFixedRotation(false);
        shape.setRadius((Constants.TILE_SIZE / 10) * Constants.SCALING);
        fdef.shape = shape;
        fdef.density = 60;
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        body.createFixture(fdef).setUserData(this);
        torch = new ConeLight(playScreen.getRayHandler(), 20, new Color(1,1,1,1), 40 * Constants.SCALING, body.getPosition().x, body.getPosition().y, 0.001f,20);
        torch.attachToBody(body);
        torch.getIgnoreAttachedBody();
        torch.setContactFilter(Constants.GROUND_BIT, (short) -1,(short)-1);
        if(!torchOn){
            torch.setActive(false);
        }
        //Change the body in whatever direction is free.
                if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == Constants.tiles.GROUND) {
                    body.setTransform(body.getPosition(),(float) Math.PI);
            } if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == Constants.tiles.GROUND) {
                body.setTransform(body.getPosition(), (float) Math.PI / 2);
            } if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == Constants.tiles.GROUND) {
                body.setTransform(body.getPosition(), (float) Math.PI * 3 / 2);
        }

    }

    public void teleport(Coordinates c){
        torch.remove();
        oldBody = body;
        playScreen.addToDeleteList(oldBody);
        body = null;
            if (playScreen.getMaze().getMazeArray()[c.f][c.x + 1][c.y] == Constants.tiles.GROUND) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x + 1, c.y));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == Constants.tiles.GROUND) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x - 1, c.y));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == Constants.tiles.GROUND) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y + 1));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == Constants.tiles.GROUND) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y - 1));
        }
    }

    public void update(float dt){
        setRegion(getCurrentTexture(dt));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRotation(body.getTransform().getRotation() * MathUtils.radiansToDegrees);

        //Friction
        if(!body.getLinearVelocity().isZero()){
            body.setLinearVelocity(body.getLinearVelocity().limit(maxVelocity));
        }
    }

    public void render(SpriteBatch batch){
        draw(batch);
    }

    public TextureRegion getCurrentTexture(float dt){
        if(body.getLinearVelocity().x == 0 && body.getLinearVelocity().y == 0){
            stateTimer = 0;
            return standTexture;
        }
        else{
            stateTimer+=dt;
            return walkingAnimation.getKeyFrame(stateTimer, true);
        }
    }

    public void pickedUpKey(Key key){
        hasKey = true;
        torch.setColor(0.5f, 0.5f, 0.5f, 1);
        maxVelocity = 7;
        returnPoint = key.pickedUp(this);
        System.out.println(returnPoint);
    }

    public void torchButton(){
        torch.setActive(!torch.isActive());
        torchOn = !torchOn;
    }

    public void trailButton(){
        trailOn = !trailOn;
    }

    public void handleInput(float dt){
        float playerXDirection = (float) (20 * Math.cos(body.getAngle()));
        float playerYDirection = (float) (20 * Math.sin(body.getAngle()));
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
           body.applyLinearImpulse(new Vector2(playerXDirection, playerYDirection), body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            body.applyLinearImpulse(new Vector2(-playerXDirection, -playerYDirection), body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            body.setTransform(body.getPosition(), body.getAngle() - (2 * (float) Math.PI) * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            body.setTransform(body.getPosition(),body.getAngle() + (2* (float)Math.PI)*dt);
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
            torchButton();
        if (Gdx.input.isKeyJustPressed(Input.Keys.W))
            trailButton();
    }

    public Constants.teams getTeam() {
        return team;
    }

    public boolean getHasKey() {
        return hasKey;
    }

    public Constants.tiles getReturnPoint() {
        return returnPoint;
    }

    public void setWinner() {
        winner = true;
    }

    public boolean isWinner() {
        return winner;
    }
}

