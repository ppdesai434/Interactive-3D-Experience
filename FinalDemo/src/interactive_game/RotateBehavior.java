package interactive_game;

import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnBehaviorPost;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

class RotateBehavior extends Behavior {

	private TransformGroup transformGroup;
	private Transform3D trans = new Transform3D();
	private WakeupCriterion criterion;
	private float angle = 0.0f;
	private final float									TRANSLATE_LEFT = -0.05f;
	private final float									TRANSLATE_RIGHT = 0.05f;

	private final int ROTATE = 1;

	// create a new RotateBehavior
	RotateBehavior(TransformGroup tg) {
		transformGroup = tg;
	}

	// initialize behavior to wakeup on a behavior post with id = ROTATE
	public void initialize() {
		criterion = new WakeupOnBehaviorPost(this, ROTATE);
		wakeupOn(criterion);
		try{
			transformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
			transformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );	
		}
		catch(Exception e)
		{
			
		}
		
		
	}

	// processStimulus to rotate the cube
	public void processStimulus(Enumeration criteria) {

	
		wakeupOn(criterion);
		
		Vector3f translate = new Vector3f( );

		Transform3D t3d = new Transform3D( );
		transformGroup.getTransform( t3d );
		t3d.get( translate );

		switch (AvatarTest.keyCode)
		{
		case 1:
			translate.x += TRANSLATE_LEFT;
			break;

			
		case 2:
			translate.x += TRANSLATE_RIGHT;
			break;
		}

		// System.out.println( "Steering: " + translate.x );                   
		translate.y = 0.5f;

		t3d.setTranslation( translate );
		transformGroup.setTransform( t3d );

		
		
		
		
		
		

	}

	// when the mouse is clicked, postId for the behavior
	void rotate() {
		postId(ROTATE);
	}
}