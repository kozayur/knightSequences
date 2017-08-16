package com.yury.knightSequences

import org.scalatest.{FlatSpec, Matchers}

class KnightSequenceJustCountingSpec extends FlatSpec with Matchers {

  behavior of "KnightSequenceJustCounting"

  val knightSequences = new KnightSequencesJustCounting

  it should "computeKnightMoves for a key specified by the coordinates" in {
    val movesFromA = knightSequences.computeKnightMoves(0, 0)

    movesFromA.length shouldBe 2
    assert(movesFromA(0).endKey == 'H' || movesFromA(1).endKey == 'H')
    assert(movesFromA(0).endKey == 'L' || movesFromA(1).endKey == 'L')

    val movesFromH = knightSequences.computeKnightMoves(2, 1)
    movesFromH.length shouldBe 6

    val movesFrom3 = knightSequences.computeKnightMoves(3, 3)
    movesFrom3.length shouldBe 3
  }

  it should "compute all knight moves on the keyboard" in {
    val allMoves = knightSequences.computeKnightMoves()

    allMoves.length shouldBe 60     // I calculated this number manually

    // Moves should start from any key on the keyboard. There are 18 keys
    allMoves.groupBy(move => move.start).toList.length shouldBe 18
  }

  it should "compute two key sequence aggregates based on Khight Moves" in {

    val allMoves = knightSequences.computeKnightMoves()
    val twoKeySequences = knightSequences.computeTwoKeySequences(allMoves)

    // Each two key sequence aggregate corresponds to a key where it starts, so the same number as keys on keyboard
    // Of course, I assume that you can do a knight move from every key (given the keyboard layout)
    twoKeySequences.toList.length shouldBe 18
    twoKeySequences.head.length shouldBe 2

    var numberOfSequences: BigInt = 0L
    for (seq <- twoKeySequences) {
      numberOfSequences += seq.quantityByVowelNumber._1
      numberOfSequences += seq.quantityByVowelNumber._2
      numberOfSequences += seq.quantityByVowelNumber._3
    }

    // Each sequence corresponds to a knight move, so the same number
    numberOfSequences shouldBe 60
  }

  it should "extend two key sequences by prepending Knight Moves" in {
    val allMoves = knightSequences.computeKnightMoves()
    val movesByEndKey = allMoves.groupBy(move => move.end)
    val twoKeySequences = knightSequences.computeTwoKeySequences(allMoves)

    knightSequences.extendsSequences(movesByEndKey, twoKeySequences, 2).toList shouldBe twoKeySequences

    val threeKeySequences = knightSequences.extendsSequences(movesByEndKey, twoKeySequences, 3).toList

    // Each two key sequence aggregate corresponds to a key where it starts, so the same number as keys on keyboard
    // Of course, I assume that you can do a knight move from every key (given the keyboard layout)
    threeKeySequences.length shouldBe 18
    threeKeySequences.head.length shouldBe 3

    var numberOfSequences: BigInt = 0L
    for (seq <- threeKeySequences) {
      numberOfSequences += seq.quantityByVowelNumber._1
      numberOfSequences += seq.quantityByVowelNumber._2
      numberOfSequences += seq.quantityByVowelNumber._3
    }

    // This number is found in one of the execution. I keep it as a reference for regression tests.
    numberOfSequences shouldBe 214
  }

}
