import java.util.*;

public class Parentheses implements Token {
  private final Label label;
  private final static Map<String, Label> labeller = new HashMap<>(){
    {
      put("(",Label.LPAREN);
      put(")",Label.RPAREN);
    }
  };

  public Parentheses(String value) {
    this.label=labeller.get(value);
  }

  public Label getLabel() {
    return label;
  }
}