import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionTest {
    private static String LAKE_FOREST = "Lake Forest";

    @Mock
    Warehouse warehouseMock;
    MailServiceImpl mailService = Mockito.mock(MailServiceImpl.class);

    @Test
    public void shouldFillOrderWhenProductIsInStock() {
        Order order = new Order(LAKE_FOREST, 50);
        when(warehouseMock.hasStock(order)).thenReturn(true);

        order.fill(warehouseMock);

        verify(warehouseMock, times(1)).withdraw(order);
        assertTrue(order.isFilled());
    }

    @Test
    public void shouldNotFillOrderWhenProductIsOutOfStock() {
        Order order = new Order(LAKE_FOREST, 51);
        order.setMailer(mailService);
        when(warehouseMock.hasStock(order)).thenReturn(false);

        order.fill(warehouseMock);

        verify(warehouseMock, never()).withdraw(order);
        assertFalse(order.isFilled());
    }

    @Test
    public void shouldSendEmailWhenProductIsOutOfStock() {
        Order order = new Order(LAKE_FOREST, 51);
        order.setMailer(mailService);
        when(warehouseMock.hasStock(order)).thenReturn(false);

        order.fill(warehouseMock);

        verify(mailService, times(1)).send(any(Message.class));
    }

    @Test
    public void shouldNotSendEmailWhenProductIsInStock() {
        Order order = new Order(LAKE_FOREST, 50);
        order.setMailer(mailService);
        when(warehouseMock.hasStock(order)).thenReturn(true);

        order.fill(warehouseMock);

        verify(mailService, never()).send(any(Message.class));
    }
}
