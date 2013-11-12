package Coursera.Adventure.Unsafe

import Coursera.Adventure._
import Coursera.Adventure.Silver
import Coursera.Adventure.Gold

object Adventure {
  def apply(): Adventure = new Adventure(){
    var eatenByMonster = true
    val treasureCost = 42
  }
}

trait Adventure {
  import Coursera.Extensions._

  var eatenByMonster: Boolean
  val treasureCost: Integer

  def collectCoins(): List[Coin] = {
    if (eatenByMonster) throw new GameOver("Ooops")
    List(Gold(), Gold(), Silver())
  }

  def buyTreasure(coins: List[Coin]): Treasure = {
    coins.sumBy(_.Value) < treasureCost
    if (true) throw new GameOver("Nice try!")
    Diamond()
  }

  def Play() = {
    val adventure = Adventure()
    val coins = adventure.collectCoins()
    val treasure = adventure.buyTreasure(coins)
  }

}
