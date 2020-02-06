class Order {
    private String product;
    private int quantity;
    private boolean filled;
    private MailServiceImpl mailer;

    public Order(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.filled = false;
    }

    public String getProductId() {
        return product;
    }

    public void setMailer(MailServiceImpl mailer) {
        this.mailer = mailer;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isFilled() {
        return filled;
    }

    public void fill(Warehouse warehouse) {
        if (warehouse.hasStock(this)) {
            warehouse.withdraw(this);
            this.filled = true;
        } else {
            mailer.send(new Message());
        }
    }
}
