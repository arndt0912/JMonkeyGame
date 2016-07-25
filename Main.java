package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class Main extends SimpleApplication implements ActionListener{

    private BulletAppState bulletAppState;
    private RigidBodyControl landscapeControl;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f(0.0f, 0.0f, 0.0f);
    private boolean right, left, up, down;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        initCamera();
        initLight();
        initPlayer();
        initTerrain();
        initKeys();
    }
    
    public void initKeys(){
        inputManager.addMapping("Left", new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(keyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(keyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(keyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(keyInput.KEY_SPACE));
        
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
            
    }
    
    public void initCamera(){
        cam.setLocation(new Vector3f(0.0f, 20.0f, 0.0f));
    }
    
    public void initLight(){
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(1.5f));
        rootNode.addLight(ambientLight);
    }
    
    public void initTerrain(){
        Spatial terrain = assetManager.loadModel("Scenes/Terrain.j3o");
        landscapeControl = new RigidBodyControl(0.0f);
        terrain.addControl(landscapeControl);
        rootNode.attachChild(terrain);
        bulletAppState.getPhysicsSpace().add(landscapeControl);
    }
    
    public void initPlayer(){
        CapsuleCollisionShape playerShape = new CapsuleCollisionShape(0.4f, 1.9f);
        player = new CharacterControl(playerShape, 0.5f);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setJumpSpeed(20);
        player.setPhysicsLocation(new Vector3f(0f, 50f, 0f));
        bulletAppState.getPhysicsSpace().add(player);
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        Vector3f camDir = cam.getDirection().multLocal(0.9f);
        Vector3f camLeft = cam.getLeft().mult(0.4f);
        
        walkDirection.set(0.0f, 0.0f, 0.0f);
        
        if(left){
            walkDirection.addLocal(camLeft);
        }else if(right){
            walkDirection.addLocal(camLeft.negate());
        }else if(up){
            walkDirection.addLocal(camDir);
        }else if(down){
            walkDirection.addLocal(camDir.negate());
        }
        walkDirection.setY(0.0f);
        player.setWalkDirection(walkDirection);
        
        cam.setLocation(player.getPhysicsLocation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Left")){
            left = isPressed;
        }else if(name.equals("Right")){
            right = isPressed;
        }else if(name.equals("Up")){
            up = isPressed;
        }else if(name.equals("Down")){
            down = isPressed;
        }else if(name.equals("Jump")){
            player.jump();
        }
    }
}
