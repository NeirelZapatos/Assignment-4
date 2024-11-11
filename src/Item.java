public class Item {
    public int value;
    public int weight;
    public double valuePerWeight;

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.valuePerWeight = (double) value / weight;
    }
}
