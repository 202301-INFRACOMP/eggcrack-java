package edu.eggcrack.storage;

import java.util.Optional;

public interface Mailbox<T> {
  boolean isFull();

  boolean isEmpty();

  Optional<T> peek();

  void send(T m);

  T get();
}
