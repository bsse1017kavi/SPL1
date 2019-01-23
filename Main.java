package testPackage;

import java.io.File;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("GameTest");
		final Pane pane = new Pane();
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		
		String audio = "C:/Users/User/eclipse-workspace/GameTest/src/testPackage/audio1.mp3";
		
		Media media = new Media(new File(audio).toURI().toString());
		
		MediaPlayer mp = new MediaPlayer(media);
		
		
		Canvas canvas = new Canvas(512,512);
		pane.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		String file_path = "file:C:/Users/User/eclipse-workspace/GameControlBasic/src/application/B-man/bMan";
		
		String img_file_path = "file:C:/Users/User/eclipse-workspace/GameTest/src/testPackage/img2.jpg";
		
		/*final Text tx = new Text("Hi");
		tx.setX(400);
		tx.setY(500);*/
		
		//pane.getChildren().add(tx);
		
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
		Image bman1_s = new Image(file_path+"1_S.png");
		
		Image background = new Image(img_file_path);
		
		double duration = 0.100;
		
		Image [] imageArr = new Image[8];
		Image [] imageArr1 = new Image[8];
		for(int i=0;i<8;i++)
		{
			imageArr[i] = new Image(file_path+ "_" + i + ".png" );
		}
		
		for(int i=0;i<8;i++)
		{
			imageArr1[i] = new Image(file_path+ "1_" + i + ".png" );
		}
		
		AnimatedImage bman = new AnimatedImage(imageArr, duration);
		
		AnimatedImage bman1 = new AnimatedImage(imageArr1, duration);
		
		final long startNanoTime = System.nanoTime();
		
		new AnimationTimer()
		{
			double x = 0;
			double y = 230;
			
			double distance = 0;
			
			
			
			
			
			boolean dir = false,lpassable = true,rpassable = true;
			
			double backgroundX = 0;
			double backgroundY = 230;
			
			@Override
			public void handle(long arg0) 
			{
				mp.setAutoPlay(true);
				
				gc.clearRect(0, 0, 512, 512);
				
				long currentNanoTime = System.nanoTime();
				
				double t = (currentNanoTime - startNanoTime)/1000000000.0;
				
				gc.drawImage(background, backgroundX, backgroundY);
				
				gc.drawImage(background, backgroundX+300, backgroundY);
				
				gc.drawImage(background, backgroundX-300, backgroundY);
				
				if(!input.contains("RIGHT") && !input.contains("LEFT") && !dir) gc.drawImage(bman_s, x, y);
				
				else if(!input.contains("RIGHT") && !input.contains("LEFT") && dir) gc.drawImage(bman1_s, x, y);
				
				//else if(!input.contains("RIGHT") && input.contains("LEFT") && dir && !passable) gc.drawImage(bman1_s, x, y);
				
				if(backgroundX<-90) backgroundX = 0;
				
				if(backgroundX>302) backgroundX = 0;
				
				if(x<10) lpassable = false;
				
				else lpassable = true;
				
				if(x>402) rpassable = false;
				
				else rpassable = true;
				
				
				/*gc.drawImage(background, backgroundX+500, backgroundY);*/
				
				if(input.contains("RIGHT"))
				{
					if(x<200 || distance>1000 && rpassable)
					{
						x+=5;
						gc.drawImage(bman.getFrame(t), x, y);
					}
					
					else if(distance>1000 && !rpassable)
					{
						gc.drawImage(bman.getFrame(t), x, y);
					}
					
					else
					{
						gc.drawImage(bman.getFrame(t), x, y);
						backgroundX-=5;;
					}
					
					distance+=5;
					
					dir = false;
					
				}
				
				else if(input.contains("LEFT"))
				{
					if((distance>1000  || distance<100)  && lpassable)
					{
						x-=5;
						gc.drawImage(bman1.getFrame(t), x, y);
					}
					
					else if(x<10 && !lpassable)
					{
						gc.drawImage(bman1.getFrame(t), x, y);
					}
					
					else
					{
						gc.drawImage(bman1.getFrame(t), x, y);
						backgroundX+=5;;
					}
					
					distance-=5;
					
					dir = true;
				}
				
				//System.out.println(distance);
			}
		}.start();
		
		primaryStage.show();
	}

	public static void main(String[] args) 
	{
		launch(args);
	}

}
