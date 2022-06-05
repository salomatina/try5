package ru.mephi;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.io.File;
import java.util.Set;

public class App extends Application {

    private TreeView<String> teachersTreeView;
    private TreeView<String> studentsTreeView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextArea textAreaImp = new TextArea();
        textAreaImp.setEditable(false);
        Group groupTextAreaImp = new Group(textAreaImp);
        textAreaImp.setPrefSize(300, 20);
        Button buttonImpDir = new Button("...");
        buttonImpDir.setPrefSize(30, 37);
        buttonImpDir.setOnAction(actionEvent -> {
            textAreaImp.clear();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                textAreaImp.appendText(file.getAbsolutePath());
            }
        });
        Group groupButtonImpDir = new Group(buttonImpDir);
        Button buttonImp = new Button("Import");
        buttonImp.setPrefSize(80, 37);
        buttonImp.setOnAction(actionEvent -> {
            Library library = new Library();
            try {
//                textAreaImp.setText("C:\\Users\\Елена\\IdeaProjects\\try5\\src\\main\\resources\\library.xlsx");
                if (textAreaImp.getText() != null) {
                    library.createStudentsSet(textAreaImp.getText());
                    library.createTeachersSet(textAreaImp.getText());
                    library.takeStudentsBooks(textAreaImp.getText());
                    library.takeTeachersBooks(textAreaImp.getText());
                    createTeachersTreeView(library.getTeacherSet());
                    createStudentsTreeView(library.getStudentSet());
                } else {
                    throw new NullPointerException("Directory isn't chosen");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Good job!");
                alert.setHeaderText(null);
                alert.setContentText("Done");
                alert.showAndWait();
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning!!!");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong and we didn't find a file");
                alert.showAndWait();
                exception.printStackTrace();
            }
        });
        Group groupButtonImp = new Group(buttonImp);
        teachersTreeView = new TreeView<>();
        teachersTreeView.setPrefSize(410, 180);
        HBox teachersTree = new HBox();
        teachersTree.getChildren().addAll(teachersTreeView);
        teachersTree.setPrefSize(510, 180);
        studentsTreeView = new TreeView<>();
        studentsTreeView.setPrefSize(410, 180);
        HBox studentsTree = new HBox();
        studentsTree.getChildren().addAll(studentsTreeView);
        studentsTree.setPrefSize(510, 180);

        HBox importChild = new HBox(groupButtonImpDir, groupTextAreaImp, groupButtonImp);
        VBox root = new VBox(importChild, teachersTree, studentsTree);
        Scene scene = new Scene(root, 410, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("let's do something!");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void createTeachersTreeView(Set<Teacher> teacherSet) {
        TreeItem<String> rootItem = new TreeItem<>("Teachers");
        for (Teacher teacher : teacherSet) {
            TreeItem<String> branchItem = new TreeItem<>(teacher.getSurname()
                    + " " + teacher.getName() + " " + teacher.getPatronymic());
            for (String s : teacher.getBookList()) {
                TreeItem<String> leafItem = new TreeItem<>(s);
                branchItem.getChildren().add(leafItem);
            }
            rootItem.getChildren().add(branchItem);
        }
        teachersTreeView.setRoot(rootItem);
        teachersTreeView.setShowRoot(true);
    }

    public void createStudentsTreeView(Set<Student> studentSet) {
        TreeItem<String> rootItem = new TreeItem<>("Students");
        for (Student student : studentSet) {
            TreeItem<String> branchItem = new TreeItem<>(student.getName() + " "
            + student.getSurname());
            for (String s : student.getBookList()) {
                TreeItem<String> leafItem = new TreeItem<>(s);
                branchItem.getChildren().add(leafItem);
            }
            rootItem.getChildren().add(branchItem);
        }
        studentsTreeView.setRoot(rootItem);
        studentsTreeView.setShowRoot(true);
    }

}