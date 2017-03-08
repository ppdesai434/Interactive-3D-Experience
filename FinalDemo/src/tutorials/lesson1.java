package tutorials;

//First we import packages that I use for Java3D
import java.awt.Frame;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class lesson1 extends Applet { // notice'lesson01', which is also the name of the file : lesson01.java
 SimpleUniverse simpleU; // this is the SimpleUniverse Class that is used for Java3D
	
	public lesson1 (){  // this constructor is sometimes needed, even when empty as in here    
	}    

	public void init() { 
	// this function will be called by both applications and applets
	//this is usually the first function to write        
	setLayout(new BorderLayout()); // standard Java code for BorderLayout

	// Canvas3D is where all the action will be taking place, don't worry, after adding it
	// to your layout, you don't have to touch it.    	
	Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration()); 

	// add Canvas3D to center of BorderLayout
 	add("Center", c);    

	simpleU= new SimpleUniverse(c); // setup the SimpleUniverse, attach the Canvas3D

	
	//This is very important, the SceneGraph (where all the action takes place) is created
	//by calling a function which here is called 'createSceneGraph'.
	//The function is not necessary, you can put all your code here, but it is a 
	//standard in Java3D to create your SceneGraph contents in the function 'createSceneGraph'

 	BranchGroup scene = createSceneGraph(); 

	//set the ViewingPlatform (where the User is) to nominal, more on this in the next lesson
     simpleU.getViewingPlatform().setNominalViewingTransform();

	// this will optimize your SceneGraph, not necessary, but it will allow your program to run faster.
     scene.compile(); 
     simpleU.addBranchGraph(scene); //add your SceneGraph to the SimpleUniverse   
 }

 public BranchGroup createSceneGraph() {      
	//Here we will create a basic SceneGraph with a ColorCube object

	// This BranchGroup is the root of the SceneGraph, 'objRoot' is the name I use,
	// and it is typically the standard name for it, but can be named anything.
	BranchGroup objRoot = new BranchGroup(); 

	// create a ColorCube object of size 0.5
	ColorCube c = new ColorCube(0.5f);
	
	// add ColorCube to SceneGraph
	objRoot.addChild(c);

	// return Scene Graph
	return objRoot;
	}

 public void destroy() {	// this function will allow Java3D to clean up upon quiting
	simpleU.removeAllLocales();    
 }  

 public static void main(String[] args) {
	// if called as an application, a 500x500 window will be opened    
     Frame frame = new MainFrame(new lesson1(), 500, 500);    
 }
}