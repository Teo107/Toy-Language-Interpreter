package view;

import controller.Controller;
import exceptions.MyException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class GuiApp extends Application {

    @Override
    public void start(Stage stage) {

        List<IStmt> programs = new ArrayList<>();
        programs.add(Interpreter.exemple1());
        programs.add(Interpreter.exemple2());
        programs.add(Interpreter.exemple3());
        programs.add(Interpreter.exemple4());
        programs.add(Interpreter.exemple5());
        programs.add(Interpreter.exemple6());
        programs.add(Interpreter.exemple7());
        programs.add(Interpreter.exemple8());
        programs.add(Interpreter.exemple9());
        programs.add(Interpreter.exemple10());
        programs.add(Interpreter.exemple11());
        programs.add(Interpreter.exemple12());

        ListView<String> listView = new ListView<>();
        for (IStmt stmt : programs) {
            listView.getItems().add(stmt.toString());
        }

        listView.setOnMouseClicked(event -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            if (index < 0) return;

            IStmt selectedProgram = programs.get(index);

            try {
                selectedProgram.typecheck(new MyDictionary<>());

                PrgState prg = new PrgState(
                        new MyStack<>(),
                        new MyDictionary<>(),
                        new MyList<>(),
                        new MyDictionary<>(),
                        new MyHeap(),
                        selectedProgram
                );

                IRepository repo = new Repository(prg, "log.txt");
                Controller controller = new Controller(repo);

                new MainWindow(controller).show();

            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(listView);

        stage.setTitle("Select a program");
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}