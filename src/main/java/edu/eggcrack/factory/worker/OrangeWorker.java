package edu.eggcrack.factory.worker;

import edu.eggcrack.factory.IdSequencer;
import edu.eggcrack.factory.Product;
import edu.eggcrack.storage.Mailbox;

public class OrangeWorker implements Runnable {
  private final String id;

  private final int productCount;

  private final boolean printIntermediate;

  private final IdSequencer sequencer;

  private final Mailbox<Product> receiveTo;

  private final Mailbox<Product> sendTo;

  public OrangeWorker(
      String id,
      int productCount,
      boolean printIntermediate,
      IdSequencer sequencer,
      Mailbox<Product> sendTo) {
    this.id = id;
    this.productCount = productCount;
    this.printIntermediate = printIntermediate;
    this.sequencer = sequencer;
    this.receiveTo = null;
    this.sendTo = sendTo;
  }

  public OrangeWorker(
      String id,
      int productCount,
      boolean printIntermediate,
      Mailbox<Product> receiveTo,
      Mailbox<Product> sendTo) {
    this.id = id;
    this.productCount = productCount;
    this.printIntermediate = printIntermediate;
    this.sequencer = null;
    this.receiveTo = receiveTo;
    this.sendTo = sendTo;
  }

  @Override
  public void run() {
    if (printIntermediate) {
      System.out.println(String.format("%s has started.", id));
    }
    for (int i = 0; i < productCount; i++) {
      Product p;
      if (sequencer != null) {
        p = generateProduct();
        if (printIntermediate) {
          System.out.println(String.format("%s has generated:", id));
          System.out.print(p);
        }
      } else {
        p = get();
        p.message += id;
        if (printIntermediate) {
          System.out.println(String.format("%s has modified:", id));
          System.out.print(p);
        }
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      send(p);
    }
    if (printIntermediate) {
      System.out.println(String.format("%s has finished.", id));
    }
  }

  private Product generateProduct() {
    final var id = sequencer.get();
    return new Product(id, String.format("📙P%d", id));
  }

  private Product get() {
    while (true) {
      synchronized (receiveTo) {
        if (!receiveTo.isEmpty() && receiveTo.peek().get().message.startsWith("📙")) {
          var p = receiveTo.get();
          receiveTo.notify();
          return p;
        }
      }
      Thread.yield();
    }
  }

  private void send(Product p) {
    while (true) {
      synchronized (sendTo) {
        if (!sendTo.isFull()) {
          sendTo.send(p);
          sendTo.notifyAll();
          break;
        }
      }
      Thread.yield();
    }
  }
}
