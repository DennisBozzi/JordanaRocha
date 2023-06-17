package jordanarocha.Controllers;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.text.NumberFormat;
import java.util.Locale;
import jordanarocha.Tabelas.Produto;

public class FormattedCurrencyCellFactory implements Callback<TableColumn<Produto, Double>, TableCell<Produto, Double>> {

    @Override
    public TableCell<Produto, Double> call(TableColumn<Produto, Double> column) {
        return new TableCell<Produto, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    setText(format.format(item));
                }
            }
        };
    }
}
