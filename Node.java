import java.util.*;

class Node {

  private Node right = null;
  private Node left = null;
  private Token token;
  private Expression value;
  private ArrayList<Token> lTokens = null;
  private ArrayList<Token> rTokens = null;

  public Node(
      Token token,
      ArrayList<Token> lTokens,
      ArrayList<Token> rTokens) {
    this.token = token;
    this.lTokens = lTokens;
    this.rTokens = rTokens;
    if (lTokens.size() > 0) {
      this.left = lNodify();
    }
    if (rTokens.size() > 0) {
      this.right = rNodify();
    }
    giveValue();
  }

  public Node(ArrayList<Token> tokens) {
    this.token = findToken(tokens);
    int index = tokens.indexOf(token);
    this.lTokens = subArrayList(tokens, 0, index);
    this.rTokens = subArrayList(tokens, index + 1, tokens.size());
    if (lTokens.size() > 0) {
      this.left = lNodify();
    }
    if (rTokens.size() > 0) {
      this.right = rNodify();
    }
    giveValue();
  }

  public Node(int n) {
    this.token = new Number(n);
  }

  public Node lNodify() {
    Token newNodeToken = findToken(lTokens);
    int index = lTokens.indexOf(newNodeToken);
    ArrayList<Token> newNodeLTokens = subArrayList(lTokens, 0, index);
    ArrayList<Token> newNodeRTokens = subArrayList(
        lTokens,
        index + 1,
        lTokens.size());
    return new Node(newNodeToken, newNodeLTokens, newNodeRTokens);
  }

  public Node rNodify() {
    Token newNodeToken = findToken(rTokens);
    int index = rTokens.indexOf(newNodeToken);
    ArrayList<Token> newNodeLTokens = subArrayList(rTokens, 0, index);
    ArrayList<Token> newNodeRTokens = subArrayList(
        rTokens,
        index + 1,
        rTokens.size());
    return new Node(newNodeToken, newNodeLTokens, newNodeRTokens);
  }

  public boolean parensSurround(ArrayList<Token> tokens) {
    return tokens.get(0).getLabel() == Label.LPAREN && tokens.get(tokens.size() - 1).getLabel() == Label.RPAREN;
  }

  public void popParens(ArrayList<Token> tokens) {
    tokens.remove(0);
    tokens.remove(tokens.size() - 1);
  }

  public Token findToken(ArrayList<Token> tokens) {

    if (tokens.size() == 1) {
      return tokens.get(0);
    } else {
      int numOfParens = 0;
      Token best = null;
      for (var i = 0; i < tokens.size(); i++) {
        if (tokens.get(i).getLabel() == Label.LPAREN) {
          numOfParens++;
        }
        if (tokens.get(i).getLabel() == Label.RPAREN) {
          numOfParens--;
        }
        if (numOfParens == 0 && i != tokens.size() - 1) {
          if (best == null) {
            best = tokens.get(i);
          }
          int bestPrec = best.getLabel().precedence();
          if (tokens.get(i).getLabel().precedence() < bestPrec) {
            
            best = tokens.get(i);
          }
        }
      }

      if (best == null) {
        if (parensSurround(tokens)) {
          popParens(tokens);
        }
        //something is wrong with parens?
        return findToken(tokens);
      } else {
        return best;
      }
    }
  }

  public Label getLabel() {
    return token.getLabel();
  }

  public ArrayList<Token> subArrayList(
      ArrayList<Token> tokens,
      int start,
      int end) {
    ArrayList<Token> newTokens = new ArrayList<>();
    for (int i = start; i < end; i++) {
      newTokens.add(tokens.get(i));
    }
    return newTokens;
  }

  public boolean hasRight() {
    return right != null;
  }

  public boolean hasLeft() {
    return left != null;
  }

  public Node getRight() {
    return right;
  }

  public Node getLeft() {
    return left;
  }

  public Expression getValue() {
    return this.value;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public Token getToken() {
    return token;
  }

  public boolean hasChild() {
    return left != null || right != null;
  }
  //clean this using a map
  public void giveValue() {
    switch (token.getLabel()) {

      case ADD:
        this.value = new Addition(left.getValue(), right.getValue());
        break;
      case SUB:
        if (left == null) {
          this.value = new Subtraction(right.getValue());
        } else {
          this.value = new Subtraction(left.getValue(), right.getValue());
        }
        break;
      case MULT:
        this.value = new Multiply(left.getValue(), right.getValue());
        break;
      case DIV:
        this.value = new Division(left.getValue(), right.getValue());
        break;
      case EXPO:
        this.value = new Exponent(left.getValue(), right.getValue());
        break;
      case SQRT:
        this.value = new SquareRoot(right.getValue());
        break;
      case VAR:
        this.value = new VarExpress();
        break;
      case NUM:
        this.value = new NumExpress((double) ((Number) token).value());
        break;
      case LPAREN:
        break;
      case RPAREN:
        break;

    }
  }
}