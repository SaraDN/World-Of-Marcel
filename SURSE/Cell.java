public class Cell {
    int ox;
    int oy;

    public enum cellType {
        EMPTY, ENEMY, SHOP, FINISH;
    }

    char cellElement;
    boolean vizited = false;
    String cellTypeS;

    public void setCellTYpe(cellType value) {
        cellTypeS = value.toString();
    }

    public String getCellTYpe() {
        return cellTypeS;
    }


}
