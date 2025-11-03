import model.expressions.ArithExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import view.View;

public class Main {

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
        return new CompStmt( new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))), new CompStmt( new OpenRFile(new VarExp("varf")),
                        new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                                new CloseRFile(new VarExp("varf")))))))))
        );

    }


    public static void main(String[] args) {
        View view = new View(null);

/*        // EXP: 1
        System.out.println("First example: \n");
        view.run(exemple1());

        // EXP: 2
        System.out.println("\n\nSecond example: \n");
        view.run(exemple2());

        // EXP: 3
        System.out.println("\n\nThird example: \n");
        view.run(exemple3());*/

        // EXP: 4
        System.out.println("\n\nFourth example: \n");
        view.run(exemple4());
    }


}

