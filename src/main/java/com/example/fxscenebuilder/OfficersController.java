package com.example.fxscenebuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import classes.*;
import database.*;

import java.io.IOException;
import java.util.ArrayList;

public class OfficersController {
    @FXML
    private TextField idText, nameText;
    @FXML
    private ChoiceBox<Rank> rankDropdown;
    @FXML
    private ListView<String> officerList;
    @FXML
    private Button getButton, getAllButton, createButton,
            updateButton, deleteButton, starshipsButton, officersButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private OfficerDatabase officerDb;
    private RankDatabase rankDb;

    @FXML
    public void initialize() {
        officerDb = new OfficerDatabase();
        rankDb = new RankDatabase();
        ArrayList<Rank> ranks = rankDb.getAllRanks();
        Rank[] rankArray = new Rank[ranks.size()];
        for (int i = 0; i < ranks.size(); i++) {
            rankArray[i] = ranks.get(i);
        }
        rankDropdown.getItems().addAll(ranks);
    }

    public void get(ActionEvent e) {
        int id = Integer.parseInt(idText.getText());
        Officer officer = officerDb.getOfficer(id);
        if (officer != null) {
            showOfficer(officer);
        } else {
            System.out.println("Not found!");
        }
    }

    public void getAll(ActionEvent e) {
        officerList.getItems().clear();
        ArrayList<Officer> officers = officerDb.getAllOfficers();
        String[] officerArray = new String[officers.size()];
        for (int i = 0; i < officers.size(); i++) {
            officerArray[i] = officers.get(i).name;
        }
        officerList.getItems().addAll(officerArray);
    }

    public void create(ActionEvent e) {
        String name = nameText.getText();
        Rank rank = rankDropdown.getValue();
        if (name.length() == 0) {
            System.out.println("Need a name!");
        }
        if (rank == null) {
            System.out.println("Need a rank!");
        }
        if (name.length() > 0 && rank != null) {
            Officer newOfficer = officerDb.createOfficer(name, rank.id);
            showOfficer(newOfficer);
        }
    }

    public void update(ActionEvent e) {
        String id = idText.getText();
        String name = nameText.getText();
        Rank rank = rankDropdown.getValue();
        if (id.length() == 0) {
            System.out.println("Need an id!");
        }
        if (name.length() == 0) {
            System.out.println("Need a name!");
        }
        if (rank == null) {
            System.out.println("Need a rank!");
        }
        if (name.length() > 0 && rank != null) {
            Officer oldOfficer = new Officer(Integer.parseInt(id), name, rank.id);
            Officer newOfficer = officerDb.updateOfficer(oldOfficer);
            showOfficer(newOfficer);
        }

    }

    public void delete(ActionEvent e) {
        String id = idText.getText();
        if (id.length() == 0) {
            System.out.println("Need an id!");
        } else {
            Officer officer = officerDb.getOfficer(Integer.parseInt(id));
            if (officer == null) {
                System.out.println("Officer not found");
            } else {
                boolean result = officerDb.deleteOfficer(officer);
                System.out.println(result);
            }
        }
    }

    public void showOfficer(Officer officer) {
        idText.setText(String.valueOf(officer.id));
        nameText.setText(officer.name);
        Rank rank = rankDb.getRank(officer.rank);
        rankDropdown.setValue(rank);
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
