import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Double> attributes;
    private String name;


    public Node(List<Double> atrybuty, String name) {
        this.attributes = atrybuty;
        this.name = name;
    }

    public void normalize(){
        double sum=0;

        for (double d : this.attributes) {
            sum += Math.pow(d, 2);
        }

        double length = sum;
        length = Math.sqrt(length);

        List<Double>  newAttributes = new ArrayList<>();

        for (int i = 0; i < this.attributes.size() ; i++) {
            newAttributes.add(i, (this.attributes.get(i) / length));
        }
        this.attributes = newAttributes;
    }

    public List<Double> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

}