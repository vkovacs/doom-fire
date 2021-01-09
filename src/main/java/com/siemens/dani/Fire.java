package com.siemens.dani;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;


public class Fire extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private int[][] pixels = new int[WIDTH][HEIGHT];
    private String[] colorCodes = new String[]{"#070707", "#1f0707", "#2f0f07", "#470f07", "#571707", "#671f07",
            "#771f07", "#8f2707", "#9f2f07", "#af3f07", "#bf4707", "#c74707", "#DF4F07", "#DF5707", "#DF5707",
            "#D75F07", "#D7670F", "#cf6f0f", "#cf770f", "#cf7f0f", "#CF8717", "#C78717", "#C78F17", "#C7971F",
            "#BF9F1F", "#BF9F1F", "#BFA727", "#BFA727", "#BFAF2F", "#B7AF2F", "#B7B72F", "#B7B737", "#CFCF6F",
            "#DFDF9F", "#EFEFC7", "#FFFFFF"};

    private Color[] fireColors = new Color[colorCodes.length];
    private Random r = new Random();
    private LocalTime start = LocalTime.now();

    @Override
    public void start(Stage stage) throws Exception {
        for (int i = 0; i < colorCodes.length; i++) {
            String code = colorCodes[i];
            fireColors[i] = Color.web(code);
        }

        for (int i = 0; i < WIDTH; i++) {
            pixels[i][HEIGHT - 1] = 35;
        }

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                for (int x = 1; x < WIDTH - 1; x++) {
                    for (int y = 0; y < HEIGHT - 1; y++) {
                        double direction = r.nextDouble();
                        if (direction < 0.4) {
                            if (r.nextDouble() < 0.2) {
                                pixels[x][y] = Math.max(0, pixels[x][y + 1] - 1);
                            } else {
                                pixels[x][y] = Math.max(0, pixels[x][y + 1]);
                            }
                        } else if (direction < 0.6) {
                            if (r.nextDouble() < 0.2) {
                                pixels[x + 1][y] = Math.max(0, pixels[x][y + 1] - 1);
                            } else {
                                pixels[x + 1][y] = Math.max(0, pixels[x][y + 1]);
                            }
                        } else {
                            if (r.nextDouble() < 0.2) {
                                pixels[x - 1][y] = Math.max(0, pixels[x][y + 1] - 1);
                            } else {
                                pixels[x - 1][y] = Math.max(0, pixels[x][y + 1]);
                            }
                        }

                        pixelWriter.setColor(x, y, fireColors[pixels[x][y]]);
                    }
                }
               if (LocalTime.now().minus(5, ChronoUnit.SECONDS).isAfter(start)) {
                    for (int i = 0; i < WIDTH; i++) {
                        if (r.nextDouble() < 0.2) {
                            pixels[i][HEIGHT - 1] -= 1;
                        }
                    }
                }
            }
        };

        Group root = new Group(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        timer.start();

    }

    public static void main(String args[]) {
        launch(args);
    }
}