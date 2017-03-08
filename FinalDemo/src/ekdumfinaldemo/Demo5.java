
package ekdumfinaldemo;

import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.vrml97.VrmlLoader;
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


public class Demo5 extends javax.swing.JFrame implements WiimoteListener{
	double theta;
	double theta1;
	double theta4;
	public static int keyCode=0,toggle;
	static double m_old=0,m_new=0,x1=0,y1=0,x2=0,y2=0;
	static double m_old1=0,m_new1=0,x11=0,y11=0,x21=0,y21=0;
	static double m_old4=0,m_new4=0,x14=0,y14=0,x24=0,y24=0;
	double scalefac;
	int count=0;
	float olddist=0,newdist=0;
	private int rotnum;
    private boolean spin = false;
    private boolean noTriangulate = false;
    private boolean noStripify = false;
    private double creaseAngle = 60.0;
    private URL filename = null;

    private SimpleUniverse univ = null;
    private BranchGroup scene = null;

    private TransformGroup objTrans;
    private RotateBehavior awtBehavior;
    protected String URLString = "";
    protected float eyeOffset =0.03F;
    protected static int size=600;
    static	int oldx = 0, oldy = 0, newx = 0, newy = 0;
    static	int dispx, dispy;
    static	double xfactor, yfactor;

    
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
        	switch(toggle)
        	{
        	case 1:
        		trans.setTranslation(new Vector3d(xfactor, 0.0,yfactor));
        		break;
        	case 2:
        		trans.rotY(theta);
        		trans.setScale(scalefac);
        		
    			
    		break;
        	case 3:
        		trans.rotZ(theta1);
        		trans.setScale(scalefac);
        		
    			break;
        	case 4:
        		trans.rotX(theta4);
        		trans.setScale(scalefac);
        		break;
    			
        	}
         	
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
       
     
       t3d.rotX(Math.toRadians(-88.0)); 
     
       t3d.setScale(0.004); 
      
  
       t3d.setTranslation(new Vector3d(0.2,-0.5,0.0)); 
       objScale.setTransform(t3d);
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
		s= loader.load("src/carl/carl house.wrl");
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

    public Demo5() {
    	    
        if (filename == null) {
           // filename = Resources.getResource("resources/geometry/galleon.obj");
            if (filename == null) {
              //  System.err.println("resources/geometry/galleon.obj not found");
              //  System.exit(1);
            }
        }     
        
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
        setTitle("Carl's House");
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
            	
              final  Demo5 objLoad = new Demo5();
               // objLoad.setVisible(true);
                Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
                Wiimote wiimote = wiimotes[0];
                wiimote.deactivateIRTRacking();
                wiimote.activateIRTRacking();
               
                wiimote.addWiiMoteEventListeners(objLoad);
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                    	objLoad.setVisible(true);
                    }
                });
                
                
   
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
			toggle=1;
			//System.out.println("in if point1");
			
			newx = IRPoints[0].getX();
			newy = IRPoints[0].getY();
			
			xfactor=(double)(newx-440)/760;
			yfactor=(double)(newy-440)/760;
			
			System.out.println("nx"+xfactor+"ny:"+yfactor);
			xfactor*=150;
			yfactor*=150;
			awtBehavior.rotate();
			
		}
		
		
		
		if (IRPoints.length == 2) {
			toggle=2;
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
		
		if (IRPoints.length == 3) {
			toggle=3;
			try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			x11 = IRPoints[0].getX();
			y11 = IRPoints[0].getY();
			x21 = IRPoints[1].getX();
			y21 = IRPoints[1].getY();
			double theta21 = 0;
			try{
				m_new1=(y21-y11)/(x21-x11);	
				
				theta21=Math.atan(m_new1);
				
			}
			catch(ArithmeticException f)
			{
				System.out.println("divide by zero"+f);
			}
			theta1=theta21;
			
			System.out.println("m_new1: " + m_new1+"Theta1: "+theta1);
			olddist=newdist;
			
			newdist= e.getDistance();
			if(olddist-newdist>2 || (olddist-newdist)<-2)
			{
				scalefac=(newdist*0.00625);	
			}
			
			//System.out.println("Distance"+newdist);
			
			awtBehavior.rotate();
		}
		

		if (IRPoints.length == 4) {
			toggle=4;
			try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			x14 = IRPoints[0].getX();
			y14 = IRPoints[0].getY();
			x24 = IRPoints[1].getX();
			y24 = IRPoints[1].getY();
			double theta24 = 0;
			try{
				m_new4=(y24-y14)/(x24-x14);	
				
				theta24=Math.atan(m_new4);
				
			}
			catch(ArithmeticException f)
			{
				System.out.println("divide by zero"+f);
			}
			theta4=theta24;
			
			System.out.println("m_new4: " + m_new4+"Theta4: "+theta4);
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
