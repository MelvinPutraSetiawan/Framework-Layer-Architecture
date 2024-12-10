package state;

public class CanceledState implements State{

	@Override
	public String handle(Order order) {
		// TODO Auto-generated method stub
		return "Already on canceled state!";
	}

	@Override
	public String cancel(Order order) {
		// TODO Auto-generated method stub
		return "Already on canceled state!";
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return "Canceled State";
	}

}
