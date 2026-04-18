package edu.eggcrack;

import edu.eggcrack.factory.ProductFactoryBuilder;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.print(
        """
========================================================================
                                       _
                                      | |
   ___  __ _  __ _  ___ _ __ __ _  ___| | __
  / _ \\/ _` |/ _` |/ __| '__/ _` |/ __| |/ /
 |  __/ (_| | (_| | (__| | | (_| | (__|   <
  \\___|\\__, |\\__, |\\___|_|  \\__,_|\\___|_|\\_\\
        __/ | __/ |
       |___/ |___/
========================================================================
""");

    System.out.println("Welcome to case study No.1 of INFRACOMP202301");
    System.out.println("Code name: Project eggcrack");
    System.out.println("Implemented on Java\n");

    System.out.println("For more information see: https://github.com/202301-INFRACOMP/eggcrack\n");

    final var pfb = new ProductFactoryBuilder();
    final var pf = pfb.build();
    pf.run();

    System.out.println("\nWork finished!!!");
    System.out.print("Press enter to finish...");
    final var sc = new Scanner(System.in);
    sc.nextLine();
  }
}
