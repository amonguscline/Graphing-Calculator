import java.util.*;

class Lexer {

  private String expression;
  private ArrayList<Token> tokens = new ArrayList<>();
  private final String operators[] = { "+", "-", "*", "/", "^", "$" };

  public Lexer(String expr) {
    expression = expr;
  }

  public ArrayList<Token> tokenize() {
    while (expression.length() > 0) {
      Number n = new Number(findInt(0));
      boolean found = false;
      if (n.value() != 0) {
        // assume that people arent stupid
        tokens.add(n);
        found = true;
      }
      if (!found) {
        found = found || findVar();
      }
      if (!found) {
        found = found || findOp();
      }
      if (!found) {
        found = found || findParens();
      }
      if (!found) {
        backPop();
      } // not parseable, we don't want this
    }
    //change mult precedence
    integrateMults();
    return tokens;
  }

  public int findInt(int n) {
    if (expression.length() == 0) {
      return n;
    }
    if (isInt(expression.substring(0, 1))) {
      String fchar = backPop();
      n = findInt(n * 10 + Integer.parseInt(fchar));
    }
    return n;
  }

  public boolean findVar() {
    if (expression.substring(0, 1).equals("x")) {
      String fchar = backPop();
      tokens.add(new Variable(fchar));
      return true;
    }
    return false;
  }

  public boolean findOp() {
    if (opContains(expression.substring(0, 1))) {
      String fchar = backPop();
      tokens.add(new Operator(fchar));
      return true;
    }
    return false;
  }

  public boolean findParens() {
    if (expression.substring(0, 1).equals("(") ||
        expression.substring(0, 1).equals(")")) {
      String fchar = backPop();
      tokens.add(new Parentheses(fchar));
      return true;
    }
    return false;
  }
  public boolean isInt(String s) {
    try {
      Integer.parseInt(s);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public String backPop() {
    String fchar = "";
    if (expression.length() >= 1) {
      fchar = expression.substring(0, 1);
      if (expression.length() > 1) {
        expression = expression.substring(1);
      } else {
        expression = "";
      }
    }
    return fchar;
  }

  public boolean opContains(String op) {
    for (var i = 0; i < operators.length; i++) {
      if (operators[i].equals(op)) {
        return true;
      }
    }
    return false;
  }

  public void integrateMults() {
    for (int i = 0; i < tokens.size() - 1; i++) {
      Label tl = tokens.get(i).getLabel();
      Label nexttl = tokens.get(i + 1).getLabel();
      if ((tl == Label.VAR && nexttl == Label.NUM) || (tl == Label.NUM && nexttl == Label.VAR)
          || (tl == Label.RPAREN && nexttl == Label.LPAREN)) {
        tokens.add(i + 1, new Operator("*"));
        i++;

      }
    }
  }
}