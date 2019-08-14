/*
 * Copyright © «Технополис» 2005. Авторские права принадлежат ООО «Технополис» г.Красноярск. Директор Козяривский Игорь Анатольевич.
 */
package Jewel;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import technopolis.action.RunAction;

/**
 * Старт выполнения файлов fxml
 * @author ShOleg
 * 14-02-2018
 */
public class runFXML {

    public runFXML(String pathFile) {
        try {
            URL url = new URL(pathFile);
            Parent root = FXMLLoader.load(url, null, null, null, Charset.forName("UTF-8"));

            Stage dialogStage = new Stage();
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.show();

        } catch (IOException ex) {
            Logger.getLogger(RunAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
