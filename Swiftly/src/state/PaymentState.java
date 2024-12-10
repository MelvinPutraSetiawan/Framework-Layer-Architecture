package state;

public class PaymentState implements State{
	public PaymentState() {
		
	}

	@Override
	public String handle(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancel(Order order) {
		order.setCurrentState(new CanceledState());
		return null;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return "Payment State";
	}
}
