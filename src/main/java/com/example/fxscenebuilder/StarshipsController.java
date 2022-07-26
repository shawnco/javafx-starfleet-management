package com.example.fxscenebuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import classes.*;
import database.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StarshipsController {
    @FXML
    private TextField idText, nameText, registryText;
    @FXML
    private ListView<String> shipList;
    @FXML
    private Button getButton, getAllButton, createButton,
            updateButton, deleteButton, starshipsButton, officersButton;

    private Parent root;
    private Stage stage;
    private Scene scene;
    private StarshipDatabase db;

    @FXML
    public void initialize() {
        db = new StarshipDatabase();
    }

    public void get(ActionEvent e) {
        int id = Integer.parseInt(idText.getText());
        Starship ship = db.getStarship(id);
        if (ship != null) {
            showStarship(ship);
        } else {
            System.out.println("Not found!");
        }
    }

    public void getAll(ActionEvent e) {
        shipList.getItems().clear();
        ArrayList<Starship> ships = db.getAllStarships();
        String[] shipNames = new String[ships.size()];
        for (int i = 0; i < ships.size(); i++) {
            shipNames[i] = ships.get(i).name;
        }
        shipList.getItems().addAll(shipNames);
    }

    public void create(ActionEvent e) {
        String name = nameText.getText();
        String registry = registryText.getText();
        if (name.length() == 0) {
            System.out.println("Need a name!");
        }
        if (registry.length() == 0) {
            System.out.println("Need a registry!");
        }
        if (name.length() > 0 && registry.length() > 0) {
            Starship newShip = db.createStarship(name, registry);
            showStarship(newShip);
        }
    }

    public void update(ActionEvent e) {
        String id = idText.getText();
        String name = nameText.getText();
        String registry = registryText.getText();
        if (id.length() == 0) {
            System.out.println("Need an id!");
        }
        if (name.length() == 0) {
            System.out.println("Need a name!");
        }
        if (registry.length() == 0) {
            System.out.println("Need a registry!");
        }
        if (name.length() > 0 && registry.length() > 0) {
            Starship oldShip = new Starship(Integer.parseInt(id), name, registry);
            Starship newShip = db.updateStarship(oldShip);
            showStarship(newShip);
        }
    }

    public void delete(ActionEvent e) {
        String id = idText.getText();
        if (id.length() == 0) {
            System.out.println("Need an id!");
        } else {
            Starship ship = db.getStarship(Integer.parseInt(id));
            if (ship == null) {
                System.out.println("Ship not found");
            } else {
                boolean result = db.deleteStarship(ship);
                System.out.println(result);
            }
        }
    }

    public void showStarship(Starship ship) {
        idText.setText(String.valueOf(ship.id));
        nameText.setText(ship.name);
        registryText.setText(ship.registry);
    }

    public void switchToStarshipScene(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Starships.fxml"));
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root, 420, 470);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOfficersView(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Officers.fxml"));
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root, 420, 470);
        stage.setScene(scene);
        stage.show();

    }
}