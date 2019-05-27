package inputPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelController
{
    @FXML
    TextField tf1;

    public void onAction(ActionEvent e)
    {
        int val = 0;
        if(!tf1.getText().isEmpty()) val = Integer.parseInt(tf1.getText());
        InputEngine.setDist(val);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();

        System.out.println(InputEngine.getDist());
    }

    public String getText()
    {
        return tf1.getText();
    }
}
