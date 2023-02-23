package edu.eggcrack.factory.worker;

import edu.eggcrack.factory.Product;
import edu.eggcrack.storage.Mailbox;
import java.util.PriorityQueue;

public class RedWorker implements Runnable {
  private final String id;
  private final int productCount;
  private final Mailbox<Product> receiveTo;
  private int currentId = 0;
  private final PriorityQueue<Product> pq = new PriorityQueue<>();

  public RedWorker(String id, int productCount, Mailbox<Product> receiveTo) {
    this.id = id;
    this.productCount = productCount;
    this.receiveTo = receiveTo;
  }

  @Override
  public void run() {
    System.out.printf("%s has started.%n", id);
    for (int i = 0; i < productCount; i++) {
      final var lastProduct = getProduct();
      pq.add(lastProduct);
      final var p = pq.peek();
      if (p.id == currentId) {
        System.out.printf("%s has printed:%n", id);
        System.out.print(p);
        pq.poll();
        currentId++;
      }
    }

    for (int i = currentId; i < productCount; i++) {
      final var p = pq.poll();
      System.out.printf("%s has printed:%n", id);
      System.out.print(p);
    }
    System.out.printf("%s has finished.%n", id);
  }

  Product getProduct() {
    while (true) {
      synchronized (receiveTo) {
        if (!receiveTo.isEmpty()) {
          var p = receiveTo.get();
          receiveTo.notifyAll();
          return p;
        }
      }
    }
  }
}
