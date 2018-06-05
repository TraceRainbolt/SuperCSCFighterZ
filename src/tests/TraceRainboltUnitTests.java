//Author: Trace Rainbolt
package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.EnergyBar;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;

public class TraceRainboltUnitTests {
	// Used to allow JavaFX to run
	public static class AsNonApp extends Application {
		@Override
		public void start(Stage primaryStage) throws Exception {
		}
	}

	@BeforeClass
	public static void initJFX() throws InterruptedException {
		Thread t = new Thread("JavaFX Init Thread") {
			public void run() {
				try {
					Application.launch(AsNonApp.class, new String[0]);
				} catch (Exception e) {
					// Don't launch another application if one is already running
				}	
			}
		};
		t.setDaemon(true);
		t.start();
		Thread.sleep(500);
	}

	@Test
	public void testEnergyBarCheckEnergyIncrease() throws InterruptedException {
		
		try {
			Window.main(null);
		}
		catch(Exception e) {
			
		}
		EnergyBar energyBar = new EnergyBar();
		energyBar.increase();
		energyBar.increase();
		
		assertTrue(energyBar.checkEnergy(2));
	}

	@Test
	public void testEnergyBarCheckEnergyDecrease() throws InterruptedException {
		
		try {
			Window.main(null);
		}
		catch(Exception e) {
			
		}
		EnergyBar energyBar = new EnergyBar();
		energyBar.increase();
		energyBar.increase();
		energyBar.decrease();
		energyBar.decrease();
		
		assertFalse(energyBar.checkEnergy(2));
	}

}
