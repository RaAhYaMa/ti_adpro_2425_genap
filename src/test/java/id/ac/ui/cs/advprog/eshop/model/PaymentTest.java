package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void testCreateDefaultPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                "Gopay",
                paymentData
        );

        assertEquals("123e4567-e89b-12d3-a456-426614174000", payment.getId());
        assertEquals("Gopay", payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testFailCreatePaymentIfMethodIsNull() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(NullPointerException.class, () -> new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                null,
                paymentData
        ));
    }

    @Test
    void testFailCreatePaymentIfMethodIsEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                "",
                paymentData
        ));
    }

    @Test
    void testSetStatusIfValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                "Gopay",
                paymentData
        );
        payment.setStatus(PaymentStatus.SUCCESS.getValue());

        assertEquals("123e4567-e89b-12d3-a456-426614174000", payment.getId());
        assertEquals("Gopay", payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testFailSetStatusIfInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                "Gopay",
                paymentData
        );
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("RANDOM"));
    }

    @Test
    void testFailCreatePaymentIfPaymentDataIsNull() {
        Map<String, String> paymentData = null;
        assertThrows(NullPointerException.class, () -> new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                "Gopay",
                paymentData
        ));
    }

    @Test
    void testFailCreatePaymentIfPaymentDataDoesNotContainSubFeature() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("Anime", "Yuru Camp");
        assertThrows(IllegalArgumentException.class, () -> new Payment(
                "123e4567-e89b-12d3-a456-426614174000",
                "Gopay",
                paymentData
        ));
    }
}
