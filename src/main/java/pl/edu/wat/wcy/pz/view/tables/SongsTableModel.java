package pl.edu.wat.wcy.pz.view.tables;

import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class SongsTableModel extends AbstractTableModel{

    private String[] columnNames =
            {
                    "Artist",
                    "Title",
                    "Path"
                    //"URL"
            };

    private List<Song> songs;

    public SongsTableModel()
    {
        songs = new ArrayList<Song>();
    }

    public SongsTableModel(List<Song> songs)
    {
        this.songs = songs;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return songs.size();
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
        Song song = getSong(row);

        switch (column)
        {
            case 0: return song.getArtist().getName();
            case 1: return song.getTitle();
            case 2: return song.getPath();
            default: return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        fireTableStructureChanged();
    }

    public Song getSong(int row){
        return songs.get(row);
    }
}
