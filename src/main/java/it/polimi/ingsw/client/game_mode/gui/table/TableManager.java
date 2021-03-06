package it.polimi.ingsw.client.game_mode.gui.table;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.Restriction;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.public_objectives.PublicObjectiveCard;
import it.polimi.ingsw.model.cards.toolcard.ToolCard;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.client.communicator.Communicator;
import it.polimi.ingsw.client.game_mode.gui.GUIData;
import it.polimi.ingsw.client.game_mode.gui.GUIManager;
import it.polimi.ingsw.client.game_mode.gui.login.LoginManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class TableManager implements GUIManager {
    private static final int NUM_OF_ROUNDS = 10;
    private static final int MAX_TURNS = 9;
    private static final int NUM_OF_FACES = 6;
    private static final int NUM_OF_COL = 5;
    private static final int NUM_OF_ROW = 4;
    private static final int START_NUMBER =0;
    private HashMap<String, Integer> comparator = null;
    private HashMap<Integer, String> dices = null;
    private HashMap<Color, String> colors = null;  //Restriction of colors
    private HashMap<String, String> colorsWindow = null;
    private ArrayList<Rectangle> cells1 = null;  //cells of player one
    private ArrayList<Rectangle> cells2 = null;  //cells of player two
    private ArrayList<Rectangle> cells3 = null;  //cells of player three
    private ArrayList<Rectangle> cells4 = null;  //cells of player four
    private ArrayList<StackPane> poolItems = null;  //dices of draftPool
    private ArrayList<StackPane> roundItems = null;  //dices of roundTrack
    private ArrayList<StackPane> window1Items = null; //dices to remove from window1
    private ArrayList<StackPane> window2Items = null; //dices to remove from window2
    private ArrayList<StackPane> window3Items = null; //dices to remove from window3
    private ArrayList<StackPane> window4Items = null; //dices to remove from window4
    private ArrayList<Rectangle> cellsRound = null;  //cells of roundTrack
    private ArrayList<Rectangle> cellsPool = null;  //cells of draftPool
    private ArrayList<Text> roundsTexts = null;
    private Table table = null;
    private Rectangle source = null;
    private int idPool = 0;
    private Communicator communicator;
    private boolean toolCardEnable;
    private boolean activeTool = false;
    private boolean roundTrackEnable = false;
    private boolean draftPoolEnable_IN = false;
    private boolean draftPoolEnable_OUT = false;
    private boolean windowEnable_IN = false;
    private boolean windowEnable_OUT = false;
    private ArrayList<Circle> signals1;
    private ArrayList<Circle> signals2;
    private ArrayList<Circle> signals3;
    private ArrayList<Circle> signals4;
    private ArrayList<Button> faces;
    private MouseEvent event;
    int initialPos;
    private StackPane draggableDice;
    private String temp = "NULL";
    private String temp2 = "NULL";

    @FXML GridPane draftPool;
    @FXML Rectangle dice1;
    @FXML Rectangle dice2;
    @FXML Rectangle dice3;
    @FXML Rectangle dice4;
    @FXML Rectangle dice5;
    @FXML Rectangle dice6;
    @FXML Rectangle dice7;
    @FXML Rectangle dice8;
    @FXML Rectangle dice9;
    //window1
    @FXML GridPane window1;
    @FXML Text username1;
    @FXML Rectangle dice11;
    @FXML Rectangle dice12;
    @FXML Rectangle dice13;
    @FXML Rectangle dice14;
    @FXML Rectangle dice15;
    @FXML Rectangle dice21;
    @FXML Rectangle dice22;
    @FXML Rectangle dice23;
    @FXML Rectangle dice24;
    @FXML Rectangle dice25;
    @FXML Rectangle dice31;
    @FXML Rectangle dice32;
    @FXML Rectangle dice33;
    @FXML Rectangle dice34;
    @FXML Rectangle dice35;
    @FXML Rectangle dice41;
    @FXML Rectangle dice42;
    @FXML Rectangle dice43;
    @FXML Rectangle dice44;
    @FXML Rectangle dice45;
    //window2
    @FXML GridPane window2;
    @FXML Text username2;
    @FXML Rectangle dice2_11;
    @FXML Rectangle dice2_12;
    @FXML Rectangle dice2_13;
    @FXML Rectangle dice2_14;
    @FXML Rectangle dice2_15;
    @FXML Rectangle dice2_21;
    @FXML Rectangle dice2_22;
    @FXML Rectangle dice2_23;
    @FXML Rectangle dice2_24;
    @FXML Rectangle dice2_25;
    @FXML Rectangle dice2_31;
    @FXML Rectangle dice2_32;
    @FXML Rectangle dice2_33;
    @FXML Rectangle dice2_34;
    @FXML Rectangle dice2_35;
    @FXML Rectangle dice2_41;
    @FXML Rectangle dice2_42;
    @FXML Rectangle dice2_43;
    @FXML Rectangle dice2_44;
    @FXML Rectangle dice2_45;
    //window3
    @FXML GridPane window3;
    @FXML Text username3;
    @FXML Rectangle dice3_11;
    @FXML Rectangle dice3_12;
    @FXML Rectangle dice3_13;
    @FXML Rectangle dice3_14;
    @FXML Rectangle dice3_15;
    @FXML Rectangle dice3_21;
    @FXML Rectangle dice3_22;
    @FXML Rectangle dice3_23;
    @FXML Rectangle dice3_24;
    @FXML Rectangle dice3_25;
    @FXML Rectangle dice3_31;
    @FXML Rectangle dice3_32;
    @FXML Rectangle dice3_33;
    @FXML Rectangle dice3_34;
    @FXML Rectangle dice3_35;
    @FXML Rectangle dice3_41;
    @FXML Rectangle dice3_42;
    @FXML Rectangle dice3_43;
    @FXML Rectangle dice3_44;
    @FXML Rectangle dice3_45;
    //window4
    @FXML GridPane window4;
    @FXML Text username4;
    @FXML Rectangle dice4_11;
    @FXML Rectangle dice4_12;
    @FXML Rectangle dice4_13;
    @FXML Rectangle dice4_14;
    @FXML Rectangle dice4_15;
    @FXML Rectangle dice4_21;
    @FXML Rectangle dice4_22;
    @FXML Rectangle dice4_23;
    @FXML Rectangle dice4_24;
    @FXML Rectangle dice4_25;
    @FXML Rectangle dice4_31;
    @FXML Rectangle dice4_32;
    @FXML Rectangle dice4_33;
    @FXML Rectangle dice4_34;
    @FXML Rectangle dice4_35;
    @FXML Rectangle dice4_41;
    @FXML Rectangle dice4_42;
    @FXML Rectangle dice4_43;
    @FXML Rectangle dice4_44;
    @FXML Rectangle dice4_45;
    @FXML TextArea text;
    //cards
    @FXML ImageView tool1;
    @FXML ImageView tool2;
    @FXML ImageView tool3;
    @FXML ImageView publicObj1;
    @FXML ImageView publicObj2;
    @FXML ImageView publicObj3;
    @FXML ImageView privateObj;
    @FXML Button moveButton;
    @FXML Button toolCardButton;
    @FXML Button skipButton;
    @FXML GridPane selectedDice;
    @FXML GridPane tableBackground;
    @FXML GridPane cardsBackground;
    @FXML GridPane roundTrackBackground;
    @FXML GridPane roundTrack;
    @FXML Rectangle diceR11; @FXML Rectangle diceR12; @FXML Rectangle diceR13; @FXML Rectangle diceR14;
    @FXML Rectangle diceR15; @FXML Rectangle diceR16; @FXML Rectangle diceR17; @FXML Rectangle diceR18;
    @FXML Rectangle diceR19; @FXML Rectangle diceR21; @FXML Rectangle diceR22; @FXML Rectangle diceR23;
    @FXML Rectangle diceR24; @FXML Rectangle diceR25; @FXML Rectangle diceR26; @FXML Rectangle diceR27;
    @FXML Rectangle diceR28; @FXML Rectangle diceR29; @FXML Rectangle diceR31; @FXML Rectangle diceR32;
    @FXML Rectangle diceR33; @FXML Rectangle diceR34; @FXML Rectangle diceR35; @FXML Rectangle diceR36;
    @FXML Rectangle diceR37; @FXML Rectangle diceR38; @FXML Rectangle diceR39; @FXML Rectangle diceR41;
    @FXML Rectangle diceR42; @FXML Rectangle diceR43; @FXML Rectangle diceR44; @FXML Rectangle diceR45;
    @FXML Rectangle diceR46; @FXML Rectangle diceR47; @FXML Rectangle diceR48; @FXML Rectangle diceR49;
    @FXML Rectangle diceR51; @FXML Rectangle diceR52; @FXML Rectangle diceR53; @FXML Rectangle diceR54;
    @FXML Rectangle diceR55; @FXML Rectangle diceR56; @FXML Rectangle diceR57; @FXML Rectangle diceR58;
    @FXML Rectangle diceR59; @FXML Rectangle diceR61; @FXML Rectangle diceR62; @FXML Rectangle diceR63;
    @FXML Rectangle diceR64; @FXML Rectangle diceR65; @FXML Rectangle diceR66; @FXML Rectangle diceR67;
    @FXML Rectangle diceR68; @FXML Rectangle diceR69; @FXML Rectangle diceR71; @FXML Rectangle diceR72;
    @FXML Rectangle diceR73; @FXML Rectangle diceR74; @FXML Rectangle diceR75; @FXML Rectangle diceR76;
    @FXML Rectangle diceR77; @FXML Rectangle diceR78; @FXML Rectangle diceR79; @FXML Rectangle diceR81;
    @FXML Rectangle diceR82; @FXML Rectangle diceR83; @FXML Rectangle diceR84; @FXML Rectangle diceR85;
    @FXML Rectangle diceR86; @FXML Rectangle diceR87; @FXML Rectangle diceR88; @FXML Rectangle diceR89;
    @FXML Rectangle diceR91; @FXML Rectangle diceR92; @FXML Rectangle diceR93; @FXML Rectangle diceR94;
    @FXML Rectangle diceR95; @FXML Rectangle diceR96; @FXML Rectangle diceR97; @FXML Rectangle diceR98;
    @FXML Rectangle diceR99; @FXML Rectangle diceR101; @FXML Rectangle diceR102; @FXML Rectangle diceR103;
    @FXML Rectangle diceR104; @FXML Rectangle diceR105; @FXML Rectangle diceR106; @FXML Rectangle diceR107;
    @FXML Rectangle diceR108; @FXML Rectangle diceR109;
    @FXML Circle signal1; @FXML Circle signal2; @FXML Circle signal3; @FXML Circle signal4; @FXML Circle signal5; @FXML Circle signal6;
    @FXML Circle signal2_1; @FXML Circle signal2_2; @FXML Circle signal2_3; @FXML Circle signal2_4; @FXML Circle signal2_5; @FXML Circle signal2_6;
    @FXML Circle signal3_1; @FXML Circle signal3_2; @FXML Circle signal3_3; @FXML Circle signal3_4; @FXML Circle signal3_5; @FXML Circle signal3_6;
    @FXML Circle signal4_1; @FXML Circle signal4_2; @FXML Circle signal4_3; @FXML Circle signal4_4; @FXML Circle signal4_5; @FXML Circle signal4_6;
    @FXML Text tool1Name; @FXML Text tool1ID; @FXML Text tool1Description; @FXML Text tool1Tokens;
    @FXML Text tool2Name; @FXML Text tool2ID; @FXML Text tool2Description; @FXML Text tool2Tokens;
    @FXML Text tool3Name; @FXML Text tool3ID; @FXML Text tool3Description; @FXML Text tool3Tokens;
    @FXML Text PVOCName; @FXML Text PVOCID; @FXML Text PVOCDescription; @FXML Text PVOCColor;
    @FXML Text POC1Name; @FXML Text POC1ID; @FXML Text POC1Description; @FXML Text POC1Points;
    @FXML Text POC2Name; @FXML Text POC2ID; @FXML Text POC2Description; @FXML Text POC2Points;
    @FXML Text POC3Name; @FXML Text POC3ID; @FXML Text POC3Description; @FXML Text POC3Points;
    @FXML Button cancelButton;
    @FXML Text roundsText; @FXML Text round1Text; @FXML Text round2Text; @FXML Text round3Text; @FXML Text round4Text; @FXML Text round5Text;
    @FXML Text round6Text; @FXML Text round7Text; @FXML Text round8Text; @FXML Text round9Text; @FXML Text round10Text;
    @FXML
    Button minus;
    @FXML
    Button plus;
    @FXML Button one; @FXML Button two; @FXML Button three; @FXML Button four; @FXML Button five; @FXML Button six;
    @FXML Rectangle tool1Border; @FXML Rectangle tool2Border; @FXML Rectangle tool3Border;
    @FXML Button joinGameButton;
    @FXML Button yesButton;
    @FXML Button noButton;
    @FXML Button playButton;

    /**
     * It's called by the FXMLLoader when the file table.fxml is loaded.
     * It initializes all javaFx items and all the attributes of the class.
     */
    public void initialize(){
        GUIData.getGUIData().getView().setGUIManager(this);
        text.setEditable(false);
        String image;
        dices = new HashMap<>();
        image = "/GUI/dice1.fxml";
        dices.put(1, image);
        image = "/GUI/dice2.fxml";
        dices.put(2, image);
        image = "/GUI/dice3.fxml";
        dices.put(3, image);
        image = "/GUI/dice4.fxml";
        dices.put(4, image);
        image = "/GUI/dice5.fxml";
        dices.put(5, image);
        image = "/GUI/dice6.fxml";
        dices.put(6, image);
        colors = new HashMap<>();
        colors.put(Color.YELLOW, "-fx-background-color: rgba(255, 230, 0, 1);");  //dices
        colors.put(Color.BLUE, "-fx-background-color: rgba(0, 160, 225, 1);");
        colors.put(Color.RED, "-fx-background-color: rgba(255, 31, 53, 1);");
        colors.put(Color.PURPLE, "-fx-background-color: rgba(255, 50, 255, 1);");
        colors.put(Color.GREEN, "-fx-background-color: rgba(0, 160, 0, 1);");
        comparator = new HashMap<>();
        comparator.put(Restriction.ONE.escape(), 1);
        comparator.put(Restriction.TWO.escape(), 2);
        comparator.put(Restriction.THREE.escape(), 3);
        comparator.put(Restriction.FOUR.escape(), 4);
        comparator.put(Restriction.FIVE.escape(), 5);
        comparator.put(Restriction.SIX.escape(), 6);
        colorsWindow = new HashMap<>(); //Restrictions
        colorsWindow.put(Restriction.WHITE.escape(), "-fx-fill: #ffffff;");
        colorsWindow.put(Restriction.RED.escape(), "-fx-fill: #ff6a49;");
        colorsWindow.put(Restriction.GREEN.escape(), "-fx-fill: #82f87e;");
        colorsWindow.put(Restriction.PURPLE.escape(), "-fx-fill: #ee82dc;");
        colorsWindow.put(Restriction.BLUE.escape(), "-fx-fill: #82c0ed;");
        colorsWindow.put(Restriction.YELLOW.escape(), "-fx-fill: #fff486;");
        cells1 = new ArrayList<>(); cells2 = new ArrayList<>(); cells3 = new ArrayList<>(); cells4 = new ArrayList<>();
        cells1.add(dice11); cells2.add(dice2_11); cells3.add(dice3_11); cells4.add(dice4_11);
        cells1.add(dice12); cells2.add(dice2_12); cells3.add(dice3_12); cells4.add(dice4_12);
        cells1.add(dice13); cells2.add(dice2_13); cells3.add(dice3_13); cells4.add(dice4_13);
        cells1.add(dice14); cells2.add(dice2_14); cells3.add(dice3_14); cells4.add(dice4_14);
        cells1.add(dice15); cells2.add(dice2_15); cells3.add(dice3_15); cells4.add(dice4_15);
        cells1.add(dice21); cells2.add(dice2_21); cells3.add(dice3_21); cells4.add(dice4_21);
        cells1.add(dice22); cells2.add(dice2_22); cells3.add(dice3_22); cells4.add(dice4_22);
        cells1.add(dice23); cells2.add(dice2_23); cells3.add(dice3_23); cells4.add(dice4_23);
        cells1.add(dice24); cells2.add(dice2_24); cells3.add(dice3_24); cells4.add(dice4_24);
        cells1.add(dice25); cells2.add(dice2_25); cells3.add(dice3_25); cells4.add(dice4_25);
        cells1.add(dice31); cells2.add(dice2_31); cells3.add(dice3_31); cells4.add(dice4_31);
        cells1.add(dice32); cells2.add(dice2_32); cells3.add(dice3_32); cells4.add(dice4_32);
        cells1.add(dice33); cells2.add(dice2_33); cells3.add(dice3_33); cells4.add(dice4_33);
        cells1.add(dice34); cells2.add(dice2_34); cells3.add(dice3_34); cells4.add(dice4_34);
        cells1.add(dice35); cells2.add(dice2_35); cells3.add(dice3_35); cells4.add(dice4_35);
        cells1.add(dice41); cells2.add(dice2_41); cells3.add(dice3_41); cells4.add(dice4_41);
        cells1.add(dice42); cells2.add(dice2_42); cells3.add(dice3_42); cells4.add(dice4_42);
        cells1.add(dice43); cells2.add(dice2_43); cells3.add(dice3_43); cells4.add(dice4_43);
        cells1.add(dice44); cells2.add(dice2_44); cells3.add(dice3_44); cells4.add(dice4_44);
        cells1.add(dice45); cells2.add(dice2_45); cells3.add(dice3_45); cells4.add(dice4_45);
        communicator = GUIData.getGUIData().getCommunicator();
        text.setText("");
        window2.setVisible(false); username2.setVisible(false);
        window3.setVisible(false); username3.setVisible(false);
        window4.setVisible(false); username4.setVisible(false);
        poolItems = new ArrayList<>();
        window1Items = new ArrayList<>();
        window2Items = new ArrayList<>();
        window3Items = new ArrayList<>();
        window4Items = new ArrayList<>();
        cellsPool = new ArrayList<>();
        cellsPool.add(dice1); cellsPool.add(dice2); cellsPool.add(dice3); cellsPool.add(dice4); cellsPool.add(dice5);
        cellsPool.add(dice6); cellsPool.add(dice7); cellsPool.add(dice8); cellsPool.add(dice9);
        selectedDice.setVisible(false);
        Image backGround = new Image(getClass().getResourceAsStream("/GUI/wood.jpg"));
        tableBackground.setBackground(new Background(new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        cardsBackground.setBackground(new Background(new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        roundTrackBackground.setBackground(new Background(new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        window1.setStyle("-fx-background-color: #FFFFFF;");
        window2.setStyle("-fx-background-color: #FFFFFF;");
        window3.setStyle("-fx-background-color: #FFFFFF;");
        window4.setStyle("-fx-background-color: #FFFFFF;");
        cellsRound = new ArrayList<>();
        roundItems = new ArrayList<>();
        cellsRound.add(diceR11); cellsRound.add(diceR12); cellsRound.add(diceR13); cellsRound.add(diceR14); cellsRound.add(diceR15); cellsRound.add(diceR16); cellsRound.add(diceR17); cellsRound.add(diceR18); cellsRound.add(diceR19);
        cellsRound.add(diceR21); cellsRound.add(diceR22); cellsRound.add(diceR23); cellsRound.add(diceR24); cellsRound.add(diceR25); cellsRound.add(diceR26); cellsRound.add(diceR27); cellsRound.add(diceR28); cellsRound.add(diceR29);
        cellsRound.add(diceR31); cellsRound.add(diceR32); cellsRound.add(diceR33); cellsRound.add(diceR34); cellsRound.add(diceR35); cellsRound.add(diceR36); cellsRound.add(diceR37); cellsRound.add(diceR38); cellsRound.add(diceR39);
        cellsRound.add(diceR41); cellsRound.add(diceR42); cellsRound.add(diceR43); cellsRound.add(diceR44); cellsRound.add(diceR45); cellsRound.add(diceR46); cellsRound.add(diceR47); cellsRound.add(diceR48); cellsRound.add(diceR49);
        cellsRound.add(diceR51); cellsRound.add(diceR52); cellsRound.add(diceR53); cellsRound.add(diceR54); cellsRound.add(diceR55); cellsRound.add(diceR56); cellsRound.add(diceR57); cellsRound.add(diceR58); cellsRound.add(diceR59);
        cellsRound.add(diceR61); cellsRound.add(diceR62); cellsRound.add(diceR63); cellsRound.add(diceR64); cellsRound.add(diceR65); cellsRound.add(diceR66); cellsRound.add(diceR67); cellsRound.add(diceR68); cellsRound.add(diceR69);
        cellsRound.add(diceR71); cellsRound.add(diceR72); cellsRound.add(diceR73); cellsRound.add(diceR74); cellsRound.add(diceR75); cellsRound.add(diceR76); cellsRound.add(diceR77); cellsRound.add(diceR78); cellsRound.add(diceR79);
        cellsRound.add(diceR81); cellsRound.add(diceR82); cellsRound.add(diceR83); cellsRound.add(diceR84);
        cellsRound.add(diceR85); cellsRound.add(diceR86); cellsRound.add(diceR87); cellsRound.add(diceR88); cellsRound.add(diceR89);
        cellsRound.add(diceR91); cellsRound.add(diceR92); cellsRound.add(diceR93); cellsRound.add(diceR94); cellsRound.add(diceR95); cellsRound.add(diceR96); cellsRound.add(diceR97); cellsRound.add(diceR98); cellsRound.add(diceR99);
        cellsRound.add(diceR101); cellsRound.add(diceR102); cellsRound.add(diceR103); cellsRound.add(diceR104); cellsRound.add(diceR105); cellsRound.add(diceR106); cellsRound.add(diceR107); cellsRound.add(diceR108); cellsRound.add(diceR109);
        signals1 = new ArrayList<>();
        signals1.add(signal1); signals1.add(signal2); signals1.add(signal3); signals1.add(signal4); signals1.add(signal5); signals1.add(signal6);
        signals2 = new ArrayList<>();
        signals2.add(signal2_1); signals2.add(signal2_2); signals2.add(signal2_3); signals2.add(signal2_4); signals2.add(signal2_5); signals2.add(signal2_6);
        signals3 = new ArrayList<>();
        signals3.add(signal3_1); signals3.add(signal3_2); signals3.add(signal3_3); signals3.add(signal3_4); signals3.add(signal3_5); signals3.add(signal3_6);
        signals4 = new ArrayList<>();
        signals4.add(signal4_1); signals4.add(signal4_2); signals4.add(signal4_3); signals4.add(signal4_4); signals4.add(signal4_5); signals4.add(signal4_6);
        for(int c=0; c<6; c++){
            signals1.get(c).setVisible(false);
            signals2.get(c).setVisible(false);
            signals3.get(c).setVisible(false);
            signals4.get(c).setVisible(false);
        }
        roundsTexts = new ArrayList<>();
        roundsTexts.add(round1Text); roundsTexts.add(round2Text); roundsTexts.add(round3Text); roundsTexts.add(round4Text); roundsTexts.add(round5Text);
        roundsTexts.add(round6Text); roundsTexts.add(round7Text); roundsTexts.add(round8Text); roundsTexts.add(round9Text); roundsTexts.add(round10Text);
        for(int i=0; i<NUM_OF_ROUNDS; i++) roundsTexts.get(i).setVisible(false);
        roundsText.setVisible(false);
        plus.setVisible(false);
        minus.setVisible(false);
        faces = new ArrayList<>();
        faces.add(one); faces.add(two); faces.add(three); faces.add(four); faces.add(five); faces.add(six);
        for(int i=0; i<NUM_OF_FACES; i++){
            faces.get(i).setVisible(false);
        }
        POC1Name.setStyle("-fx-text-alignment: center;");
        POC1Description.setStyle("-fx-text-alignment: center;");
        POC2Name.setStyle("-fx-text-alignment: center;");
        POC2Description.setStyle("-fx-text-alignment: center;");
        POC3Name.setStyle("-fx-text-alignment: center;");
        POC3Description.setStyle("-fx-text-alignment: center;");
        PVOCName.setStyle("-fx-text-alignment: center;");
        PVOCDescription.setStyle("-fx-text-alignment: center;");
        tool1Name.setStyle("-fx-text-alignment: center;");
        tool1Description.setStyle("-fx-text-alignment: center;");
        tool2Name.setStyle("-fx-text-alignment: center;");
        tool2Description.setStyle("-fx-text-alignment: center;");
        tool3Name.setStyle("-fx-text-alignment: center;");
        tool3Description.setStyle("-fx-text-alignment: center;");
        text.setScrollTop(Double.MIN_VALUE);
        tool1Border.setStyle("-fx-fill: #00ffff;"); tool1Border.setVisible(false);
        tool2Border.setStyle("-fx-fill: #00ffff;"); tool2Border.setVisible(false);
        tool3Border.setStyle("-fx-fill: #00ffff;"); tool3Border.setVisible(false);
        moveButton.setVisible(false); toolCardButton.setVisible(false); skipButton.setVisible(false); cancelButton.setVisible(false);
        joinGameButton.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        playButton.setVisible(false);
    }

    /**
     * It's called if the button cancelButton is pressed.
     * It sends to the server the message "cancel".
     */
    public void cancelAction(){
        try {
            communicator.sendMessage("cancel");
        } catch (NetworkErrorException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * It's called when a drag is dropped over a rectangle of the player's window.
     * It sends to the server the position in which the drag is dropped.
     * It clears the dragBoard and consumes the event.
     */
    public void dragDroppedWindow(DragEvent event) {
        if(windowEnable_IN){
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                success = true;
                int row = NUM_OF_ROW, col = NUM_OF_COL;
                source = (Rectangle) event.getTarget();
                for (int j = START_NUMBER+1; j <= NUM_OF_ROW; j++) {
                    for (int k = 1; k <= NUM_OF_COL; k++) {
                        if (source.equals(cells1.get((j - 1) * (NUM_OF_COL) + (k - 1)))) {
                            row = j;
                            col = k;
                        }
                    }
                }
                try {
                    communicator.sendMessage(row+"/"+col);
                }
                catch (NetworkErrorException e1) {
                    e1.printStackTrace();
                }
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);
            db.clear();
        }
        event.consume();
    }

    /**
     * It's called when a drag is over a cell of the player's window.
     * It accept the transfer mode and consumes the event.
     */
    public void dragOverWindow(DragEvent event){
        if (event.getDragboard().hasString()) {
            /* allow for moving */
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    /**
     * It's called when is pressed a rectangle of the player's window.
     * It sends to the server the position of the rectangle in the window.
     * It consumes the event.
     */
    public void mousePressedWindow(MouseEvent e) {
        if(windowEnable_OUT){
            WindowFrame window= new WindowFrame();
            for(Player p : table.getPlayers()){
                if(p.getName().equals(GUIData.getGUIData().getUsername())){
                    window = p.getWindowFrame();
                }
            }
            int row = NUM_OF_ROW, col = NUM_OF_COL;
            source = (Rectangle) e.getSource();
            for (int j = START_NUMBER+1; j <= NUM_OF_ROW; j++) {
                for (int k = START_NUMBER+1; k <= NUM_OF_COL; k++) {
                    if (source.equals(cells1.get((j - 1) * (NUM_OF_COL) + (k - 1)))) {
                        row = j;
                        col = k;
                    }
                }
            }
            if(window.getDice(row, col) != null) {
                SnapshotParameters sp = new SnapshotParameters();
                sp.setTransform(Transform.scale(1.5, 1.5));
                int num = START_NUMBER;
                int stop = NUM_OF_COL * (row - 1) + col;
                int cont = START_NUMBER+1;
                for (int i = 1; i <= NUM_OF_ROW; i++) {
                    for (int j = 1; j <= NUM_OF_COL; j++) {
                        if (cont < stop) {
                            if (window.getDice(i, j) != null || window.getPatternCard().getRestriction(i, j).escape().compareTo("\u2680") >= 0) {
                                num++;
                            }
                            cont++;
                        }
                    }
                }
                draggableDice = window1Items.get(num);
                try {
                    communicator.sendMessage(row+"/"+col);
                } catch (NetworkErrorException e1) {
                    e1.printStackTrace();
                }
            }
        }
        e.consume();
    }

    /**
     * It's called if a drag in the windowFrame is detected.
     * The method works only if the windowFrame is enabled to be used.
     * It takes a snapshot of the related dice, it puts the snapshot in a clipboard
     * and accept the DragAndDrop over all rectangles of the window's player.
     * It sends to the server the index of the dragged dice in the window.
     * It consumes the event.
     */
    @FXML void dragDetectedWindow(){
        if(draggableDice != null){
            SnapshotParameters sp =  new SnapshotParameters();
            sp.setTransform(Transform.scale(1.5, 1.5));
            WritableImage preview = draggableDice.snapshot(sp,null);
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(source.getId());
            db.setContent(content);
            db.setDragView(preview, 45, 45);
            for(Rectangle r : cells1){
                r.startDragAndDrop(TransferMode.ANY); //display the dragAndDrop event
            }
            draggableDice=null;
        }
    }

    /**
     * It's called when a dice in the draftPool is pressed.
     * It sends to the server the number of the pressed dice.
     * It consumes the event.
     */
    @FXML
    public void mousePressedPool(MouseEvent e) {
        if(draftPoolEnable_OUT){
            source = (Rectangle) e.getSource();
            for(int i=0; i<MAX_TURNS; i++){
                if (source == cellsPool.get(i)) {
                    idPool = i+1;
                }
            }
            if(poolItems.get(idPool-initialPos-1)!=null) draggableDice = poolItems.get(idPool-initialPos-1);
            try {
                communicator.sendMessage(Integer.toString((idPool-initialPos)));
            } catch (NetworkErrorException e1) {
                e1.printStackTrace();
            }
        }
        e.consume();
    }

    /**
     * It's called if a drag in the draftPool is detected.
     * The method works only if the draftPool is enabled to be used.
     * It takes a snapshot of the related dice, it puts the snapshot in a clipboard
     * and accept the DragAndDrop over all rectangles of the window's player.
     * It sends to the server the index of the dragged dice in the draftPool.
     * It consumes the event.
     */
    @FXML
    public void dragDetectedPool() {
        if(draggableDice!=null){
            SnapshotParameters sp =  new SnapshotParameters();
            sp.setTransform(Transform.scale(1.5, 1.5));
            WritableImage preview = draggableDice.snapshot(sp,null);
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(source.getId());
            db.setContent(content);
            db.setDragView(preview, 45, 45);
            for(Rectangle r : cells1){
                r.startDragAndDrop(TransferMode.ANY); //display the dragAndDrop event
            }
        }
        draggableDice = null;
    }

    /**
     * It's called if a drag is detected on the selectDice.
     * It takes a snapshot of the related dice, it puts the snapshot in a clipboard
     * and accept the DragAndDrop over all rectangles of the window's player.
     * It sends to the server the index of the rectangle in the window's player
     * in which the drag is dropped.
     * It consumes the event.
     */
    @FXML
    public void dragDetectedSelectedDice(MouseEvent event){
        SnapshotParameters sp =  new SnapshotParameters();
        sp.setTransform(Transform.scale(1.5, 1.5));
        WritableImage preview = ((StackPane)selectedDice.getChildren().get(selectedDice.getChildren().size()-1)).snapshot(sp,null);
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getId());
        db.setContent(content);
        db.setDragView(preview, 45, 45);
        event.consume();
    }

    /**
     * It's called if a dice of the roundTrack is pressed.
     * The method works only if the roundTrack is enable to be pressed.
     * It sends to the server the index of the dice selected.
     * It consumes the event.
     */
    @FXML
    public void mousePressedRound(MouseEvent e) {
        if(roundTrackEnable){
            source = (Rectangle) e.getSource();
            for (int i = 0; i < NUM_OF_ROUNDS; i++) {
                for (int j = 0; j < MAX_TURNS; j++) {
                    if (source == cellsRound.get((j) * (NUM_OF_ROUNDS) + (i))) {
                        System.out.println("Selected roundTrack ROUND "+(i+1)+" DICE "+(j+1));
                        try {
                            GUIData.getGUIData().getCommunicator().sendMessage((i+1)+"/"+(j+1));
                        } catch (NetworkErrorException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
        e.consume();
    }

    /**
     * It's called when the moveButton is pressed.
     * It sends to the server the message "move".
     */
    @FXML
    public void moveAction(ActionEvent e){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("move");
        } catch (NetworkErrorException exc) {
            exc.printStackTrace();
        }
        e.consume();
    }

    /**
     * It's called when the toolCardButton is pressed.
     * It sends to the server the message "toolcard".
     */
    @FXML
    public void toolCardAction(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("toolcard");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * It's called when the skipButton is pressed.
     * It sends to the server the message "skip".
     */
    @FXML
    public void skipAction(ActionEvent e){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("skip");
        } catch (NetworkErrorException exc) {
            exc.printStackTrace();
        }
        e.consume();
    }

    /**
     * It's called when the joinGameButton is pressed.
     * It sends to the server the message "join".
     */
    @FXML
    public void joinGameAction(ActionEvent e){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("join");
        } catch (NetworkErrorException exc) {
            exc.printStackTrace();
        }
        joinGameButton.setVisible(false);
        e.consume();
    }

    /**
     * It's called by the class view(CLIView or GUIView) and receives as parameters a message
     * to print into the properly TextArea in the table.
     */
    public synchronized void editMessage(String message) {
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    if(!temp.equals("NULL")) {
                        if(temp.equals("ACTIVE")) temp="";
                        GUIData.getGUIData().getView().getGUIManager().editMessage(message);
                    }

                    if (message != null && (message.contains("New Turn.") || message.contains("NewTurn.") || message.equals("Wait your turn.") || message.contains("New round") || message.contains("It's your turn!"))) {
                        if (activeTool) {
                            removeBorder();
                            activeTool = false;
                        }
                    }
                    if(message!=null) {
                        this.text.appendText(message+"\n");
                    }
                    if(message.equals("Choose [play] to play again, [logout] to go back to login")){
                        playButton.setVisible(true);
                        joinGameButton.setVisible(false);
                    }
                    if(!temp2.equals("NULL")){
                        if(temp2.equals("ACTIVE")) temp2="";
                        GUIData.getGUIData().getView().getGUIManager().editMessage(message);
                    }
                }
        );
    }

    /**
     * It's empty since the patterns are shown only in the lobby.fxml file
     */
    public void showPattern(PatternCard pattern) {
    }

    /**
     * It's called by the class View (GUIView or CLIView) in order to update all the table
     * in javaFx application. This method calls methods: showPUOCs, showTools, showPVOC, showDraftPool,
     * showSelectedDice and showRoundTrack in order to properly update every component of the table.
     */
    public synchronized void updateTable(Table table) {
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    this.table = table;
                    showPUOCs(table.getGamePublicObjectiveCards());
                    showTools(table.getGameToolCards());
                    showPVOC(table.getPlayer(GUIData.getGUIData().getUsername()).getPrivateObjectiveCard());
                    showDraftPool(table.getDrawPool());
                    showSelectedDice(table.getActiveDice());
                    showRoundTrack(table.getRoundTrack());
                    int size = table.getPlayers().size();
                    int i=START_NUMBER;
                    int j=START_NUMBER;
                    for(Player p : table.getPlayers()){
                        if(p.getName().equals(GUIData.getGUIData().getUsername())) {
                            showWindow(p, window1, cells1, username1, window1Items, signals1);
                        }
                        else {
                            if(size==2) {showWindow(p, window2, cells2, username2, window2Items, signals2);}
                            else{
                                if(size==3 && i==0) {showWindow(p, window3, cells3, username3, window3Items, signals3); i++;}
                                else{
                                    if(size==3 && i==1) showWindow(p, window4, cells4, username4, window4Items, signals4);
                                    else {
                                        if(j==0) {showWindow(p, window2, cells2, username2, window2Items, signals2); j++;}
                                        else {if(j==1) {showWindow(p, window3, cells3, username3, window3Items, signals3); j++;}
                                        else if(j==2) {showWindow(p, window4, cells4, username4, window4Items, signals4); i++;}}
                                    }
                                }
                            }
                        }

                    }
                    if(!table.getScoreTrack().isEmpty()) {
                        this.showScoreTrack(table.getScoreTrack());
                        this.temp="ACTIVE";
                    }
                }
        );
    }

    /**
     * It's called by the method updateTable and contains the selectedDice.
     * This method show the related dice in the javaFx application in the properly box.
     */
    public void showSelectedDice(Dice item){
        if(item != null){
            selectedDice.getChildren().removeAll();
            StackPane dice = null;
            try {
                dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) item.valueOf())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dice.setStyle(colors.get(item.getColor()));
            selectedDice.add(dice, 0,0);
            selectedDice.setVisible(true);
        }  else selectedDice.setVisible(false);
    }

    /**
     * It's called by the method updateTable and contains the roundTrack.
     * This method show the related dices in the javaFx application in the properly boxes.
     */
    public void showRoundTrack(RoundTrack RT){
        Dice elem;
        StackPane dice = null;
        int size = roundItems.size();
        for(int k=START_NUMBER; k<size; k++){  //reset roundTrack
            roundTrack.getChildren().remove(roundItems.get(0));
            roundItems.remove(0);
        }
        roundsTexts.get(0).setStyle("-fx-fill: #00ff00");
        roundsTexts.get(0).setVisible(true);
        for (int i=0; i<10; i++){
            ArrayList<Dice> temp = RT.getRoundDices(i);
            if(temp.size()!=0){
                roundsTexts.get(i).setStyle("-fx-fill: #ffffff");
                roundsTexts.get(i).setVisible(true);
                if((i+1)<NUM_OF_ROUNDS){
                    roundsTexts.get(i+1).setStyle("-fx-fill: #00ff00");
                    roundsTexts.get(i+1).setVisible(true);
                }
            }
            if(temp != null){
                roundsText.setVisible(true);
                for(int j=START_NUMBER; j<MAX_TURNS; j++){
                    elem = null;
                    if(temp.size()>j) elem = temp.get(j);
                    if(elem != null){  //if there's a dice to add to roundTrack (ROUND i)
                        try {
                            dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) elem.valueOf())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dice.setStyle(colors.get(elem.getColor()));
                        roundItems.add(dice);
                        roundTrack.add(dice, i, j);
                        cellsRound.get((i) * (9) + (j)).toFront();
                    }
                }
            }
        }
    }

    /**
     * It's called by the method updateTable and contains an arrayList of PublicObjectiveCard.
     * This method show the related cards in the javaFx application in the properly boxes.
     */
    public void showPUOCs(ArrayList<PublicObjectiveCard> cards) {
        Image image;
        int i = START_NUMBER;
        for (PublicObjectiveCard card : cards) {
            InputStream inputStream= this.getClass().getResourceAsStream("/GUI/publicObj.PNG");
            image = new Image(inputStream);
            switch (i) {
                case (0):
                    POC1Name.setText(card.getName());
                    POC1ID.setText("ID: "+card.getID());
                    POC1Description.setText(card.getDescription());
                    POC1Points.setText(Integer.toString(card.getPoints()));
                    publicObj1.setImage(image);
                    break;
                case (1):
                    POC2Name.setText(card.getName());
                    POC2ID.setText("ID: "+card.getID());
                    POC2Description.setText(card.getDescription());
                    POC2Points.setText(Integer.toString(card.getPoints()));
                    publicObj2.setImage(image);
                    break;
                case (2):
                    POC3Name.setText(card.getName());
                    POC3ID.setText("ID: "+card.getID());
                    POC3Description.setText(card.getDescription());
                    POC3Points.setText(Integer.toString(card.getPoints()));
                    publicObj3.setImage(image);
                    break;
            }
            i++;
        }

    }

    /**
     * It's called by the method updateTable and contains an arrayList of ToolCard.
     * This method show the related cards in the javaFx application in the properly boxes.
     */
    public void showTools(ArrayList<ToolCard> cards) {
        Image image;
        int i = START_NUMBER;
        for (ToolCard card : cards) {
            InputStream inputStream= this.getClass().getResourceAsStream("/GUI/toolCard.PNG");
            image = new Image(inputStream);
            switch (i) {
                case (0):
                    tool1Name.setText(card.getName());
                    tool1ID.setText(Integer.toString(card.getID()));
                    String description = card.getDescription();
                    tool1Description.setText(description);
                    tool1Tokens.setText("Tokens: "+card.getNumOfTokens());
                    tool1.setImage(image);
                    break;
                case (1):
                    tool2Name.setText(card.getName());
                    tool2ID.setText(Integer.toString(card.getID()));
                    description = card.getDescription();
                    tool2Description.setText(description);
                    tool2Tokens.setText("Tokens: "+card.getNumOfTokens());
                    tool2.setImage(image);
                    break;
                case (2):
                    tool3Name.setText(card.getName());
                    tool3ID.setText(Integer.toString(card.getID()));
                    description = card.getDescription();
                    tool3Description.setText(description);
                    tool3Tokens.setText("Tokens: "+card.getNumOfTokens());
                    tool3.setImage(image);
                    break;
            }
            i++;
        }
    }

    /**
     * It's called by the method updateTable and contains an arrayList of PrivateObjectiveCard.
     * This method show the related cards in the javaFx application in the properly boxes.
     */
    public void showPVOC(PrivateObjectiveCard card) {
        PVOCName.setText(card.getName());
        PVOCID.setText("ID: "+card.getID());
        PVOCDescription.setText(card.getDescription());
        PVOCColor.setText(card.getColor().name());
        InputStream inputStream= this.getClass().getResourceAsStream("/GUI/privateObj.PNG");
        Image image = new Image(inputStream);
        privateObj.setImage(image);
    }

    /**
     * It's called by the method updateTable and contains the draftPool.
     * This method show the related dices in the javaFx application in the properly boxes.
     */
    public void showDraftPool(ArrayList<Dice> pool) {
        StackPane dice = null;
        int i = START_NUMBER;
        int size = poolItems.size();
        for(int k=START_NUMBER; k<size; k++){  //reset draftPool
            draftPool.getChildren().remove(poolItems.get(0));
            poolItems.remove(0);
        }
        initialPos = ((MAX_TURNS-pool.size())/2);
        i = initialPos;
        for(Rectangle p : cellsPool){
            p.setDisable(true);
        }
        for (Dice elem : pool) {
            try {
                dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) elem.valueOf())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dice.setStyle(colors.get(elem.getColor()));
            poolItems.add(dice);
            draftPool.add(dice, i, 0);
            cellsPool.get(i).setDisable(false);
            cellsPool.get(i).toFront();
            i++;
        }
    }

    /**
     * It's called by the method updateTable and contains all dices of a window.
     * This method show the related dices in the javaFx application in the properly boxes.
     */
    public void showWindow(Player player, GridPane grid, ArrayList<Rectangle> cells, Text username, ArrayList<StackPane> windowItems, ArrayList<Circle> signals) {
        for(int w=START_NUMBER; w<signals.size(); w++) signals.get(w).setVisible(false);
        int n = player.getNumOfTokens();
        for(int p=START_NUMBER; p<n; p++){
            signals.get(p).setVisible(true);
        }
        grid.setVisible(true);
        username.setVisible(true);
        if(!player.getName().equals(username.getText()))username.setText(player.getName());
        WindowFrame window = player.getWindowFrame();
        PatternCard pattern = player.getPatternCard();
        StackPane dice;
        int q=windowItems.size();
        for(int p=START_NUMBER; p<q; p++){  //reset window
            if(grid.getChildren().contains(windowItems.get(0))) grid.getChildren().remove(windowItems.get(0));
            windowItems.remove(0);
        }
        for (int j = START_NUMBER+1; j <= NUM_OF_ROW; j++) {
            for (int k = START_NUMBER+1; k <= NUM_OF_COL; k++) {
                dice = null;
                if (window.getDice(j, k) != null || pattern.getRestriction(j, k).escape().compareTo("\u2680") >= 0) {  //se la restrizione ?? un numero oppure c'?? un dado nella cella
                    try {
                        if (window.getDice(j, k) != null) { //se c'?? un dado nella cella
                            dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) window.getDice(j, k).valueOf())));
                            dice.setStyle(colors.get(window.getDice(j, k).getColor()));
                        } else  //se la restrizione ?? un numero
                            dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) (comparator.get(pattern.getRestriction(j, k).escape())))));
                        //dice.setStyle("-fx-background-color: #ffffff;");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (dice != null) {  //se devo aggiungere alla griglia un dado oppure una restrizione di numero
                    cells.get((j - 1) * (NUM_OF_COL) + (k - 1)).setStyle(null);
                    windowItems.add(dice);
                    grid.add(dice, k - 1, j - 1);
                    cells.get((j - 1) * (NUM_OF_COL) + (k - 1)).toFront();
                } else {  //se devo aggiungere una restrizione di colore
                    cells.get((j - 1) * (NUM_OF_COL) + (k - 1)).setStyle(null);
                    cells.get((j - 1) * (NUM_OF_COL) + (k - 1)).setStyle(colorsWindow.get(pattern.getRestriction(j, k).escape()));
                }
            }
        }
    }

    /**
     * This method is called if the toolCard1 is pressed and sends to the server the message "1".
     * This method works properly only if toolCards are enabled to be selecetd.
     */
    @FXML
    public void selectedTool1(){
        if(toolCardEnable) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("1");
                tool1Border.setVisible(true);
                activeTool = true;
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called if the toolCard2 is pressed and sends to the server the message "2".
     * This method works properly only if toolCards are enabled to be selecetd.
     */
    @FXML
    public void selectedTool2(){
        if(toolCardEnable) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("2");
                tool2Border.setVisible(true);
                activeTool = true;

            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called if the toolCard3 is pressed and sends to the server the message "3".
     * This method works properly only if toolCards are enabled to be selecetd.
     */
    @FXML
    public void selectedTool3(){
        if(toolCardEnable) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("3");
                tool3Border.setVisible(true);
                activeTool = true;
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called if the minusButton is pressed and sends to the server the message "-1".
     */
    @FXML
    public void minusPressed(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("-1");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called if the plusButton is pressed and sends to the server the message "+1".
     */
    @FXML
    public void plusPressed(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("+1");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called if the yesButton is pressed and sends to the server the message "1".
     */
    @FXML
    public void yesPressed(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("1");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called if the noButton is pressed and sends to the server the message "2".
     */
    @FXML
    public void noPressed(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("2");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called if a faceButton(one, two, three, four, five or six) is pressed
     * and sends to the server the related face.
     */
    @FXML
    public void faceSelected(ActionEvent event) {
        Button source = (Button) event.getSource();
        try {
            GUIData.getGUIData().getCommunicator().sendMessage(source.getText());
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called if the exitButton is pressed and sends to the server the message "logout".
     * It consumes the event.
     */
    @FXML
    public void exitAction(MouseEvent event){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("logout");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            URL location = getClass().getResource("/GUI/login.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            stage.setScene(new Scene(fxmlLoader.load(), 500, 650));
            stage.centerOnScreen();
            LoginManager LM = fxmlLoader.getController();
            GUIData.getGUIData().getView().setGUIManager(LM);
            GUIData.getGUIData().setTime(-1);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called if the exitButton is pressed and sends to the server the message "logout".
     * It consumes the event.
     */
    @FXML
    public void playAction(MouseEvent event){
        temp2 = "ACTIVE";
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("play");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/lobby.fxml"))));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method make every toolCards' component visible.
     */
    public void removeBorder(){
        tool1Border.setVisible(false);
        tool2Border.setVisible(false);
        tool3Border.setVisible(false);
    }

    /**
     * This method make moveButton, skipButton and toolCardButton not visible.
     */
    public void hideMoveButtons(){
        moveButton.setVisible(false);
        skipButton.setVisible(false);
        toolCardButton.setVisible(false);
    }

    /**
     * This method is empty because it's called only in the lobby.fxml file.
     */
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard){}

    /**
     * This method is called by the class View(GUIView or CLIView) when the game is ended.
     */
    public void showScoreTrack(ScoreTrack scoreTrack){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    Stage stage;
                    if(event!=null) {
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    } else stage = GUIData.getGUIData().getStage();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/scoreTrack.fxml"));
                        stage.setScene(new Scene(fxmlLoader.load()));
                        GUIData.getGUIData().getView().setGUIManager(fxmlLoader.getController());
                        stage.centerOnScreen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GUIData.getGUIData().getView().getGUIManager().showScoreTrack(scoreTrack);
                }
        );
    }

    /**
     * This method is called by the updateTable method and it makes the related active item visible on the screen.
     */
    public void activeElement(String element){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    if(element.equals("SEQUENTIAL")){
                        plus.setVisible(true);
                        minus.setVisible(true);
                    } else{
                        plus.setVisible(false);
                        minus.setVisible(false);
                    }
                    if(element.equals("CHOOSE")){
                        for(int i=START_NUMBER; i<NUM_OF_FACES; i++) faces.get(i).setVisible(true);
                    } else for(int i=START_NUMBER; i<NUM_OF_FACES; i++){
                        faces.get(i).setVisible(false);
                    }
                    if(element.equals("DRAFTPOOL_IN")){
                        draftPoolEnable_IN=true;
                    } else draftPoolEnable_IN=false;
                    if(element.equals("DRAFTPOOL_OUT")){
                        draftPoolEnable_OUT=true;
                    } else draftPoolEnable_OUT=false;
                    if(element.equals("WINDOW_IN")){
                        windowEnable_IN=true;
                    } else{
                        windowEnable_IN=false;
                    }
                    if(element.equals("WINDOW_OUT")){
                        windowEnable_OUT=true;
                    } else windowEnable_OUT=false;
                    if(element.equals("ROUNDTRACK")){
                        roundTrackEnable=true;
                    } else roundTrackEnable=false;
                    if(element.equals("TOOLCARD")){
                        toolCardEnable=true;
                    } else toolCardEnable=false;
                    if(element.equals("CHOOSE_ACTION")){
                        moveButton.setVisible(true);
                        toolCardButton.setVisible(true);
                        skipButton.setVisible(true);
                        cancelButton.setVisible(false);
                    } else {
                        moveButton.setVisible(false);
                        toolCardButton.setVisible(false);
                        skipButton.setVisible(false);
                        cancelButton.setVisible(true);
                    }
                    if(element.equals("START")){
                        cancelButton.setVisible(false);
                    }
                    if(element.equals("JOIN")){
                        joinGameButton.setVisible(true);
                    }
                    if(element.equals("INACTIVE_TABLE") || element.equals("JOIN")){
                        hideMoveButtons();
                        cancelButton.setVisible(false);
                    }
                    if(element.equals("YES_NO")) {
                        yesButton.setVisible(true);
                        noButton.setVisible(true);
                    } else{
                        yesButton.setVisible(false);
                        noButton.setVisible(false);
                    }
                }
        );
    }
}
