package Coursera.Adventure.Unsafe

import Coursera.Adventure._
import Coursera.Adventure.Silver
import Coursera.Adventure.Gold
import Coursera.Extensions

object Adventure {
  def apply(): Adventure = new Adventure(){
    var eatenByMonster: Boolean = true
    val treasureCost: Integer = 42
  }
}

trait Adventure {
  import Extensions._

  var eatenByMonster: Boolean
  val treasureCost: Integer

  def collectCoins(): List[Coin] = {
    if (eatenByMonster) throw new GameOver("Ooops")
    List(Gold(), Gold(), Silver())
  }

  def buyTreasure(coins: List[Coin]): Treasure = {
    coins.sumBy(x => x.Value) < treasureCost
    if (true) throw new GameOver("Nice try!")
    Diamond()
  }

  def Play() : Unit = {
    val adventure = Adventure()
    val coins = adventure.collectCoins()
    val treasure = adventure.buyTreasure(coins)
  }

}

