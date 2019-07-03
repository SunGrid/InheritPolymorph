/* Inheritance and Polymorphism

 */

enum class SoldierClassType{
    LIGHT,
    HEAVY;

    //fun isLight() : Boolean {return this == LIGHT
    fun isLight() = this == LIGHT
    fun isHeavy() = this == HEAVY
}

//<editor-fold desc="Interfaces">
interface Healable {
    fun heal(amount: Int)
}

interface Shooter{
    fun reload()
}
//</editor-fold>

abstract class Soldier(health: Int, var weapon: String) {

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
    var type = SoldierClassType.LIGHT  //Default setting in now LIGHT

    init {
        //without this.health = health, passed from mian method, the var health up above will be set to 0 and run at 0
        this.health = health

        println("Soldier inint called")
    }

    fun isLight() = type.isLight()
    fun isHeavy() = type.isHeavy()

    //polymorphic parameter
    open fun attack(soldier: Soldier) {
        val percentage = (damage * 0.1).toInt()  //calculating 10 % of damage
        var damageToTake = damage

/*
        if (type == SoldierClassType.LIGHT && soldier.type == SoldierClassType.HEAVY){ //light vs heavy
            damageToTake = damage - percentage //10% less damage
        }else if (type == SoldierClassType.HEAVY && soldier.type == SoldierClassType.LIGHT){ // heavy vs light
            damageToTake = damage + percentage //10% more damage
        }
*/
/*

        if (type.isLight() && soldier.type.isHeavy()){ //light vs heavy
            damageToTake = damage - percentage //10% less damage
        }else if (type.isHeavy() && soldier.type.isLight()){ // heavy vs light
            damageToTake = damage + percentage //10% more damage
        }
*/
        if (isLight() && soldier.isHeavy()){ //light vs heavy
            damageToTake = damage - percentage //10% less damage
        }else if (isHeavy() && soldier.isLight()){ // heavy vs light
            damageToTake = damage + percentage //10% more damage
        }


        println("attacking $soldier with $weapon")
        soldier.takeDamage(damageToTake)
    }

    fun takeDamage(damageToTake: Int) {
        health -= damageToTake
    }

    abstract fun run()
}

class Pikeman(health: Int, var armor: Int) : Soldier(health, "pike") {
    override fun run() {
        println("Pikeman running")
    }

    init {
        type = SoldierClassType.HEAVY
        println("Pikeman init called")
    }
}

class Archer(health: Int, var arrowCount: Int) : Soldier(health, "bow"), Shooter {

    init {
        println("Archer init called")
    }

    override fun attack(soldier: Soldier) {
        if (arrowCount <= 0) {
            println("no more arrows, Reload Quiver")
            reload()
        } //removed else statement so that archer will attach after reloading.
            super.attack(soldier)
            arrowCount--
            println("arrows left = $arrowCount")

    }

    override fun run(){
        println("Archer running")
    }

    override fun reload(){
        if (arrowCount <= 0 ) {
            println("reloading quiver")
            arrowCount = 5
        }
    }
}

class Pistolero(health: Int, var bulletCount: Int = 0) : Soldier(health,"pistol"), Healable, Shooter {

    override fun reload(){
        if(bulletCount <=0){
            println("reloading pistol")
            bulletCount = 6
        }
    }
    init {
        println("Pistolero init called")
    }

    override fun attack(soldier: Soldier) {
        if (bulletCount <= 0){
            println("No more bullets. Reload!")
            reload()
        }
        super.attack(soldier)
        println("firing bullet at $soldier")
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

    //vals being declared as "Soldier"
    val pikeman: Soldier = Pikeman(100, 100)
    pikeman.damage = 15
    pikeman.run()

    val archer: Soldier = Archer(health = 100, arrowCount = 5)
    archer.damage = 10
    archer.run()

    val pistolero: Soldier = Pistolero(100,6)
    pistolero.damage = 20
    pistolero.run()

    println("pikeman type = ${pikeman.type}")
    println("archer type = ${archer.type}")
    println("pistolero type = ${pistolero.type}")

    println("pikeman heavy? ${pikeman.isHeavy()} light? ${pikeman.isLight()}")
    println("Archer heavy? ${archer.isHeavy()} light? ${archer.isLight()}")
    println("Pistolero heavy? ${pistolero.isHeavy()} light? ${pistolero.isLight()}")

    pikeman.attack(archer)
    archer.attack(pikeman)
    println("pikeman health = ${pikeman.health} Archer Health = ${archer.health}")

    pikeman.attack(pistolero)
    pistolero.attack(pikeman)
    println("pikeman health = ${pikeman.health} Pistolero Health = ${pistolero.health}")

    archer.attack(pistolero)
    pistolero.attack(archer)
    println("Archer health = ${archer.health} pistolero Health = ${pistolero.health}")

    /*
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
       }*/

/*    pikeman.attack(archer)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")

    archer.attack(pikeman)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")

    pikeman.attack(archer)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")

    archer.attack(pikeman)
    println("pikeman health = ${pikeman.health} archer health = ${archer.health}")*/

}