package leJOS_oruga;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.robotics.navigation.DifferentialPilot;

public class BTOruga {
	static int xPosition;
	static int yPosition;
	static int gRotacion = 0;
	static DifferentialPilot pilot = new DifferentialPilot(30, 122.5, Motor.B, Motor.A);
	
	public static void main(String [] args)  throws IOException, InterruptedException
	{	
		pilot.setTravelSpeed(3);
		String connected = "Conexion\n exitosa!!!";
        String waiting = "Esperando\n conexion...";
        String closing = "Cerrando\n conexion...";

        
        pilot.reset();
        Motor.C.resetTachoCount();
		while (true)
		{
			LCD.drawString(waiting,1,0);
			LCD.refresh();

	        BTConnection btc = Bluetooth.waitForConnection();
	        
			LCD.clear();
			LCD.drawString(connected,1,0);
			LCD.refresh();	

			DataInputStream dataIn = btc.openDataInputStream();
			DataOutputStream dataOut = btc.openDataOutputStream();
			int option = -1;
			while(option != 0){
				option = dataIn.readInt();
				if(option == 1){
					trotar();
				}
				else if(option == 2){
					correr();
				}
				else if(option == 3){
					retroceder();
				}
				else if(option == 4){
					girarDerecha();
				}
				else if(option == 5){
					girarIzquierda();
				}
				else if(option == 6){
					chutar();
				}
				else if(option == 7){
					patear();
				}
				else{
					break;
				}
				dataOut.writeInt(xPosition);
				dataOut.writeInt(yPosition);
				dataOut.flush();
				}
			
			LCD.drawString(closing, 1, 0);
			dataIn.close();
			dataOut.close();
			btc.close();
			Thread.sleep(500);//damos tiempo para que evacue los datos
			}

		   
		}
		
	public static void trotar() throws InterruptedException{
		pilot.setTravelSpeed(150);
		pilot.travel(200);
		if(gRotacion == 0 || gRotacion == 360 || gRotacion == -360) actualizarPosicion(200,0);
		else if(gRotacion == 45 || gRotacion == -315 ) actualizarPosicion(100,100);
		else if(gRotacion == 90 || gRotacion == -270) actualizarPosicion(0,200);
		else if(gRotacion == 135 || gRotacion == -225) actualizarPosicion(-100,100);
		else if(gRotacion == 180 || gRotacion == -180) actualizarPosicion(-200,0);
		else if(gRotacion == 225 || gRotacion == -135) actualizarPosicion(-100,-100);
		else if(gRotacion == 270 || gRotacion == -90) actualizarPosicion(0,-200);
		else if(gRotacion == 315 || gRotacion == -45) actualizarPosicion(100,-100);
	}
	public static void correr() throws InterruptedException{
		pilot.setTravelSpeed(300);
		pilot.travel(200);
		if(gRotacion == 0 || gRotacion == 360 || gRotacion == -360) actualizarPosicion(200,0);
		else if(gRotacion == 45 || gRotacion == -315 ) actualizarPosicion(100,100);
		else if(gRotacion == 90 || gRotacion == -270) actualizarPosicion(0,200);
		else if(gRotacion == 135 || gRotacion == -225) actualizarPosicion(-100,100);
		else if(gRotacion == 180 || gRotacion == -180) actualizarPosicion(-200,0);
		else if(gRotacion == 225 || gRotacion == -135) actualizarPosicion(-100,-100);
		else if(gRotacion == 270 || gRotacion == -90) actualizarPosicion(0,-200);
		else if(gRotacion == 315 || gRotacion == -45) actualizarPosicion(100,-100);
	}
	public static void retroceder() throws InterruptedException{
		pilot.setTravelSpeed(150);
		pilot.travel(-150);
		if(gRotacion == 0 || gRotacion == 360 || gRotacion == -360) actualizarPosicion(-150,0);
		if(gRotacion == 45 || gRotacion == -315 ) actualizarPosicion(-75,-75);
		if(gRotacion == 90 || gRotacion == -270) actualizarPosicion(0,-150);
		if(gRotacion == 135 || gRotacion == -225) actualizarPosicion(75,-75);
		if(gRotacion == 180 || gRotacion == -180) actualizarPosicion(150,0);
		if(gRotacion == 225 || gRotacion == -135) actualizarPosicion(75,75);
		if(gRotacion == 270 || gRotacion == -90) actualizarPosicion(0,150);
		if(gRotacion == 315 || gRotacion == -45) actualizarPosicion(-75,75);
	}
	public static void girarDerecha() throws InterruptedException{
		pilot.setTravelSpeed(10);
		pilot.rotate(-170);
		actualizarGRotacion(45);
	}
	public static void girarIzquierda() throws InterruptedException{
		pilot.setTravelSpeed(10);
		pilot.rotate(170);
		actualizarGRotacion(-45);
	}
	public static void chutar() throws InterruptedException{
		Motor.C.setSpeed(200);
		Motor.C.rotateTo(-30);
		Motor.C.rotateTo(-5);
	}
	public static void patear() throws InterruptedException{
		Motor.C.setSpeed(400);
		Motor.C.rotateTo(-40);
		Motor.C.rotateTo(-5);
	}
	
	public static void actualizarPosicion(int x, int y){
		xPosition += x;
		yPosition += y;
	}
	
	public static void actualizarGRotacion(int g){
		gRotacion += g;
		if(gRotacion > 360){
			gRotacion = gRotacion -360;
		}
		else if (gRotacion < -360){
			gRotacion = gRotacion + 360;
		}
	}
}