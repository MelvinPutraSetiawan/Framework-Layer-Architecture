package observer;

public interface Subject {
	public void addObserver(Observer trader);
	public void removeObserver(Observer trader);
	public void notifyObserver(Event event);
}
