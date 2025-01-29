import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
public class Calculadora {
    public static void main(String[] args) {
        String operacion, numeroActual="";
        double numeroActualDouble=0, resultado=0, operacionE;
        int caracter2, numOperacion=0;
        boolean preparado= false;
        boolean programa = true;
        String[][] array = new String[0][3];
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        Scanner lector = new Scanner(System.in);
        while (programa) {
            System.out.print("""
                    
                    [Q] Terminar el programa\
                    
                    [H] Ayuda\
                    
                    [L] Listar operaciones realizadas\
                    
                    
                    Operación:\s""");
            operacion = lector.nextLine().toLowerCase();
            switch (operacion) {
                case "q":
                    programa = false;
                    break;
                case "h":
                    System.out.println("""
                            Normas de escritura:
                            1. No juntes los símbolos (+ y -), ejemplo: [1++1] [1-+1] [1--1]
                            2. Carácteres admitidos en las operaciones: ([0-∞], [.] (para decimales), [+], [-], [*], [/], [=])""");
                    break;
                case "l":
                    for (String[] strings : array) {
                        for (int j = 0; j < 3; j++) {
                            System.out.print(strings[j] + " ");
                        }
                        System.out.println();
                    }
                    break;
                default:
                    operacion = operacion.replaceAll(" ", "");
                    operacion = operacion.replaceAll("=", "");
                    String[][] arrayCopia = new String[array.length + 1][3];
                    System.arraycopy(array, 0, arrayCopia, 0, array.length);
                    array = arrayCopia;
                    numOperacion++;
                    LocalTime now = LocalTime.now();
                    array[array.length - 1][0] = "[" + numOperacion + "]";
                    array[array.length - 1][1] = " (" + dtf.format(now) + ") ";
                    array[array.length - 1][2] = operacion + " = ";
                    operacion = operacion.replaceAll("\\+\\+", "+");
                    operacion = operacion.replaceAll("-\\+", "-");
                    operacion = operacion.replaceAll("\\+-", "-");
                    operacion = operacion.replaceAll("--", "+");
                    operacion = operacion.replaceAll(",", ".");
                    for (int caracter = 0; caracter < operacion.length(); caracter++) {
                        if (operacion.charAt(caracter) == '+') {
                            resultado += numeroActualDouble;
                            numeroActual = "+";
                        } else if (operacion.charAt(caracter) == '-') {
                            resultado += numeroActualDouble;
                            numeroActual = "-";
                        } else if (operacion.charAt(caracter) == '/' || operacion.charAt(caracter) == '*') {
                            StringBuilder num2 = new StringBuilder();
                            caracter2 = caracter + 1;
                            if (operacion.charAt(caracter2) == '+' || operacion.charAt(caracter2) == '-') {
                                num2.append(operacion.charAt(caracter2));
                                caracter2++;
                            }
                            for (; caracter2 < operacion.length()
                                    && operacion.charAt(caracter2) != '-'
                                    && operacion.charAt(caracter2) != '+'
                                    && operacion.charAt(caracter2) != '*'
                                    && operacion.charAt(caracter2) != '/'
                                    ; caracter2++) {
                                num2.append(operacion.charAt(caracter2));
                            }
                            if (!preparado || numeroActual.charAt(0) == '+' || numeroActual.charAt(0) == '-') {
                                if (operacion.charAt(caracter) == '/') {
                                    operacionE = numeroActualDouble / Double.parseDouble(num2.toString());
                                } else {
                                    operacionE = numeroActualDouble * Double.parseDouble(num2.toString());
                                }
                                resultado += operacionE;
                            } else {
                                if (operacion.charAt(caracter) == '/') {
                                    resultado = resultado / Double.parseDouble(num2.toString());
                                } else {
                                    resultado = resultado * Double.parseDouble(num2.toString());
                                }
                            }
                            caracter = caracter2 - 1;
                            numeroActual = "0";
                            numeroActualDouble = 0;
                            preparado = true;
                        } else {
                            numeroActual += operacion.charAt(caracter);
                            numeroActualDouble = Double.parseDouble(numeroActual);
                        }
                    }
                    resultado += numeroActualDouble;
                    System.out.println("= " + resultado);
                    array[array.length - 1][2] += resultado;
                    break;
            }
            resultado=0;
            numeroActual="";
            preparado = false;
        }
    }
}