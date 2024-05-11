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

    public boolean isDead() {
        return currentHealth <= 0;
    }
    
    public void restoreHealth() {
    	currentHealth = maxHealth;
    }

}
