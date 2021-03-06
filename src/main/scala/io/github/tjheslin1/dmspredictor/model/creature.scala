package io.github.tjheslin1.dmspredictor.model

import cats.Show
import cats.syntax.option._
import io.github.tjheslin1.dmspredictor.classes.barbarian.{Barbarian, Berserker, TotemWarrior}
import io.github.tjheslin1.dmspredictor.classes.fighter._
import io.github.tjheslin1.dmspredictor.equipment.Equipment
import io.github.tjheslin1.dmspredictor.equipment.armour.Armour
import io.github.tjheslin1.dmspredictor.model.BaseStats.Stat
import io.github.tjheslin1.dmspredictor.model.ProficiencyBonus.ProficiencyBonus
import io.github.tjheslin1.dmspredictor.monsters._
import monocle.{Lens, Optional}

sealed trait CreatureType extends Product with Serializable

case object Monster         extends CreatureType
case object PlayerCharacter extends CreatureType

trait Creature {

  val creatureType: CreatureType

  val health: Int
  val maxHealth: Int
  val stats: BaseStats
  val baseWeapon: Weapon
  def weapon[_: RS]: Weapon
  val armour: Armour
  val offHand: Option[Equipment]
  val armourClass: Int
  val proficiencyBonus: ProficiencyBonus
  val resistances: List[DamageType]
  val immunities: List[DamageType]
  val attackStatus: AttackStatus
  val defenseStatus: AttackStatus
  val name: String

  val abilities: List[CombatantAbility]

  val isConscious = health > 0

  def updateHealth(modification: Int): Creature
  def scoresCritical(roll: Int): Boolean

  def turnReset(): Creature
}

object Creature {

  implicit def creatureShow[_: RS]: Show[Creature] = Show.show { creature =>
    s"${creature.creatureType} - " +
      s"Name: ${creature.name}, " +
      s"health: ${creature.health}, " +
      s"AC: ${creature.armourClass}"
  }

  val creatureHealthLens: Lens[Creature, Int] = Lens[Creature, Int](_.health) { hp =>
    {
      case c: BattleMaster   => BattleMaster._health.set(hp)(c)
      case c: EldritchKnight => EldritchKnight._health.set(hp)(c)
      case c: Champion       => Champion._health.set(hp)(c)
      case c: Fighter        => Fighter._health.set(hp)(c)

      case c: Barbarian    => Barbarian._health.set(hp)(c)
      case c: Berserker    => Berserker._health.set(hp)(c)
      case c: TotemWarrior => TotemWarrior._health.set(hp)(c)

      case c: Goblin   => Goblin._health.set(hp)(c)
      case c: Werewolf => Werewolf._health.set(hp)(c)
    }
  }

  val creatureMaxHealthLens: Lens[Creature, Int] = Lens[Creature, Int](_.maxHealth) { hp =>
    {
      case c: BattleMaster   => BattleMaster._maxHealth.set(hp)(c)
      case c: EldritchKnight => EldritchKnight._maxHealth.set(hp)(c)
      case c: Champion       => Champion._maxHealth.set(hp)(c)
      case c: Fighter        => Fighter._maxHealth.set(hp)(c)

      case c: Barbarian    => Barbarian._maxHealth.set(hp)(c)
      case c: Berserker    => Berserker._maxHealth.set(hp)(c)
      case c: TotemWarrior => TotemWarrior._maxHealth.set(hp)(c)

      case c: Goblin   => Goblin._maxHealth.set(hp)(c)
      case c: Werewolf => Werewolf._maxHealth.set(hp)(c)
    }
  }

