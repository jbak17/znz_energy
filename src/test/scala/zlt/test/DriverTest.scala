package zlt.test

import org.scalatest.FlatSpec
import zlt.model.Depot


class DriverTest extends FlatSpec {

  val d1 = Depot(10000, 2000, 9000)
  val final_demand = 3000

  "A depot" should "never have more fuel than it's maximum capacity" in {
    val d2 = Depot.replenish(d1)
    assert(d2.current_stock == d1.max_capacity)
  }

  "It" should "increase stock by the incrementer amount when less than maximum" in {
    val dep = Depot(10000, 2000, 2000)
    val d2 = Depot.replenish(dep)
    assert(d2.current_stock == dep.current_stock + dep.replenishment_rate_monthly)
  }

  "It" should "decrease stock by the usage amount" in {
    val d2: Depot = Depot.distribute(d1, final_demand)
    val demand: Int = 1000
    assert(d2.current_stock == d1.current_stock - demand)
  }

  "It" should "never have negative stock" in {
    val dep = Depot(10000, 2000, 2000)
    val d2 = Depot.distribute(dep, final_demand)
    assert(d2.current_stock == 0)
  }

}
