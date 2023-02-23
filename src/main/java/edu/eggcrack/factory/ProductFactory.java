package edu.eggcrack.factory;

import edu.eggcrack.factory.worker.BlueWorker;
import edu.eggcrack.factory.worker.OrangeWorker;
import edu.eggcrack.factory.worker.RedWorker;
import edu.eggcrack.storage.FiniteMailbox;
import edu.eggcrack.storage.InfiniteMailbox;
import edu.eggcrack.storage.Mailbox;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory implements Runnable {
  private final List<Runnable> workers = new ArrayList<>();

  public ProductFactory(
      int bufferSize, int stageGroupSize, int productCount, boolean printIntermediate) {
    final var initialStage = 0;
    final var stages = 3;
    final var sequencer = new IdSequencer();
    Mailbox<Product> lastMailbox = new InfiniteMailbox<>();
    Mailbox<Product> prevMailbox = null;
    for (int i = 0; i < stages; i++) {
      Mailbox<Product> nextMailbox;
      if (i == stages - 1) {
        nextMailbox = lastMailbox;
      } else {
        nextMailbox = new FiniteMailbox<>(bufferSize);
      }
      for (int j = 0; j < stageGroupSize - 1; j++) {
        if (i == initialStage) {
          workers.add(
              new BlueWorker(
                  String.format("📘L%dN%d", i, j),
                  productCount,
                  printIntermediate,
                  sequencer,
                  nextMailbox));
        } else {
          workers.add(
              new BlueWorker(
                  String.format("📘L%dN%d", i, j),
                  productCount,
                  printIntermediate,
                  prevMailbox,
                  nextMailbox));
        }
      }
      if (i == initialStage) {
        workers.add(
            new OrangeWorker(
                String.format("📙L%d", i),
                productCount,
                printIntermediate,
                sequencer,
                nextMailbox));
      } else {
        workers.add(
            new OrangeWorker(
                String.format("📙L%d", i),
                productCount,
                printIntermediate,
                prevMailbox,
                nextMailbox));
      }
      prevMailbox = nextMailbox;
    }
    workers.add(new RedWorker("📕", productCount * stageGroupSize, lastMailbox));
  }

  @Override
  public void run() {
    var threads = new ArrayList<Thread>(workers.size());

    for (final var w : workers) {
      final var tmp = new Thread(w);
      threads.add(tmp);
      tmp.start();
    }

    for (final var t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
