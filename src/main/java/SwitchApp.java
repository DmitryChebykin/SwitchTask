import java.util.Scanner;

enum AllowedOperation {
    ADDITION("Сложение", "+", 1),
    SUBTRACTION("Вычитание", "-", 2),
    MULTIPLICATION("Умножение", "*", 3),
    DIVISION("Деление", ":", 4);

    private final String ruOperationName;
    private final String operationPrintSign;
    private final int operationCode;

    AllowedOperation(String operationName, String operationSign, int operationCode) {
        this.ruOperationName = operationName;
        this.operationPrintSign = operationSign;
        this.operationCode = operationCode;
    }

    public String getRuOperationName() {
        return ruOperationName;
    }

    public String getOperationPrintSign() {
        return operationPrintSign;
    }

    public int getOperationCode() {
        return operationCode;
    }
}

public class SwitchApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static double number1;
    private static double number2;
    private static int codeOperation;

    public static void main(String[] args) {
        System.out.println("Над двумя вводимыми числами определены следующие операции:");
        printAllowedOperationsInfo();

        System.out.println("Потребуется ввести два числа и код операции.");
        getOperationData();

        printOperationInfo();
    }

    private static void printOperationInfo() {
        if (!isOperationAllowed(codeOperation)) {
            System.out.println("Действие невозможно, для заданного кода не определена операция.");
            return;
        }

        if (isDivisionByZero()) {
            System.out.println("Действие невозможно. Делить на ноль нельзя.");
            return;
        }

        printInputOperationDescription();

        double result = getResult(number1, number2);
        printOperationResultPrompt(result);
    }

    private static void printInputOperationDescription() {
        AllowedOperation operation = AllowedOperation.values()[codeOperation - 1];

        String operationType = operation.name();
        String ruOperationType = operation.getRuOperationName();
        System.out.println("Вы задали операцию " + operationType + "(" + ruOperationType + "):");
    }

    private static void printOperationResultPrompt(double result) {
        AllowedOperation operation = AllowedOperation.values()[codeOperation - 1];
        System.out.println(number1 + " " + operation.getOperationPrintSign() + " " +
                number2 + " = " + result);
    }

    private static double getResult(double number1, double number2) {
        double operationResult = 0;

        switch (codeOperation) {
            case 1:
                operationResult = number1 + number2;
                break;
            case 2:
                operationResult = number2 - number1;
                break;
            case 3:
                operationResult = number1 * number2;
                break;
            case 4:
                operationResult = number1 / number2;
                break;
        }

        return operationResult;
    }

    private static void getOperationData() {

        boolean isRightInput = false;

        while (!isRightInput) {
            try {
                System.out.println("Введите первое число:");
                number1 = scanner.nextDouble();

                System.out.println("Введите второе число:");
                number2 = scanner.nextDouble();

                System.out.println("Введите код операции:");
                codeOperation = scanner.nextInt();

                isRightInput = true;
            } catch (Exception e) {
                System.out.println("Неверный ввод, попробуйте снова!");
                scanner.nextLine();
            }
        }
    }

    private static boolean isOperationAllowed(double operationCode) {
        int countAllowedOperations = AllowedOperation.values().length;
        return 1 <= (int) operationCode && (int) operationCode <= countAllowedOperations;
    }

    private static boolean isDivisionByZero() {
        return number2 == 0 && codeOperation == 4;
    }

    private static void printAllowedOperationsInfo() {
        for (AllowedOperation operation : AllowedOperation.values()) {
            System.out.printf("%-9s %s%n", operation.getRuOperationName(), " код " + operation.getOperationCode() + "");
        }
    }
}