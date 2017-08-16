package com.yury.knightSequences

/**
  * A single knight move
  *
  * @param start start key of the move
  * @param end end key of the move
  */
case class KnightMove(start: (Int, Int), end: (Int, Int)) {

  // These values are computed once during the move object construction, so they are not recalcualted when
  // queried multiple times.
  val isStartKeyAVowel = Keyboard.isKeyAVowel(start)
  val startKey = Keyboard(start)
  val endKey = Keyboard(end)

  // Number of vowels in the move
  val vowelWeight: Int = {
    val w1 = if (Keyboard.isKeyAVowel(start)) 1 else 0
    val w2 = if (Keyboard.isKeyAVowel(end)) 1 else 0
    w1 + w2
  }
}
