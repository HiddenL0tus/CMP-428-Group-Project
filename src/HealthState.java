
public class HealthState {
	
	private int maxHealth;
    private int currentHealth;

    public HealthState(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public void takeDamage(int damageAmount) {
        currentHealth -= damageAmount;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

}
