package testPackage;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("GameTest");
		Pane pane = new Pane();
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		
		Canvas canvas = new Canvas(512,512);
		pane.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		String file_path = "file:C:/Users/User/eclipse-workspace/GameControlBasic/src/application/B-man/bMan";
		
		String img_file_path = "file:C:/Users/User/eclipse-workspace/GameTest/src/testPackage/img2.jpg";
		
		ArrayList<String> input = new ArrayList<String>();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				String code = event.getCode().toString();
				if(!input.contains(code))input.add(code);
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent event) 
			{
				String code = event.getCode().toString();
				input.remove(code);
			}
		});
		
		Image bman_s = new Image(file_path+"_S.png");
		
		Image background = new Image(img_file_path);
		
		double duration = 0.100;
		
		Image [] imageArr = new Image[8];
		for(int i=0;i<8;i++)
		{
			imageArr[i] = new Image(file_path+ "_" + i + ".png" );
		}
		
		AnimatedImage bman = new AnimatedImage(imageArr, duration);
		
		final long startNanoTime = System.nanoTime();
		
		new AnimationTimer()
		{
			double x = 0;
			double y = 230;
			
			double backgroundX = 0;
			double backgroundY = 230;
			
			@Override
			public void handle(long arg0) 
			{
				gc.clearRect(0, 0, 512, 512);
				
				long currentNanoTime = System.nanoTime();
				
				double t = (currentNanoTime - startNanoTime)/1000000000.0;
				
				gc.drawImage(background, backgroundX, backgroundY);
				
				gc.drawImage(background, backgroundX+300, backgroundY);
				
				if(!input.contains("RIGHT")) gc.drawImage(bman_s, x, y);
				
				if(backgroundX<-90) backgroundX = 0;
				
				
				/*gc.drawImage(background, backgroundX+500, backgroundY);*/
				
				if(input.contains("RIGHT"))
				{
					if(x<200)
					{
						x+=5;
						gc.drawImage(bman.getFrame(t), x, y);
					}
					
					else
					{
						gc.drawImage(bman.getFrame(t), x, y);
						backgroundX-=5;;
					}
					
				}
			}
		}.start();
		
		primaryStage.show();
	}

	public static void main(String[] args) 
	{
		launch(args);
	}

}
