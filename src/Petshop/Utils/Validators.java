package Petshop.Utils;

public class Validators {

    // Function to validate CPF format (only checks if it has 11 numeric digits)
    public static boolean validarCPF(String cpf) {
        // Checks if the string has exactly 11 characters and if all are numeric
        return cpf != null && cpf.length() == 11 && cpf.matches("\\d+");
    }

    // Function to validate email format
    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email != null && email.matches(regex);
    }

    // Function to check if a string is a number
    public static boolean isNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Function to check if the string contains only letters
    public static boolean isString(String str) {
        return str != null && str.matches("[a-zA-Z]+");
    }
}
