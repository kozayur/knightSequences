package com.yury.knightSequences

/**
  * This object represents a key board. [[\u0000]] represents not a key position on the keyboard.
  */
private object Keyboard {
  val keys: Array[Array[Char]] = Array(
    Array('A',      'B', 'C', 'D', 'E'),
    Array('F',      'G', 'H', 'I', 'J'),
    Array('K',      'L', 'M', 'N', 'O'),
    Array('\u0000', '1', '2', '3', '\u0000')
  )

  // All keys that are vowels
  val vowels = Set('A', 'E', 'I', 'O')

  // return a value of the key by its coordinates
  def apply(key: (Int, Int)): Char = {
    keys(key._2)(key._1)
  }

  def heigh = keys.length

  def width = keys.head.length

  def hasKeyAt(x: Int, y: Int) = (x >= 0 && x < keys.head.length && y >= 0 && y < keys.length && keys(y)(x) != '\u0000')

  def isKeyAVowel(key: (Int, Int)): Boolean = {
    vowels.contains(keys(key._2)(key._1))
  }
}
