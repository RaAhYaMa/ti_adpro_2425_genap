package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        validateMethod(method);
        validatePaymentData(paymentData);
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
    }

    public void validateMethod(String method) {
        if (method == null) {
            throw new NullPointerException();
        }
        if (method.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public void validatePaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new NullPointerException();
        }
        if (!(paymentData.containsKey("voucherCode") ||
                paymentData.containsKey("address") && paymentData.containsKey("deliveryFee") ||
                paymentData.containsKey("bankName") && paymentData.containsKey("referenceCode"))) {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus(String status) {
        if (status.equals("SUCCESS") || status.equals("REJECTED")) {
            this.status = status;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
