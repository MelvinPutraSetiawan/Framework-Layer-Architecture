package state;

public interface State {
    String handle(Order order);
    String cancel(Order order);
    String getStateName(); 
}
