public record Range(double min, double max) {
  public double getRange() {
    return max - min;
  }
}
