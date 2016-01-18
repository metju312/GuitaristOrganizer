package pl.edu.wat.wcy.pz.view.tables;

import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class CoversTableModel extends AbstractTableModel{

    private String[] columnNames =
            {
                    "Song",
                    "Title",
                    "Path",
                    "URL"
            };

    private List<Cover> covers;

    public CoversTableModel()
    {
        covers = new ArrayList<Cover>();
    }

    public CoversTableModel(List<Cover> covers)
    {
        this.covers = covers;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return covers.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }


    @Override
    public Object getValueAt(int row, int column) {
        Cover cover = getCover(row);

        switch (column)
        {
            case 0: return cover.getSong().getTitle();
            case 1: return cover.getTitle();
            case 2: return cover.getPath();
            case 3: return cover.getUrl();
            default: return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        fireTableStructureChanged();
    }

    public Cover getCover(int row){
        return covers.get(row);
    }
}
