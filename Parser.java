import java.util.*;

public class Parser {

  ArrayList<Token> tokens = new ArrayList<>();
  Node root;

  public Parser(String expression) {
    Lexer lexer = new Lexer(expression);
    tokens = lexer.tokenize();
    parseTokens(tokens);
  }

  public double evaluateAt(double x) {
    return root.getValue().evaluateAt(x);
  }

  public void parseTokens(ArrayList<Token> tokens) {
    root = new Node(tokens);
  }
}