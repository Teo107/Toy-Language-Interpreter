import controller.Controller;
import model.PrgState;
import model.adt.MyDictionary;
import model.adt.MyList;
import model.adt.MyStack;
import model.expressions.ArithExp;
import model.expressions.RelationalExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

public class Interpreter {

    public static IStmt exemple1() {
        // int v; v=2; print(v);
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                ));
    }

    public static IStmt exemple2() {
        // int a; int b; a=2+3*5; b=a+1; Print(b);
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1, new ValueExp(new IntValue(2)),
                                new ArithExp(3, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp(1, new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b")))))
        );
    }

    public static IStmt exemple3() {
        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        return new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v",
                                        new ValueExp(new IntValue(2))), new AssignStmt("v",
                                        new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v")))))
        );
    }


    public static IStmt exemple4() {
       /* string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf)*/
        return new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFile(new VarExp("varf")),
                        new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                                new CloseRFile(new VarExp("varf")))))))))
        );
    }

    public static IStmt exemple5() {
//            *//*
//            int a,b;
//             a = 2;
//             b = 3;
//            if (a < b) "a mai mic"
//            else "b mai mic"
//            *//*
        return new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(2))),
                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(3))),
                                new IfStmt(new RelationalExp(1, new VarExp("a"), new VarExp("b")),
                                        new PrintStmt(new ValueExp(new StringValue("a mai mic"))),
                                        new PrintStmt(new ValueExp(new StringValue("b mai mic"))))))));
    }


    public static void main(String[] args) {
        // Example 1
        IStmt ex1 = exemple1();
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        // Exemple 2
        IStmt ex2 = exemple2();
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        // Example 3
        IStmt ex3 = exemple3();
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        // Example 4
        IStmt ex4 = exemple4();
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        IStmt ex5 = exemple4();
        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), ex4);
        IRepository repo5 = new Repository(prg4, "log5.txt");
        Controller ctr5 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));

        menu.show();
    }
}


