package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PaymentRepository {
    private List<Payment> paymentList = new ArrayList<>();
    private Map<String, Order> paymentToOrder = new HashMap<>();

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (order == null) {
            throw new NullPointerException();
        }
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData);
        paymentList.add(payment);
        paymentToOrder.put(payment.getId(), order);
        return payment;
    }

    public Payment setStatus(Payment payment, String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        payment.setStatus(status);
        Order order = this.getOrder(payment.getId());
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        }
        else {
            order.setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;
    }

    public Payment getPayment(String paymentId) {
        for (Payment payment : paymentList) {
            if (payment.getId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        List<Payment> result = new ArrayList<>();
        for (Payment payment : paymentList) {
            result.add(payment);
        }
        return result;
    }

    public Order getOrder(String paymentId) {
        return paymentToOrder.get(paymentId);
    }

    public Order findOrderByOrderId(String orderId) {
        return null;
    }
}
