package ajax;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class PaginateResponse {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private ArrayList<ArrayList<String>> data;
    public PaginateResponse(int draw, int recordsTotal, int recordsFiltered, ArrayList<ArrayList<String>> data)
    {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }
    public int getDraw()
    {
        return draw;
    }

    public void setDraw(int draw)
    {
        this.draw = draw;
    }

    public int getRecordsTotal()
    {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal)
    {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered()
    {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered)
    {
        this.recordsFiltered = recordsFiltered;
    }

    public ArrayList<ArrayList<String>> getData()
    {
        return data;
    }

    public void setData(ArrayList<ArrayList<String>> data)
    {
        this.data = data;
    }
    
}
