package finaldemonstration;

import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.vrml97.VrmlLoader;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

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
import java.awt.GraphicsConfiguration;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.vp.*;
import java.io.FileNotFoundException;


public class enginecar extends javax.swing.JFrame implements WiimoteListener{
	double scalefac;
	double theta;
	static	int oldx = 0, oldy = 0, newx = 0, newy = 0;
	static	int dispx, dispy;
	static	double xfactor, yfactor;
	int count=0;
	static double m_old=0,m_new=0,x1=0,y1=0,x2=0,y2=0;
	double XE,YE;
	float olddist=0,newdist=0;
	private int rotnum;
    private boolean spin = false;
    private boolean noTriangulate = false;
    private boolean noStripify = false;
    private double creaseAngle = 60.0;
    private URL filename = null;
    private SimpleUniverse univ = null;
    private BranchGroup scene = null;
    private IRSource IRPoints[];
    int flag;
    private TransformGroup objTrans;
    private RotateBehavior awtBehavior;
    protected String URLString = "";
    protected float eyeOffset =0.03F;
    protected static int size=600;
    
    
    class RotateBehavior extends Behavior {

        private TransformGroup transformGroup;
        private Transform3D trans = new Transform3D();
        private WakeupCriterion criterion;
        private float angle = 0.0f;

        private final int ROTATE = 1;

        // create a new RotateBehavior
        RotateBehavior(TransformGroup tg) {
            transformGroup = tg;
        }

        // initialize behavior to wakeup on a behavior post with id = ROTATE
        public void initialize() {
            criterion = new WakeupOnBehaviorPost(this, ROTATE);
            wakeupOn(criterion);
        }

        // processStimulus to rotate the cube
        public void processStimulus(Enumeration criteria) {
        	
        	wakeupOn(criterion);
        //	trans.rotZ(theta);
        	trans.rotZ(theta);
         	trans.setScale(scalefac);
			trans.setTranslation(new Vector3d(xfactor, yfactor, 0.0));
			transformGroup.setTransform(trans);
            
            wakeupOn(criterion);
               	            
        }

        // when the mouse is clicked, postId for the behavior
        void rotate() {
            postId(ROTATE);
        }
    }
    
    
    
    
    
    public BranchGroup createSceneGraph() {
	// Create the root of the branch graph
	BranchGroup objRoot= new BranchGroup();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();
        
        
        
     
      
     
      t3d.rotX(Math.toRadians(-90.0)); 
  
     
       
      
     t3d.setTranslation(new Vector3d(-0.0,-0.0,0.0));
     t3d.setScale(0.01); // for untitled(2).wrl 
     objScale.setTransform(t3d);
        //t3d.rotY(Math.toRadians(10.0));
  
        objRoot.addChild(objScale);

	// Create the transform group node and initialize it to the
	// identity.  Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at runtime.  Add it to the
	// root of the subgraph.
	TransformGroup objTrans = new TransformGroup();
	objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
	objScale.addChild(objTrans);

	int flags = ObjectFile.RESIZE;	
	if (!noTriangulate) flags |= ObjectFile.TRIANGULATE;
	if (!noStripify) flags |= ObjectFile.STRIPIFY;
	ObjectFile f = new ObjectFile(flags, 
	  (float)(creaseAngle * Math.PI / 180.0));
	Scene s = null;
	VrmlLoader loader = new VrmlLoader( );
	try {
	
	//	s= loader.load("src/un/Un.wrl");
	s=loader.load("src/carwithe/Shelby GT 500 tuned.wrl");
	}
	catch (FileNotFoundException e) {
	  System.err.println(e);
	  System.exit(1);
	}
	catch (ParsingErrorException e) {
	  System.err.println(e);
	  System.exit(1);
	}
	catch (IncorrectFormatException e) {
	  System.err.println(e);
	  System.exit(1);
	}
	  
	objTrans.addChild(s.getSceneGroup());

	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

        /*if (spin) {
	  Transform3D yAxis = new Transform3D();
	  Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
					  0, 0,
					  4000, 0, 0,
					  0, 0, 0);

	  RotationInterpolator rotator =
	      new RotationInterpolator(rotationAlpha, objTrans, yAxis,
				       0.0f, (float) Math.PI*2.0f);
	  rotator.setSchedulingBounds(bounds);
	  objTrans.addChild(rotator);
	} */

        // Set up the background
        Color3f bgColor = new Color3f(0.00f, 0.00f, 0.0f);
        Background bgNode = new Background(bgColor);
        bgNode.setApplicationBounds(bounds);
        objRoot.addChild(bgNode);
        
        awtBehavior = new RotateBehavior(objTrans);
		BoundingSphere boundse = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
							   100.0);
		
		awtBehavior.setSchedulingBounds(boundse);
		
		objTrans.addChild(awtBehavior);

