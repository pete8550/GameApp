/**
 *
 * @author pete8550
 * @version 0.1
 *
 * JANUAR OPGAVEN, DATAMATIKERUDDANNELSEN, 2. Semester.
 *
 * Type: Spil
 * Titel: Dog VS Katz
 * Krav: Design et spil vha. FXGL
 * Dokumentation: https://github.com/pete8550/GameApp
 *
 */

package com.peter.spil;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.components.KeepOnScreenComponent;
import com.almasb.fxgl.extra.entity.components.OffscreenCleanComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;

import javafx.geometry.Point2D;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

import static com.peter.spil.EntityType.ENEMY;

//main-classen med nedarvning fra GameApplication
public class GameApp extends GameApplication {

    //Variablerne player og enemy deklareres
    private Entity player;
    private Entity enemy;

    //Metode til at lade noget ske, før spilstart. I dette tilfælde, baggrundsmusik i loop
    @Override
    protected void preInit() {
        getAudioPlayer().loopBGM("bgm.wav");
    }

    //Metode til at lave spilvinduet + startmenu
    @Override
    protected void initSettings(GameSettings settings) {

        settings.setWidth(900); //Spilvinduets bredde
        settings.setHeight(660); //Spilvinduets højde
        settings.setTitle("Dog VS Katz"); //Titlen
        settings.setVersion("0.1"); //Version nummeret
        settings.setManualResizeEnabled(false); //Fastlåst størrelse af spilvinduet, (false)
        settings.setApplicationMode(ApplicationMode.RELEASE); //App mode
        settings.setMenuEnabled(true); //Default menu før spilvinduet åbnes, (true)
    }

