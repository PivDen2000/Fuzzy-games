public class FuzzyInteger {
    public Integer up;
    public Integer down;
    public Integer left;
    public Integer right;

    public FuzzyInteger() {
        this.up = 0;
        this.down = 0;
        this.left = 0;
        this.right = 0;
    }

    public FuzzyInteger(Integer up, Integer down, Integer left, Integer right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public FuzzyInteger(Integer up) {
        this(up, up, up, up);
    }

    public FuzzyInteger(Integer up, Integer left) {
        this(up, up, left, left);
    }

    public FuzzyInteger(Integer up, Integer down, Integer left) {
        this(up, down, left, left);
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public Integer getDown() {
        return down;
    }

    public void setDown(Integer down) {
        this.down = down;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public FuzzyInteger add(FuzzyInteger other) {
        FuzzyInteger result = new FuzzyInteger();
        result.setUp(this.up + other.getUp());
        result.setDown(this.down + other.getDown());
        result.setLeft(this.left + other.getLeft());
        result.setRight(this.right + other.getRight());
        return result;
    }

    public FuzzyInteger subtract(FuzzyInteger other) {
        FuzzyInteger result = new FuzzyInteger();
        result.setUp(this.up - other.getDown());
        result.setDown(this.down - other.getUp());
        result.setLeft(this.left - other.getRight());
        result.setRight(this.right - other.getLeft());
        return result;
    }

    public FuzzyInteger multiply(Integer num) {
        FuzzyInteger result = new FuzzyInteger();
        result.setUp(this.up * num);
        result.setDown(this.down * num);
        result.setLeft(this.left * num);
        result.setRight(this.right * num);
        return result;
    }

    @Override
    public String toString() {
        return "[" + up + ", " + down + ", " + left + ", " + right + "]";
    }

}
