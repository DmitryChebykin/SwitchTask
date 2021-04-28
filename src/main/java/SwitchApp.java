import java.util.Scanner;

public class SwitchApp {
    public static void main(String[] args) {
        System.out.println("Над двумя вводимыми числами определены следующие операции:");
        printAllowedOperationsInfo();

        System.out.println("Введите два числа и код операции через пробелы (например:  124  23 3):");
        double[] operationData = getInputOperationData();

        printOperationInfo(operationData);
    }

    private static void printOperationInfo(double[] operationData) {
        if (!isInputOperationAllowed(operationData[2])) {
            System.out.println("Действие невозможно, для заданного кода не определена операция.");
        } else if (isDivisionByZero(operationData)) {
            System.out.println("Действие невозможно. Делить на ноль нельзя.");
        } else {
            printInputOperationDescription(operationData);

            double result = getResult(operationData);
            printOperationResultPrompt(operationData, result);
        }
    }

    private static void printInputOperationDescription(double[] operationData) {
        AllowedOperation operation = AllowedOperation.values()[(int) operationData[2] - 1];

        String operationType = operation.name();
        String ruOperationType = operation.getRuOperationName();
        System.out.println("Вы задали операцию " + operationType + "(" + ruOperationType + "):");
    }

    private static void printOperationResultPrompt(double[] operationData, double result) {
        AllowedOperation operation = AllowedOperation.values()[(int) operationData[2] - 1];
        System.out.println(operationData[0] + " " +
                operation.getOperationPrintSign() + " " +
                operationData[1] + " = " + result);
    }

    private static double getResult(double[] operationData) {
        double operationResult = 0;

        switch ((int) operationData[2]) {
            case 1:
                operationResult = operationData[0] + operationData[1];
                break;
            case 2:
                operationResult = operationData[0] - operationData[1];
                break;
            case 3:
                operationResult = operationData[0] * operationData[1];
                break;
            case 4:
                operationResult = operationData[0] / operationData[1];
                break;
        }

        return operationResult;
    }

    private static double[] getInputOperationData() {
        Scanner scanner = new Scanner(System.in);

        double[] operationData = new double[3];
        boolean isRightInput = false;

        while (!isRightInput) {
            String inputString = scanner.nextLine().replaceAll("\\s+", " ").trim();
            String[] parsedString = inputString.split("\\s");
            try {
                operationData[0] = Double.parseDouble(parsedString[0]);
                operationData[1] = Double.parseDouble(parsedString[1]);
                operationData[2] = Double.parseDouble(parsedString[2]);

                if (operationData[2] % 1 != 0) {
                    System.out.println("Неверный ввод кода операции, попробуйте снова!");
                    continue;
                }

                isRightInput = true;
            } catch (Exception e) {
                System.out.println("Неверный ввод, попробуйте снова!");
            }
        }

        return operationData;
    }

    private static boolean isInputOperationAllowed(double operationCode) {
        int countAllowedOperations = AllowedOperation.values().length;
        return 1 <= (int) operationCode && (int) operationCode <= countAllowedOperations;
    }

    private static boolean isDivisionByZero(double[] operationData) {
        return operationData[1] == 0 && operationData[2] == 4;
    }

    private static void printAllowedOperationsInfo() {
        for (AllowedOperation operation : AllowedOperation.values()) {
            System.out.printf("%-9s %s%n", operation.getRuOperationName(), " код " + operation.getOperationCode() + "");
        }
    }

    public enum AllowedOperation {
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
}