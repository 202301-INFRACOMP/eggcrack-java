package edu.eggcrack.storage;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class InfiniteMailbox<T> implements Mailbox<T> {
  private final Deque<T> queue = new ArrayDeque<>();

  public InfiniteMailbox() {}

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public Optional<T> peek() {
    if (isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(queue.peek());
    }
  }

  @Override
  public void send(T m) {
    queue.offer(m);
  }

  @Override
  public T get() {
    return queue.poll();
  }
}
