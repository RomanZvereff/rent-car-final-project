import entity.Invoice;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvoiceTest {

    @Test
    public void shouldGenerateInvoiceNumber() {
        Invoice invoice = new Invoice();
        int number = invoice.generateInvoiceNumber();
        assertNotNull(number);
    }

    @Test
    public void shouldGenerateCorrectLengthInvoiceNumber() {
        Invoice invoice = new Invoice();
        int number = invoice.generateInvoiceNumber();
        String strNumber = String.valueOf(number);
        int numberLength = 6;
        assertEquals(numberLength, strNumber.length());
    }

}
