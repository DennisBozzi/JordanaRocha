package jordanarocha.Controllers;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import jordanarocha.Tabelas.Atributo;

public class CRUDAtributo {

    public Atributo getAtributoFromInput(JFXTextField nomeAtributoField, JFXRadioButton btnAcessorio, JFXRadioButton btnLiga, JFXRadioButton btnPedra, JFXRadioButton btnTamanho) {
        String nome = nomeAtributoField.getText();
        String valor = "";

        if (btnAcessorio.isSelected()) {
            valor = "Acessorio";
        } else if (btnLiga.isSelected()) {
            valor = "Liga";
        } else if (btnPedra.isSelected()) {
            valor = "Pedra";
        } else if (btnTamanho.isSelected()) {
            valor = "Tamanho";
        }

        return new Atributo(nome, valor);
    }
    
}
