package observer;

import java.util.ArrayList;

public class Admin extends User implements Subject{
	private ArrayList<Observer> traders = new ArrayList<>();
	private Event currentEvent = null;
	
	public Admin(int id, String name, String email, String password) {
		super(id, name, email, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addObserver(Observer trader) {
		// TODO Auto-generated method stub
		traders.add(trader);
		System.out.println("Added new Trader");
	}

	@Override
	public void removeObserver(Observer trader) {
		// TODO Auto-generated method stub
		((Trader) trader).setEvent(null);
		int index = traders.indexOf(trader);
		traders.remove(index);
		System.out.println("Remove Trader");
	}

	@Override
	public void notifyObserver(Event event) {
		// TODO Auto-generated method stub
		for (Observer observer : traders) {
			observer.update(event);
		}
	}
	
	public void createEvent(String name, String description, int discount) {
		System.out.println("========================================");
		System.out.println("============ Event Observer ============");
		System.out.println("========================================");
		System.out.println("Admin Make EVENT For Observer");
		System.out.println("Event Name : " + name);
		System.out.println("Event Description : " + description);
		System.out.println("Event Discount : " + discount);
		System.out.println("========================================");
		this.currentEvent = new Event(name, description, discount);
		notifyObserver(currentEvent);
	}

	public ArrayList<Observer> getTraders() {
		return traders;
	}

	public void setTraders(ArrayList<Observer> traders) {
		this.traders = traders;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}
}
