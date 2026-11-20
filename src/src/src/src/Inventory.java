class InventoryManagementSystem {

    private ArrayList<Product> products = new ArrayList<>();

    private PriorityQueue<ReplenishmentRequest> pendingRequests =
        new PriorityQueue<>(
            Comparator.comparingInt(ReplenishmentRequest::getPriority)
                      .thenComparingLong(ReplenishmentRequest::getOrder)
        );

    private int nextRequestId = 1;
    private long requestOrder = 1;

    private final int REORDER_LEVEL = 10;
    private final int TARGET_STOCK = 20;

    public InventoryManagementSystem() {

        products.add(new Product(101, "Keyboard", 0));
        products.add(new Product(102, "Mouse", 3));
        products.add(new Product(103, "Monitor", 8));
        products.add(new Product(104, "RAM", 15));
        products.add(new Product(105, "Hard Drive", 5));
    }

    // ================= ADD PRODUCT =================

    public void addProduct(Scanner scanner) {

        System.out.println("\n===== ADD PRODUCT =====");

        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Product p : products) {
            if (p.getProductId() == id) {
                System.out.println("Product ID already exists!");
                return;
            }
        }

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        products.add(new Product(id, name, quantity));

        System.out.println("Product added successfully!");
    }

        // ================= UPDATE PRODUCT =================

    public void updateProduct(Scanner scanner) {

        System.out.println("\n===== UPDATE PRODUCT =====");

        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();

        for (Product p : products) {

            if (p.getProductId() == id) {

                System.out.println("Current Product : " +
                        p.getProductName());

                System.out.println("Current Quantity: " +
                        p.getQuantity());

                System.out.print("Enter New Quantity: ");
                int quantity = scanner.nextInt();

                p.setQuantity(quantity);

                System.out.println("Product updated successfully!");
                return;
            }
        }

        System.out.println("Product not found!");
    }
    // ================= DISPLAY INVENTORY =================

    public void displayInventory() {

        System.out.println("\n========== INVENTORY ==========");

        System.out.printf("%-10s %-20s %-12s %-20s%n",
                "ID", "PRODUCT", "QUANTITY", "STATUS");

        for (Product p : products) {

            System.out.printf("%-10d %-20s %-12d %-20s%n",
                    p.getProductId(),
                    p.getProductName(),
                    p.getQuantity(),
                    p.getStockStatus());
        }
    }

    // ================= ASSIGN PRIORITY =================

    private int assignPriority(Product p) {

        if (p.getQuantity() <= 0) {
            return 1;
        } else if (p.getQuantity() <= 5) {
            return 2;
        } else {
            return 3;
        }
    }

    // ================= CHECK PENDING REQUEST =================

    private boolean hasPendingRequest(Product product) {

        for (ReplenishmentRequest request : pendingRequests) {

            if (request.getProduct() == product) {
                return true;
            }
        }

        return false;
    }
    
        // ================= DETECT LOW STOCK =================

    public void detectLowStock() {

        System.out.println("\n===== STOCK DETECTION =====");

        boolean detected = false;

        for (Product p : products) {

            if (p.getQuantity() <= REORDER_LEVEL) {

                detected = true;

                System.out.println("\nProduct: " + p.getProductName());
                System.out.println("Stock Status: " + p.getStockStatus());

                if (!hasPendingRequest(p)) {

                    int priority = assignPriority(p);
                    int neededQuantity = TARGET_STOCK - p.getQuantity();

                    ReplenishmentRequest request =
                            new ReplenishmentRequest(
                                    nextRequestId++,
                                    p,
                                    neededQuantity,
                                    priority,
                                    requestOrder++
                            );

                    pendingRequests.offer(request);

                    System.out.println("Replenishment Request Generated!");
                    System.out.println("Request ID: " + request.getRequestId());
                    System.out.println("Priority: " + request.getPriorityName());
                    System.out.println("Quantity to Replenish: "
                            + request.getRequestedQuantity());

                } else {

                    System.out.println(
                            "A replenishment request is already pending.");
                }
            }
        }

        if (!detected) {
            System.out.println("All products have sufficient stock.");
        }
    }
       // ================= DISPLAY PENDING REQUESTS =================

    public void displayPendingRequests() {

        System.out.println("\n===== PENDING REPLENISHMENT REQUESTS =====");

        if (pendingRequests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }

        PriorityQueue<ReplenishmentRequest> tempQueue =
                new PriorityQueue<>(pendingRequests);

        while (!tempQueue.isEmpty()) {

            ReplenishmentRequest request = tempQueue.poll();

            System.out.println("\nRequest ID: " + request.getRequestId());
            System.out.println("Product: " +
                    request.getProduct().getProductName());
            System.out.println("Current Stock: " +
                    request.getProduct().getQuantity());
            System.out.println("Requested Quantity: " +
                    request.getRequestedQuantity());
            System.out.println("Priority: " +
                    request.getPriorityName());

            System.out.println("--------------------------------");
        }
    }

    // ================= PROCESS HIGHEST PRIORITY =================

    public void processHighestPriority() {

        System.out.println("\n===== PROCESS REPLENISHMENT =====");

        if (pendingRequests.isEmpty()) {
            System.out.println("No requests to process.");
            return;
        }

        ReplenishmentRequest request = pendingRequests.poll();

        Product product = request.getProduct();

        System.out.println("Processing Request ID: " +
                request.getRequestId());

        System.out.println("Product: " +
                product.getProductName());

        System.out.println("Priority: " +
                request.getPriorityName());

        System.out.println("Replenishment Quantity: " +
                request.getRequestedQuantity());

        product.setQuantity(
                product.getQuantity() +
                request.getRequestedQuantity());

        System.out.println("\nRequest Successfully Processed!");
        System.out.println("Updated Stock: " +
                product.getQuantity());
        System.out.println("Status: " +
                product.getStockStatus());
    }

}