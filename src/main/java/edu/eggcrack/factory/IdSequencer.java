package edu.eggcrack.factory;

public class IdSequencer {
  private int id = 0;

  public IdSequencer() {}

  public synchronized int get() {
    return id++;
  }
}
