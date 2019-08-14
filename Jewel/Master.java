/*
 * Copyright © «Технополис» 2005. Авторские права принадлежат ООО «Технополис» г.Красноярск. Директор Козяривский Игорь Анатольевич.
 */
package Jewel;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.Locate;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;
import java.awt.Desktop;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.VALUE_STRING;
import javax.script.Invocable;
import javax.script.ScriptException;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import technopolis.Designer.Contact.ContactUserProperty;
import technopolis.MainFrame;
import technopolis.MetaSerializ.FileLoader;
import technopolis.RunITec;

import technopolis.action.RunAction;
import technopolis.action.RunDealer;
import technopolis.constant.iTecBrowserCodeNode;
import technopolis.constant.iniFile.IniConst;
import technopolis.constant.iniFile.IniSettings;
import technopolis.enterprise.ConstantEnterprise;
import technopolis.explorer.ReferenceHelper;
import technopolis.explorer.TechExplorer;
import technopolis.explorer.TechExplorerPanel;
import technopolis.explorer.View;
import technopolis.javaFx.DecimalField;
import technopolis.javaFx.DoubleField;
import technopolis.javaFx.NumberField;
import technopolis.javaFx.NumberSpinner;
import technopolis.javaFx.iTecTableView;
import technopolis.query.AbstractQuery;

import technopolis.tool.RunSQLFile;
import technopolis.util.FileUtil;
import technopolis.модель.ContactRight;
import technopolis.модель.Dealer;
import technopolis.модель.DealerHelper;
import technopolis.модель.iTecModel;
import technopolis.модель.modelResources;
import technopolis.модель.Пользователь;

/**
 * Библиотека функций и методов прикладного языка программирования
 *
 * @author ShOleg 14-02-2018
 */
public class Master {

    Stage dialogStage = new Stage();
    GridPane grid = new GridPane();

    DataSet dataSet = null;
    JsonObjectBuilder returnVar = Json.createObjectBuilder();
    String ret = "";
    VBox vbox = new VBox(5);

    BorderPane border = new BorderPane();
    Scene scene = null;

    Invocable inv;
    String style = "-fx-font-size:14px";
    String useTable = null;

    boolean retForm = false;

    public Master() {

        JInternalFrame[] frame = MainFrame.getDeskTop().getAllFrames();
        for (JInternalFrame internalFrame : frame) {
            if (internalFrame instanceof TechExplorer) {
                if (internalFrame.isSelected()) {
                    dataSet = ((TechExplorer) internalFrame).getTechDataSet().getQueryDataSet();
                }
            }
        }
    }

    public Master(Invocable inv) {
        this.inv = inv;
    }

    public Master(Invocable inv, DataSet dataSet) {
        this.inv = inv;
        this.dataSet = dataSet;    
    }

