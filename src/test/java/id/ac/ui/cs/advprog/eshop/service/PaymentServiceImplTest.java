package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private List<Order> orders;
    private List<Payment> payments;
    private Map<String, String> paymentDatas;

    @BeforeEach
    public void setUp() {
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

        payments = new ArrayList<>();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", paymentDatas.get("voucherCode"));
        Payment payment1 = new Payment(
                "b2f1f2c1-56a1-4f9f-9d4f-8bae6f9bb6e4",
                "Gopay",
                paymentData
        );
        payments.add(payment1);
        paymentData = new HashMap<>();
        paymentData.put("address", paymentDatas.get("address"));
        paymentData.put("deliveryFee", paymentDatas.get("deliveryFee"));
        Payment payment2 = new Payment(
                "0b2f1f2c-56a1-4f9f-9d4f-8bae6f9bb6e5",
                "Bank Transfer",
                paymentData
        );
        payments.add(payment2);
        paymentData = new HashMap<>();
        paymentData.put("bankName", paymentDatas.get("bankName"));
        paymentData.put("referenceCode", paymentDatas.get("referenceCode"));
        Payment payment3 = new Payment(
                "5f6a6f2c-56a1-4f9f-9d4f-8bae6f9bb6e6",
                "BCA",
                paymentData
        );
        payments.add(payment3);
    }

    @Test
    public void testAddPayment() {
        Payment payment = payments.get(0);
        Order order = orders.get(0);

        when(paymentRepository.addPayment(order, payment.getMethod(), payment.getPaymentData())).thenReturn(payment);

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    public void testSetStatusSUCCESS() {
        Payment payment = payments.get(0);
        payment.setStatus(PaymentStatus.SUCCESS.getValue());
        Order order = orders.get(0);
        order.setStatus(OrderStatus.SUCCESS.getValue());
        when(paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue())).thenReturn(payment);

        payment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    public void testSetStatusREJECTED() {
        Payment payment = payments.get(0);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        Order order = orders.get(0);
        order.setStatus(OrderStatus.FAILED.getValue());
        when(paymentRepository.setStatus(payment, PaymentStatus.REJECTED.getValue())).thenReturn(payment);

        paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    public void testGetPayment() {
        Payment payment = payments.get(0);
        when(paymentRepository.getPayment(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());

        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    public void testGetPaymentNotFound() {
        Payment payment = payments.get(0);
        String id = payment.getId();
        when(paymentRepository.getPayment(any(String.class))).thenReturn(null);
        id = id.charAt(0) == '0' ? '1' + id.substring(1) : '0' + id.substring(1);

        Payment result = paymentService.getPayment(id);

        assertNull(result);
    }

    @Test
    public void testGetAllPayments() {
        when(paymentRepository.getAllPayments()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(payments, result);
    }
}
