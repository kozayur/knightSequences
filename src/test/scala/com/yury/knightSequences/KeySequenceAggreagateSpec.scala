package com.yury.knightSequences

import org.scalatest.{FlatSpec, Matchers}

class KeySequenceAggreagateSpec extends FlatSpec with Matchers {
  behavior of "KeySequenceAggreagate"

  it should "be empty only when all quantityByVowelNumber are zeros" in {
    val emptyAggregate = KeySequenceAggreagate(5, (0, 0), (0, 0, 0))
    assert(emptyAggregate.notEmpty == false)

    val notEmptyAggregate1 = KeySequenceAggreagate(5, (0, 0), (1, 0, 0))
    assert(notEmptyAggregate1.notEmpty == true)

    val notEmptyAggregate2 = KeySequenceAggreagate(5, (0, 0), (0, 1, 0))
    assert(notEmptyAggregate2.notEmpty == true)

    val notEmptyAggregate3 = KeySequenceAggreagate(5, (0, 0), (0, 0, 1))
    assert(notEmptyAggregate3.notEmpty == true)
  }
}
