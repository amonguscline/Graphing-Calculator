import java.util.*;
public enum Label {
  ADD,
  SUB,
  MULT,
  DIV,
  EXPO,
  SQRT,
  LPAREN,
  RPAREN,
  NUM,
  VAR;

  private final static Map<Label,Integer> precedencifier = new HashMap<>(){
    {
      put(Label.ADD,1);
      put(Label.SUB,1);
      put(Label.MULT,2);
      put(Label.DIV,2);
      put(Label.EXPO,3);
      put(Label.SQRT,3);
      put(Label.LPAREN,4);
      put(Label.RPAREN,4);
      put(Label.VAR,5);
      put(Label.NUM,5);
    }
  };

  public int precedence() {
    return precedencifier.getOrDefault(this, 0);
  }
}