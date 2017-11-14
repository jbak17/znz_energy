package zlt.model

class Driver {

}

/*
Driver for simulation of energy use on Zanzibar based on a single
point of importation
 */

object Depot {
  val replenish_rate_monthly = 5000 //just a guess
  val depletion_rate = 2000 //just a guess
  val max_capacity = 16000
  val current_stock = 16000

}

case class User(capacity: Int, depletion_rate:  Int)


object Main extends App{

  var final_demand = 10000
  var growth_rate_pa = 1.07
  var growth_rate_monthly = growth_rate_pa/12

  //temp users
  val u1: User = User(1000, 500)
  val u2: User = User(1000, 500)
  val u3: User = User(1000, 500)
  val u4: User = User(1000, 500)
  val u5: User = User(1000, 500)

  //list to hold all users
  val users: List[User] = List()

  def addUser(capacity: Int, depletion_rate: Int): List[User] = {
    List()
  }

  /*
  Test if there is fuel in the depot. If not there is a shortage
  If there is fuel replenish the depot, deploy the fuel, update demand iterate...
   */
  def driver(constraint: () => Boolean): Unit = {

    while (constraint()){
      print("testing")
    }

  }

  print("hello")


}
