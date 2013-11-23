package Coursera

import scala.util.{Failure, Success, Try}
import Coursera.Adventure.{Treasure, Coin}

object Socket {
  def apply(): Adventure = new Adventure(){
    var eatenByMonster = true
    val treasureCost = 42
  }
}


trait Socket {

  var eatenByMonster: Boolean
  val treasureCost: Integer

  def collectCoins(): Try[List[Coin]] = ???

  def buyTreasure(coins: List[Coin]): Try[Treasure] = ???

  def Play_I() = {
    val adventure = Adventure() 
    val coins: Try[List[Coin]] = adventure.collectCoins() 
    val treasure: Try[Treasure] = coins match {
      case Success(cs)          => adventure.buyTreasure(cs)
      case Failure(t)           => Failure(t)
    }
  }

  def Play_II() = {
    val adventure = Adventure() 
    val coins: Try[List[Coin]] = adventure.collectCoins() 
    val treasure: Try[Treasure] = coins.flatMap(cs => adventure.buyTreasure(cs))
  }

  def Play_III() = {
    val adventure = Adventure()
    val treasure: Try[Treasure] = for {
      coins <- adventure.collectCoins()
      treasure <- buyTreasure(coins)
    } yield treasure
  }
}

