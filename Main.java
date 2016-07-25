package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private BulletAppState bulletAppState;
    private RigidBodyControl landscapeControl;
    private CharacterControl player;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        cam.setLocation(new Vector3f(0.0f, 20.0f, 0.0f));
        
        initLight();
        initPlayer();
        initTerrain();
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
        cam.setLocation(player.getPhysicsLocation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    
}
