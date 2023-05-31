package com.example.national_id_parser;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    boolean checkForID;
    String natIDPattern = "([23])(\\d\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-4]|1[1-9]|2[1-9]|3[1-5]|88)\\d{3}(\\d)\\d";
    @Override
    public void start(Stage stage) throws IOException {
        checkForID = false;
        Insets def = new Insets(10,10,10,10);
        GridPane pane = new GridPane();
        Label natIDLbl = new Label("National ID Number: ");
        pane.add(natIDLbl,0,0);
        TextField natID = new TextField();
        pane.add(natID,1,0);
        pane.setAlignment(Pos.CENTER);
        natIDLbl.setPadding(def);
        Label validNatIDIndicator = new Label();
        validNatIDIndicator.setTextFill(Color.RED);
        pane.add(validNatIDIndicator,1,1);

        Label genderLbl = new Label("Gender: ");
        Label govLbl = new Label("Birth Governorate: ");
        Label bdateLbl = new Label("Birthdate: ");
        genderLbl.setPadding(def);
        govLbl.setPadding(def);
        bdateLbl.setPadding(def);
        pane.add(genderLbl,0,2);
        pane.add(govLbl,0,3);
        pane.add(bdateLbl,0,4);

        TextField gender = new TextField();
        TextField gov = new TextField();
        TextField bdate = new TextField();
        gender.setEditable(false);
        gov.setEditable(false);
        bdate.setEditable(false);
        gender.setStyle("-fx-control-inner-background: #F4F4F4;");
        gov.setStyle("-fx-control-inner-background: #F4F4F4;");
        bdate.setStyle("-fx-control-inner-background: #F4F4F4;");

        pane.add(gender,1,2);
        pane.add(gov,1,3);
        pane.add(bdate,1,4);

        natID.setOnKeyTyped(e -> {
            if (natID.getText().matches(natIDPattern)) {
                validNatIDIndicator.setText("");
                natID.setBorder(gov.getBorder());
                natID.setBackground(gov.getBackground());
                Pattern r = Pattern.compile(natIDPattern);
                Matcher m = r.matcher(natID.getText());
                m.find();
                gov.setText(numToGov(m.group(5)));
                gender.setText(numtoGender(m.group(6)));
                bdate.setText(StringToBdate(m.group(4), m.group(3), m.group(2), m.group(1)));
            } else if (natID.getText().matches("[23]") || natID.getText().matches("[23]\\d{1,2}") || natID.getText().matches("[23]\\d\\d[01]")
                    || natID.getText().matches("([23])(\\d\\d)(0[1-9]|1[0-2])") || natID.getText().matches("([23])(\\d\\d)(0[1-9]|1[0-2])[0-3]")
                    || natID.getText().matches("([23])(\\d\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])") || natID.getText().matches("([23])(\\d\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[01238]")
                    || natID.getText().matches("([23])(\\d\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-4]|1[1-9]|2[1-9]|3[1-5]|88)") || natID.getText().matches("([23])(\\d\\d)(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-4]|1[1-9]|2[1-9]|3[1-5]|88)\\d{1,4}")) {
                natID.setBorder(gov.getBorder());
                natID.setBackground(gov.getBackground());
                validNatIDIndicator.setText((14 - natID.getText().length()) + " digit(s) left");
                gov.setText("");
                gender.setText("");
                bdate.setText("");
            } else if (natID.getText().isBlank()) {
                validNatIDIndicator.setText("");
                gov.setText("");
                gender.setText("");
                bdate.setText("");
            } else if (natID.getText().length() == 14) {
                validNatIDIndicator.setText("Invalid National ID");
                natID.setBorder(Border.stroke(Color.RED));
                natID.setBackground(Background.fill(Color.rgb(255,200,200)));
                gov.setText("");
                gender.setText("");
                bdate.setText("");
            } else if (natID.getText().length() > 14 || (e.getCharacter().matches("\\D") && !(e.getCharacter().equals("\b")))) {
                natID.deletePreviousChar();
            } else {
                validNatIDIndicator.setText("Invalid National ID");
                natID.setBorder(Border.stroke(Color.RED));
                natID.setBackground(Background.fill(Color.rgb(255,200,200)));
                gov.setText("");
                gender.setText("");
                bdate.setText("");
            }
        });

        Scene scene = new Scene(pane);
        stage.setTitle("National ID Parser");
        stage.setWidth(400);
        stage.setHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String numToGov(String num) {
        if (num.equals("01")) return "Cairo";
        if (num.equals("22")) return "Beni Suef";
        if (num.equals("02")) return "Alexandria";
        if (num.equals("23")) return "Fayoum";
        if (num.equals("03")) return "Port Said";
        if (num.equals("24")) return "Minya";
        if (num.equals("04")) return "Suez";
        if (num.equals("25")) return "Assiut";
        if (num.equals("11")) return "Damietta";
        if (num.equals("26")) return "Sohag";
        if (num.equals("12")) return "Daqahliya";
        if (num.equals("27")) return "Qena";
        if (num.equals("13")) return "Sharqiya";
        if (num.equals("28")) return "Aswan";
        if (num.equals("14")) return "Qaliubia";
        if (num.equals("29")) return "Luxor";
        if (num.equals("15")) return "Kafr El-Sheikh";
        if (num.equals("31")) return "Red Sea";
        if (num.equals("16")) return "Gharbiya";
        if (num.equals("32")) return "New Valley";
        if (num.equals("17")) return "Menofiya";
        if (num.equals("33")) return "Matruh";
        if (num.equals("18")) return "Beheira";
        if (num.equals("34")) return "North Sinai";
        if (num.equals("19")) return "Ismailia";
        if (num.equals("35")) return "South Sinai";
        if (num.equals("21")) return "Giza";
        else return "Outside Egypt";
    }

    public static String numtoGender(String num) {
        if (num.matches("[13579]")) return "Male";
        else return "Female";
    }

    public static String StringToBdate(String day, String month, String year, String century) {
        if (century.equals("2")) century = "19";
        else century = "20";

        return day + "/" + month + "/" + century + year;
    }
}