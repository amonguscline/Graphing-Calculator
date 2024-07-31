public record Variable(String value) implements Token {

  public Label getLabel() {
    return Label.VAR;
  }

  public String toString() {
    return value;
  }
}