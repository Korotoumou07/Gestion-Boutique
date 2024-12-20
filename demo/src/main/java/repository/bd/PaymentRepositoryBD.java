package repository.bd;

import entites.Payment;
import repository.PaymentRepository;


public class PaymentRepositoryBD extends ReopsitoryBDImpl<Payment> implements PaymentRepository {

    public PaymentRepositoryBD() {
        super(Payment.class);
        
    }

    
}
