package edu.eggcrack.factory;

import java.util.Scanner;

public class ProductFactoryBuilder {
  public ProductFactoryBuilder() {}

  public ProductFactory build() {
    final var sc = new Scanner(System.in);
    System.out.print("Enter the product count: ");
    final var productCount = sc.nextInt();

    System.out.print("Enter the number of threads per stage: ");
    final var threadCount = sc.nextInt();

    System.out.print("Enter the size of the buffer: ");
    final var bufferSize = sc.nextInt();

    System.out.print("Do you wanna print the intermediate messages (y/n): ");
    final var opPrint = sc.next();
    final var printIntermediate = opPrint.startsWith("y");

    return new ProductFactory(bufferSize, threadCount, productCount, printIntermediate);
  }
}
