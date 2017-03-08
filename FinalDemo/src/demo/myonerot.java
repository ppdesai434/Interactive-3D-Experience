
package demo;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
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
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.JPopupMenu;

import wiiusej.wiiusejevents.utils.WiimoteListener;


public class myonerot extends javax.swing.JFrame implements WiimoteListener {
	private int rotnum;
    private SimpleUniverse univ = null;
    private BranchGroup scene = null;

    private TransformGroup objTrans;
    private RotateBehavior awtBehavior;
    public BranchGroup createSceneGraph() {
	
	BranchGroup objRoot = new BranchGroup();

	objTrans = new TransformGroup();
	objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRoot.addChild(objTrans);

	
	objTrans.addChild(new ColorCube(0.4));
System.out.println("Awt behavior flag");
		
       
        
        awtBehavior = new RotateBehavior(objTrans);
	BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
						   100.0);
	
	awtBehavior.setSchedulingBounds(bounds);
	
	objRoot.addChild(awtBehavior);
	

	return objRoot;
    }

    private Canvas3D createUniverse() {
	GraphicsConfiguration config =
	    SimpleUniverse.getPreferredConfiguration();

	Canvas3D c = new Canvas3D(config);

	univ = new SimpleUniverse(c);

	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
        univ.getViewingPlatform().setNominalViewingTransform();

	// Ensure at least 5 msec per frame (i.e., < 200Hz)
	univ.getViewer().getView().setMinimumFrameCycleTime(20);

	return c;
    }

    /**
     * Creates new form SwingInteraction
     */
    public myonerot() {
        // Initialize the GUI components
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        initComponents();

        // Create Canvas3D and SimpleUniverse; add canvas to drawing panel
        Canvas3D c = createUniverse();
        drawingPanel.add(c, java.awt.BorderLayout.CENTER);

        // Create the content branch and add it to the universe
        scene = createSceneGraph();
        univ.addBranchGraph(scene);
    }

    /**
     * Behavior class that waits for a behavior post from the AWT event handler
     */
    
   
    
    
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
            if(rotnum==6)
            	{angle += Math.toRadians(10.0);
            trans.rotY(angle);
            transformGroup.setTransform(trans);
            wakeupOn(criterion);
            	}
            else if(rotnum==4)
        	{angle -= Math.toRadians(10.0);
        		trans.rotY(angle);
        		transformGroup.setTransform(trans);
        		wakeupOn(criterion);
        	}
            else if(rotnum==8)
        	{angle -= Math.toRadians(10.0);
        		trans.rotX(angle);
        		transformGroup.setTransform(trans);
        		wakeupOn(criterion);
        	}
            else if(rotnum==2)
        	{angle += Math.toRadians(10.0);
        		trans.rotX(angle);
        		transformGroup.setTransform(trans);
        		wakeupOn(criterion);
        	}
            
            
            
        }

        // when the mouse is clicked, postId for the behavior
        void rotate() {
            postId(ROTATE);
        }
    }

    // ----------------------------------------------------------------
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        guiPanel = new javax.swing.JPanel();
        rotateButton = new javax.swing.JButton();
        drawingPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wiimote Interaction Test");
        guiPanel.setLayout(new java.awt.GridBagLayout());

        rotateButton.setText("Rotate");
        rotateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        guiPanel.add(rotateButton, gridBagConstraints);

        getContentPane().add(guiPanel, java.awt.BorderLayout.NORTH);

        drawingPanel.setLayout(new java.awt.BorderLayout());

        drawingPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        getContentPane().add(drawingPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rotateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateButtonActionPerformed
        awtBehavior.rotate();
    }//GEN-LAST:event_rotateButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
        Wiimote wiimote = wiimotes[0];
        wiimote.activateIRTRacking();
        wiimote.activateMotionSensing();
        final myonerot r = new myonerot();
        
        wiimote.addWiiMoteEventListeners(r);
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
            	r.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel drawingPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JPanel guiPanel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton rotateButton;
    // End of variables declaration//GEN-END:variables

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.isButtonRightPressed())
		{
			
			System.out.println("-> PRessed");
			rotnum=6;
			awtBehavior.rotate();
			
		}
		if(arg0.isButtonLeftPressed())
		{	
			System.out.println("<- PRessed");
			rotnum=4;
			awtBehavior.rotate();
			
		}
		
		if(arg0.isButtonDownPressed())
		{
			System.out.println("\\/ PRessed");
			rotnum=2;
			awtBehavior.rotate();
		}
		if(arg0.isButtonUpPressed())
		{
			System.out.println("/\\ PRessed");
			rotnum=8;
			awtBehavior.rotate();
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
