package jordanarocha.Controllers;

import jordanarocha.Models.JoalheriaDAO;

public class Validador {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    //Método para conferir se o CPF é válido
    public static boolean isCPF(String cpf) {
        if (cpf == null) {
            return false;
        }

        // Remove pontos e traços
        cpf = cpf.replaceAll("[.-]", "");

        if (cpf.length() != 11) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }    
    
    // Método para conferir se o CPF já está cadastrado no banco de dados
    public static boolean isCpfCadastrado(String cpf) {
        JoalheriaDAO dao = new JoalheriaDAO();
        return dao.cpfExists(cpf);
    }
}