    public Master(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public void showDictTech() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dealer handbook = DealerHelper.getDealer("Словарь.ЛьготаИмущество");
                Variant var = ReferenceHelper.showHandBook(handbook, "", "Код", "");
            }
        });

    }

    public void showDict() {
        final SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dealer d = DealerHelper.getDealer("Словарь.ЛьготаИмущество");
                final TechExplorerPanel panel = new TechExplorerPanel(View.Table, d, null);

                swingNode.setContent(panel);
            }
        });

        Pane pane = new Pane();
        pane.getChildren().add(swingNode); // Adding swing node

        Stage stage = new Stage();
        stage.setScene(new Scene(pane, 100, 50));
        stage.show();
    }

    public void execObject(String className) {

        try {
            SwingUtilities.invokeAndWait(() -> {
                Dealer dealer = DealerHelper.getDealer(className);
                RunDealer r = new RunDealer();
                r.showITecObject(dealer, "");
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void execAction(String className) {
        RunAction.runAction(className);
    }

    public String execSQL(String SQL) {
        System.out.println("exec sql " + SQL);
        String ret = "";

        String[] sql = SQL.split(";");

        for (String string : sql) {
            System.out.println(string);
        }

        try {
            ret = RunSQLFile.makeCommand(technopolis.MainFrame.getDB_ТехКорпорация(), sql, null, "");
        } catch (Exception ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    public String getDBMS() {
        if (IniSettings.get(IniConst.DBMS) != null && IniSettings.get(IniConst.DBMS).contains("DB2")) {
            return "DB2";
        } else if (IniSettings.get(IniConst.DBMS) != null && IniSettings.get(IniConst.DBMS).contains("derby:net")) {
            return "derby:net";
        } else if (IniSettings.get(IniConst.DBMS) != null && IniSettings.get(IniConst.DBMS).contains("derby")) {
            return "derby";
        } else {
            return "";
        }
    }

    public void say(String text) {
        this.say(text, this.style);
    }

    public void log(String text) {
        System.out.println(text);
    }

    public void say(String text, String Style) {
        Label label1 = new Label(text);
        label1.setFont(new Font("Arial", 12));
        label1.setTextFill(Color.web("#0076a3"));
        label1.setTextAlignment(TextAlignment.JUSTIFY);
        label1.setStyle(Style);

        // grid.add(label1, 0, 0);
        vbox.getChildren().add(label1);
    }

    public JsonObject show() {
        dialogStage.show();
        return returnVar.build();
    }

    public JsonObject showModal() {
        dialogStage.showAndWait();
        return returnVar.build();
    }

    public String getMyParam(String name) {
        String xret = "";
        ContactUserProperty user = new ContactUserProperty(modelResources.findCluster(RunITec.профиль, iTecBrowserCodeNode.CODE_NODE_USER));
        ConstantEnterprise constEnt = technopolis.enterprise.ConstantEnterprise.getInstance(user.getПредприятие());

        switch (name) {
            case "Пользователь":
                xret = user.getКодПользователя();
                break;
            case "ПолноеИмя":
                xret = user.getПолноеИмя();
                break;
            case "РабочаяГруппа":
                xret = user.getРабочаяГруппа();
                break;
            case "Роль":
                xret = user.getРоль();
                break;
            case "Подразделение":
                xret = user.getПодразделение();
                break;
            case "Должность":
                xret = user.getДолжность();
                break;
            case "Телефон":
                xret = user.getТелефон();
                break;
            case "ЭлАдрес":
                xret = user.getЭлАдрес();
                break;
            case "ДатаРождения":
                //xret = technopolis.util.DateUtil.formatDate(user.getДатаРождения());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(user.getДатаРождения().getTime()));
                break;
            case "Пол":
                xret = user.getПол();
                break;
            case "ДатаСтажа":
                //xret = technopolis.util.DateUtil.formatDate(user.getДатаСтажа());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(user.getДатаСтажа().getTime()));
                break;
            case "ПланСчетов":
                xret = user.getПланСчетов();
                break;
            case "НачалоПериода":
                //xret = technopolis.util.DateUtil.formatDate(user.getСтартПериода());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(user.getСтартПериода().getTime()));
                break;
            case "КонецПериода":
                //xret = technopolis.util.DateUtil.formatDate(user.getКонецПериода());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(user.getКонецПериода().getTime()));
                break;
            case "ПоследнийСеанс":
                //xret = technopolis.util.DateUtil.formatDate(user.getПоследнийСеанс());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(user.getПоследнийСеанс().getTime()));
                break;
            case "РабочаяДата":
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(user.getРабочаяДата().getTime()));
                break;
            case "Предприятие":
                xret = user.getПредприятие();
                break;
            case "ПериодЗарплата":
                xret = constEnt.getCurrentPeriod();
                break;
            case "ПериодОС":
                xret = constEnt.getOSPeriod();
                break;
            case "ДатаНачалаПериода":
                //xret = technopolis.util.DateUtil.formatDate(constEnt.getDatePeriodStart());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(constEnt.getDatePeriodStart().getTime()));
                break;
            case "ДатаРасчетаСальдо":
                //xret = technopolis.util.DateUtil.formatDate(constEnt.getDateRaschetSaldo());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(constEnt.getDateRaschetSaldo().getTime()));
                break;
            case "ДатаРаннегоДокумента":
                //xret = technopolis.util.DateUtil.formatDate(constEnt.getDateEarlyDoc());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(constEnt.getDateEarlyDoc().getTime()));
                break;
            case "ДатаЗарплатаПериод":
                //xret = technopolis.util.DateUtil.formatDate(constEnt.getDateSalary());
                xret = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(constEnt.getDateSalary().getTime()));
                break;
            case "РангАвтозамок":
                xret = String.valueOf(constEnt.getLockSalary());
                break;
            case "РангАдминистратор":
                xret = String.valueOf(constEnt.getRangAdmin());
                break;
            case "ИмпортПуть":
                xret = constEnt.getImportPath();
                break;
            case "ЭкспортПуть":
                xret = constEnt.getExportPath();
                break;
            case "Домен":
                xret = getDomen();
                break;
            case "Сервер":
                xret = getServer();
                break;
            default:
                break;
        }

        return xret;
    }

    private String getDomen() {
        String domen = "";
        try {
            PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement("SELECT \"Домен\" FROM T.\"МоеПредприятие_Узел\"");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                domen = rs.getString("Домен");
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return domen;
    }

    private String getServer() {
        String server = "";
        try {
            PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement("SELECT \"Сервер\" FROM T.\"МоеПредприятие_Узел\"");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                server = rs.getString("Сервер");
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return server;
    }

    /*public String getString(String id) {
        String value = "";

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            
                if (node instanceof TextField) {
                    if ((((TextField) node).getId()).equals(id)) {
                        value = ((TextField) node).getText();
                        break;
                    }
                }
            
        }

        return value;
    }*/
    public String getText(String id) {
        String value = "";

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {
                    if (nodeDP instanceof TextField) {
                        if ((((TextField) nodeDP).getId()).equals(id)) {
                            value = ((TextField) nodeDP).getText();
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public String setText(String id, String value) {
        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {
                    if (nodeDP instanceof TextField) {
                        if ((((TextField) nodeDP).getId()).equals(id)) {
                            ((TextField) nodeDP).setText(value);
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public String getRadioButton(String id) {
        String value = "";

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {
                    if (nodeDP instanceof RadioButton) {
                        if ((((RadioButton) nodeDP).getId()).equals(id)) {
                            value = ((RadioButton) nodeDP).getText();
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public long getUnique() {
        long value = technopolis.utility.UniqueCode.get();
        return value;
    }

    public BigDecimal getNumber(String id) {
        BigDecimal value = BigDecimal.ZERO;

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {

                    if (nodeDP instanceof NumberField) {
                        if ((((NumberField) nodeDP).getId()).equals(id)) {
                            //value = Integer.parseInt(((NumberField) nodeDP).getText());
                            value = new BigDecimal(((NumberField) nodeDP).getText());
                            break;
                        }

                    }
                    if (nodeDP instanceof DecimalField) {
                        if ((((DecimalField) nodeDP).getId()).equals(id)) {
                            //value = Integer.parseInt(((DecimalField) nodeDP).getText());
                            value = new BigDecimal(((DecimalField) nodeDP).getText());
                            break;
                        }
                    }
                    if (nodeDP instanceof DoubleField) {
                        if ((((DoubleField) nodeDP).getId()).equals(id)) {
                            //value = Integer.parseInt(((DoubleField) nodeDP).getText());
                            value = new BigDecimal(((DoubleField) nodeDP).getText());
                            break;
                        }

                    } else if (nodeDP instanceof NumberSpinner) {
                        if ((((NumberSpinner) nodeDP).getId()).equals(id)) {
                            //value = ((NumberSpinner) nodeDP).getValue().intValue();
                            value = new BigDecimal(((NumberSpinner) nodeDP).getText().replace(",", "."));
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public BigDecimal getDecimal(String id) {
        BigDecimal value = BigDecimal.ZERO;

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {

                    if (nodeDP instanceof DecimalField) {
                        if ((((DecimalField) nodeDP).getId()).equals(id)) {
                            //value = Integer.parseInt(((DecimalField) nodeDP).getText());
                            value = new BigDecimal(((DecimalField) nodeDP).getText());
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public String getComboBox(String id) {
        String cb = "";

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {

                    if (nodeDP instanceof javafx.scene.control.ComboBox) {
                        if ((((javafx.scene.control.ComboBox) nodeDP).getId()).equals(id)) {
                            cb = (String) ((javafx.scene.control.ComboBox) nodeDP).getValue();
                            break;
                        }
                    }
                }
            }
        }

        return cb;
    }

    public Double getDouble(String id) {
        Double value = new Double(0);

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {

                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {

                    if (nodeDP instanceof DoubleField) {
                        if ((((DoubleField) nodeDP).getId()).equals(id)) {
                            //value = ((DoubleField) nodeDP);
                            value = Double.valueOf(((DoubleField) nodeDP).toString());
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public Number getNumberSpinner(String id) {
        Number value = 0;

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {
                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {
                    if (nodeDP instanceof NumberSpinner) {
                        if ((((NumberSpinner) nodeDP).getId()).equals(id)) {
                            value = ((NumberSpinner) nodeDP).getValue();
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    public String getDate(String id) {
        String xdate = null;
        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {

            if (node instanceof GridPane) {
                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {
                    if (nodeDP instanceof DatePicker) {
                        if ((((DatePicker) nodeDP).getId()).equals(id)) {
                            LocalDate date = ((DatePicker) nodeDP).getValue();

                            Calendar cal = new GregorianCalendar(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                            xdate = technopolis.util.DateUtil.formatDateSQL(new java.util.Date(cal.getTimeInMillis()));

                            break;
                        }
                    }
                }
            }
        }
        return xdate;
    }

    public boolean getBoolean(String id) {
        boolean value = true;

        ObservableList<Node> list = vbox.getChildren();
        for (Node node : list) {
            if (node instanceof GridPane) {
                GridPane gp = (GridPane) node;

                ObservableList<Node> listGP = gp.getChildren();

                for (Node nodeDP : listGP) {
                    if (nodeDP instanceof CheckBox) {
                        if ((((CheckBox) nodeDP).getId()).equals(id)) {
                            if (!((CheckBox) nodeDP).isSelected()) {
                                value = false;
                            }
                            break;
                        }
                    }
                }
            }
        }

        return value;
    }

    private void setEffect(Node node, JsonObject jo) {
        String effect;
        try {
            effect = jo.getString("effect");
        } catch (java.lang.NullPointerException ex) {
            effect = null;
        }

        if (effect != null && effect.equals("bloom")) {
            Bloom bloom = new Bloom();

            String threshold = "";
            try {
                //threshold = jo.getString("threshold");
                bloom.setThreshold(Double.valueOf("0.9"));
            } catch (java.lang.NullPointerException ex) {
                threshold = "";
            }

            node.setEffect(bloom);
        }

        if (effect != null && effect.equals("reflection")) {
            Reflection reflection = new Reflection();

            String bottomOpacity = "";
            String topOpacity = "";
            String topOffset = "";
            String fraction = "";
            try {
                /*bottomOpacity = jo.getString("bottomOpacity");
                topOpacity = jo.getString("topOpacity");
                bottomOpacity = jo.getString("topOffset");
                bottomOpacity = jo.getString("fraction");*/

                reflection.setBottomOpacity(Double.valueOf("0.0"));
                reflection.setTopOpacity(Double.valueOf("0.5"));
                reflection.setTopOffset(Double.valueOf("10.0"));
                reflection.setFraction(Double.valueOf("0.7"));

                node.setEffect(reflection);

            } catch (java.lang.NullPointerException ex) {
                System.out.print(ex.getMessage());
            }

        }

        if (effect != null && effect.equals("dropShadow")) {
            DropShadow dropShadow = new DropShadow();

            String radius = "";
            String offsetX = "";
            String offsetY = "";

            try {
                //  radius = jo.getString("radius");
                //  offsetX = jo.getString("offsetX");
                //  offsetY = jo.getString("offsetY");

                dropShadow.setRadius(Double.valueOf("3.0"));
                dropShadow.setOffsetX(Double.valueOf("5.0"));
                dropShadow.setOffsetY(Double.valueOf("5.0"));

                dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

                node.setEffect(dropShadow);

            } catch (java.lang.NullPointerException ex) {
                System.out.print(ex.getMessage());
            }

        }

        if (effect != null && effect.equals("boxBlur")) {
            BoxBlur boxBlur = new BoxBlur();

            String width = "";
            String height = "";
            String iterations = "";

            try {
                // width = jo.getString("width");
                // height = jo.getString("height");
                // iterations = jo.getString("iterations");

                // Min: 0.0 Max: 255.0
                boxBlur.setWidth(Double.valueOf("5.0"));

                // Min: 0.0 Max: 255.0
                boxBlur.setHeight(Double.valueOf("3.0"));

                // Min: 0 Max: 3
                boxBlur.setIterations(Integer.valueOf("2"));

                node.setEffect(boxBlur);

            } catch (java.lang.NullPointerException ex) {
                System.out.print(ex.getMessage());
            }

        }

        if (effect != null && effect.equals("glow")) {
            Glow glow = new Glow();
            glow.setLevel(0.9);
            node.setEffect(glow);;
        }

        if (effect != null && effect.equals("innerShadow")) {
            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setOffsetX(4);
            innerShadow.setOffsetY(4);
            innerShadow.setColor(Color.GRAY);

            node.setEffect(innerShadow);;
        }

        if (effect != null && effect.equals("lighting")) {
            Lighting lighting = new Lighting();
            node.setEffect(lighting);;
        }

    }

    private void setAlignment(Node node, JsonObject jo) {
        String alignment;
        try {
            alignment = jo.getString("alignment");
        } catch (java.lang.NullPointerException ex) {
            alignment = "left";
        }

        switch (alignment) {
            case "top":
                GridPane.setValignment(node, VPos.TOP);
                break;
            case "bottom":
                GridPane.setValignment(node, VPos.BOTTOM);
                break;
            case "right":
                GridPane.setHalignment(node, HPos.RIGHT);
                break;
            case "left":
                GridPane.setHalignment(node, HPos.LEFT);
                break;
            case "center":
                GridPane.setHalignment(node, HPos.CENTER);
                break;
            default:
                break;
        }

    }

    private void setState(Node node, JsonObject jo) {
        String state;
        try {
            state = jo.getString("state");
        } catch (java.lang.NullPointerException ex) {
            state = null;
        }

        if (state != null && state.equals("readonly")) {
            if (node instanceof TextField) {
                ((TextField) node).setEditable(false);
            }
        }

        if (state != null && state.equals("disable")) {
            node.setDisable(true);
        }

        if (state != null && state.equals("hidden")) {
            node.setVisible(false);
        }

    }

    private void setMargin(Node node, JsonObject jo) {
        Insets insetsMargin = null;

        JsonObject margin = jo.getJsonObject("margin");
        if (margin != null) {
            insetsMargin = new Insets(margin.getInt("top"), margin.getInt("right"), margin.getInt("bottom"), margin.getInt("left"));
        }

        if (insetsMargin != null) {
            GridPane.setMargin(node, insetsMargin);
        }

    }

    private void setPadding(Control node, JsonObject jo) {
        Insets insetsPadding = null;

        JsonObject padding = jo.getJsonObject("padding");
        if (padding != null) {
            insetsPadding = new Insets(padding.getInt("top"), padding.getInt("right"), padding.getInt("bottom"), padding.getInt("left"));
        }

        if (insetsPadding != null) {
            node.setPadding(insetsPadding);
        }
    }

    private void setTooltip(Control node, JsonObject jo) {
        String xtooltip;
        try {
            xtooltip = jo.getString("tooltip");
        } catch (java.lang.NullPointerException ex) {
            xtooltip = null;
        }

        Tooltip tooltip = new Tooltip();
        tooltip.setText(xtooltip);
        tooltip.setStyle("-fx-font-size:14px; -fx-background-color: #CCFF99; -fx-text-fill: black");

        if (xtooltip != null) {
            node.setTooltip(tooltip);
        }

    }

    private void setPrefWidth(Control node, JsonObject jo) {
        int size = 50;
        try {
            size = jo.getInt("size");
        } catch (java.lang.NullPointerException ex) {
            size = 0;
        }

        if (size != 0) {
            node.setPrefWidth(size);
        }
    }

    private void setStyle(Control node, JsonObject jo) {
        String xstyle;

        try {
            xstyle = jo.getString("style");
        } catch (java.lang.NullPointerException ex) {
            xstyle = this.style;
        }

        node.setStyle(xstyle);

    }

    private void setID(Control node, JsonObject jo) {
        try {
            node.setId(jo.getString("id"));
        } catch (java.lang.NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean form(String... var) {
        StringBuilder sb = new StringBuilder();
        sb = sb.append("[");
        for (String string : var) {
            sb = sb.append(string).append(",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        sb = sb.append("]");

        JsonReader jsonReader = Json.createReader(new StringReader(sb.toString()));
        JsonArray jsonObject = jsonReader.readArray();

        boolean fl = true;

        ObservableList<Node> listGP = vbox.getChildren();
        for (Node nodeDP : listGP) {
            if (nodeDP instanceof GridPane) {
                fl = false;
            }
        }

        if (fl) {
            vbox.getChildren().add(grid);
        }

        for (int i = 0; i < jsonObject.size(); i++) {
            JsonObject jo = jsonObject.getJsonObject(i);

            String type = jo.getString("type");

            String action = null;
            try {
                action = jo.getString("action");
            } catch (java.lang.NullPointerException ex) {
                action = null;
            }

            String[] xgrid = jo.getString("grid").split(",");
            int col = Integer.parseInt(xgrid[0]);
            int row = Integer.parseInt(xgrid[1]);

            int colspan = 1;
            int rowspan = 1;

            if (xgrid.length == 3) {
                colspan = Integer.parseInt(xgrid[2]);
            }

            if (xgrid.length == 4) {
                colspan = Integer.parseInt(xgrid[3]);
            }

            switch (type) {
                case "label":
                    getLabel(jo, col, row, colspan, rowspan);
                    break;
                case "text":
                    getTextField(jo, action, col, row, colspan, rowspan);
                    break;
                case "number":
                    getDecimalField(jo, action, col, row, colspan, rowspan);
                    break;
                case "numberspinner":
                    getNumberSpinner(jo, action, col, row, colspan, rowspan);
                    break;
                case "сheckBox":
                    getCheckBox(jo, action, col, row, colspan, rowspan);
                    break;
                case "button":
                    getButton(jo, action, col, row, colspan, rowspan);
                    break;
                case "date":
                    getDatePicker(jo, action, col, row, colspan, rowspan);
                    break;
                case "listview":
                    getListView(jo, action, col, row, colspan, rowspan);
                    break;
                case "combobox":
                    getComboBox(jo, action, col, row, colspan, rowspan);
                    break;
                case "radio":
                    getRadioButton(jo, action, col, row, colspan, rowspan);
                    break;
                default:
                    break;
            }
        }
        dialogStage.requestFocus();
        return retForm;
    }

    private ImageView getImageView(JsonObject jo) throws MalformedURLException {
        java.io.File xfile = new java.io.File(FileUtil.getAppServer() + "/Inspiration/" + jo.getString("value"));
        ImageView imageHouse = new ImageView(xfile.toURL().toString());
        return imageHouse;
    }

    private void SettingCommon(Control control, JsonObject jo) {
        setPrefWidth(control, jo);

        setAlignment(control, jo);

        setMargin(control, jo);

        setTooltip(control, jo);

        setPadding(control, jo);

        setEffect(control, jo);

        setStyle(control, jo);

        setID(control, jo);

        setState(control, jo);
    }

    private Label getLabel(JsonObject jo, int col, int row, int colspan, int rowspan) {
        Label lbl = new Label(jo.getString("value"));

        SettingCommon(lbl, jo);

        grid.add(lbl, col, row, colspan, rowspan);

        return lbl;
    }

    private TextField getTextField(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        TextField tf = new TextField();

        tf.setText(jo.getString("value"));

        SettingCommon(tf, jo);

        try {
            tf.setPromptText(jo.getString("placeholder"));
        } catch (java.lang.NullPointerException ex) {

        }

        if (action != null && tf.getAccessibleHelp() == null) {
            tf.setAccessibleHelp(action);
        }

        tf.setOnAction((javafx.event.ActionEvent event) -> {
            TextField tff = (TextField) event.getSource();

            try {
                Object obj = inv.invokeFunction(tff.getAccessibleHelp(), tf.getText());
                tf.setText((String) obj);
                System.out.println((String) obj);
            } catch (ScriptException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.add(tf, col, row, colspan, rowspan);

        return tf;
    }

    //          "\\d{0,7}([\\.]\\d{0,4})?"
    private DecimalField getDecimalField(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {

        String pattern = "";
        try {
            pattern = jo.getString("pattern").replace("/", "\\");
        } catch (java.lang.NullPointerException ex) {

            try {
                if (jo.getString("format").contains(".")) {
                    String[] x = jo.getString("format").replace(".", ",").split(",");
                    pattern = String.format("\\d{0,%s}([\\.]\\d{0,%s})?", (x[0]).length(), (x[1]).length());
                } else {
                    pattern = String.format("\\d{0,%s}?", jo.getString("format").length());
                }
            } catch (java.lang.NullPointerException ex1) {
                pattern = String.format("\\d{0,%s}?", 10);
            }
        }

        DecimalField df = new DecimalField(pattern);

        df.setText(jo.getJsonNumber("value").toString());

        SettingCommon(df, jo);

        if (action != null && df.getAccessibleHelp() == null) {
            df.setAccessibleHelp(action);
        }

        df.setOnAction((javafx.event.ActionEvent event) -> {
            DecimalField tff = (DecimalField) event.getSource();
            try {
                Object obj = inv.invokeFunction(tff.getAccessibleHelp(), tff.getText());
                tff.setText((String) obj);
            } catch (ScriptException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        grid.add(df, col, row, colspan, rowspan);
        return df;
    }

    private NumberField getNumberField(JsonObject jo, String xstyle) {
        int minValue = 0;
        int maxValue = 0;
        int value = 0;

        try {
            minValue = jo.getInt("min");
        } catch (java.lang.NullPointerException ex) {
            minValue = 0;
        }

        try {
            maxValue = jo.getInt("maxvalue");
        } catch (java.lang.NullPointerException ex) {
            maxValue = 100;
        }

        try {
            value = jo.getInt("value");
        } catch (java.lang.NullPointerException ex) {
            value = 0;
        }

        NumberField intf = new NumberField(minValue, maxValue, value);
        intf.setStyle(xstyle);
        intf.setId(jo.getString("id"));

        return intf;

    }

    private NumberSpinner getNumberSpinner(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        int minValue = 0;
        int maxValue = 0;
        int step = 1;

        try {
            minValue = jo.getInt("min");
        } catch (java.lang.NullPointerException ex) {
            minValue = 0;
        }

        try {
            maxValue = jo.getInt("max");
        } catch (java.lang.NullPointerException ex) {
            maxValue = 100;
        }

        try {
            step = jo.getInt("step");
        } catch (java.lang.NullPointerException ex) {
            step = 1;
        }

        NumberSpinner ns = new NumberSpinner();
        NumberFormat decimalNumberFormat = NumberFormat.getNumberInstance();
        decimalNumberFormat.setMinimumFractionDigits(1);
        ns.setNumberStringConverter(new NumberStringConverter(decimalNumberFormat));
        ns.setMinValue(minValue);
        ns.setMaxValue(maxValue);
        ns.setStepWidth(step);
        ns.setValue(jo.getInt("value"));
        ns.setHAlignment(HPos.RIGHT);
        ns.setPromptText("Введите число");

        SettingCommon(ns, jo);

        if (action != null && ns.getAccessibleHelp() == null) {
            ns.setAccessibleHelp(action);
        }

        grid.add(ns, col, row, colspan, rowspan);

        return ns;
    }

    private CheckBox getCheckBox(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        CheckBox cb = new CheckBox(jo.getString("value"));

        SettingCommon(cb, jo);

        if (action != null && cb.getAccessibleHelp() == null) {
            cb.setAccessibleHelp(action);
        }

        cb.setOnAction((javafx.event.ActionEvent event) -> {
            CheckBox tff = (CheckBox) event.getSource();

            try {
                Object obj = inv.invokeFunction(tff.getAccessibleHelp(), tff.isSelected());
                //tff.setText((String)obj);
            } catch (ScriptException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        grid.add(cb, col, row, colspan, rowspan);

        return cb;
    }

    private Button getButton(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        Button btn = new Button(jo.getString("value"));

        SettingCommon(btn, jo);

        if (action != null && btn.getAccessibleHelp() == null) {
            btn.setAccessibleHelp(action);
        }

        btn.setOnAction((javafx.event.ActionEvent event) -> {
            Button tff = (Button) event.getSource();

            try {
                Object obj = inv.invokeFunction(tff.getAccessibleHelp());
            } catch (ScriptException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        grid.add(btn, col, row, colspan, rowspan);

        return btn;
    }

    private void getRadioButton(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        Pane root = new HBox();
        final ToggleGroup group = new ToggleGroup();
        String[] v = jo.getString("value").split(",");
        for (String s : v) {
            RadioButton rb1 = new RadioButton(s);
            rb1.setUserData(s);
            rb1.setToggleGroup(group);

            SettingCommon(rb1, jo);

            root.getChildren().add(rb1);
        }

        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            System.out.println(group.getSelectedToggle().getUserData().toString());
        });

        grid.add(root, col, row, colspan, rowspan);
    }

    private javafx.scene.control.ComboBox getComboBox(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        String[] valCB = jo.getString("value").split(",");

        List<String> xlCB = new ArrayList<String>();
        for (String l : valCB) {
            xlCB.add(l);
        }

        javafx.scene.control.ComboBox control = new javafx.scene.control.ComboBox();

        ObservableList<String> itemsCB = FXCollections.observableArrayList(xlCB);
        control.setItems(itemsCB);
        control.setValue(xlCB.get(0));

        SettingCommon(control, jo);

        if (action != null && control.getAccessibleHelp() == null) {
            control.setAccessibleHelp(action);
        }

        control.setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                javafx.scene.control.ComboBox tff = (javafx.scene.control.ComboBox) event.getSource();

                try {
                    Object obj = inv.invokeFunction(tff.getAccessibleHelp(), tff.getValue());
                    //   tff.setValue(obj);
                } catch (ScriptException ex) {
                    Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        grid.add(control, col, row, colspan, rowspan);

        return control;
    }

    private void getListView(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        String[] val = jo.getString("value").split(",");

        ListView<String> list = new ListView<String>();
        List<String> xlist = new ArrayList<String>();
        for (String l : val) {
            xlist.add(l);
        }
        ObservableList<String> items = FXCollections.observableArrayList(xlist);
        list.setItems(items);

        SettingCommon(list, jo);

        list.setPrefHeight(xlist.size() * 15);

        if (action != null && list.getAccessibleHelp() == null) {
            list.setAccessibleHelp(action);
        }

        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            System.out.println(new_val);

            try {
                Object obj = inv.invokeFunction(list.getAccessibleHelp(), new_val);
                //      dp.getEditor().setText((String) obj);
            } catch (ScriptException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        grid.add(list, col, row, colspan, rowspan);
    }

    private DatePicker getDatePicker(JsonObject jo, String action, int col, int row, int colspan, int rowspan) {
        String pattern;

        try {
            pattern = jo.getString("pattern").toLowerCase();
        } catch (java.lang.NullPointerException ex) {
            pattern = "dd-MM-yyyy";
        }

        DatePicker datePicker = new DatePicker();

        datePicker.setPromptText(pattern);

        if (!jo.getString("value").isEmpty()) {
            datePicker.setValue(LocalDate.parse(jo.getString("value")));
        }

        SettingCommon(datePicker, jo);

        if (action != null && datePicker.getAccessibleHelp() == null) {
            datePicker.setAccessibleHelp(action);
        }

        datePicker.setOnAction((javafx.event.ActionEvent event) -> {
            DatePicker tff = (DatePicker) event.getSource();

            try {
                Object obj = inv.invokeFunction(tff.getAccessibleHelp(), tff.getEditor().getText());
                datePicker.getEditor().setText((String) obj);
            } catch (ScriptException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        grid.add(datePicker, col, row, colspan, rowspan);

        return datePicker;
    }

    public void winOpen(String text) {

        InputStream is = null;
        try {
            is = new ByteArrayInputStream(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        JsonReader jsonReader = Json.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();

        if (jsonObject.containsKey("time")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(jsonObject.getInt("time")));
            delay.setOnFinished(event -> dialogStage.close());
            delay.play();
        }

        dialogStage.setTitle(jsonObject.getString("title"));

        String screen;

        try {
            screen = jsonObject.getString("screen");
        } catch (java.lang.NullPointerException ex) {
            screen = null;
        }

        int[] l = detLocation(screen);

        dialogStage.setX(l[0]);
        dialogStage.setY(l[1]);

        dialogStage.setWidth(l[2]);
        dialogStage.setHeight(l[3]);

        StackPane root = new StackPane();
        ScrollPane sp = new ScrollPane();

        sp.setContent(vbox);
        root.getChildren().add(sp);

        scene = new Scene(vbox);

        dialogStage.setScene(scene);

        grid.setHgap(3);
        grid.setVgap(3);
        grid.setPadding(new Insets(2));

        javafx.scene.layout.ColumnConstraints column1 = new javafx.scene.layout.ColumnConstraints();
        javafx.scene.layout.ColumnConstraints column2 = new javafx.scene.layout.ColumnConstraints();
        column1.setFillWidth(true);
        column2.setFillWidth(true);

        column1.setHgrow(Priority.ALWAYS);
        column2.setHgrow(Priority.ALWAYS);
        //   grid.getColumnConstraints().addAll(column1, column2);

        dialogStage.show();

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void winClose() {

        /* try {
            Thread.sleep(7000);
        } catch (InterruptedException ignored) {
        }*/
        dialogStage.close();

    }

    public void winClear() {
        vbox.getChildren().clear();
    }

    /* public JsonArray getSelect(String SQL) {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        QueryDataSet query = new QueryDataSet();
        query.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(MainFrame.getDB_ТехКорпорация(), SQL, null, true, Load.ALL));
        query.executeQuery();
        if (!query.isEmpty()) {
            do {
                JsonObjectBuilder resultJson = Json.createObjectBuilder();

                Column[] columns = query.getColumns();
                for (Column column : columns) {
                    _add(resultJson, column, query);
                }
                jab.add(resultJson.build());

            } while (query.next());

        }
        query.close();

        return jab.build();
    }*/
    public String getSelect(String SQL) {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        QueryDataSet query = new QueryDataSet();
        query.setQuery(new com.borland.dx.sql.dataset.QueryDescriptor(MainFrame.getDB_ТехКорпорация(), SQL, null, true, Load.ALL));
        query.executeQuery();
        if (!query.isEmpty()) {
            do {
                JsonObjectBuilder resultJson = Json.createObjectBuilder();

                Column[] columns = query.getColumns();
                for (Column column : columns) {
                    _add(resultJson, column, query);
                }
                jab.add(resultJson.build());

            } while (query.next());

        }
        query.close();

        return jab.build().toString();
    }

    public String getDataRow() {
        if (dataSet == null) {
            dataSet = createDataSet();
        }

        JsonObjectBuilder job = Json.createObjectBuilder();
        Column[] columns = dataSet.getColumns();
        for (Column column : columns) {
            _add(job, column, dataSet);
        }
        return job.build().toString();
    }

    public Variant getData(String nameField) {

        Column column = dataSet.getColumn(nameField);
        Variant v = new Variant();

        _add(v, column, dataSet);

        return v;
    }

    public void putData(String field, String value) {

        Column column = dataSet.getColumn(field);

        switch (column.getDataType()) {
            case Variant.LONG:
                dataSet.setLong(field, Long.getLong(value));
                break;
            case Variant.STRING:
                dataSet.setString(field, value);
                break;
            case Variant.BIGDECIMAL:
                dataSet.setBigDecimal(field, new BigDecimal(value));
                break;
            case Variant.INT:
                dataSet.setInt(field, Integer.getInteger(value));
                break;
            case Variant.DATE:

                dataSet.setDate(field, technopolis.util.DateUtil.getSqlDateFromDate(technopolis.util.DateUtil.parseDate("dd.MM.yyyy", value)));
                break;
            case Variant.DOUBLE:
                dataSet.setDouble(field, Double.parseDouble(value));
                break;
            case Variant.SHORT:
                dataSet.setShort(field, Short.parseShort(value));
                break;
            case Variant.TIMESTAMP:

                break;
            default:
                break;
        }

    }

    /*public void insertInto(String tableName, String... var) {
        try {
            StringBuilder sb = new StringBuilder();
            sb = sb.append("[");
            for (String string : var) {
                sb = sb.append(string).append(",");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb = sb.append("]");

            JsonReader jsonReader = Json.createReader(new StringReader(sb.toString()));
            JsonArray jsonObject = jsonReader.readArray();
            JsonObject jo = jsonObject.getJsonObject(0);

            StringBuilder flds = new StringBuilder();
            StringBuilder vals = new StringBuilder();

            Set<String> keys = jo.keySet();
            for (Object key : keys) {
                flds = flds.append("\"").append(key).append("\",");
                vals = vals.append("?,");
            }

            flds = flds.append("\"").append("Группа").append("\",");
            flds = flds.append("\"").append("Изменено").append("\",");
            flds = flds.append("\"").append("Автор").append("\",");

            vals = vals.append("?,");
            vals = vals.append("?,");
            vals = vals.append("?,");

            flds = flds.deleteCharAt(flds.length() - 1);
            vals = vals.deleteCharAt(vals.length() - 1);

            String SQL = "INSERT INTO T.\"" + tableName + "\" (" + flds.toString() + ") VALUES(" + vals.toString() + ")";
            PreparedStatement pst = technopolis.MainFrame.getDB_ТехКорпорация().createPreparedStatement(SQL);

            // -----------------------------------------------------------------
            HashMap hm = new HashMap();

            DatabaseMetaData metaData = MainFrame.getDB_ТехКорпорация().getMetaData();
            ResultSet rs = metaData.getColumns(null, "T", tableName, null);
            while (rs.next()) {
                hm.put(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"));
            }
            rs.close();

            int n = 0;

            for (String col_name : keys) {

                String col_type = (String) hm.get(col_name);
                n = n + 1;

                switch (col_type) {
                    case "BIGINT":
                        pst.setLong(n, jo.getJsonNumber(col_name).longValue());
                        break;
                    case "VARCHAR":
                        pst.setString(n, jo.getString(col_name));
                        break;
                    case "DECIMAL":
                        pst.setBigDecimal(n, jo.getJsonNumber(col_name).bigDecimalValue());
                        break;
                    case "INT":
                        pst.setInt(n, jo.getInt(col_name));
                        break;
                    case "DATE":
                        pst.setDate(n, technopolis.util.DateUtil.getSqlDateFromDate(technopolis.util.DateUtil.parseDate("dd.MM.yyyy", jo.getString(col_name))));
                        break;
                    case "DOUBLE":
                        pst.setDouble(n, jo.getJsonNumber(col_name).doubleValue());
                        break;
                    case "SHORT":
                        pst.setShort(n, (short) jo.getJsonNumber(col_name).intValue());
                        break;
                    case "TIMESTAMP":
                        pst.setTimestamp(n, new Timestamp(System.currentTimeMillis()));
                        break;
                    default:
                        break;
                }
            }

            pst.setString(++n, Пользователь.РабочаяГруппа);
            pst.setTimestamp(++n, new Timestamp(System.currentTimeMillis()));
            pst.setString(++n, Пользователь.Код());

            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Jewel.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }*/
    public String getUser() {
        return Пользователь.Код;
    }

    //  public String getPeriod() {
    //      return ConstantEnterprise.getInstance(MainFrame.ПРЕДПРИЯТИЕ).getCurrentPeriod();
    //  }
    public void execPanorama(String kod) {
        technopolis.iTec.AcionPerformed.Panorama panorama = new technopolis.iTec.AcionPerformed.Panorama(kod);
        panorama.runAction();
    }

    public void execAutobot(String kod) {
        technopolis.information.Transformer.ExecuteAutobot autobot = new technopolis.information.Transformer.ExecuteAutobot(kod);
        autobot.runAction();
    }

    public void refreshData() {
        getCurrentObject("refresh", null, null);
    }

    public void firstRow() {
        getCurrentObject("first", null, null);
    }

    public void lastRow() {
        getCurrentObject("last", null, null);
    }

    public void nextRow() {
        getCurrentObject("next", null, null);
    }

    public void priorRow() {
        getCurrentObject("prior", null, null);
    }

    public void insertRow() {
        getCurrentObject("insert", null, null);
    }

    public void deleteRow() {
        getCurrentObject("delete", null, null);
    }

    public void saveRow() {
        getCurrentObject("save", null, null);
    }

    public void copyRow() {
        getCurrentObject("copy", null, null);
    }

    public void locateRow(String field, String value) {
        getCurrentObject("locate", field, value);
    }

    public int getRowCount() {
        int rowCont = 0;

        JInternalFrame[] frame = MainFrame.getDeskTop().getAllFrames();
        for (JInternalFrame internalFrame : frame) {
            if (internalFrame instanceof TechExplorer) {
                if (internalFrame.isSelected()) {
                    DataSet ds = ((TechExplorer) internalFrame).getTechDataSet().getQueryDataSet();
                    rowCont = ds.getRowCount();
                }
            }
        }
        return rowCont;
    }

    private void getCurrentObject(String command, String field, String value) {
        JInternalFrame[] frame = MainFrame.getDeskTop().getAllFrames();
        for (JInternalFrame internalFrame : frame) {
            if (internalFrame instanceof TechExplorer) {
                if (internalFrame.isSelected()) {
                    DataSet ds = ((TechExplorer) internalFrame).getTechDataSet().getQueryDataSet();

                    int row = ds.getRow();

                    switch (command) {
                        case "refresh":
                            ds.refresh();
                            ds.goToRow(row);
                            break;

                        case "first":
                            ds.first();
                            break;
                        case "last":
                            ds.last();
                            break;
                        case "next":
                            ds.next();
                            break;
                        case "prior":
                            ds.prior();
                            break;
                        case "insert":
                            ((TechExplorer) internalFrame).getTechDataSet().insertRow();
                            break;
                        case "delete":
                            ((TechExplorer) internalFrame).getTechDataSet().deleteRow();
                            break;
                        case "save":
                            ((TechExplorer) internalFrame).getTechDataSet().save();
                            break;
                        case "copy":
                            ((TechExplorer) internalFrame).getTechDataSet().copyRow();
                            break;
                        case "locate":
                            ((TechExplorer) internalFrame).getTechDataSet().locateNext(value, field);
                            break;
                        default:
                            throw new AssertionError();
                    }

                }
            }
        }
    }

    public void putDataRow(String text) {

        InputStream is = null;
        try {
            is = new ByteArrayInputStream(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }

        String key = null;
        String value = null;

        JsonParser parser = Json.createParser(is);

        while (parser.hasNext()) {

            final Event event = parser.next();

            switch (event) {
                case KEY_NAME:
                    key = parser.getString();
                    break;
                case VALUE_STRING:
                    value = parser.getString();

                    putData(key, value);

                    break;
                case END_OBJECT:

                    break;
            }
        }
    }

    public String getTableName() {
        String tableName = "неопределена";

        if (dataSet != null) {
            tableName = dataSet.getTableName();
        }

        return tableName;
    }

    public void table(String text) {

        TableView tableView = new TableView<>();

        InputStream is = null;
        try {
            is = new ByteArrayInputStream(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<Map> allData = FXCollections.observableArrayList();
        Map<String, String> dataRow = new HashMap<>();

        String key = null;
        String value = null;

        JsonParser parser = Json.createParser(is);
        boolean flag = true;

        while (parser.hasNext()) {

            final Event event = parser.next();

            switch (event) {
                case KEY_NAME:
                    key = parser.getString();
                    if (flag) {
                        javafx.scene.control.TableColumn<Map, String> firstDataColumn = new javafx.scene.control.TableColumn<>(key);
                        firstDataColumn.setCellValueFactory(new MapValueFactory(key));
                        firstDataColumn.setMinWidth(130);

                        //firstDataColumn.getCellObservableValue(dataRow)
                        tableView.getColumns().add(firstDataColumn);
                    }
                    break;
                case VALUE_STRING:
                    value = parser.getString();
                    break;
                case END_OBJECT:
                    flag = false;
                    allData.add(dataRow);

                    dataRow = new HashMap<>();
                    break;
            }

            dataRow.put(key, value);
        }

        tableView.setEditable(true);
        //tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(allData);

        parser.close();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (tableView.getSelectionModel().getSelectedItem() != null) {
                    dialogStage.close();
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                // TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                // ObservableList selectedCells = selectionModel.getSelectedCells();
                // TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                // Object val = tablePosition.getTableColumn().getCellData(newValue);

                // System.out.println("Selected Value " + val);
                HashMap hp = (HashMap) observableValue.getValue();
                Set set = hp.entrySet();
                for (Iterator iter = set.iterator(); iter.hasNext();) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String skey = (String) entry.getKey();
                    String svalue = (String) entry.getValue();
                    //    retJob.add(skey, svalue);

                }

                System.out.println("Selected row " + newValue);
            }
        });

        vbox.getChildren().add(tableView);

    }

    public String getInfo(String nameObject, String fldFind, String valueFind) {
        String xret = null;

        // контроль права доступа
        ContactRight contactRight = DealerHelper.getRightObject(nameObject);
        if (!contactRight.доступ()) {
            JOptionPane.showMessageDialog(null, "Отказ в доступе", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        } else {

            Variant Источник = iTecModel.get(nameObject, iTecModel.Свойства.Источник);

            try {
                Dealer dea = DealerHelper.getDealer(nameObject);
                Class clazz = this.getClass().getClassLoader().loadClass(Источник.getString());
                AbstractQuery source = (AbstractQuery) clazz.newInstance();
                source.init(dea, (Object[]) null);

                QueryDataSet q = source.getQuery();
                q.open();

                if (!q.isEmpty()) {

                    DataRow dr = new DataRow(q, new String[]{fldFind});
                    dr.setString(fldFind, valueFind);
                    if (q.locate(dr, Locate.FIRST)) {

                        xret = xGet(q);

                    } else {

                        iTecTableView table = new iTecTableView(nameObject, xGetColumns(q));
                        JsonObjectBuilder job = table.Show();

                        JsonObject jo = job.build();
                        String retValue = jo.getString(fldFind);

                        xret = jo.toString();

                    }
                }

            } catch (Exception ex) {
                System.out.print(ex.getMessage());
            }

        }

        return xret;
    }

    public String xGet(QueryDataSet query) {

        JsonObjectBuilder resultJson = Json.createObjectBuilder();

        Column[] columns = query.getColumns();
        for (Column column : columns) {
            _add(resultJson, column, query);
        }

        return resultJson.build().toString();
    }

    public String xGetColumns(QueryDataSet query) {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        do {
            JsonObjectBuilder resultJson = Json.createObjectBuilder();

            Column[] columns = query.getColumns();
            for (Column column : columns) {
                _add(resultJson, column, query);
            }
            jab.add(resultJson.build());

        } while (query.next());

        return jab.build().toString();
    }

    public String getDataTable() {

        if (dataSet == null) {
            dataSet = createDataSet();
        }

        JsonArrayBuilder jab = Json.createArrayBuilder();

        dataSet.first();
        do {
            JsonObjectBuilder resultJson = Json.createObjectBuilder();

            Column[] columns = dataSet.getColumns();
            for (Column column : columns) {
                _add(resultJson, column, dataSet);
            }
            jab.add(resultJson.build());

        } while (dataSet.next());

        return jab.build().toString();
    }

    public String getDataParent() {
        DataSet dsParent = dataSet.getMasterLink().getMasterDataSet();
        JsonObjectBuilder job = Json.createObjectBuilder();
        Column[] columns = dsParent.getColumns();
        for (Column column : columns) {
            _add(job, column, dsParent);
        }
        return job.build().toString();
    }

    private void _add(JsonObjectBuilder job, Column column, DataSet ds) {
        String col_name = column.getColumnName();

        switch (column.getDataType()) {
            case Variant.STRING:
                job.add(col_name, ds.getString(col_name));
                break;
            case Variant.LONG:
                job.add(col_name, String.valueOf(ds.getLong(col_name)));
                //job.add(col_name, ds.getLong(col_name));
                break;
            case Variant.BIGDECIMAL:
                job.add(col_name, ds.getBigDecimal(col_name));
                break;
            case Variant.INT:
                job.add(col_name, ds.getInt(col_name));
                break;
            case Variant.DATE:
                job.add(col_name, ds.getDate(col_name).toString());
                break;
            case Variant.DOUBLE:
                job.add(col_name, ds.getDouble(col_name));
                break;
            case Variant.SHORT:
                job.add(col_name, ds.getShort(col_name));
                break;
            case Variant.TIMESTAMP:
                //  resultJson.put(col_name, dataSet.getTimestamp(col_name));
                break;
            default:
                break;
        }
    }

    private void _add(Variant var, Column column, DataSet ds) {
        String col_name = column.getColumnName();

        switch (column.getDataType()) {
            case Variant.STRING:
                var.setString(ds.getString(col_name));
                break;
            case Variant.LONG:
                var.setLong(ds.getLong(col_name));
                break;
            case Variant.BIGDECIMAL:
                var.setBigDecimal(ds.getBigDecimal(col_name));
                break;
            case Variant.INT:
                var.setInt(ds.getInt(col_name));
                break;
            case Variant.DATE:
                var.setDate(ds.getDate(col_name));
                break;
            case Variant.DOUBLE:
                var.setDouble(ds.getDouble(col_name));
                break;
            case Variant.SHORT:
                var.setShort(ds.getShort(col_name));
                break;
            case Variant.TIMESTAMP:
                var.setTimestamp(dataSet.getTimestamp(col_name));
                break;
            case Variant.BOOLEAN:
                var.setBoolean(dataSet.getBoolean(col_name));
                break;
            default:
                break;
        }
    }

    public String getDataChild(String tableName) {

        JsonArrayBuilder jab = Json.createArrayBuilder();

        DataSet[] dss = dataSet.getDetails();

        if (dss != null) {
            for (DataSet ds : dss) {
                if (ds.getTableName() != null && ds.getTableName().equals(tableName)) {

                    if (ds.isOpen()) {
                        ds.first();
                        do {
                            JsonObjectBuilder resultJson = Json.createObjectBuilder();

                            Column[] columns = ds.getColumns();
                            for (Column column : columns) {
                                _add(resultJson, column, ds);
                            }

                            jab.add(resultJson.build());
                        } while (ds.next());
                    }
                }
            }
        }

        return jab.build().toString();
    }

    private DataSet createDataSet() {
        QueryDataSet q = new QueryDataSet();

        Column col1 = new Column("Код", null, Variant.STRING);
        Column col2 = new Column("Наименование", null, Variant.STRING);

        q.setColumns(new Column[]{col1, col2});
        q.open();

        DataRow dr = new DataRow(q);
        dr.setString("Код", "КодКодКод");
        dr.setString("Наименование", "НаименованиеНаименованиеНаименование");

        q.addRow(dr);

        dr.setString("Код", "11111111");
        dr.setString("Наименование", "22222222222222222222222");

        q.addRow(dr);

        return q;

    }

    public void aLert(String text) {
        this.alert("", text, style);
    }

    public void alert(String text) {
        this.alert("", text, style);
    }

    public void alert(String title, String text, String style) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.getDialogPane().setStyle(style);
        alert.showAndWait();
    }

    public String choiceList(String title, String HeaderText, String ContentText, String... value) {
        String xret = "";
        List<String> choices = new ArrayList<>();

        for (String string : value) {
            choices.add(string);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choices);
        dialog.setTitle(title);
        dialog.setHeaderText(HeaderText);
        dialog.setContentText(ContentText);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            xret = result.get();
        }
        return xret;
    }

    public String prompt(String ContentText, String value) {
        String xret = "";
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle("");
        dialog.setHeaderText("");
        dialog.setContentText(ContentText);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            xret = result.get();
        }
        return xret;
    }

    public String confirm(String text) {
        return confirm(text, "Да", "Нет");
    }

    public String confirm(String text, String v1, String v2) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText(text);

        ButtonType buttonTypeOk = new ButtonType(v1);
        ButtonType buttonTypeCancel = new ButtonType(v2);

        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOk) {
            return "Ok";
        } else if (result.get() == buttonTypeCancel) {
            return "Cancel";
        } else {
            return "Cancel";
        }
    }

    // new 16.08.2017  
    public String choice(String headerText, String[] value) {
        return this.choice("", headerText, "", value);
    }

    public String choice(String title, String headerText, String contentText, String[] value) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        List<ButtonType> list = new ArrayList<ButtonType>();
        ObservableList<ButtonType> observableList = FXCollections.observableList(list);

        for (String string : value) {
            ButtonType btn = new ButtonType(string);
            observableList.add(btn);
        }

        alert.getButtonTypes().setAll(observableList);

        //    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        //    alert.getButtonTypes().add(buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();

        return result.get().getText();
    }

    public String getAppServer() {
        return IniSettings.get(IniConst.APP_SERVER);
    }

    public String getDataServer() {
        return IniSettings.get(IniConst.DATA_SERVER);
    }

    public String getFileServer() {
        return IniSettings.get(IniConst.FILE_SERVER);
    }

    public void openFile(String fileName) {

        fileName = technopolis.util.FileUtil.getAbsolutePath(fileName);

        java.io.File f = new java.io.File(fileName);
        if (f.exists()) {
            try {
                Desktop.getDesktop().open(f);
            } catch (IOException ex) {
                Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert("Файл не найден");
        }
    }

    public void execFile(String fileName) {
        java.io.File f = new java.io.File(FileUtil.getRevelationPath() + File.separator + fileName);
        if (f.exists()) {
            technopolis.util.Util.runFile(fileName, null);
        } else {
            alert("Файл не найден");
        }
    }

    public String getTest() {
        return "строка теста";
    }

    protected int[] detLocation(String par) {
        int[] location = new int[4];

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        int grid = 100;

        Dimension parentSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (par == null || par.isEmpty()) {
            location[0] = 0;
            location[1] = 0;
            location[2] = parentSize.width;
            location[3] = parentSize.height;
        } else {
            String[] xlo = par.split(",");
            if (xlo.length > 1) {
                x = Integer.parseInt(xlo[0]);
                y = Integer.parseInt(xlo[1]);
                width = Integer.parseInt(xlo[2]);
                height = Integer.parseInt(xlo[3]);

                int deltaX = parentSize.width / grid;
                int deltaY = parentSize.height / grid;

                location[0] = deltaX * (x - 1);
                location[1] = deltaY * (y - 1);
                location[2] = deltaX * width;
                location[3] = deltaY * height;
            }
        }

        return location;
    }

    public void writeFile(String FileName, String string) {
        String userDir = RunITec.Work_Dir + File.separator + IniSettings.get(IniConst.LOGIN) + File.separator + FileName;
        File file = new File(userDir);

        try {
            technopolis.net.atlanticbb.tantlinger.io.IOUtils.write(file, string);
        } catch (IOException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String readFile(String fileNameSource) {

        File srcFile = null;
        String text = null;

        if (fileNameSource.equals("*") || fileNameSource.equals("*.*")) {
            srcFile = xFileChooserSource();
        } else {
            String userDir = RunITec.Work_Dir + File.separator + IniSettings.get(IniConst.LOGIN) + File.separator + fileNameSource;
            //String userDir = "C:/tmp/1C.json";
            srcFile = new File(userDir);
        }

        try {
            //    text = technopolis.net.atlanticbb.tantlinger.io.IOUtils.read(file);
            StringBuilder b = new StringBuilder();
            String str;
            java.io.BufferedReader readStream = new java.io.BufferedReader(new InputStreamReader(new java.io.FileInputStream(srcFile), "Cp1251"));
            while ((str = readStream.readLine()) != null) {
                b.append(" ").append(str);
            }
            readStream.close();

            text = b.toString();

        } catch (IOException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }

    public boolean copyFile(String fileNameSource, String fileNameTarget) {
        String srs;
        String trg;

        File srcFile = null;

        if (fileNameSource.equals("*") || fileNameSource.equals("*.*")) {
            srcFile = xFileChooserSource();
        }

        if (srcFile != null) {

            fileNameSource = srcFile.getAbsolutePath();

            if (fileNameTarget.equals("*") || fileNameTarget.equals("*.*")) {
                fileNameTarget = xFileChooserTarget() + File.separator + srcFile.getName();
            }
        }

        if (fileNameSource.contains(File.separator)) {
            srs = fileNameSource;
        } else {
            srs = RunITec.Work_Dir + File.separator + IniSettings.get(IniConst.LOGIN) + File.separator + fileNameSource;
        }

        if (fileNameTarget.contains(File.separator)) {
            trg = fileNameTarget;
        } else {
            trg = RunITec.Work_Dir + File.separator + IniSettings.get(IniConst.LOGIN) + File.separator + fileNameTarget;
        }

        File fileSrs = new File(srs);
        File fileTrg = new File(trg);

        if (!fileSrs.exists()) {
            JOptionPane.showMessageDialog(technopolis.MainFrame.getDeskTop(), "Файл " + fileSrs.getAbsolutePath() + " не найден. Копирование невозможно", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (fileTrg.exists()) {
            int x = JOptionPane.showOptionDialog(technopolis.MainFrame.getDeskTop(), "Файл " + fileTrg.getAbsolutePath() + " для копирования уже существует.", "Внимание", JOptionPane.YES_NO_OPTION, JOptionPane.CLOSED_OPTION, null, new String[]{"Переписать", "Отменить"}, "");
            if (x == 1) {
                return false;
            }
        }

        try {
            technopolis.net.atlanticbb.tantlinger.io.IOUtils.copy(fileSrs, fileTrg, true);
        } catch (IOException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;

    }

    public boolean renameFile(String fileNameSource, String fileNameTarget) {
        String srs;

        File srcFile = null;
        if (fileNameSource.equals("*") || fileNameSource.equals("*.*")) {
            srcFile = xFileChooserSource();
        }

        if (srcFile != null) {
            fileNameSource = srcFile.getAbsolutePath();
        }

        if (fileNameSource.contains(File.separator)) {
            srs = fileNameSource;
        } else {
            srs = RunITec.Work_Dir + File.separator + IniSettings.get(IniConst.LOGIN) + File.separator + fileNameSource;
        }

        File fileSrs = new File(srs);
        if (!fileSrs.exists()) {
            JOptionPane.showMessageDialog(technopolis.MainFrame.getDeskTop(), "Файл " + fileSrs.getAbsolutePath() + " не найден. Переименование невозможно", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (fileNameTarget.equals("*") || fileNameTarget.equals("*.*")) {
            fileNameTarget = prompt("Введите новое имя файла ", fileSrs.getName());
            if (fileNameTarget == null || fileNameTarget.equals("")) {
                return false;
            }
        }

        File fileTrg = new File(fileSrs.getParent() + File.separator + fileNameTarget);

        if (fileTrg.exists()) {
            String rez = confirm("Файл " + fileTrg.getAbsolutePath() + " для переименования уже существует. Переписать ?");
            if (rez.equals("Cancel")) {
                return false;
            }
        }

        fileSrs.renameTo(fileTrg);

        return true;
    }

    private static File xFileChooserSource() {
        JFXPanel fxPanel = new JFXPanel();

        final javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы ", "*.*"),
                new FileChooser.ExtensionFilter("bat", "*.bat"),
                new FileChooser.ExtensionFilter("exe", "*.exe"),
                new FileChooser.ExtensionFilter("jar", "*.jar"),
                new FileChooser.ExtensionFilter("sql", "*.sql"));

        String CurrentDirectory = RunITec.Work_Dir;

        if (CurrentDirectory != null && !CurrentDirectory.isEmpty() && new File(CurrentDirectory).exists()) {
            chooser.setInitialDirectory(new File(CurrentDirectory));
        }

        File file = chooser.showOpenDialog(null);

        return file;
    }

    private String xFileChooserTarget() {
        final javafx.stage.DirectoryChooser chooser = new javafx.stage.DirectoryChooser();
        JFXPanel fxPanel = new JFXPanel();

        File file = chooser.showDialog(null);

        String CurrentDirectory = RunITec.Work_Dir;

        if (CurrentDirectory != null && !CurrentDirectory.isEmpty() && new File(CurrentDirectory).exists()) {
            chooser.setInitialDirectory(new File(CurrentDirectory));
        }

        return file.getAbsolutePath();
    }

    public String FileChooserJSON(String path) {
        JFXPanel fxPanel = new JFXPanel();

        final javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы ", "*.*"),
                new FileChooser.ExtensionFilter("json", "*.json"));

        String CurrentDirectory;

        if (path != null && !path.isEmpty()) {
            CurrentDirectory = path;
        } else {
            CurrentDirectory = RunITec.Work_Dir;
        }
        if (CurrentDirectory != null && !CurrentDirectory.isEmpty() && new File(CurrentDirectory).exists()) {
            chooser.setInitialDirectory(new File(CurrentDirectory));
        }

        File file = chooser.showOpenDialog(null);

        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }

    public String jsonToXml(String text) {
        Jewel.json.JSONObject obj = new Jewel.json.JSONObject(text);
        return Jewel.json.XML.toString(obj);
    }

    public String xmlToJson(String xmlObj) {
        Jewel.json.JSONObject jObj = Jewel.json.XML.toJSONObject(xmlObj);
        return jObj.toString();
    }
    
    
    public void saveMemory(String ИмяХранения, String Значение) {
        Variant var = new Variant();
        var.setString(Значение);
        saveMemory(ИмяХранения, var);
    }

    public void saveMemory(String ИмяХранения, int Значение) {
        Variant var = new Variant();
        var.setInt(Значение);
        saveMemory(ИмяХранения, var);
    }
    
    public void saveMemory(String ИмяХранения, double Значение) {
        Variant var = new Variant();
        var.setDouble(Значение);
        saveMemory(ИмяХранения, var);
    }

    public void saveMemory(String ИмяХранения, Variant Значение) {
        File file = new File(MainFrame.CatalogMem + File.separator + ИмяХранения +".mem");
        FileLoader.storeData(file, Значение);
    }

    public Variant readMemory(String ИмяХранения) {
        File file = new File(MainFrame.CatalogMem + File.separator + ИмяХранения +".mem");
        Variant var = (Variant)FileLoader.loadData(file);
        return var;
    }

}
