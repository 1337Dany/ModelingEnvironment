package data;

import javax.swing.*;

public class Model extends JLabel {
    /**
     * Models is classes through which the data is passed to the view.
     */

    public Model(String modelName) {
        this.setText(modelName);
    }
}
