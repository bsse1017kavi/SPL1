package storyPackage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import mainPackage.GameEngine;
import mainPackage.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StoryEngine implements Initializable
{
    @FXML
    Label tx1;
    @FXML Label tx2;
    ArrayList<String> lines = new ArrayList<>();

    @FXML
    ImageView iv;

    Image nilkomol = new Image("nilkomol_standing.gif");
    Image shakchunni = new Image("sakchunni_standing.gif");

    @FXML
    AnchorPane root0;

    int i = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Image im = new Image("bangoma.gif",628,294,true,true);
        iv.setImage(im);
        tx1.setText("");
        tx2.setText("");
        InputStream inputStream = ResourceLoader.load("story.txt");
        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root0.widthProperty().divide(600));
        scale.yProperty().bind(root0.heightProperty().divide(400));
        root0.getTransforms().add(scale);
        try
        {
            Scanner sc = new Scanner(inputStream);
            while(sc.hasNextLine())
            {
                lines.add(sc.nextLine());
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setText(KeyEvent e) throws MalformedURLException
    {
        if(e.getCode() == KeyCode.ENTER) {
            if (i == lines.size())
            {
                Pane root = new Pane();
                Scene scene = new Scene(root);
                Scale scale = new Scale(1, 1, 0, 0);
                scale.xProperty().bind(root.widthProperty().divide(900));
                scale.yProperty().bind(root.heightProperty().divide(473));
                root.getTransforms().add(scale);

                Canvas canvas = new Canvas(900,473);
                root.getChildren().add(canvas);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                GameEngine gameEngine = new GameEngine(gc,scene,1);
                gameEngine.play();
                Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.setResizable(true);
                window.setFullScreen(true);
                window.show();
            }
            else {
                tx1.setText(lines.get(i));
                i++;
                if(i!= lines.size())
                {
                    tx2.setText(lines.get(i));
                    i++;
                }
                else tx2.setText("");
            }

            if(i==lines.size()-2)
            {
                iv.setImage(shakchunni);
            }

            if(i==lines.size())
            {
                iv.setImage(nilkomol);
            }
        }
    }
}