  val creatureStatsLens: Lens[Creature, BaseStats] = Lens[Creature, BaseStats](_.stats) { stats =>
    {
      case c: BattleMaster   => BattleMaster._stats.set(stats)(c)
      case c: EldritchKnight => EldritchKnight._stats.set(stats)(c)
      case c: Champion       => Champion._stats.set(stats)(c)
      case c: Fighter        => Fighter._stats.set(stats)(c)

      case c: Barbarian    => Barbarian._stats.set(stats)(c)
      case c: Berserker    => Berserker._stats.set(stats)(c)
      case c: TotemWarrior => TotemWarrior._stats.set(stats)(c)

      case c: Goblin   => Goblin._stats.set(stats)(c)
      case c: Werewolf => Werewolf._stats.set(stats)(c)
    }
  }

  val creatureStrengthLens: Lens[Creature, Stat] = Lens[Creature, Stat](_.stats.strength) {
    strScore =>
      {
        case c: BattleMaster   => BattleMaster.strengthLens.set(strScore)(c)
        case c: EldritchKnight => EldritchKnight.strengthLens.set(strScore)(c)
        case c: Champion       => Champion.strengthLens.set(strScore)(c)
        case c: Fighter        => Fighter.strengthLens.set(strScore)(c)

        case c: Barbarian    => Barbarian.strengthLens.set(strScore)(c)
        case c: Berserker    => Berserker.strengthLens.set(strScore)(c)
        case c: TotemWarrior => TotemWarrior.strengthLens.set(strScore)(c)

        case c: Goblin   => Goblin.strengthLens.set(strScore)(c)
        case c: Werewolf => Werewolf.strengthLens.set(strScore)(c)
      }
  }

  val creatureDexterityLens: Lens[Creature, Stat] = Lens[Creature, Stat](_.stats.dexterity) {
    dexScore =>
      {
        case c: BattleMaster   => BattleMaster.dexterityLens.set(dexScore)(c)
        case c: EldritchKnight => EldritchKnight.dexterityLens.set(dexScore)(c)
        case c: Champion       => Champion.dexterityLens.set(dexScore)(c)
        case c: Fighter        => Fighter.dexterityLens.set(dexScore)(c)

        case c: Barbarian    => Barbarian.dexterityLens.set(dexScore)(c)
        case c: Berserker    => Berserker.dexterityLens.set(dexScore)(c)
        case c: TotemWarrior => TotemWarrior.dexterityLens.set(dexScore)(c)

        case c: Goblin   => Goblin.dexterityLens.set(dexScore)(c)
        case c: Werewolf => Werewolf.dexterityLens.set(dexScore)(c)
      }
  }

  val creatureConstitutionLens: Lens[Creature, Stat] = Lens[Creature, Stat](_.stats.constitution) {
    conScore =>
      {
        case c: BattleMaster   => BattleMaster.constitutionLens.set(conScore)(c)
        case c: EldritchKnight => EldritchKnight.constitutionLens.set(conScore)(c)
        case c: Champion       => Champion.constitutionLens.set(conScore)(c)
        case c: Fighter        => Fighter.constitutionLens.set(conScore)(c)

        case c: Barbarian    => Barbarian.constitutionLens.set(conScore)(c)
        case c: Berserker    => Berserker.constitutionLens.set(conScore)(c)
        case c: TotemWarrior => TotemWarrior.constitutionLens.set(conScore)(c)

        case c: Goblin   => Goblin.constitutionLens.set(conScore)(c)
        case c: Werewolf => Werewolf.constitutionLens.set(conScore)(c)
      }
  }

  val creatureWisdomLens: Lens[Creature, Stat] = Lens[Creature, Stat](_.stats.wisdom) { wisScore =>
    {
      case c: BattleMaster   => BattleMaster.wisdomLens.set(wisScore)(c)
      case c: EldritchKnight => EldritchKnight.wisdomLens.set(wisScore)(c)
      case c: Champion       => Champion.wisdomLens.set(wisScore)(c)
      case c: Fighter        => Fighter.wisdomLens.set(wisScore)(c)

      case c: Barbarian    => Barbarian.wisdomLens.set(wisScore)(c)
      case c: Berserker    => Berserker.wisdomLens.set(wisScore)(c)
      case c: TotemWarrior => TotemWarrior.wisdomLens.set(wisScore)(c)

      case c: Goblin   => Goblin.wisdomLens.set(wisScore)(c)
      case c: Werewolf => Werewolf.wisdomLens.set(wisScore)(c)
    }
  }

