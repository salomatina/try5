package ru.mephi;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.mephi.books.Book;
import ru.mephi.books.BookException;
import ru.mephi.people.People;
import ru.mephi.people.Student;
import ru.mephi.people.Teacher;

import java.io.File;
import java.util.List;

public class App extends Application {

    private TreeView<String> teachersTreeView = new TreeView<>();
    private TreeView<String> studentsTreeView = new TreeView<>();
    private Generator generator;
    private Library library;

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
            try {
                textAreaImp.setText("C:\\Users\\Елена\\IdeaProjects\\try5\\src\\main\\resources\\library.xlsx");
                generator = new Generator(textAreaImp.getText());
                library = generator.generateLibrary();

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
        studentsTree.setPrefSize(410, 180);

        Button nextWeekButton = new Button("Next week");
        nextWeekButton.setPrefSize(410, 40);
        nextWeekButton.setOnAction(actionEvent -> {
            for (int i = 0; i < library.getTakenBooks().size(); i++) {
                Record record = library.getTakenBooks().get(i);
                record.makeLengthShorter();
                if (record.getLength() == 0) {
                    People person = record.getPerson();
                    Book book = record.getBook();
                    try {
                        person.returnBook(book, library);
                    }
                    catch (BookException e) {
                        e.printStackTrace();
                    }
                }
            }
            int n = (library.getStudents().size() + library.getTeachers().size()) / 4;
            for (int i = 0; i < n; i++) {
                try {
                    generator.randomMove(library);
                }
                catch (BookException e) {
                    e.printStackTrace();
                }
            }
            createStudentsTreeView(library.getStudents());
            createTeachersTreeView(library.getTeachers());
        });
        Group weekGroup = new Group(nextWeekButton);

        HBox importChild = new HBox(groupButtonImpDir, groupTextAreaImp, groupButtonImp);
        VBox root = new VBox(importChild, teachersTree, studentsTree, weekGroup);
        Scene scene = new Scene(root, 410, 440);
        primaryStage.setScene(scene);
        primaryStage.setTitle("let's do something!");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void createTeachersTreeView(List<Teacher> teacherSet) {
        TreeItem<String> rootItem = new TreeItem<>("Teachers");
        for (Teacher teacher : teacherSet) {
            TreeItem<String> branchItem = new TreeItem<>(teacher.toString());
            for (Book s : teacher.getBookList()) {
                TreeItem<String> leafItem = new TreeItem<>(s.toString());
                branchItem.getChildren().add(leafItem);
            }
            rootItem.getChildren().add(branchItem);
        }
        teachersTreeView.setRoot(rootItem);
        teachersTreeView.setShowRoot(true);
    }

    public void createStudentsTreeView(List<Student> studentSet) {
        TreeItem<String> rootItem = new TreeItem<>("Students");
        for (Student student : studentSet) {
            TreeItem<String> branchItem = new TreeItem<>(student.toString());
            for (Book s : student.getBookList()) {
                TreeItem<String> leafItem = new TreeItem<>(s.toString());
                branchItem.getChildren().add(leafItem);
            }
            rootItem.getChildren().add(branchItem);
        }
        studentsTreeView.setRoot(rootItem);
        studentsTreeView.setShowRoot(true);
    }

}
