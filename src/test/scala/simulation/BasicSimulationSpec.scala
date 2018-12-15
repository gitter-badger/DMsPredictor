package simulation

import base.PropertyChecksBase
import eu.timepit.refined.auto._
import io.github.tjheslin1.dmspredictor.classes.Fighter
import io.github.tjheslin1.dmspredictor.model.Dice
import io.github.tjheslin1.dmspredictor.simulation._
import io.github.tjheslin1.dmspredictor.strategy.LowestFirst
import org.scalatest.{FeatureSpec, Matchers}
import util.TestData
import util.TestData._

class BasicSimulationSpec extends FeatureSpec with Matchers with PropertyChecksBase with TestData {

  feature("BasicSimulation") {

    val info = "Fighter vs TestMonster"

    scenario("One Fighter vs a TestMonster where Fighter wins") {
      forAll { (fighter: Fighter, monster: TestMonster) =>
        val healthyFighter = fighter.withHealth(1000).withStrength(10)
        val weakTestMonster = monster.withHealth(1)

        BasicSimulation(List(healthyFighter, weakTestMonster), LowestFirst)
          .run(info)(Dice.naturalTwenty) shouldBe SimulationResult(Success, info)
      }
    }

    scenario("One Fighter vs a TestMonster where TestMonster wins") {
      forAll { (fighter: Fighter, monster: TestMonster) =>
        val weakFighter = fighter.withHealth(1)
        val healthyTestMonster = monster.withHealth(1000).withStrength(10)

        BasicSimulation(List(weakFighter, healthyTestMonster), LowestFirst)
          .run(info)(Dice.naturalTwenty) shouldBe SimulationResult(Loss, info)
      }
    }
  }
}
