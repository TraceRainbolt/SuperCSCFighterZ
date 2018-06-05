//Author: Connor Alvin
package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fighterz.main.Fighter;
import com.fighterz.main.FighterFalessi;
import com.fighterz.main.FightingStage;
import com.fighterz.main.Window;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestFightingStage {
	
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
	public void testFightingStageSetup() throws InterruptedException {
		try {
			Window.main(null);
		}
		catch(Exception e) {
			
		}
		
		FightingStage fs = Window.getGame().getFightingStage();
		
		Fighter f = new FighterFalessi("right");
		
		fs.setFighterRight(f);
		
		assertNotNull(fs.getFighterRight());
		
	}
	
	@Test
	public void testFightingStageSetup2() throws InterruptedException {
		try {
			Window.main(null);
		}
		catch(Exception e) {
			
		}
		
		FightingStage fs = Window.getGame().getFightingStage();
		
		Fighter f = new FighterFalessi("right");
		
		fs.setFighterRight(f);
		
		fs.nullFighters();
		
		assertNull(fs.getFighterRight());
		
	}

}
