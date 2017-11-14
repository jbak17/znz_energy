package zlt.model

import scalax.chart.api._

class Driver {

}

/*
Driver for simulation of energy use on Zanzibar based on a single
point of importation
 */

case class Depot(max_capacity: Double, replenishment_rate_monthly: Double, current_stock: Double)

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

  def depotEmpty(depot: Depot): Boolean = depot.current_stock == 0

}

case class User(capacity: Int, depletion_rate:  Int)

object User{

  def addUser(capacity: Int, depletion_rate: Int): List[User] = ???

}

case class Scenario(annualDemand: Double, annualGrowth: Double, title: String = ""){
  val growth_rate_monthly: Double = 1 + (annualGrowth / 12)
}

object Scenario {
  def grow(scenario: Scenario): Scenario = {
    val newDemand = scenario.annualDemand * scenario.growth_rate_monthly
    scenario.copy(annualDemand = newDemand)
  }
}

object Main extends App{

  var final_demand_annual: Double = 10000
  var growth_rate_pa = 0.07

  /*******************
  TEST OBJECTS
  ********************/

  val d1: Depot = Depot(15000, 5000,10000) //high fuel start
  val d2: Depot = Depot(15000, 5000,5000) //low fuel start
  val d3: Depot = Depot(15000, 2000,5000) //low fuel start, low replenishment rate
  val s1: Scenario = Scenario(final_demand_annual, growth_rate_pa)

  /*****************
    *
    * @param assumptions - demand and other necessary assumptions to test
    * @param depot - import facility conditions for assessment
    */
  def driver(assumptions: Scenario, depot: Depot): Unit = {

    var incrementer = 0 //time period
    var scenario_depot = depot
    var scenario = assumptions

    //Storage to hold intermediate results
    //used for charting and reporting
    var demand_data: List[Double] = List()
    var storage_data: List[Double] = List()

    /*
    Test against constraint. If constraint not met report otherwise
    replenish the depot,
    deploy the fuel,
    update demand,
    iterate...
     */
    while (scenario_depot.current_stock > 0){
      print("testing")
      scenario_depot = Depot.replenish(scenario_depot) //restock
      scenario_depot = Depot.distribute(scenario_depot, scenario.annualDemand/12) //use fuel
      scenario = Scenario.grow(scenario)

      incrementer += 1

      storage_data = scenario_depot.current_stock :: storage_data
      demand_data = scenario.annualDemand/12 :: demand_data

      //report interim results
      println(s"Year: ${incrementer/12}, month: ${incrementer%12 + 1} \n" +
        s"Depot stock: ${scenario_depot.current_stock}\n" +
        s"Final demand: ${scenario.annualDemand}\n" +
        s"Monthly demand: ${scenario.annualDemand/12}\n" +
        s"Replenishment rate: ${scenario_depot.replenishment_rate_monthly}\n")

    }

    println(s"Constraint was exceeded after $incrementer iterations")
    Display.supplyDemandChart(storage_data, demand_data)

  }

  print("hello")

  driver(s1,d3)

}
