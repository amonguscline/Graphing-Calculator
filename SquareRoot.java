public record SquareRoot(Expression child) implements Expression {
    public double evaluateAt(double d) {
        return Math.sqrt(child.evaluateAt(d));
    }
}