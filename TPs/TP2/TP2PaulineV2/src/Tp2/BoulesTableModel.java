package Tp2;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BoulesTableModel extends AbstractTableModel {
    private final String[] colonnes = {"x", "y"};
    private List<Boule> boules;

    public BoulesTableModel(List<Boule> boules) {
        this.boules = boules;
    }

    @Override
    public int getRowCount() {
        return boules.size();
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Boule boule = boules.get(rowIndex);
        return columnIndex == 0 ? boule.getX() : boule.getY();
    }

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }
    
    public void setBoules(List<Boule> boules) {
        this.boules = boules;
        fireTableDataChanged();
    }

}
