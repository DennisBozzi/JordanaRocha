package jordanarocha.Controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.converter.IntegerStringConverter;
import jordanarocha.Models.JoalheriaDAO;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/* -------------------------------------- BIBLIOTECA CONTROLSFX ---------------------------------------*/
public class Formatacao {

    //Instanciando clientesDAO
    JoalheriaDAO clientesDAO = new JoalheriaDAO();

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Autocomplete Nome TextField
    public void autocompleteCliente(JFXTextField textFieldNome, JFXTextField textFieldCPF) {

        List<String> suggestions = clientesDAO.getNomes(); //Lista que é populada para Clientes

        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(textFieldNome, suggestions); //Método da biblioteca ControlsFX

        autoCompletionBinding.setMinWidth(378); //O tamanho do pane que abre para o autocomplete

        // Adicione um ChangeListener ao textFieldNome
        textFieldNome.textProperty().addListener((observable, oldValue, newValue) -> {
            // Quando o valor do textFieldNome muda, consulte o CPF associado ao novo nome e preencha o textFieldCPF
            String cpf = clientesDAO.getCPFByNome(newValue);
            textFieldCPF.setText(cpf);
        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Autocomplete CPF TextField 
    public void autocompleteCPF(JFXTextField textFieldCPF, JFXTextField textFieldNome) {

        ObservableList<String> cpfs = clientesDAO.getCPFs(); // Lista que é populada para CPF

        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(textFieldCPF, cpfs); //Método da biblioteca ControlsFX

        autoCompletionBinding.setMinWidth(141); //O tamanho do pane que abre para o autocomplete

        // Adicione um ChangeListener ao textFieldCPF
        textFieldCPF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Quando o valor do textFieldCPF muda, consulte o nome associado ao novo CPF e preencha o textFieldNome
            String nome = clientesDAO.getNomeByCPF(newValue);
            textFieldNome.setText(nome);
        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Formata o CPF enquanto digita - TextField
    public void formataCPFEnquantoDigita(JFXTextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String digits = newValue.replaceAll("[^0-9]", "");

            StringBuilder formatted = new StringBuilder();
            if (digits.length() > 0) {
                formatted.append(digits.substring(0, Math.min(3, digits.length()))).append(".");
            }
            if (digits.length() > 3) {
                formatted.append(digits.substring(3, Math.min(6, digits.length()))).append(".");
            }
            if (digits.length() > 6) {
                formatted.append(digits.substring(6, Math.min(9, digits.length()))).append("-");
            }
            if (digits.length() > 9) {
                formatted.append(digits.substring(9, Math.min(11, digits.length())));
            }

            textField.setText(formatted.toString());
        });

        // Adiciona um listener para escutar o evento de tecla pressionada
        textField.setOnKeyPressed(event -> {
            // Verifica se a tecla pressionada foi o BACKSPACE
            if (event.getCode() == KeyCode.BACK_SPACE) {
                // Limpa o conteúdo do JFXTextField
                textField.clear();
            }
        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Formata o celular enquanto digita - TextField
    public void formataCelularEnquantoDigita(JFXTextField textField) {

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String digits = newValue.replaceAll("[^0-9]", "");

            StringBuilder formatted = new StringBuilder();
            if (digits.length() > 0) {
                formatted.append("(").append(digits.charAt(0));
            }
            if (digits.length() > 1) {
                formatted.append(digits.charAt(1)).append(") ");
            }
            if (digits.length() > 2) {
                formatted.append(digits.substring(2, Math.min(3, digits.length()))).append("-");
            }
            if (digits.length() > 3) {
                formatted.append(digits.substring(3, Math.min(7, digits.length()))).append("-");
            }
            if (digits.length() > 7) {
                formatted.append(digits.substring(7, Math.min(11, digits.length())));
            }

            textField.setText(formatted.toString());
        });

        // Adiciona um listener para escutar o evento de tecla pressionada
        textField.setOnKeyPressed(event -> {

            // Verifica se a tecla pressionada foi o BACKSPACE - Pois o cliente não consegue apagar as barras e parenteses que adicionei
            if (event.getCode() == KeyCode.BACK_SPACE) {
                // Limpa o conteúdo do JFXTextField
                textField.clear();
            }

        });
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Formata o RG enquanto digita - TextField
    public void formataRGEnquantoDigita(JFXTextField textField) {

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String digits = newValue.replaceAll("[^0-9]", "");

            StringBuilder formatted = new StringBuilder();
            if (digits.length() > 0) {
                formatted.append(digits.substring(0, Math.min(2, digits.length()))).append(".");
            }
            if (digits.length() > 2) {
                formatted.append(digits.substring(2, Math.min(5, digits.length()))).append(".");
            }
            if (digits.length() > 5) {
                formatted.append(digits.substring(5, Math.min(8, digits.length()))).append("-");
            }
            if (digits.length() > 8) {
                formatted.append(digits.substring(8, Math.min(9, digits.length())));
            }

            textField.setText(formatted.toString());
        });

        // Adiciona um listener para escutar o evento de tecla pressionada
        textField.setOnKeyPressed(event -> {

            // Verifica se a tecla pressionada foi o BACKSPACE - Pois o cliente não consegue apagar os pontos e traços que adicionei
            if (event.getCode() == KeyCode.BACK_SPACE) {
                // Limpa o conteúdo do JFXTextField
                textField.clear();
            }

        });
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Faz que ele apenas ative retorne uma String quando o length do texto for maior que 1
    public String toString(String value) {

        if (value == null || value.length() < 1) {
            return "";
        }
        if (value.length() < 11) {
            value = String.format("%011d", Long.valueOf(value));
        }
        return String.format("%s.%s.%s-%s", value.substring(0, 3), value.substring(3, 6), value.substring(6, 9), value.substring(9, 11));
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Faz com que o textField aceite apenas números, com no máximo 20 algarísmos
    public static void soNumerosTextField(JFXTextField textField) {
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), null,
                change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("-?\\d{0,20}")) { // Permite até 20 dígitos numéricos
                        return change;
                    }
                    return null;
                });
        textField.setTextFormatter(textFormatter);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Converte byte to image
    public Image convertByteToImage(byte[] byteData) {
        if (byteData == null) {
            return null;
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(byteData);
        Image image = new Image(bis);
        return image;
    }
}
