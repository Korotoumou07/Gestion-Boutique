package sevices;

import entites.Payment;
import repository.PaymentRepository;

public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;
    
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void createPayment(Payment payment) {
        paymentRepository.insert(payment);
    }
    
}
