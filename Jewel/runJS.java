/*
 * Copyright © «Технополис» 2005. Авторские права принадлежат ООО «Технополис» г.Красноярск. Директор Козяривский Игорь Анатольевич.
 */
package Jewel;

import com.borland.dx.dataset.DataSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Старт выполнения файлов javascript
 *
 * @author ShOleg 14-02-2018
 */
public class runJS {

    String pathFile;
    DataSet ds;

    public runJS(String pathFile, DataSet ds) {
        this.pathFile = pathFile;
        this.ds = ds;

        try {

            InputStreamReader br = new InputStreamReader(new FileInputStream(pathFile), "utf-8");
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            Invocable inv = (Invocable) engine;
            engine.put("act", inv);
            if (ds != null) {
                engine.put("data", ds);
            }

            engine.eval(br);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(runJS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(runJS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex) {
            Logger.getLogger(runJS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
