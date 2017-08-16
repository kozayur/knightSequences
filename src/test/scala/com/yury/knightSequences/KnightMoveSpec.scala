package com.yury.knightSequences

import org.scalatest.{FlatSpec, Matchers}

class KnightMoveSpec extends FlatSpec with Matchers {

  behavior of "KnightMove"

  it should "match response from Keyboard when asked if the start key is a vowel" in {
    val moveFromA = KnightMove((0, 0), (2, 1))   // A -> H
    assert(moveFromA.isStartKeyAVowel == true)

    val moveFromF = KnightMove((0, 1), (2, 2))   // F -> M
    assert(moveFromF.isStartKeyAVowel == false)
  }

  it should "return correct value of the keys" in {
    val move = KnightMove((0, 0), (2, 1))   // A -> H

    move.startKey shouldBe 'A'
    move.endKey shouldBe 'H'
  }

  it should "calculate number of vowels in the move" in {
    val moveFromAtoH = KnightMove((0, 0), (2, 1))
    moveFromAtoH.vowelWeight shouldBe 1

    val moveFromFtoM = KnightMove((0, 1), (2, 2))
    moveFromFtoM.vowelWeight shouldBe 0

    // I know this is not a knight move (buy the rules), but it can test vowels on both ends
    val moveFromAtoI = KnightMove((0, 0), (3, 1))
    moveFromAtoI.vowelWeight shouldBe 2
  }
}
