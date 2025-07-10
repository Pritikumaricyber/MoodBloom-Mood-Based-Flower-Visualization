package controllers;

import database.DBConnection;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDateTime;

public class MoodController {
    private BorderPane root;
    private ListView<String> historyList;
    private StackPane flowerDisplay;

    public MoodController() {
        root = new BorderPane();

        VBox topBox = new VBox(new Text("🌸 MoodBloom - Virtual Garden"));
        topBox.setPadding(new Insets(10));
        root.setTop(topBox);

        VBox moodBox = new VBox(10);
        moodBox.setPadding(new Insets(10));
        String[] moods = {"Happy", "Sad", "Angry", "Calm", "Excited"};
        for (String mood : moods) {
            Button btn = new Button(mood);
            btn.setOnAction(event -> handleMood(mood));
            moodBox.getChildren().add(btn);
        }
        root.setLeft(moodBox);

        flowerDisplay = new StackPane();
        flowerDisplay.setPrefSize(400, 400);
        flowerDisplay.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc;");
        root.setCenter(flowerDisplay);

        VBox historyBox = new VBox(10);
        historyBox.setPadding(new Insets(10));
        historyBox.getChildren().add(new Label("🕒 Mood History"));
        historyList = new ListView<>();
        historyBox.getChildren().add(historyList);
        root.setRight(historyBox);

        loadHistory();
    }

    public Pane getView() {
        return root;
    }

