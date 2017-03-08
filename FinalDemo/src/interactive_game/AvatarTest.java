package interactive_game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PositionInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.selman.java3d.book.common.ComplexObject;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.values.IRSource;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

import com.sun.j3d.audioengines.javasound.JavaSoundMixer;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewerAvatar;
import com.tornadolabs.j3dtree.Java3dTree;


public class AvatarTest extends javax.swing.JFrame implements WiimoteListener
{
	
public static int keyCode=0,count=0;
public static float collidecount=(float) -13;
static double m_old=0,m_new=0,x1=0,y1=0,x2=0,y2=0;
static RotateBehavior awtBehavior;
static Wiimote wiimote;
public static void collidedisp()
{
	System.out.println("Collidecount:"+collidecount);
}
	AvatarTest()
	{  
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("My Game");
	}
	public AvatarTest(int j)
	{
		System.out.println("j is "+j);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("My Game");
	}

	public BranchGroup createSceneGraph( )
	{
		BranchGroup bg = new BranchGroup( );

		TransformGroup tgRoot = addBehaviors( bg );

		createBuildings( tgRoot );
		createRoad( tgRoot );
		createLand( tgRoot );
		createCars( tgRoot );
		createBackground( bg );

		return bg;
	}

	public void createBackground( Group bg )
	{
		// add the sky backdrop
		Background back = new Background( );
		back.setApplicationBounds( getBoundingSphere( ) );
		bg.addChild( back );

		BranchGroup bgGeometry = new BranchGroup( );

		// create an appearance and assign the texture image
		Appearance app = new Appearance( );				
		Texture tex = new TextureLoader( "bk3.jpg", this ).getTexture( );
		app.setTexture( tex );

		Sphere sphere = new Sphere( 1.0f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS_INWARD, app );

		bgGeometry.addChild( sphere );
		back.setGeometry( bgGeometry );
	}


