package zlt.model

class Driver {

}

/*
Driver for simulation of energy use on Zanzibar based on a single
point of importation
 */

case class Depot(max_capacity: Double, replenishment_rate_monthly: Double, depletion_rate: Double, current_stock: Double)

object Depot {

  /*
  Add to the stock the maximum monthly replenishment rate. If this would exceed the
  capacity the depot is refilled to maximum.
   */
  def replenish(depot: Depot): Depot = {
    val stock = depot.current_stock + depot.replenishment_rate_monthly
    if (stock > depot.max_capacity) depot.copy(current_stock = depot.max_capacity)
    else depot.copy(current_stock = stock)
  }

  /*
  Distribute fuel to users at the rate of depletion_rate
  If depot does not have enough fuel the stock level is set to
  zero
   */
  def distribute(depot: Depot, demand: Double): Depot = {
    val stockLevel: Double = depot.current_stock - demand

    if (stockLevel > 0) depot.copy(current_stock = stockLevel)
    else depot.copy(current_stock = 0)
  }

}

case class User(capacity: Int, depletion_rate:  Int)


object Main extends App{

  var final_demand_annual: Double = 10000
  var growth_rate_pa = 0.07
  var growth_rate_monthly = 1 + (growth_rate_pa/12) //1 to make sure we're growing

  //temp users
  val u1: User = User(1000, 500)
  val u2: User = User(1000, 500)
  val u3: User = User(1000, 500)
  val u4: User = User(1000, 500)
  val u5: User = User(1000, 500)

  val d1: Depot = Depot(15000, 5000, 0, 10000)

  //list to hold all users
  val users: List[User] = List()

  def addUser(capacity: Int, depletion_rate: Int): List[User] = {
    List()
  }

  def depotEmpty(depot: Depot): Boolean = depot.current_stock == 0

  /*
  Test against constraint. If constraint not met report otherwise
  replenish the depot,
  deploy the fuel,
  update demand,
  iterate...
   */
  def driver(depot: Depot): Unit = {

    var incrementer = 0 //time period
    var scenario_depot = depot
    while (scenario_depot.current_stock > 0){
      print("testing")
      scenario_depot = Depot.replenish(scenario_depot)
      scenario_depot = Depot.distribute(scenario_depot, final_demand_annual/12)
      final_demand_annual = final_demand_annual*growth_rate_monthly
      incrementer += 1

      //report interim results
      println(s"Year: ${incrementer/12}, month: ${incrementer%12 + 1} \n" +
        s"Depot stock: ${scenario_depot.current_stock}\n" +
        s"Final demand: $final_demand_annual\n" +
        s"Monthly demand: ${final_demand_annual/12}\n" +
        s"Replenishment rate: ${scenario_depot.replenishment_rate_monthly}\n")

    }

    println(s"Constraint was exceeded after $incrementer iterations")

  }

  print("hello")

  driver(d1)

}
