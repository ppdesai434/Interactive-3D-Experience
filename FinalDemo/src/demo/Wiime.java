package demo;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.values.IRSource;
import wiiusej.values.Orientation;
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

public class Wiime implements WiimoteListener {

	/**
	 * @param args
	 */
	static Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
	static Wiimote wiimote;
	int cnt;
	int cntg,lightcnt;
	float gX , gY ,gXn , gYn , gXt ,gYt;
	Orientation tempg;
	void loopem()
	{
		for(int i=0;i!=65980;i++)
		{
			for(int i1=0;i1!=6000;i1++)
			{
				
			}
		}
	}
	void lighting(){
		//int a = cnt % 4;
		int a = lightcnt % 4;
		switch (a)
		{
		case 0:
			wiimote.setLeds(true, false, false, false);
			loopem();
			cnt++;
			break;
			
		case 1:
			wiimote.setLeds(false, true, false, false);
			loopem();
			cnt++;
			break;
			
		case 2:
			wiimote.setLeds(false, false, true, false);
			loopem();
			cnt++;
			break;
			
		case 3:
			wiimote.setLeds(false, false, false, true);
			loopem();
			cnt++;
			break;
			
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        wiimote = wiimotes[0];
        //wiimote.activateIRTRacking();
        wiimote.activateMotionSensing();
        wiimote.addWiiMoteEventListeners(new Wiime());
        wiimote.getStatus();
       
	}

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
		
		
		// TODO Auto-generated method stub
		/*System.out.println("Abso X:"+e.getAx());
		System.out.println("Abso Y:"+e.getAy());
		System.out.println("X:"+e.getX());
		System.out.println("Y:"+e.getY());
		System.out.println("Z:"+e.getZ());
		
		System.out.println("****************");*/
	
	  
		//IRSource[] f = e.getIRPoints();
		//f[0]
				
		
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent e) {
		// TODO Auto-generated method stub
		//lighting();
		if(cntg==0)
		{
			tempg  = e.getOrientation();
			gX = tempg.getPitch();
		
		}
		else
		{
			tempg  = e.getOrientation();
			gXn =  tempg.getPitch();
			
		}
		
		
		
		
		if(gX>gXn)
		{
			lightcnt++;
			lighting();
		}
		else if(gX<gXn)
		{
			lightcnt--;
			lighting();
		}
		
		
		
		gX = gXn;
		gY = gYn;
		cntg++;
		
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
		if(!(e.isSpeakerEnabled()))
		{
			System.out.println("Speaker Inactive");
		}
	}

	
	
	
}
