package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Order> orders;
    Map<String, String> paymentDatas;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Saffira Sudrajat"
        );
        orders.add(order1);
        Order order2 = new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Saffira Sudrajat"
        );
        orders.add(order2);
        Order order3 = new Order(
                "e33def40-9eff-4da8-9487-8ee697ecbf1e",
                products,
                1708570000L,
                "Bambang Sudrajat"
        );
        orders.add(order3);

        paymentDatas = new HashMap<>();
        paymentDatas.put("voucherCode", "ESHOP1234ABC5678");
        paymentDatas.put("address", "Jl. Bambang No. 1, Jakarta Pusat, Indonesia, 10110");
        paymentDatas.put("deliveryFee", "10000");
        paymentDatas.put("bankName", "BCA");
        paymentDatas.put("referenceCode", "ESHOP1234ABC5678");
    }

    @Test
    void testAddPayment() {
        Order order = orders.get(1);
        String method = "Gopay";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));

        Payment payment = paymentRepository.addPayment(order, method, paymentData);
        assertSame(order, paymentRepository.getOrder(payment.getId()));
        assertEquals(method, payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
    }

    @Test
    void testFailAddPaymentIfOrderIsNull() {
        Order order = null;
        String method = "Gopay";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));

        assertThrows(NullPointerException.class, () -> paymentRepository.addPayment(order, method, paymentData));
    }

    @Test
    void testSetStatus() {
        Order order = orders.get(1);
        String method = "Gopay";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));

        Payment payment = paymentRepository.addPayment(order, method, paymentData);
        paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(payment.getStatus(), PaymentStatus.SUCCESS.getValue());
        assertEquals(order.getStatus(), OrderStatus.SUCCESS.getValue());
    }

    @Test
    void testFailSetStatusIfStatusIsInvalid() {
        Order order = orders.get(1);
        String method = "Gopay";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));

        Payment payment = paymentRepository.addPayment(order, method, paymentData);
        assertThrows(IllegalArgumentException.class, () -> paymentRepository.setStatus(payment, "TK ANUM KELUAR"));
    }

    @Test
    void testGetPayment() {
        Order order = orders.get(1);
        String method = "Gopay";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));

        Payment payment = paymentRepository.addPayment(order, method, paymentData);

        assertSame(payment, paymentRepository.getPayment(payment.getId()));
    }

    @Test
    void testGetPaymentIfNotFound() {
        Order order = orders.get(1);
        String method = "Gopay";
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));
        Payment payment = paymentRepository.addPayment(order, method, paymentData);

        String id = payment.getId();
        id = id.charAt(0) == '0' ? '1' + id.substring(1) : '0' + id.substring(1);

        assertNull(paymentRepository.getPayment(id));
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        Order order1 = orders.get(1);
        String method1 = "Gopay";
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", paymentDatas.get("voucherCode"));
        Payment payment1 = paymentRepository.addPayment(order1, method1, paymentData1);
        payments.add(payment1);

        Order order2 = orders.get(2);
        String method2 = "Gopay";
        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", paymentDatas.get("voucherCode"));
        Payment payment2 = paymentRepository.addPayment(order2, method2, paymentData2);
        payments.add(payment2);

        List<Payment> resultPayments = paymentRepository.getAllPayments();
        assertEquals(payments, resultPayments);
        assertSame(resultPayments.get(0), payment1);
        assertSame(resultPayments.get(1), payments);
    }

    @Test
    void testGetAllPaymentsIfEmpty() {
        List<Payment> payments = paymentRepository.getAllPayments();
        assertTrue(payments.isEmpty());
    }
}