  val creatureIntelligenceLens: Lens[Creature, Stat] = Lens[Creature, Stat](_.stats.intelligence) {
    intScore =>
      {
        case c: BattleMaster   => BattleMaster.intelligenceLens.set(intScore)(c)
        case c: EldritchKnight => EldritchKnight.intelligenceLens.set(intScore)(c)
        case c: Champion       => Champion.intelligenceLens.set(intScore)(c)
        case c: Fighter        => Fighter.intelligenceLens.set(intScore)(c)

        case c: Barbarian    => Barbarian.intelligenceLens.set(intScore)(c)
        case c: Berserker    => Berserker.intelligenceLens.set(intScore)(c)
        case c: TotemWarrior => TotemWarrior.intelligenceLens.set(intScore)(c)

        case c: Goblin   => Goblin.intelligenceLens.set(intScore)(c)
        case c: Werewolf => Werewolf.intelligenceLens.set(intScore)(c)
      }
  }

  val creatureCharismaLens: Lens[Creature, Stat] = Lens[Creature, Stat](_.stats.charisma) {
    chaScore =>
      {
        case c: BattleMaster   => BattleMaster.charismaLens.set(chaScore)(c)
        case c: EldritchKnight => EldritchKnight.charismaLens.set(chaScore)(c)
        case c: Champion       => Champion.charismaLens.set(chaScore)(c)
        case c: Fighter        => Fighter.charismaLens.set(chaScore)(c)

        case c: Barbarian    => Barbarian.charismaLens.set(chaScore)(c)
        case c: Berserker    => Berserker.charismaLens.set(chaScore)(c)
        case c: TotemWarrior => TotemWarrior.charismaLens.set(chaScore)(c)

        case c: Goblin   => Goblin.charismaLens.set(chaScore)(c)
        case c: Werewolf => Werewolf.charismaLens.set(chaScore)(c)
      }
  }

  val creatureBaseWeaponLens: Lens[Creature, Weapon] = Lens[Creature, Weapon](_.baseWeapon) { wpn =>
    {
      case c: BattleMaster   => BattleMaster._baseWeapon.set(wpn)(c)
      case c: EldritchKnight => EldritchKnight._baseWeapon.set(wpn)(c)
      case c: Champion       => Champion._baseWeapon.set(wpn)(c)
      case c: Fighter        => Fighter._baseWeapon.set(wpn)(c)

      case c: Barbarian    => Barbarian._baseWeapon.set(wpn)(c)
      case c: Berserker    => Berserker._baseWeapon.set(wpn)(c)
      case c: TotemWarrior => TotemWarrior._baseWeapon.set(wpn)(c)

      case c: Goblin   => Goblin._baseWeapon.set(wpn)(c)
      case c: Werewolf => Werewolf._baseWeapon.set(wpn)(c)
    }
  }

  val creatureArmourLens: Lens[Creature, Armour] = Lens[Creature, Armour](_.armour) { armr =>
    {
      case c: BattleMaster   => BattleMaster._armour.set(armr)(c)
      case c: EldritchKnight => EldritchKnight._armour.set(armr)(c)
      case c: Champion       => Champion._armour.set(armr)(c)
      case c: Fighter        => Fighter._armour.set(armr)(c)

      case c: Barbarian    => Barbarian._armour.set(armr)(c)
      case c: Berserker    => Berserker._armour.set(armr)(c)
      case c: TotemWarrior => TotemWarrior._armour.set(armr)(c)

      case c: Goblin   => Goblin._armour.set(armr)(c)
      case c: Werewolf => Werewolf._armour.set(armr)(c)
    }
  }

