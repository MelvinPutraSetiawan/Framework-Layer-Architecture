package state;

public class CompleteState implements State{

	@Override
	public String handle(Order order) {
		// TODO Auto-generated method stub
		return "Order is completed. No further actions required.";
	}

	@Override
	public String cancel(Order order) {
		// TODO Auto-generated method stub
		return "Cannot be canceled already on completed state!";
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return "Completed State";
	}

}
