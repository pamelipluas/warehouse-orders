import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class OrderStateTest {
    private static final String LAKE_FOREST = "Lake Forest";
    private static final String HIGHLAND_PARK = "Highland Park";
    private final Warehouse warehouse = new Warehouse();
    MailServiceImpl mailService = Mockito.mock(MailServiceImpl.class);

    @Before
    public void setUp() {
        warehouse.add(new Product(HIGHLAND_PARK), 25);
        warehouse.add(new Product(LAKE_FOREST), 50);
    }

    @Test
    public void shouldFillOrderWhenProductIsInStock() {
        Order order = new Order(LAKE_FOREST, 50);
        order.fill(warehouse);
        Assert.assertTrue(order.isFilled());
        Assert.assertEquals(0, warehouse.getStock(LAKE_FOREST));
    }

    @Test
    public void orderDoesNotRemoveWhenNotEnoughInWarehouse() {
        Order order = new Order(LAKE_FOREST, 51);
        order.setMailer(mailService);
        order.fill(warehouse);
        Assert.assertFalse(order.isFilled());
        Assert.assertEquals(50, warehouse.getStock(LAKE_FOREST));
    }

    @Test
    public void orderSendsMailWhenUnfilled() {
        Order order = new Order(LAKE_FOREST, 51);
        MailServiceImpl mailer = new MailServiceImpl();
        order.setMailer(mailer);
        order.fill(warehouse);
        Assert.assertEquals(1, mailer.numberSent());
    }
}
