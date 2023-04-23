import java.util.Arrays;

public class FuzzyNumber {
    public Double[] arr;

    public FuzzyNumber(Double[] arr) {
        this.arr = arr;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}