	return objRoot;
    }
    
    private Canvas3D createUniverse() {
	// Get the preferred graphics configuration for the default screen
	GraphicsConfiguration config =
	    SimpleUniverse.getPreferredConfiguration();

	// Create a Canvas3D using the preferred configuration
	Canvas3D canvas3d = new Canvas3D(config);

	// Create simple universe with view branch
	univ = new SimpleUniverse(canvas3d);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

	// add mouse behaviors to the ViewingPlatform
	ViewingPlatform viewingPlatform = univ.getViewingPlatform();

	PlatformGeometry pg = new PlatformGeometry();

	// Set up the ambient light
	Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
	AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	ambientLightNode.setInfluencingBounds(bounds);
	pg.addChild(ambientLightNode);

	// Set up the directional lights
	Color3f light1Color = new Color3f(1.0f, 1.0f, 0.9f);
	Vector3f light1Direction  = new Vector3f(1.0f, 1.0f, 1.0f);
	Color3f light2Color = new Color3f(1.0f, 1.0f, 1.0f);
	Vector3f light2Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);

	DirectionalLight light1
	    = new DirectionalLight(light1Color, light1Direction);
	light1.setInfluencingBounds(bounds);
	pg.addChild(light1);

	DirectionalLight light2
	    = new DirectionalLight(light2Color, light2Direction);
	light2.setInfluencingBounds(bounds);
	pg.addChild(light2);

	viewingPlatform.setPlatformGeometry( pg );
      
	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
	viewingPlatform.setNominalViewingTransform();

	if (!spin) {
            OrbitBehavior orbit = new OrbitBehavior(canvas3d,
						    OrbitBehavior.REVERSE_ALL);
            orbit.setSchedulingBounds(bounds);
            viewingPlatform.setViewPlatformBehavior(orbit);	    
	}        
        
        // Ensure at least 5 msec per frame (i.e., < 200Hz)
	univ.getViewer().getView().setMinimumFrameCycleTime(5);

	return canvas3d;
    }

    private void usage() {
        System.out.println(
                "Usage: java ObjLoad [-s] [-n] [-t] [-c degrees] <.obj file>");
        System.out.println("  -s Spin (no user interaction)");
        System.out.println("  -n No triangulation");
        System.out.println("  -t No stripification");
        System.out.println(
                "  -c Set crease angle for normal generation (default is 60 without");
        System.out.println(
                "     smoothing group info, otherwise 180 within smoothing groups)");
        System.exit(0);
    } // End of usage

    /**
     * Creates new form ObjLoad
     */
    public enginecar(String args[]) {
        
            
             
        
	// Initialize the GUI components
	initComponents();

	// Create Canvas3D and SimpleUniverse; add canvas to drawing panel
	Canvas3D c = createUniverse();
	drawingPanel.add(c, java.awt.BorderLayout.CENTER);

	// Create the content branch and add it to the universe
	scene = createSceneGraph();
	univ.addBranchGraph(scene);
    }

    // ----------------------------------------------------------------
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        drawingPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mclaren");
        drawingPanel.setLayout(new java.awt.BorderLayout());

        drawingPanel.setPreferredSize(new java.awt.Dimension(700, 700));
        getContentPane().add(drawingPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
    	
   //     java.awt.EventQueue.invokeLater(new Runnable() {
        	
            //public void run() {
            	
              final  enginecar Mclaren = new enginecar(args);
               // objLoad.setVisible(true);
                Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
                Wiimote wiimote = wiimotes[0];
                wiimote.deactivateIRTRacking();
                wiimote.activateIRTRacking();
                //wiimote.activateMotionSensing();
                wiimote.addWiiMoteEventListeners(Mclaren);
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                    	Mclaren.setVisible(true);
                    }
                });
                
                
     //       }
       // });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel drawingPanel;
    // End of variables declaration//GEN-END:variables

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		// TODO Auto-generated method stub
		
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
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		IRSource[] IRPoints = e.getIRPoints();
		
		
		if (IRPoints.length == 1) {
			//System.out.println("in if point1");
			
			newx = IRPoints[0].getX();
			newy = IRPoints[0].getY();
			
			xfactor=(double)(newx-440)/760;
			yfactor=(double)(newy-440)/760;
			xfactor*=150;
			yfactor*=150;
			System.out.println("nx"+xfactor+"ny:"+yfactor);
			
			awtBehavior.rotate();
			
		}
		
		
		
		if (IRPoints.length == 2) {
			
			try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
			
			//System.out.println("in if point1");
			
			x1 = IRPoints[0].getX();
			y1 = IRPoints[0].getY();
			x2 = IRPoints[1].getX();
			y2 = IRPoints[1].getY();
			double theta2 = 0;
			try{
				m_new=(y2-y1)/(x2-x1);	
				//theta2=(Math.atan(m_new)*180)/Math.PI;
				theta2=Math.atan(m_new);
				
			}
			catch(ArithmeticException f)
			{
				System.out.println("divide by zero"+f);
			}
			theta=theta2;
			
			/*if((theta2-theta)>15 || (theta2-theta)<-15)
			{
				if(theta2<0)
				{
					theta=theta2;
				}
				else{
					theta=theta2;
				}
				
		}*/
			
			System.out.println("m_new: " + m_new+"Theta: "+theta);
			olddist=newdist;
			
			newdist= e.getDistance();
			if(olddist-newdist>2 || (olddist-newdist)<-2)
			{
				scalefac=(newdist*0.00625);	
			}
			
			//System.out.println("Distance"+newdist);
			
			awtBehavior.rotate();
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
