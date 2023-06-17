package jordanarocha.Controllers;

import java.io.ByteArrayInputStream;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jordanarocha.Tabelas.Produto;

public class ImageTableCell extends TableCell<Produto, byte[]> {

    private ImageView imageView = new ImageView();

    public ImageTableCell() {
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
    }

    @Override
    protected void updateItem(byte[] item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            Image image = new Image(new ByteArrayInputStream(item));
            imageView.setImage(image);
            setGraphic(imageView);
        }
    }
}
