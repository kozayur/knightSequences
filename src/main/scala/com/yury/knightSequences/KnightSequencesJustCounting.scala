package com.yury.knightSequences

import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

import scala.collection.mutable

// Start the application
object KnightSequencesJustCounting {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(Array(classOf[KnightSequencesJustCounting]).asInstanceOf[Array[AnyRef]], args)
  }
}

@SpringBootApplication
@PropertySource(Array("application.properties"))
class KnightSequencesJustCounting extends CommandLineRunner {
  override def run(args: String*): Unit = {
    val startTime = System.currentTimeMillis

    // Default sequence length. Overriden if a number parameter is supplied on the command line
    var keySequenceLength = 10
    if (args.length > 0)
      keySequenceLength = args(0).toInt

    if (keySequenceLength < 2)
      throw new RuntimeException("The key sequence length cannot be less than 2")

    val moves = computeKnightMoves

    val movesByEndKey = moves.groupBy(knightMove => knightMove.end)

    val twoKeySequences: Iterable[KeySequenceAggreagate] = computeTwoKeySequences(moves)

    val multiKeySequences = extendsSequences(movesByEndKey, twoKeySequences, keySequenceLength)
    var numberOfSequences: BigInt = 0L
    for (seq <- multiKeySequences.toList) {
      numberOfSequences += seq.quantityByVowelNumber._1
      numberOfSequences += seq.quantityByVowelNumber._2
      numberOfSequences += seq.quantityByVowelNumber._3
    }

    println(numberOfSequences)

    println(s"sequences of length ${keySequenceLength} found")

    println(s"Execution took ${System.currentTimeMillis() - startTime}ms")
  }

  // Use KnightMoves and compute KeySequenceAggregates that represent all 2 key sequences
  def computeTwoKeySequences(moves: List[KnightMove]): Iterable[KeySequenceAggreagate] = {
    val movesByStartKey = moves.groupBy(knightMove => knightMove.start)

    movesByStartKey.map(knightMoveGroup => {
      val knightMovesByVowelWeight = knightMoveGroup._2.groupBy(knightMove => knightMove.vowelWeight)
      KeySequenceAggreagate(2, knightMoveGroup._1,
        (
          knightMovesByVowelWeight.getOrElse(0, Nil).length,
          knightMovesByVowelWeight.getOrElse(1, Nil).length,
          knightMovesByVowelWeight.getOrElse(2, Nil).length
          )
      )
    })
  }

  // Compute all knight moves on the keyboard
  def computeKnightMoves(): List[KnightMove] = {
    val keyboardHeight = Keyboard.heigh
    val keyboardWidth = Keyboard.width

    val knightMoves: mutable.MutableList[KnightMove] = mutable.MutableList()

    for( y <- 0 until keyboardHeight) {
      for (x <- 0 until keyboardWidth) {
        knightMoves ++= computeKnightMoves(x, y)
      }
    }

    knightMoves.toList
  }

  // Compute all knight moves starting at the key (x,y)
  def computeKnightMoves(x: Int, y: Int): List[KnightMove] = {
    val knightMoves: mutable.MutableList[KnightMove] = mutable.MutableList()

    // Knight can have up to 8 moves from a single point
    if (Keyboard.hasKeyAt(x,y)) {
      if (Keyboard.hasKeyAt(x - 1, y - 2))
        knightMoves += KnightMove((x, y), (x - 1, y - 2))

      if (Keyboard.hasKeyAt(x - 2, y - 1))
        knightMoves += KnightMove((x, y), (x - 2, y - 1))

      if (Keyboard.hasKeyAt(x + 1, y - 2))
        knightMoves += KnightMove((x, y), (x + 1, y - 2))

      if (Keyboard.hasKeyAt(x + 2, y - 1))
        knightMoves += KnightMove((x, y), (x + 2, y - 1))

      if (Keyboard.hasKeyAt(x - 1, y + 2))
        knightMoves += KnightMove((x, y), (x - 1, y + 2))

      if (Keyboard.hasKeyAt(x - 2, y + 1))
        knightMoves += KnightMove((x, y), (x - 2, y + 1))

      if (Keyboard.hasKeyAt(x + 1, y + 2))
        knightMoves += KnightMove((x, y), (x + 1, y + 2))

      if (Keyboard.hasKeyAt(x + 2, y + 1))
        knightMoves += KnightMove((x, y), (x + 2, y + 1))
    }
    knightMoves.toList
  }

  // Given all knight moves and two key sequences compute key sequences of a specific length
  def extendsSequences(
                        knightMovesByEndKey: Map[(Int, Int), Iterable[KnightMove]],
                        twoKeySequences: Iterable[KeySequenceAggreagate],
                        keySequenceLength: Int): Iterable[KeySequenceAggreagate] = {
    if (keySequenceLength > 2) {
      val sequences: Iterable[KeySequenceAggreagate] = extendsSequences(knightMovesByEndKey, twoKeySequences, keySequenceLength - 1)

      sequences.map(keySequence => {
        knightMovesByEndKey(keySequence.startKey)
          .map(move => {
            val quantityWithZeroVowel: BigInt = if (move.isStartKeyAVowel) 0 else keySequence.quantityByVowelNumber._1
            val quantityWithOneVowel: BigInt = if (move.isStartKeyAVowel) keySequence.quantityByVowelNumber._1 else keySequence.quantityByVowelNumber._2
            val quantityWithTwoVowel: BigInt = if (move.isStartKeyAVowel) keySequence.quantityByVowelNumber._2 else keySequence.quantityByVowelNumber._3
            val quantityWithThreeVowel: BigInt = if (move.isStartKeyAVowel) keySequence.quantityByVowelNumber._3 else 0
            KeySequenceAggreagate(keySequence.length+1, move.start, (quantityWithZeroVowel, quantityWithOneVowel, quantityWithTwoVowel))
          })
      }).flatten
      .filter(keySequenceAggregate => keySequenceAggregate.notEmpty)
      .groupBy(keySequence => keySequence.startKey)
      .map(keySequenceAggreagatesFromSameKey => {
        val aggregates = keySequenceAggreagatesFromSameKey._2
        var withZeroVowels, withOneVowel, withTwoVowels: BigInt = 0
        for (aggregate <-  aggregates) {
          withZeroVowels += aggregate.quantityByVowelNumber._1
          withOneVowel += aggregate.quantityByVowelNumber._2
          withTwoVowels += aggregate.quantityByVowelNumber._3
        }
        KeySequenceAggreagate(
          keySequenceLength,
          keySequenceAggreagatesFromSameKey._1,
          (withZeroVowels, withOneVowel, withTwoVowels)
        )
      })
    } else {
      twoKeySequences
    }
  }
}
