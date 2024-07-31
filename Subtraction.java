public class Subtraction implements Expression {
    Expression left;
    Expression right = null;

    public Subtraction(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Subtraction(Expression child) {
        this.left = child;
    }

    public double evaluateAt(double d) {
        if (this.right == null) {
            return -left.evaluateAt(d);
        }
        return left.evaluateAt(d) - right.evaluateAt(d);
    }
}