  val creatureOffHandLens: Lens[Creature, Option[Equipment]] =
    Lens[Creature, Option[Equipment]](_.offHand) { offH =>
      {
        case c: BattleMaster   => BattleMaster._offHand.set(offH)(c)
        case c: EldritchKnight => EldritchKnight._offHand.set(offH)(c)
        case c: Champion       => Champion._offHand.set(offH)(c)
        case c: Fighter        => Fighter._offHand.set(offH)(c)

        case c: Barbarian    => Barbarian._offHand.set(offH)(c)
        case c: Berserker    => Berserker._offHand.set(offH)(c)
        case c: TotemWarrior => TotemWarrior._offHand.set(offH)(c)

        case c: Goblin   => Goblin._offHand.set(offH)(c)
        case c: Werewolf => Werewolf._offHand.set(offH)(c)
      }
    }

  val creatureArmourClassOptional: Optional[Creature, Int] = Optional[Creature, Int] {
    case c: Goblin   => c.armourClass.some
    case c: Werewolf => c.armourClass.some
    case _           => none[Int]
  } { ac =>
    {
      case c: Goblin   => Goblin._armourClass.set(ac)(c)
      case c: Werewolf => Werewolf._armourClass.set(ac)(c)
      case c: Creature => c
    }
  }

  val creatureProficiencyBonusOptional: Optional[Creature, ProficiencyBonus] =
    Optional[Creature, ProficiencyBonus] {
      case c: BattleMaster   => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some
      case c: EldritchKnight => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some
      case c: Champion       => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some
      case c: Fighter        => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some

      case c: Barbarian    => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some
      case c: Berserker    => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some
      case c: TotemWarrior => val pb: ProficiencyBonus = c.proficiencyBonus; pb.some

      case _ => none[ProficiencyBonus]
    } { profBonus =>
      {
        case c: BattleMaster   => BattleMaster._proficiencyBonus.set(profBonus)(c)
        case c: EldritchKnight => EldritchKnight._proficiencyBonus.set(profBonus)(c)
        case c: Champion       => Champion._proficiencyBonus.set(profBonus)(c)
        case c: Fighter        => Fighter._proficiencyBonus.set(profBonus)(c)

        case c: Barbarian    => Barbarian._proficiencyBonus.set(profBonus)(c)
        case c: Berserker    => Berserker._proficiencyBonus.set(profBonus)(c)
        case c: TotemWarrior => TotemWarrior._proficiencyBonus.set(profBonus)(c)

        case c: Creature => c
      }
    }

  val creatureResistancesLens: Lens[Creature, List[DamageType]] =
    Lens[Creature, List[DamageType]](_.resistances) { res =>
      {
        case c: BattleMaster   => BattleMaster._resistances.set(res)(c)
        case c: EldritchKnight => EldritchKnight._resistances.set(res)(c)
        case c: Champion       => Champion._resistances.set(res)(c)
        case c: Fighter        => Fighter._resistances.set(res)(c)

        case c: Barbarian    => Barbarian._resistances.set(res)(c)
        case c: Berserker    => Berserker._resistances.set(res)(c)
        case c: TotemWarrior => TotemWarrior._resistances.set(res)(c)

        case c: Goblin   => Goblin._resistances.set(res)(c)
        case c: Werewolf => Werewolf._resistances.set(res)(c)
      }
    }

  val creatureImmunitiesLens: Lens[Creature, List[DamageType]] =
    Lens[Creature, List[DamageType]](_.immunities) { res =>
      {
        case c: BattleMaster   => BattleMaster._immunities.set(res)(c)
        case c: EldritchKnight => EldritchKnight._immunities.set(res)(c)
        case c: Champion       => Champion._immunities.set(res)(c)
        case c: Fighter        => Fighter._immunities.set(res)(c)

        case c: Barbarian    => Barbarian._immunities.set(res)(c)
        case c: Berserker    => Berserker._immunities.set(res)(c)
        case c: TotemWarrior => TotemWarrior._immunities.set(res)(c)

        case c: Goblin   => Goblin._immunities.set(res)(c)
        case c: Werewolf => Werewolf._immunities.set(res)(c)
      }
    }

