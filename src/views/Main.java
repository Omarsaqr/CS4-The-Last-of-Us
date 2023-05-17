package views;

import engine.Game;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import model.characters.*;
import model.collectibles.Vaccine;
import model.world.*;

public class Main extends Application {
	
	static Hero currentHero;
	static GridPane gridPane = new GridPane();
	static Statistics statistics = new Statistics();
	
	public void start(Stage primaryStage) throws Exception {
		
		Game.loadHeroes("test_heros.csv");
		
		Game.startGame(Game.availableHeroes.remove(0));
		
		primaryStage.setTitle("The Last Of Us - Legacy");
		
		BorderPane borderPane = new BorderPane();
		BorderPane borderPane2 = new BorderPane();
		HBox hBox = new HBox();
		Game.availableHeroes.forEach(hero ->{
			hBox.getChildren().add(new Button(hero.getName()));
		});
		
		
		
		gridPane.setAlignment(Pos.CENTER);
		
		updateMap();
		
		Controls controls = new Controls();
		
		
		borderPane.setCenter(gridPane);
		
		borderPane.setLeft(controls);
		borderPane.setRight(statistics);
		
		statistics.setStatistics("Hero1");
		borderPane2.setCenter(hBox);
		borderPane2.setLeft(statistics);
		
		
	
	
		
		Scene scene = new Scene(borderPane);
		Scene loadingScreen = new Scene(borderPane2);
		
		primaryStage.setScene(loadingScreen);
		
		primaryStage.show();
		
	}
	
	public static void updateMap() {
	
		gridPane.getChildren().clear();
		statistics.updateStatistics();
		
		for (int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				
				
				if(Game.map[j][i] instanceof CollectibleCell)
					if(((CollectibleCell)Game.map[j][i]).getCollectible() instanceof Vaccine)
						gridPane.add(new VaccineCellView(), i, j);
					else
						gridPane.add(new SupplyCellView(), i, j);
				
				
				else if(Game.map[j][i] instanceof TrapCell)
					gridPane.add(new TrapCellView(), i, j);
				
				else if(Game.map[j][i] instanceof CharacterCell && ((CharacterCell) Game.map[j][i] ).getCharacter() instanceof Hero) {
					
					Hero hero = (Hero) ((CharacterCell) Game.map[j][i] ).getCharacter();
					HeroCellView heroCellView = new HeroCellView(hero); 
					
					heroCellView.getHeroView().setHealth(hero.getCurrentHp()/hero.getMaxHp() * 100);
					
					gridPane.add(new HeroCellView(hero), i, j);
				}else if(Game.map[j][i] instanceof CharacterCell && ((CharacterCell) Game.map[j][i] ).getCharacter() instanceof Zombie)
					gridPane.add(new ZombieCellView(), i, j);
				else
					gridPane.add(new CellView(), i, j);
				
			}
		}
		
		
	}
	
	public static int[] transform (int x , int y) {
		
		return new int[] {14-y,x};
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}