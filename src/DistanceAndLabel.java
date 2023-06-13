public class DistanceAndLabel implements Comparable<DistanceAndLabel> {
    public double distance;
    public String label;

    public DistanceAndLabel(double distance, String label) {
        this.distance = distance;
        this.label = label;
    }

    @Override
    public int compareTo(DistanceAndLabel data) {
        return Double.compare(distance, data.distance);
    }
}