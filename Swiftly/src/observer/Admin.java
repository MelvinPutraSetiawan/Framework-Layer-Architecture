package observer;

import java.util.ArrayList;

public class Admin extends User implements Subject{
	private ArrayList<Observer> traders = new ArrayList<>();
	
	public Admin(int id, String name, String email, String password) {
		super(id, name, email, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addObserver(Observer trader) {
		// TODO Auto-generated method stub
		traders.add(trader);
	}

	@Override
	public void removeObserver(Observer trader) {
		// TODO Auto-generated method stub
		int index = traders.indexOf(trader);
		traders.remove(index);
	}

	@Override
	public void notifyObserver(Event event) {
		// TODO Auto-generated method stub
		for (Observer observer : traders) {
			observer.update(event);
		}
	}

}
