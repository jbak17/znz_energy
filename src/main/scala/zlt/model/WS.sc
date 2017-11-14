import scalax.chart.api._

val names: List[String] = "Series A" :: "Series B" :: Nil

def randomSeries(name: String): XYSeries =
  List.tabulate(5)(x => (x,util.Random.nextInt(5))).toXYSeries(name)

val data: XYSeries = for (name <- names) yield randomSeries(name)

val chart = XYLineChart(data)