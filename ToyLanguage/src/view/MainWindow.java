package view;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PrgState;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class MainWindow {
    private TextField nrPrgState;
    private TableView<Map.Entry<Integer, IValue>> heapTable;
    private ListView<String> outView;
    private ListView<String> fileTableView;
    private ListView<Integer> prgStateIdView;
    private TableView<Map.Entry<String, IValue>> symTableView;
    private ListView<String> exeStackView;
    private Button runOneStepB;

    private Controller controller;


    public MainWindow(Controller controller) {
        this.controller = controller;
    }

    private void populateSymTable(PrgState prg) {
        symTableView.getItems().clear();

        Map<String, IValue> symTable = prg.getSymTable().getContent();
        symTableView.getItems().addAll(symTable.entrySet());
    }

    private void populateExeStack(PrgState prg) {
        exeStackView.getItems().clear();

        List<IStmt> stack = prg.getExeStack().getStack();
        for (int i=stack.size()-1; i>=0; i--)
            exeStackView.getItems().add(stack.get(i).toString());
    }

    private void refresh() {
        // a)
        List<PrgState> prgList = controller.getRepo().getPrgList();
        nrPrgState.setText("Number of PrgState: " + prgList.size());

        //b)
        heapTable.getItems().clear();
        if (!prgList.isEmpty()) {
            Map<Integer, IValue> heap = prgList.get(0).getHeap().getContent();

            heapTable.getItems().addAll(heap.entrySet());
        }

        //c)
        outView.getItems().clear();
        if(!prgList.isEmpty()){
            List<IValue> out = prgList.get(0).getOut().getList();
            for(IValue value : out)
                outView.getItems().add(value.toString());
        }

        //d)
        fileTableView.getItems().clear();

        if(!prgList.isEmpty()){
            Map<StringValue, BufferedReader> fileTable = prgList.get(0).getFileTable().getContent();

            for(StringValue fileName : fileTable.keySet())
                fileTableView.getItems().add(fileName.toString());
        }

        //e
        prgStateIdView.getItems().clear();
        for(PrgState prgState : prgList)
            prgStateIdView.getItems().add(prgState.getId());

        if(!prgList.isEmpty()){
            prgStateIdView.getSelectionModel().select(0);
            populateSymTable(prgList.get(0));
            populateExeStack(prgList.get(0));
        }
    }

    public void show() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();

        // a)
        nrPrgState = new TextField();
        nrPrgState.setEditable(false);
        root.setTop(nrPrgState);

        // b)
        heapTable = new TableView<>();

        TableColumn<Map.Entry<Integer, IValue>, Integer> keyCol = new TableColumn<>("Address");
        keyCol.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getKey()));

        TableColumn<Map.Entry<Integer, IValue>, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().toString()));

        heapTable.getColumns().add(keyCol);
        heapTable.getColumns().add(valueCol);
        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.setCenter(heapTable);

        // c), d)
        outView = new ListView<>();
        fileTableView = new ListView<>();
        VBox center  = new VBox(new Label("Heap"), heapTable,
                new Label("Out"), outView,
                new Label("FileTable"), fileTableView);

        center.setSpacing(10);
        root.setCenter(center);

        // e)
        prgStateIdView = new ListView<>();

        //h)
        runOneStepB = new Button("Run");
        runOneStepB.setOnAction(e -> {
            try {
                controller.oneStepForAllPrg();
                refresh();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
        });
        // id and button
        VBox left = new VBox(new Label("PrgState ID"), prgStateIdView,
                runOneStepB);
        left.setSpacing(10);
        root.setLeft(left);

        //f)
        symTableView = new TableView<>();

        TableColumn<Map.Entry<String, IValue>, String> nameCol = new TableColumn<>("Variable name");
        nameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getKey()));

        TableColumn<Map.Entry<String,IValue>, String> valCol = new TableColumn<>("Value");
        valCol.setCellValueFactory(data->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getValue().toString()));

        symTableView.getColumns().add(nameCol);
        symTableView.getColumns().add(valCol);
        symTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        prgStateIdView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldId, newId) -> {
                    if (newId == null)
                        return;
                    List<PrgState> prgList = controller.getRepo().getPrgList();
                    for (PrgState prg : prgList)
                        if (prg.getId() == newId) {
                            populateSymTable(prg);
                            populateExeStack(prg);
                            break;
                        }
                }
        );

        VBox right = new VBox(
                new Label("SymTable"),
                symTableView
        );
        right.setSpacing(10);
        root.setRight(right);

        //g)
        exeStackView = new ListView<>();
        VBox rightExe = new VBox(
                new Label("SymTable"), symTableView,
                new Label("ExeStack"), exeStackView);
        rightExe.setSpacing(10);
        root.setRight(rightExe);

        stage.setTitle("Program State");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        refresh();
    }
}