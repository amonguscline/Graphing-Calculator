public record Addition(Expression left, Expression right) implements Expression {
    public double evaluateAt(double d) {
        return left.evaluateAt(d) + right.evaluateAt(d);
    }
}
