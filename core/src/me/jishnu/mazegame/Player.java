package me.jishnu.mazegame;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.concurrent.ThreadLocalRandom;

import box2dLight.ConeLight;

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
    private int team;

    //Torch related stuff
    private float torchBattery;

    public Player(PlayScreen playScreen, Coordinates c , int team) {
        super(playScreen.getAtlas().findRegion("Player"),0,0,16,16);
        completeTextures = playScreen.getAtlas().findRegion("Player");
        standTexture = new TextureRegion(completeTextures,0,0,16,16);
        this.team = team;
        setRegion(standTexture);
        setSize(getWidth() * MazeGame.SCALING / 4, getHeight() * MazeGame.SCALING / 4);
        setOriginCenter();
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        createBody(c);
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 3; i ++){
            frames.add(new TextureRegion(completeTextures,i*16, 0, 16, 16));
        }
        walkingAnimation = new Animation(0.2f, frames);

        if(team == 0){
        }
        else if(team== 1){

        }
        else if(team == 2){

        }
        else{

        }
    }
    public void createBody(Coordinates c){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(((Constants.TILE_SIZE * c.f * playScreen.getMaze().getSizeX()) + Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.x) * MazeGame.SCALING, (Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.y) * MazeGame.SCALING);
        body = world.createBody(bdef);

        body.setFixedRotation(false);
        shape.setRadius((Constants.TILE_SIZE / 10) * MazeGame.SCALING);
        fdef.shape = shape;
        fdef.density = 60;
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        body.createFixture(fdef).setUserData(this);

        torch = new ConeLight(playScreen.getRayHandler(), 20, new Color(0,1,1,1), 40 * MazeGame.SCALING, body.getPosition().x, body.getPosition().y, 0.001f,20);
        torch.attachToBody(body);
        //Change the body in whatever direction is free.
                if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == 1) {
                    body.setTransform(body.getPosition(),(float) Math.PI);
            } if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == 1) {
                body.setTransform(body.getPosition(), (float) Math.PI / 2);
            } if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == 1) {
                body.setTransform(body.getPosition(), (float) Math.PI * 3 / 2);
        }
    }

    public void teleport(Coordinates c){
        torch.remove();
        oldBody = body;
        playScreen.addToDeleteList(oldBody);
        body = null;
            if (playScreen.getMaze().getMazeArray()[c.f][c.x + 1][c.y] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x + 1, c.y));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x - 1, c.y));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y + 1));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y - 1));
        }
    }

    public void update(float dt){
        setRegion(getCurrentTexture(dt));
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRotation(body.getTransform().getRotation() * MathUtils.radiansToDegrees);

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

    public void pickedUpKey(){
        torch.setColor(0.5f,0.5f,0.5f,1);
        //int index = ThreadLocalRandom.current().nextInt(0, .size);
    }

    public void torchButton(){
        torch.setActive(!torch.isActive());
    }
}
