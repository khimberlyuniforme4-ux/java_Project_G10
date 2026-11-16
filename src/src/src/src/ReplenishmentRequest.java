class ReplenishmentRequest {

    private int requestId;
    private Product product;
    private int requestedQuantity;
    private int priority;
    private long order;

    public ReplenishmentRequest(int requestId, Product product,
                                int requestedQuantity, int priority,
                                long order) {
        this.requestId = requestId;
        this.product = product;
        this.requestedQuantity = requestedQuantity;
        this.priority = priority;
        this.order = order;
    }

    public int getRequestId() {
        return requestId;
    }

    public Product getProduct() {
        return product;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getPriority() {
        return priority;
    }

    public long getOrder() {
        return order;
    }

    public String getPriorityName() {
        switch (priority) {
            case 1:
                return "HIGHEST - OUT OF STOCK";
            case 2:
                return "HIGH - CRITICAL STOCK";
            case 3:
                return "MEDIUM - LOW STOCK";
            default:
                return "NORMAL";
        }
    }
}