    //Metode til at definere spillets Entities
    @Override
    protected void initGame() {

        //Konstruktion af player (hund objektet)
        player = Entities.builder()
                .type(EntityType.PLAYER)
                .at(400, 500)
                .viewFromTextureWithBBox("dog.png") //Bemærk: Beskæring af billede mangler
                .with(new CollidableComponent(true))
                .with(new KeepOnScreenComponent(true, true))
                .buildAndAttach(getGameWorld());

        //Konstruktion af food (mad objektet)
        Entities.builder()
                .type(EntityType.FOOD)
                .at(500, 200)
                .viewFromTextureWithBBox("food.png")
                .with(new CollidableComponent(true))
                .buildAndAttach(getGameWorld());

        //Konstruktion af enemy (firkant objektet)
         enemy = Entities.builder()
                .type(ENEMY)
                .at(200, 50)
                .viewFromNodeWithBBox(new Rectangle(20, 20, Color.GREEN))
                .with(new CollidableComponent(true))
                .with("velocity", new Point2D(2, 2))
                .buildAndAttach(getGameWorld());



         //Dette er Spillets design hvor et level-system opbygger forskellige tiltag i spillet
        getGameState().<Integer>addListener("foodCount", (prev, now) -> {

            //Level 2
            if (now == 5) {

                getGameState().increment("levelCount", +1);
                getAudioPlayer().playSound("Meow1.wav");

                //Endnu en enemy, dog med andre egenskaber (enemy_3 objektet)
                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 650)
                        .viewFromTextureWithBBox("enemy3.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            //Level 3
            if (now == 10) {
                getGameState().increment("levelCount", +1);
                getAudioPlayer().playSound("Meow2.wav");

                //En til enemy, også andre egenskaber (enemy_2 objektet)
                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("enemy2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            //Level 4
            if (now == 15) {
                getGameState().increment("levelCount", +1);

                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 500)
                        .viewFromTextureWithBBox("enemy3.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("enemy2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                //Tekst der bliver added til spillet
                Text textDUp = new Text("DOBBEL KATZE!");
                textDUp.setFont(Font.font("Verdana", 25));
                textDUp.setTranslateX(685);
                textDUp.setTranslateY(70);
                textDUp.setFill(Color.RED);
                textDUp.setUnderline(true);

                //Frigiver teksten til scene graph
                getGameScene().addUINode(textDUp);
            }

            //Level 5
            if (now == 20) {
                getGameState().increment("levelCount", +1);
                getAudioPlayer().playSound("Meow1.wav");

                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 500)
                        .viewFromTextureWithBBox("enemy3.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("enemy2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            if (now == 22) {
                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("enemy2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                //Endnu en tekst til spillet
                Text kat1 = new Text("KATZ UNO");
                kat1.setFont(Font.font("Verdana", 30));
                kat1.setTranslateX(50);
                kat1.setTranslateY(600);
                kat1.setFill(Color.YELLOW);
                kat1.setUnderline(true);

                getGameScene().addUINode(kat1);
            }

            if (now == 24) {
                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 500)
                        .viewFromTextureWithBBox("enemy3.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                //Endnu en tekst til spillet
                Text kat2 = new Text("KATZ DOS");
                kat2.setFont(Font.font("Verdana", 30));
                kat2.setTranslateX(370);
                kat2.setTranslateY(600);
                kat2.setFill(Color.YELLOW);
                kat2.setUnderline(true);

                getGameScene().addUINode(kat2);
            }

            if (now == 26) {
                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("enemy2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                //Endnu en tekst til spillet
                Text kat3 = new Text("KATZ TRES");
                kat3.setFont(Font.font("Verdana", 30));
                kat3.setTranslateX(650);
                kat3.setTranslateY(600);
                kat3.setFill(Color.YELLOW);
                kat3.setUnderline(true);

                getGameScene().addUINode(kat3);
            }

            if (now == 28) {
                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 500)
                        .viewFromTextureWithBBox("enemy3.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            //Level 6
            if (now == 30) {
                getGameState().increment("levelCount",+1);
                getAudioPlayer().playSound("Meow2.wav");

                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 500)
                        .viewFromTextureWithBBox("bigcat.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            //Level 7
            if (now == 40) {
                getGameState().increment("levelCount", +1);
                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("bigcat2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            //Level 8 - Last attack
            if (now == 50) {
                getGameState().increment("levelCount", +1);
                Entities.builder()
                        .type(EntityType.ENEMY_3)
                        .at(FXGLMath.random(getWidth() - 40), 500)
                        .viewFromTextureWithBBox("bigcat.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());

                Entities.builder()
                        .type(EntityType.ENEMY_2)
                        .at(FXGLMath.random(getWidth() - 40), 10)
                        .viewFromTextureWithBBox("bigcat2.png")
                        .with(new CollidableComponent(true))
                        .buildAndAttach(getGameWorld());
            }

            //Level 9 - The end
            if (now == 60) {
                getGameState().increment("levelCount", +1);

                //Messagebox der angiver at spillet er vundet, inklusiv lydfil i getAudioPlayer metoden
                getDisplay().showMessageBox("Tillykke! Du vandt mod kattehæren!");
                getAudioPlayer().playSound("CatScream.wav");
            }
        });
    }

    //Metode til at registrere hvordan enemies skal opføres sig (deres bevægelser)
    @Override
    protected void onUpdate(double tpf) {
        //Enemy 2 + 3 kommer ind i spilvinduet fra henholdsvis oppe- og nedefra
        getGameWorld().getEntitiesByType(EntityType.ENEMY_2).forEach(droplet -> droplet.translateY(250 * tpf));
        getGameWorld().getEntitiesByType(EntityType.ENEMY_3).forEach(droplet -> droplet.translateY(-250 * tpf));

        //Point2D bruges til at få enemy_1 (firkant objektet) til at svæve rundt i spilvinduet
        Point2D velocity = enemy.getObject("velocity");
        enemy.translate(velocity);

        //if-statements anvendes til at lade firkanten fortsætte rundt ved kontakt med spilrammen
        if (enemy.getBottomY() >= getHeight()) {
            enemy.setY(getHeight() - 20);
            enemy.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }

        if (enemy.getY() <= 0) {
            enemy.setY(0);
            enemy.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }

        if (enemy.getRightX() >= getWidth()) {
            enemy.setX(getWidth() - 20);
            enemy.setProperty("velocity", new Point2D(velocity.getY(), -velocity.getX()));
        }

        if (enemy.getX() <= 0) {
            enemy.setX(0);
            enemy.setProperty("velocity", new Point2D(velocity.getY(), -velocity.getX()));
        }
    }

    //metode til at angive, hvad der sker ved kontakt mellem 2 objekter
    @Override
    protected void initPhysics() {
        //kontakt mellem player og food angives
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.FOOD) {

            @Override
            protected void onCollisionBegin(Entity player, Entity food) {
                food.removeFromWorld();
                getGameState().increment("foodCount", +1);
                getAudioPlayer().playSound("Eat.wav");

                        Entities.builder()
                        .type(EntityType.FOOD)
                        //Bruger her FXGLMath klassens random-metode til at spawne food et tilfældigt sted
                        .at(FXGLMath.random(getWidth() - 64), (FXGLMath.random(getHeight() - 64)))
                        .viewFromTextureWithBBox("food.png")
                        .with(new CollidableComponent(true))
                        .with(new OffscreenCleanComponent())
                        .buildAndAttach(getGameWorld());
            }
        });

        //kontakt mellem player og enemy angives
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, ENEMY) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                getDisplay().showMessageBox("Hov! Ikke ram firkanten... Den er ond");
                getAudioPlayer().playSound("Hit.wav");

            }
        });

        //kontakt mellem player og enemy_2 angives
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ENEMY_2) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy_2) {
                getDisplay().showMessageBox("MiAV det gjorde ondt... Prøv igen");
                getAudioPlayer().playSound("Hit.wav");
            }
        });

        //kontakt mellem player og enemy_3 angives
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ENEMY_3) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy_3) {
                getDisplay().showMessageBox("Du KAT tro at du blev ramt... Prøv igen");
                getAudioPlayer().playSound("Hit.wav");
            }
        });

    }

    //metode til at styre playeren (hund objektet)
    //Styringen sker her med WASD-knapperne
    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(new UserAction("Gå til højre") {
            @Override
            protected void onAction() {
                player.translateX(5);
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Gå til venstre") {
            @Override
            protected void onAction() {
                player.translateX(-5);
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Gå op") {
            @Override
            protected void onAction() {
                player.translateY(-5);
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Gå ned") {
            @Override
            protected void onAction() {
                player.translateY(5);
                getGameState().increment("pixelsMoved", +5);
            }
        }, KeyCode.S);

        //Dette gør, at hunden kan gø ved at man klikker SPACE
        input.addAction(new UserAction("Gø") {
            @Override
            protected void onActionBegin() {

                getAudioPlayer().playSound("bark.wav");

            }
        }, KeyCode.SPACE);
    }

    //Metode der håndtere spilvariabler
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("foodCount", 0);
        vars.put("levelCount", 1);
    }

    //Metode der tilføjer diverse tekster og elementer til GUI'en.
    @Override
    protected void initUI() {

        //Angiver teksten "Retter spist:"
        Text textFood = new Text("Retter spist:");
        textFood.setFont(Font.font("Bradley Hand", 20));
        textFood.setTranslateX(95);
        textFood.setTranslateY(45);
        textFood.setFill(Color.RED);

        //Angiver tallet med, hvor mange retter der er spist
        Text food = new Text();
        food.setFont(Font.font("Bradley Hand", 20));
        food.setTranslateX(203);
        food.setTranslateY(45);
        food.setFill(Color.RED);

        //Forbinder "textFood" med "food"
        food.textProperty().bind(getGameState().intProperty("foodCount").asString());

        //Angiver teksten "Nuværende level:"
        Text textLevel = new Text("Nuværende level:");
        textLevel.setFont(Font.font("Bradley Hand", 20));
        textLevel.setTranslateX(95);
        textLevel.setTranslateY(70);
        textLevel.setFill(Color.RED);

        //Angiver tallet med antal level
        Text level = new Text();
        level.setFont(Font.font("Bradley Hand", 20));
        level.setTranslateX(250);
        level.setTranslateY(70);
        level.setFill(Color.RED);

        //Forbinder "textLevel" med "level"
        level.textProperty().bind(getGameState().intProperty("levelCount").asString());

        //Alle elementer tilføjes nu til scene graph
        getGameScene().addUINode(textLevel);
        getGameScene().addUINode(level);
        getGameScene().addUINode(textFood);
        getGameScene().addUINode(food);
        getGameScene().setBackgroundRepeat("background.jpg"); //Dette er baggrunden
    }

    //Main metoden der gør, at spillet kan køres
    public static void main (String[]args){
            launch(args);
        }
    }