 public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        InventoryManagementSystem system =
            new InventoryManagementSystem();

        int choice = 0 ;

        do {

            System.out.println("[1] Display Inventory");
            System.out.println("[2] Add Product");
            System.out.println("[3] Update Product");
            System.out.println("[4] Detect Low Stock");
            System.out.println("[5] Display Pending Replenishment Requests");
            System.out.println("[6] Process Highest Priority Request");
            System.out.println("[7] Exit");

      System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
     System.out.println("Invalid input. Please enter a number from 1-7.");
     scanner.next();
     System.out.print("Enter your choice: ");
}
    choice = scanner.nextInt();

     switch (choice) {

    case 1:
        system.displayInventory();
        break;

    case 2:
        system.addProduct(scanner);
        break;

    case 3:
        system.updateProduct(scanner);
        break;

    case 4:
        system.detectLowStock();
        break;

    case 5:
        system.displayPendingRequests();
        break;

    case 6:
        system.processHighestPriority();
        break;

    case 7:
        System.out.println("Thank you for using the system!");
        break;

    default:
        System.out.println("Invalid choice. Please try again.");
}

        } while (choice != 7);

        scanner.close();
    }
}