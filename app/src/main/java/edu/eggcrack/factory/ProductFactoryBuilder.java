package edu.eggcrack.factory;

import edu.eggcrack.App;

public class ProductFactoryBuilder {
  public ProductFactoryBuilder() {}

  public ProductFactory build() {
    final var sc = App.sc;
    System.out.print("Enter the product count: ");
    final var productCount = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter the number of threads per stage: ");
    final var threadCount = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter the size of the buffer: ");
    final var bufferSize = sc.nextInt();
    sc.nextLine();

    System.out.print("Do you wanna print the intermediate messages (y/n): ");
    final var opPrint = sc.next();
    sc.nextLine();
    final var printIntermediate = opPrint.startsWith("y");

    return new ProductFactory(bufferSize, threadCount, productCount, printIntermediate);
  }
}
