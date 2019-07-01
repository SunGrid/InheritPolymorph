/* Inheritance and Polymorphism

 */

interface Healable {
    fun heal(amount: Int)
}

abstract class Enemy(health: Int, var weapon: String) {

    var health: Int = 0
        set(value){
            field = value

            if (value < 0){
                field = 0
            }

            if(value > 100) {
                field = 100
            }
        }
    var damage: Int = 0

    init {
        //without this.health = health, passed from mian method, the var health up above will be set to 0 and run at 0
        this.health = health

        println("Enemy inint called")
    }

    //polymorphic parameter
    open fun attack(enemy: Enemy) {
        println("attacking $enemy with $weapon")
        enemy.takeDamage(damage)
    }

    fun takeDamage(damageToTake: Int) {
        health -= damageToTake
    }

    abstract fun run()
}

class Pikeman(health: Int, var armor: Int) : Enemy(health, "pike") {
    override fun run() {
        println("Pikeman running")
    }

    init {
        println("Pikeman init called")
    }
}

class Archer(health: Int, var arrowCount: Int) : Enemy(health, "bow") {

    init {
        println("Archer init called")
    }

    override fun attack(enemy: Enemy) {
        if (arrowCount <= 0) {
            println("no more arrows")
        } else {
            super.attack(enemy)
            arrowCount--
            println("arrows left = $arrowCount")
        }
    }

    override fun run(){
        println("Archer running")
    }


}

class Pistolero(health: Int, var bulletCount: Int = 0) : Enemy(health,"pistol"), Healable {


    fun reload(){
        if(bulletCount <=0){
            println("reloading pistol")
            bulletCount = 6
        }
    }
    init {
        println("Pistolero init called")
    }

    override fun attack(enemy: Enemy) {
        if (bulletCount <= 0){
            println("No more bullets. Reload!")
            reload()
        }
        super.attack(enemy)
        println("firing bullet at $enemy")
        bulletCount --
        println("bullets left = $bulletCount")


    }

    override fun run(){
        println("Pistolero running")
    }

    override fun heal(amount: Int) {
        if (amount < 0){
            println("can't heal with negative amount")
            return
        }
        println("healing with amount $amount")
        health += amount
    }
}

fun main() {

    //vals being declared as "Enemy"
    val pikeman: Enemy = Pikeman(100, 100)
    pikeman.damage = 5
    pikeman.run()

    val archer: Enemy = Archer(health = 100, arrowCount = 5)
    archer.damage = 5
    archer.run()

    val pistolero: Enemy = Pistolero(100,6)
    pistolero.damage = 10
    pistolero.run()

    while (archer.health > 0){
        pistolero.attack(archer)
        archer.attack(pistolero)
    }
    println("archer died")
    println("pistolero health = ${pistolero.health}")


       // pistolero.heal(200)//this does not work until in the if statment with smart casting from Kotlin
    if (pistolero is Healable) {
        val healable = pistolero as Healable // casting, converting to Healable.

        healable.heal(10) //using physical casting
        println("health = ${pistolero.health}")

        pistolero.heal(-10) // using smart casting that the if statement created
        println("health = ${pistolero.health}")

        pistolero.heal(200)
        println("health = ${pistolero.health}")
    }

/*    pikeman.attack(archer)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")

    archer.attack(pikeman)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")

    pikeman.attack(archer)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")

    archer.attack(pikeman)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")*/

}