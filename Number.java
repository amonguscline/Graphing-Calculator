public record Number(int value) implements Token {
  public Label getLabel() {
    return Label.NUM;
  }
  public String toString(){
    return ""+value;
  }
}
