package com.guils.guils;

// import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/*-------------------------TO DO-------------------------------------
1. Добавить сортировку по расширению *.txt
6. edit file from GuilS UI
7. Cache данные, указанная в прошлый раз директория или класс(школьный) сохронялись при перезапуске программы
8. Перемещенеи окна 

-----------------------------------------------------------------------*/

public class GuilSController {

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button editFileButton;

    @FXML
    private Button randButton;

    @FXML
    private Button directory;

    @FXML
    private ChoiceBox<String> gradeChoiceBox;

    @FXML
    private Pane scenePane;

    @FXML
    private Text textChoiceBox;

    @FXML
    private Text randomStudent;

    @FXML
    private Text directoryText;

    @FXML
    private Pane topPane;

    private double xOffset = 0;
    private double yOffset = 0;
    Stage primaryStage;
    Scene scene;
    String files;
    File selectedDirFile;
    private ArrayList<File> fileDirectoryList = new ArrayList<File>();
    ArrayList<String> directoryFileNameList = new ArrayList<String>();
    Scanner scanner = new Scanner("");
    ArrayList<String> students = new ArrayList<String>();

    @FXML
    void closeAction(ActionEvent event) {
        primaryStage = (Stage) scenePane.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    void minimizeAction(ActionEvent event) {
        primaryStage = (Stage) scenePane.getScene().getWindow();
        primaryStage.setIconified(true);
    }

    @FXML
    protected void handleMovementAction(MouseEvent event) {
        Stage stage = (Stage) topPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    protected void handlePressedAction(MouseEvent event) {
        xOffset = event.getX();
        yOffset = event.getY();
    }

    @FXML
    void editFileAction(ActionEvent event) {
        // try {
        // // constructor of file class having file as argument
        // File file = new File("C:\\demo\\demofile.txt");
        // if (!Desktop.isDesktopSupported())// check if Desktop is supported by
        // Platform or not
        // {
        // System.out.println("not supported");
        // return;
        // }
        // Desktop desktop = Desktop.getDesktop();
        // if (file.exists()) // checks file exists or not
        // desktop.open(file); // opens the specified file
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    void readDirectory() {
        // Чтение директории
        for (File file : selectedDirFile.listFiles()) {
            if (file.isFile())
                fileDirectoryList.add(file);
        }
    }

    @FXML
    void directoryChoice(ActionEvent event) throws IOException {
        try {
            if (directoryFileNameList.size() > 1) {
                directoryFileNameList.clear();
                fileDirectoryList.clear();
                gradeChoiceBox.getItems().clear();
            }

            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("src"));
            selectedDirFile = directoryChooser.showDialog(primaryStage);

            String selectedDirPath = selectedDirFile.getPath();
            directoryText.setText(selectedDirPath);

            readDirectory();
            // Вывод содержимого директории и добавление имен файлов в список
            // directoryNameList
            for (File file : fileDirectoryList) {
                String fileName = file.getName();
                directoryFileNameList.add(fileName);
            }
            // Выводит все содерждимое списка directoryFileNameList и добавляет в
            // grade ChoiseBox
            for (String s : directoryFileNameList) {
                gradeChoiceBox.getItems().addAll(s);
                gradeChoiceBox.setOnAction(this::gradeChoiceAction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gradeChoiceAction(ActionEvent event) {
        files = gradeChoiceBox.getValue();
        textChoiceBox.setText(files);
    }

    void readFile() {
        try {
            FileReader fileRead = new FileReader(fileDirectoryList.get(directoryFileNameList.indexOf(files)));
            BufferedReader reader = new BufferedReader(fileRead);
            String line = reader.readLine();

            for (int i = 0; line != null; i++) {
                students.add(i, line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("readFile ERROR");
        }

    }

    @FXML
    void randomChoiceFunction(ActionEvent event) {
        try {
            readFile();
            Random rand = new Random();
            String randomStudentString = students.get(rand.nextInt(students.size()));
            randomStudent.setText(randomStudentString);
            students.clear();
        } catch (Exception e) {
            System.out.print("");
        }

    }

}