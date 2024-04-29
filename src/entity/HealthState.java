package entity;

public class HealthState {
	
	private int maxHealth;
    private int currentHealth;

    public HealthState(int maxHealth) {
        this.maxHealth     = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void takeDamage(int damageAmount) {
        currentHealth -= damageAmount;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
