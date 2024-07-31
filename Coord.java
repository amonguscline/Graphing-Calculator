class Coord {
  public double x;
  public double y;

  public Coord(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void scaleX(double factor) {
    x *= factor;
  }

  public void scaleY(double factor) {
    y *= factor;
  }

  public void putInQ4(Range r, Range d, Range D, Range R) {
    y = -y - r.min()*R.getRange()/r.getRange();
    x=x-d.min()*D.getRange()/d.getRange();
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
  public String toString(){
    return ""+x+", " +y;
  }
}