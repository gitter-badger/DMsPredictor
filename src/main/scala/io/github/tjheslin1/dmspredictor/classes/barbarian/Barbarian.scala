package io.github.tjheslin1.dmspredictor.classes.barbarian

import cats.Show
import cats.syntax.option._
import eu.timepit.refined.auto._
import io.github.tjheslin1.dmspredictor.classes.CoreAbilities.extraAttack
import io.github.tjheslin1.dmspredictor.classes.barbarian.Barbarian._
import io.github.tjheslin1.dmspredictor.classes.barbarian.BaseBarbarian._
import io.github.tjheslin1.dmspredictor.equipment.Equipment
import io.github.tjheslin1.dmspredictor.equipment.armour._
import io.github.tjheslin1.dmspredictor.equipment.weapons.Greatsword
import io.github.tjheslin1.dmspredictor.model.BaseStats.Stat
import io.github.tjheslin1.dmspredictor.model.ProficiencyBonus.ProficiencyBonus
import io.github.tjheslin1.dmspredictor.model._
import io.github.tjheslin1.dmspredictor.util.NameGenerator
import monocle.Lens
import monocle.macros.{GenLens, Lenses}

@Lenses("_") case class Barbarian(level: Level,
                                  health: Int,
                                  maxHealth: Int,
                                  stats: BaseStats,
                                  baseWeapon: Weapon,
                                  rageUsages: Int,
                                  armour: Armour = NoArmour,
                                  offHand: Option[Equipment] = None,
                                  proficiencyBonus: ProficiencyBonus = 0,
                                  resistances: List[DamageType] = List.empty,
                                  immunities: List[DamageType] = List.empty,
                                  bonusActionUsed: Boolean = false,
                                  abilities: List[CombatantAbility] = standardBarbarianAbilities,
                                  attackStatus: AttackStatus = Regular,
                                  defenseStatus: AttackStatus = Regular,
                                  inRage: Boolean = false,
                                  rageTurnsLeft: Int = 10,
                                  name: String = NameGenerator.randomName)
    extends BaseBarbarian {

  def weapon[_: RS]: Weapon = weaponWithRageDamage(baseWeapon, inRage)

  val armourClass: Int = calculateArmourClass(stats, armour, offHand)

  def updateHealth(modification: Int): Creature = copy(health = Math.max(0, health + modification))

  def scoresCritical(roll: Int): Boolean = roll == 20
}

object Barbarian {

  import BaseBarbarianAbilities._

  val standardBarbarianAbilities: List[CombatantAbility] = List(
    rage(1),
    extraAttack(2),
    recklessAttack(3)
  )

  def levelOneBarbarian[_: RS](weapon: Weapon = Greatsword,
                               armour: Armour = NoArmour): Barbarian = {
    val health = calculateHealth(LevelOne, 14)
    Barbarian(LevelOne,
              health,
              health,
              BaseStats(15, 13, 14, 12, 8, 10),
              weapon,
              rageUsages = 3,
              NoArmour,
              none[Equipment],
              2)
  }

  implicit def barbarianShow[_: RS]: Show[Barbarian] = Show.show { barbarian =>
    s"Barbarian: " +
      s"Name: ${barbarian.name}, " +
      s"health: ${barbarian.health}, " +
      s"AC: ${barbarian.armourClass}"
  }

  val strengthLens: Lens[Barbarian, Stat]  = _stats composeLens GenLens[BaseStats](_.strength)
  val dexterityLens: Lens[Barbarian, Stat] = _stats composeLens GenLens[BaseStats](_.dexterity)
  val constitutionLens: Lens[Barbarian, Stat] = _stats composeLens GenLens[BaseStats](
    _.constitution)
  val wisdomLens: Lens[Barbarian, Stat] = _stats composeLens GenLens[BaseStats](_.wisdom)
  val intelligenceLens: Lens[Barbarian, Stat] = _stats composeLens GenLens[BaseStats](
    _.intelligence)
  val charismaLens: Lens[Barbarian, Stat] = _stats composeLens GenLens[BaseStats](_.charisma)
}
