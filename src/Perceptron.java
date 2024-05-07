import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Perceptron {
    String languageName;
    private List<Double> vector;
    private double threshold;
    private final double a;

    public Perceptron(int vectorSize, double a, String languageName) {
        this.a = a;
        this.languageName = languageName;
        this.vector = new ArrayList<>(Collections.nCopies(vectorSize, 0.1));
        this.threshold = 1.0;
    }

    public void learn(Node node, int y) {
        double scalar = 0;

        if (node.getAttributes().size() != this.vector.size()) {
            throw new IllegalArgumentException("Wrong size of attributes: " + node.getAttributes().size() + ", expected: " + this.vector.size());
        }

        for (int i = 0; i < node.getAttributes().size(); i++) {
            scalar += node.getAttributes().get(i) * this.vector.get(i);
        }

        int _y = (scalar >= this.threshold ? 1 : 0);

        if (_y != y) {
            for (int i = 0; i < node.getAttributes().size(); i++) {
                this.vector.set(i, this.vector.get(i) + ((y - _y) * a * node.getAttributes().get(i)));
            }
            this.threshold -= (y - _y) * a;
        }
    }



    public double value(Node node){
        double scalar = 0;
        for (int i = 0; i < node.getAttributes().size() ; i++)
            scalar += node.getAttributes().get(i) * this.vector.get(i);

        return scalar - threshold;
    }

    public void normalize(){
        double sum=0;

        for (double d : this.vector)
            sum+=Math.pow(d,2);

        double length = Math.sqrt(sum);

        List<Double> newVector = new ArrayList<>();

        for (int i = 0; i < this.vector.size() ; i++)
            newVector.add(i,(this.vector.get(i)/length));

        this.threshold = threshold/length;
        this.vector = newVector;
    }

    public String getLanguageName() {
        return languageName;
    }
}