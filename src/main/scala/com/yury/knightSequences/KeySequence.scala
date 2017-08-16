package com.yury.knightSequences

/**
  * This class respresent a signle key sequence
  *
  * @param legnth key sequence length
  * @param startKey start key of the sequence
  * @param endKey end key of the sequence
  * @param vowelWeight number of vowels in the sequence
  * @param value literal representation of the sequence
  */
case class KeySequence(legnth: Int, startKey: (Int, Int), endKey: (Int, Int), vowelWeight: Int, value: String) {
}