    private void handleMood(String mood) {
        try (Connection conn = DBConnection.connect()) {
            String flowerType = mood.toLowerCase();
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO mood_history(mood, flower_type, date_time) VALUES (?, ?, ?)"
            );
            stmt.setString(1, mood);
            stmt.setString(2, flowerType);
            stmt.setTimestamp(3, timestamp);
            stmt.executeUpdate();

            Node flower = createFlowerForMood(mood);
            flowerDisplay.getChildren().setAll(flower);
            historyList.getItems().add(mood + " - " + timestamp.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM mood_history")) {

            while (rs.next()) {
                String mood = rs.getString("mood");
                String date = rs.getString("date_time");
                historyList.getItems().add(mood + " - " + date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   

    private Node createFlowerForMood(String mood) {
        Pane flowerPane = new Pane();

        switch (mood.toLowerCase()) {
            case "happy":
                   for (int i = 0; i < 8; i++) {
        double angle = i * Math.PI / 4;
        double x = 200 + Math.cos(angle) * 30;
        double y = 200 + Math.sin(angle) * 30;

        Ellipse petal = new Ellipse(x, y, 30, 15); // Oval petal
        petal.setFill(Color.web("#fe7696"));
        petal.setRotate(i * 45); // rotate each petal to fan around
        flowerPane.getChildren().add(petal);
    }

    // Inner darker petals (like your HTML rose layers)
    for (int i = 0; i < 6; i++) {
        double angle = i * Math.PI / 3;
        double x = 200 + Math.cos(angle) * 15;
        double y = 200 + Math.sin(angle) * 15;

        Ellipse petal = new Ellipse(x, y, 20, 10);
        petal.setFill(Color.web("#f03a64"));
        petal.setRotate(i * 60);
        flowerPane.getChildren().add(petal);
    }

    // Center core
    Circle center = new Circle(200, 200, 10, Color.web("#d1123f"));
    flowerPane.getChildren().add(center);

    // Stem
    Rectangle stem = new Rectangle(195, 250, 10, 80);
    stem.setFill(Color.web("#77a377"));
    flowerPane.getChildren().add(stem);

    // Leaves
    Ellipse leftLeaf = new Ellipse(180, 270, 20, 10);
    leftLeaf.setFill(Color.web("#8ac78a"));
    leftLeaf.setRotate(-30);
    flowerPane.getChildren().add(leftLeaf);

    Ellipse rightLeaf = new Ellipse(220, 270, 20, 10);
    rightLeaf.setFill(Color.web("#8ac78a"));
    rightLeaf.setRotate(30);
    flowerPane.getChildren().add(rightLeaf);
    break;


            case "sad":
               for (int i = 0; i < 5; i++) {
                    double angle = i * 2 * Math.PI / 5;
                    double x = 200 + Math.cos(angle) * 50;
                    double y = 200 + Math.sin(angle) * 50;
                    Circle petal = new Circle(x, y, 25, Color.YELLOW);
                    flowerPane.getChildren().add(petal);
                }
 

    // Sad face center
    Circle sadFace = new Circle(200, 200, 35);
    sadFace.setFill(Color.BISQUE);
    flowerPane.getChildren().add(sadFace);

    // Eyes
    Circle sadLeftEye = new Circle(190, 190, 5, Color.BLACK);
    Circle sadRightEye = new Circle(210, 190, 5, Color.BLACK);
    flowerPane.getChildren().addAll(sadLeftEye, sadRightEye);

    Circle sadLeftPupil = new Circle(191, 189, 2, Color.WHITE);
    Circle sadRightPupil = new Circle(211, 189, 2, Color.WHITE);
    flowerPane.getChildren().addAll(sadLeftPupil, sadRightPupil);

    // Sad mouth
    QuadCurve sadMouth = new QuadCurve();
    sadMouth.setStartX(190);
    sadMouth.setStartY(215);
    sadMouth.setEndX(210);
    sadMouth.setEndY(215);
    sadMouth.setControlX(200);
    sadMouth.setControlY(190);  // curve up for frown
    sadMouth.setStroke(Color.DARKRED);
    sadMouth.setFill(null);
    sadMouth.setStrokeWidth(2);
    flowerPane.getChildren().add(sadMouth);

    // Stem
    QuadCurve sadStem = new QuadCurve(200, 235, 180, 300, 200, 350);
    sadStem.setStroke(Color.DARKGREEN);
    sadStem.setFill(null);
    sadStem.setStrokeWidth(6);
    flowerPane.getChildren().add(sadStem);

    // Leaves
    Ellipse sadLeftLeaf = new Ellipse(185, 290, 15, 8);
    sadLeftLeaf.setFill(Color.FORESTGREEN);
    sadLeftLeaf.setRotate(-30);
    flowerPane.getChildren().add(sadLeftLeaf);

    Ellipse sadRightLeaf = new Ellipse(215, 310, 15, 8);
    sadRightLeaf.setFill(Color.FORESTGREEN);
    sadRightLeaf.setRotate(30);
    flowerPane.getChildren().add(sadRightLeaf);
    break;


            case "angry":
                 // 🔺 Outer sharp petals (like lotus spikes)
    int angryPetalCount = 8;
    double cx = 200, cy = 200, outerRadius = 70;

    for (int i = 0; i < angryPetalCount; i++) {
        double angle = 2 * Math.PI * i / angryPetalCount;

        double x1 = cx + Math.cos(angle) * outerRadius;
        double y1 = cy + Math.sin(angle) * outerRadius;

        double x2 = cx + Math.cos(angle + 0.2) * (outerRadius + 25);
        double y2 = cy + Math.sin(angle + 0.2) * (outerRadius + 25);

        double x3 = cx + Math.cos(angle - 0.2) * (outerRadius + 25);
        double y3 = cy + Math.sin(angle - 0.2) * (outerRadius + 25);

        Polygon triangle = new Polygon(cx, cy, x2, y2, x3, y3);
        triangle.setFill(Color.DARKRED);
        flowerPane.getChildren().add(triangle);
    }

    // 🔴 Center circle
    Circle core = new Circle(cx, cy, 35);
    core.setFill(Color.CRIMSON);
    core.setStroke(Color.BLACK);
    core.setStrokeWidth(2);
    flowerPane.getChildren().add(core);

    // ⚡ Cracked overlay shape (simulate broken core)
    Polygon crackLeft = new Polygon(
     180, 208,
    190, 198,
    195, 213,
    200, 198,
    205, 218,
    210, 203,
    220, 208,
    200, 223
    );
    crackLeft.setFill(Color.PINK);
    flowerPane.getChildren().add(crackLeft);

    // 🟩 Stem
    Rectangle angrystem = new Rectangle(195, 235, 10, 160);
    angrystem.setFill(Color.DARKGREEN);
    flowerPane.getChildren().add(angrystem);

    // 🌿 Angry wide leaves
    Ellipse angryleftLeaf = new Ellipse(170, 350, 30, 12);
    angryleftLeaf.setFill(Color.FORESTGREEN);
    angryleftLeaf.setRotate(-25);
    flowerPane.getChildren().add(angryleftLeaf);

    Ellipse angryrightLeaf = new Ellipse(230, 320, 30, 12);
    angryrightLeaf.setFill(Color.FORESTGREEN);
    angryrightLeaf.setRotate(25);
    flowerPane.getChildren().add(angryrightLeaf);

    // Eyes
Circle leftEye = new Circle(190, 190, 5, Color.BLACK);
Circle rightEye = new Circle(210, 190, 5, Color.BLACK);
flowerPane.getChildren().addAll(leftEye, rightEye);

// Angry Eyebrows
Line leftBrow = new Line(185, 180, 195, 185); // slanted down
Line rightBrow = new Line(205, 185, 215, 180); // slanted up
leftBrow.setStrokeWidth(2);
rightBrow.setStrokeWidth(2);
flowerPane.getChildren().addAll(leftBrow, rightBrow);

    break;
            case "calm":
                // Center of flower
            double cx1 = 200;
            double cy1 = 180;

// Main front petal (oval)
Ellipse calmfrontPetal = new Ellipse(cx1, cy1, 35, 50);
calmfrontPetal.setFill(Color.GOLD);
calmfrontPetal.setStroke(Color.ORANGE);

// Left petal (slightly behind)
Ellipse calmleftPetal = new Ellipse(cx1 - 25, cy1, 25, 50);
calmleftPetal.setFill(Color.GOLDENROD);
calmleftPetal.setStroke(Color.ORANGE);

// Right petal
Ellipse calmrightPetal = new Ellipse(cx1 + 25, cy1, 25, 50);
calmrightPetal.setFill(Color.GOLDENROD);
calmrightPetal.setStroke(Color.ORANGE);

// Curved stem
QuadCurve calmstem = new QuadCurve(cx1, cy1 + 50, cx1 - 10, cy1 + 150, cx1, cy1 + 250);
calmstem.setStroke(Color.DARKGREEN);
calmstem.setStrokeWidth(4);
calmstem.setFill(null);

// Left long leaf
//QuadCurve calmleftLeaf = new QuadCurve(cx1 - 10, cy1 + 150, cx1 - 60, cy1 + 200, cx1 - 10, cy1 + 250);
//calmleftLeaf.setFill(Color.FORESTGREEN);
//calmleftLeaf.setStroke(Color.DARKGREEN);

// Right long leaf
//QuadCurve calmrightLeaf = new QuadCurve(cx1 + 10, cy1 + 150, cx1 + 60, cy1 + 200, cx1 + 10, cy1 + 250);
//calmrightLeaf.setFill(Color.FORESTGREEN);
//calmrightLeaf.setStroke(Color.DARKGREEN);

// LEFT Leaf
Path calmleftLeaf = new Path();
calmleftLeaf.getElements().addAll(
    new MoveTo(190, 330),
    new QuadCurveTo(140, 350, 190, 380), // left curve
    new QuadCurveTo(170, 350, 190, 330)  // right curve (back to top)
);
calmleftLeaf.setFill(Color.FORESTGREEN);
calmleftLeaf.setStroke(Color.DARKGREEN);

// RIGHT Leaf
Path calmrightLeaf = new Path();
calmrightLeaf.getElements().addAll(
    new MoveTo(220, 280),
    new QuadCurveTo(270, 300, 220, 360), // right curve
    new QuadCurveTo(240, 300, 220, 280)  // left curve (back to top)
);
calmrightLeaf.setFill(Color.FORESTGREEN);
calmrightLeaf.setStroke(Color.DARKGREEN);

// Add to flowerPane
flowerPane.getChildren().addAll(calmleftPetal, calmrightPetal,calmfrontPetal, calmstem, calmleftLeaf, calmrightLeaf);

break;

            case "excited":
                   double centerX = 200;
    double centerY = 200;

    // 🌼 Yellow center
    Circle excitedCenter = new Circle(centerX, centerY, 15);
    excitedCenter.setFill(Color.GOLD);

    // 🌸 Outer petals (lavender, large)
    int outerPetals = 7;
    double outerRadius1 = 60;
    double outerWidth = 25;
    double outerHeight = 70;

    for (int i = 0; i < outerPetals; i++) {
        double angle = 2 * Math.PI * i / outerPetals;
        double x = centerX + outerRadius1 * Math.cos(angle);
        double y = centerY + outerRadius1 * Math.sin(angle);

        Ellipse petal = new Ellipse(x, y, outerWidth, outerHeight);
        petal.setFill(Color.web("#D3BCEB")); // lavender
        petal.setStroke(Color.PURPLE);
        petal.setRotate(Math.toDegrees(angle));
        flowerPane.getChildren().add(petal);
    }

    // 🌸 Inner petals
    int innerPetals = 3;
    double innerRadius = 20;
    double innerWidth = 18;
    double innerHeight = 45;

    for (int i = 0; i < innerPetals; i++) {
        double angle = 2 * Math.PI * i / innerPetals;
        double x = centerX + innerRadius * Math.cos(angle);
        double y = centerY + innerRadius * Math.sin(angle);

        Ellipse petal = new Ellipse(x, y, innerWidth, innerHeight);
        petal.setFill(Color.web("#D3BCEB"));
        petal.setStroke(Color.PURPLE);
        petal.setRotate(Math.toDegrees(angle));
        flowerPane.getChildren().add(petal);
    }

    // 🌿 Stem
    Rectangle excitedStem = new Rectangle(centerX - 3, centerY + 50, 6, 150);
    excitedStem.setFill(Color.DARKGREEN);
    flowerPane.getChildren().add(excitedStem);

    // 🍃 Leaves
    Ellipse excitedLeftLeaf = new Ellipse(centerX - 30, centerY+50 + 100, 25, 12);
    excitedLeftLeaf.setFill(Color.FORESTGREEN);
    excitedLeftLeaf.setRotate(-25);

    Ellipse excitedRightLeaf = new Ellipse(centerX + 30, centerY+30 + 90, 25, 12);
    excitedRightLeaf.setFill(Color.FORESTGREEN);
    excitedRightLeaf.setRotate(25);

    flowerPane.getChildren().addAll(excitedLeftLeaf, excitedRightLeaf, excitedCenter);
    break;
            default:
                flowerPane.getChildren().add(new Text(180, 200, "No flower"));
        }

        return flowerPane;
    }
}
