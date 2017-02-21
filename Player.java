public class Player {
 private String name;
 private int damage;
 private int maxHealth;
 private int curHealth;
 private double speed;
 private boolean dead;
 private boolean airborn;

 public Player(){
  name = "Bob";
  damage = 10;
  maxHealth = 100;
  curHealth = 100;
  speed = 10.0; //changed dependent on pixels and specific character role
  dead = false;
  airborn = false; //starts grounded //based on y coordinate and ground
 }

  public Player(String newName, int newDamage, int newMaxHealth, double newSpeed){
    name = newName;
    damage = newDamage;
    maxHealth = newMaxHealth;
    curHealth = newMaxHealth;
    speed = newSpeed;
    dead = false;
    airborn = false;
  }

 public void playerInfo(){
  System.out.println("Player name: " + name);
  System.out.println(name + "'s health: " + curHealth);
  System.out.println(name + "'s damage: " + damage);
  System.out.println(name + "'s speed: " + speed);
 }

 public void passiveHeal(){
  if(curHealth < maxHealth){
   curHealth = curHealth +1;
  }
 }

  public void heal(int healHP){
    if(curHealth + healHP >= maxHealth){
     curHealth = maxHealth;
    }
    else{
     curHealth = curHealth + healHP;
    }
  }

  public void changeMaxHealth(int newMaxHealth){
    maxHealth = newMaxHealth;
  }

  public void takeDamage(int damageTaken){
    if(curHealth - damageTaken < 0){
     dead = true;
    }
    else{
     curHealth = curHealth - damageTaken;
    }
  }

 public boolean isDead(){
  if(curHealth == 0 || curHealth < 0){
   dead = true;
  }
  return dead;
 }

 public String getName(){
  return name;
 }

 public void setName(String newName){
  name = newName;
 }

 public int getCurrentHealth(){
  return curHealth;
 }

 public int getMaximumHealth(){
  return maxHealth;
 }

 public int getDamage(){
  return damage;
 }

 public void setDamage(int newDamage){
  damage = newDamage;
 }

 public double getSpeed(){
  return speed;
 }

 public void setSpeed(double newSpeed){
  speed = newSpeed;
 }
}