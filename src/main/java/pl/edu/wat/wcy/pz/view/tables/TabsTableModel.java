package pl.edu.wat.wcy.pz.view.tables;

import pl.edu.wat.wcy.pz.model.entities.music.Tab;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class TabsTableModel extends AbstractTableModel{

    private String[] columnNames =
            {
                    "Cover",
                    "Title",
                    "Path",
                    "URL"
            };

    private List<Tab> tabs;

    public TabsTableModel()
    {
        tabs = new ArrayList<Tab>();
    }

    public TabsTableModel(List<Tab> tabs)
    {
        this.tabs = tabs;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return tabs.size();
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
        Tab tab = getTab(row);

        switch (column)
        {
            case 0: return tab.getCover().getTitle();
            case 1: return tab.getTitle();
            case 2: return tab.getPath();
            case 3: return tab.getUrl();
            default: return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        fireTableStructureChanged();
    }

    public Tab getTab(int row){
        return tabs.get(row);
    }
}
