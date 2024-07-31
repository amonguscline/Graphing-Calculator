public record Exponent(Expression left, Expression right) implements Expression {
    public double evaluateAt(double d) {
        return Math.pow(left.evaluateAt(d), right.evaluateAt(d));
    }
}