	public Group createLand( Group g )
	{	
		Land land = new Land( this, g, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		return land.createObject( new Appearance( ), new Vector3d( 0,0,0 ), new Vector3d( 1,1,1 ), "land.jpg", null, null );
	}

	public Group createRoad( Group g )
	{	
		Road road = new Road( this, g, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		return road.createObject( new Appearance( ), new Vector3d( 0,0,0 ), new Vector3d( 1,1,1 ), "road.jpg", null, null );
	}


	private float getRandomNumber( float basis, float random )
	{
		return basis + ( (float) Math.random( ) * random * 2 ) - (random);
	}

	public Group createBuildings( Group g )
	{
		BranchGroup bg = new BranchGroup( );

		for( int n = (int) Road.ROAD_LENGTH; n < 0; n = n + 10 )
		{
			Building building = new Building( this, bg, ComplexObject.GEOMETRY | ComplexObject.TEXTURE | ComplexObject.COLLISION );

			building.createObject( new Appearance( ), 
				new Vector3d( getRandomNumber( -4.0f, 0.25f ),
				getRandomNumber( 1.0f, 0.5f ),
				getRandomNumber( n, 0.5f ) ),
				new Vector3d( 1,1,1 ),
				"house1.jpg",
				null,
				null );

			building = new Building( this, bg, ComplexObject.GEOMETRY | ComplexObject.TEXTURE | ComplexObject.COLLISION );

			building.createObject( new Appearance( ),
				new Vector3d( getRandomNumber( 4.0f, 0.25f ),
				getRandomNumber( 1.0f, 0.5f ),
				getRandomNumber( n, 0.5f ) ),
				new Vector3d( 1,1,1 ),
				"house1.jpg",
				null,
				null );

		}

		g.addChild( bg );

		return bg;
	}


	public Group createCars( Group g )
	{
		BranchGroup bg = new BranchGroup( );

		for( int n = (int) Road.ROAD_LENGTH; n < 0; n = n + 10 )
		{
			Car car = new Car( this, bg, 	ComplexObject.GEOMETRY | 
				ComplexObject.TEXTURE | 
				ComplexObject.SOUND );

			car.createObject( new Appearance( ),
				new Vector3d( getRandomNumber( 0.0f, 2.0f ),
				Car.CAR_HEIGHT/2.0f,
				getRandomNumber( n, 5.0f ) ),
				new Vector3d( 1,1,1 ),
				"stone.jpg",
				"car.wav",
				"collide.wav" );
		}

		g.addChild( bg );
		return bg;
	}

	public TransformGroup addBehaviors( Group bgRoot )
	{
		// Create the transform group node and initialize it to the
		// identity.  Enable the TRANSFORM_WRITE capability so that
		// our behavior code can modify it at runtime.  Add it to the
		// root of the subgraph.
		TransformGroup objTrans = new TransformGroup( );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

		Transform3D zAxis = new Transform3D( );
		zAxis.rotY( Math.toRadians( 90.0 ) );

		Alpha zoomAlpha = new Alpha( -1, Alpha.INCREASING_ENABLE,
			0, 0,
			20000, 0, 0,
			0, 0, 0 );

		PositionInterpolator posInt = new PositionInterpolator( zoomAlpha, objTrans, zAxis, 0, -160 );

		posInt.setSchedulingBounds( getBoundingSphere( ) );
		objTrans.addChild( posInt );

		bgRoot.addChild( objTrans );

		return objTrans;
	}

	BoundingSphere getBoundingSphere( )
	{
		return new BoundingSphere( new Point3d( 0.0,0.0,0.0 ), 400.0 );
	}

	ViewerAvatar createAvatar( )
	{				
		ViewerAvatar va = new ViewerAvatar( );
		TransformGroup tg = new TransformGroup( );

		Car car = new Car( this, tg, 	ComplexObject.GEOMETRY | 
			ComplexObject.TEXTURE | 
			ComplexObject.COLLISION | 
			ComplexObject.COLLISION_SOUND );

		car.createObject( new Appearance( ),
			new Vector3d( 0,-0.3,-0.3 ),
			new Vector3d( 0.3,0.3,1 ),
			"platform.jpg",
			null,
			"collide.wav" );

		tg.addChild( car );
		va.addChild( tg );

		return va;
	}

	private static final WindowAdapter listener = new WindowAdapter() {

        @Override
        public void windowClosed(WindowEvent e) {
        	System.out.println("Closed");
        }

        @Override
        public void windowClosing(WindowEvent e) {
        	wiimote.deactivateIRTRacking();
    		wiimote.deactivateMotionSensing();
    		System.out.println("Finalized");
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        	System.out.println("Deactivated");
        }

        private void print(WindowEvent e) {
            System.out.println(e.getWindow().getName() + ":" + e);
        }
    };

	
	public static void main( String[] args )
	{
		AvatarTest avatarTest = new AvatarTest(5 );
		avatarTest.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		avatarTest.setTitle("My Game");
		
		// Create a simple scene and attach it to the virtual universe
		SimpleUniverse u = new SimpleUniverse( );

		PhysicalEnvironment physicalEnv = u.getViewer( ).getPhysicalEnvironment( );

		TransformGroup tg = u.getViewer( ).getViewingPlatform( ).getViewPlatformTransform( );

		Transform3D t3d = new Transform3D( );
		t3d.set( new Vector3f( 0,0.5f,0 ) );
		tg.setTransform( t3d );  

		CarSteering keys = new CarSteering( tg );
		keys.setSchedulingBounds( avatarTest.getBoundingSphere( ) );

		awtBehavior = new RotateBehavior(tg);
		BoundingSphere boundse = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		awtBehavior.setSchedulingBounds(boundse);

		
		u.getViewer( ).setAvatar( avatarTest.createAvatar( ) );

		if (physicalEnv != null)
		{
			JavaSoundMixer javaSoundMixer = new JavaSoundMixer( physicalEnv );

			if (javaSoundMixer == null) 
				System.out.println( "Unable to create AudioDevice." );

			javaSoundMixer.initialize( );
		}

		// Add everthing to the scene graph - it will now be displayed.
		BranchGroup scene = avatarTest.createSceneGraph( );
		scene.addChild( keys );
		scene.addChild(awtBehavior);
		Java3dTree j3dTree = new Java3dTree( );

		j3dTree.recursiveApplyCapability( scene );

		u.addBranchGraph( scene );

		j3dTree.updateNodes( u );

		u.getViewingPlatform( ).getViewPlatform( ).setActivationRadius( 2 );
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		wiimote = wiimotes[0];
		wiimote.deactivateIRTRacking();
		wiimote.activateIRTRacking();
		wiimote.activateMotionSensing();
		wiimote.addWiiMoteEventListeners(avatarTest);

		avatarTest.addWindowListener(listener);
		avatarTest.pack();
		
	}
	
	
	 
	
	public void finalize()
	{
		
		wiimote.deactivateIRTRacking();
		wiimote.deactivateMotionSensing();
		System.out.println("Finalized");
	}

	@Override	
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		// TODO Auto-generated method stub
		if(e.isButtonLeftPressed())
		{
			if(!(count<-40))
			{
			keyCode=1;
			count--;
			awtBehavior.rotate();
			}
			
		}
		if(e.isButtonRightPressed())
		{
			if(!(count>40))
			{
			keyCode=2;
			count++;
			awtBehavior.rotate();
			}
			
		}
		
		
		
	}

	@Override
	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExpansionEvent(ExpansionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIrEvent(IREvent e) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IRSource[] IRPoints = e.getIRPoints();
		if (IRPoints.length == 2) {
			System.out.println("in if point1");
			double theta=0;
			x1 = IRPoints[0].getX();
			y1 = IRPoints[0].getY();
			x2 = IRPoints[1].getX();
			y2 = IRPoints[1].getY();
			
			try{
				m_new=(y2-y1)/(x2-x1);	
				theta=(Math.atan(m_new)*180)/Math.PI;
				
			}
			catch(ArithmeticException f)
			{
				System.out.println("divide by zero"+f);
			}
			
			System.out.println("m_new: " + m_new+"Theta: "+theta);
			
			
			if(theta>15 && count>-40)
			{
				keyCode=1;
				count--;
				awtBehavior.rotate();
			}
			if(theta<-15 && count<40)
			{
				
				keyCode=2;
				count++;
				awtBehavior.rotate();
			}
			
			
			
			
						
			
			
			
			
			
			
			
			
		}
		
		
		
		
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusEvent(StatusEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	
