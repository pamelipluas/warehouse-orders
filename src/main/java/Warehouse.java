import java.util.HashMap;
import java.util.Map;

class Warehouse {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void add(Product product, int stock) {
        inventory.put(product.getId(), stock);
    }

    public int getStock(String productId) {
        return inventory.getOrDefault(productId, 0);
    }

    public boolean hasStock(Order order) {
        return order.getQuantity() <= getStock(order.getProductId());
    }

    public void withdraw(Order order) {
        int stock = getStock(order.getProductId());
        inventory.put(order.getProductId(), stock - order.getQuantity());
    }
}
