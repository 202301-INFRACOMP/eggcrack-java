package edu.eggcrack.factory;

public class Product implements Comparable<Product> {
  public int id;

  public String message;

  public Product(int id, String message) {
    this.id = id;
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format(
        """
            ╔
            ║Product
            ║->id: %d
            ║->message: %s
            ╚
            """,
        id, message);
  }

  @Override
  public int compareTo(Product o) {
    return id - o.id;
  }
}
