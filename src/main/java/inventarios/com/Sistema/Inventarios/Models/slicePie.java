package inventarios.com.Sistema.Inventarios.Models;

public class slicePie {

    private String name;
    private double y;
    private boolean slice;
    private boolean selected;

    public slicePie(String name, double y, boolean slice, boolean selected){
        this.name = name;
        this.y = y;
        this.slice = slice;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isSlice() {
        return slice;
    }

    public void setSlice(boolean slice) {
        this.slice = slice;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
