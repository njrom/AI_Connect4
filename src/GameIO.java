public class GameIO {

    public static void printBoard(Board b) {
        System.out.println();
        for(int i = 0; i < b.getWidth(); i++) {
            System.out.printf(" %s", i);
        }
        System.out.println(" ");

        for(int i = 0; i <b.getHeight(); i++) {
            System.out.printf("|");
            for(int j = 0; j < b.getWidth(); j++) {
                System.out.printf("%s|",b.getData()[j][i]);
            }
            System.out.printf("\n");
        }

        for(int i = 0; i < b.getWidth(); i++) {
            System.out.printf(" %s", i);
        }
        System.out.println(" ");
        System.out.println();
    }
}