  val creatureAbilitiesLens: Lens[Creature, List[CombatantAbility]] =
    Lens[Creature, List[CombatantAbility]](_.abilities) { res =>
      {
        case c: BattleMaster   => BattleMaster._abilities.set(res)(c)
        case c: EldritchKnight => EldritchKnight._abilities.set(res)(c)
        case c: Champion       => Champion._abilities.set(res)(c)
        case c: Fighter        => Fighter._abilities.set(res)(c)

        case c: Barbarian    => Barbarian._abilities.set(res)(c)
        case c: Berserker    => Berserker._abilities.set(res)(c)
        case c: TotemWarrior => TotemWarrior._abilities.set(res)(c)

        case c: Goblin   => Goblin._abilities.set(res)(c)
        case c: Werewolf => Werewolf._abilities.set(res)(c)
      }
    }

  val creatureAttackStatusLens: Lens[Creature, AttackStatus] =
    Lens[Creature, AttackStatus](_.attackStatus) { status =>
      {
        case c: BattleMaster   => BattleMaster._attackStatus.set(status)(c)
        case c: EldritchKnight => EldritchKnight._attackStatus.set(status)(c)
        case c: Champion       => Champion._attackStatus.set(status)(c)
        case c: Fighter        => Fighter._attackStatus.set(status)(c)

        case c: Barbarian    => Barbarian._attackStatus.set(status)(c)
        case c: Berserker    => Berserker._attackStatus.set(status)(c)
        case c: TotemWarrior => TotemWarrior._attackStatus.set(status)(c)

        case c: Goblin   => Goblin._attackStatus.set(status)(c)
        case c: Werewolf => Werewolf._attackStatus.set(status)(c)
      }
    }

  val creatureDefenseStatusLens: Lens[Creature, AttackStatus] =
    Lens[Creature, AttackStatus](_.defenseStatus) { status =>
      {
        case c: BattleMaster   => BattleMaster._defenseStatus.set(status)(c)
        case c: EldritchKnight => EldritchKnight._defenseStatus.set(status)(c)
        case c: Champion       => Champion._defenseStatus.set(status)(c)
        case c: Fighter        => Fighter._defenseStatus.set(status)(c)

        case c: Barbarian    => Barbarian._defenseStatus.set(status)(c)
        case c: Berserker    => Berserker._defenseStatus.set(status)(c)
        case c: TotemWarrior => TotemWarrior._defenseStatus.set(status)(c)

        case c: Goblin   => Goblin._defenseStatus.set(status)(c)
        case c: Werewolf => Werewolf._defenseStatus.set(status)(c)
      }
    }

  val creatureLevelOptional: Optional[Creature, Level] = Optional[Creature, Level] {
    case c: BattleMaster   => val lvl: Level = c.level; lvl.some
    case c: EldritchKnight => val lvl: Level = c.level; lvl.some
    case c: Champion       => val lvl: Level = c.level; lvl.some
    case c: Fighter        => val lvl: Level = c.level; lvl.some

    case c: Barbarian    => val lvl: Level = c.level; lvl.some
    case c: Berserker    => val lvl: Level = c.level; lvl.some
    case c: TotemWarrior => val lvl: Level = c.level; lvl.some

    case _ => none[Level]
  } { lvl =>
    {
      case c: BattleMaster   => BattleMaster._level.set(lvl)(c)
      case c: EldritchKnight => EldritchKnight._level.set(lvl)(c)
      case c: Champion       => Champion._level.set(lvl)(c)
      case c: Fighter        => Fighter._level.set(lvl)(c)

      case c: Barbarian    => Barbarian._level.set(lvl)(c)
      case c: Berserker    => Berserker._level.set(lvl)(c)
      case c: TotemWarrior => TotemWarrior._level.set(lvl)(c)

      case c: Creature => c
    }
  }
}
