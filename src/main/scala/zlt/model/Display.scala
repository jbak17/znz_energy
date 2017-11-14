package zlt.model

import scalax.chart.api._

object Display {

  /*
  Display a supply demand chart with the time in months on the x axis and the available fuel
  on the y-axis.
   */
  def supplyDemandChart(storage_data: List[Double], demand_data: List[Double]): Unit = {

    val data1 = storage_data //data to work with
      .reverse //we added data at the head, we want to view chronologically
      .zipWithIndex
      .map(x => (x._2,x._1)) //we want the index on the x-axis
      .toXYSeries("Fuel at depot") //legend
    val data2 = demand_data.reverse.zipWithIndex.map(x => (x._2,x._1)).toXYSeries("Demand per month")

    //consolidate data to show on a single graph
    val chartData = data1 :: data2 :: Nil
    val chart = XYLineChart(chartData)
    chart.title_=("Demand for fuel")

    chart.show()
  }
}

/*
MULTI-LINE CHART EXAMPLE
val names: List[String] = "Series A" :: "Series B" :: Nil

val data = for {
name <- names
series = for (x <- 1 to 5) yield (x,util.Random.nextInt(5))
} yield name -> series

val chart = XYLineChart(data)
*/
