package com.yury.knightSequences

/**
  * This class represents all sequences of [[#length]] starting at the same key [[#startKey]]
  *
  * @param length sequence length
  * @param startKey start key of sequences
  * @param quantityByVowelNumber represents how many KeySequences have vowel number 0, 1 or 2
  */
case class KeySequenceAggreagate(length: Int, startKey: (Int, Int), quantityByVowelNumber: (BigInt, BigInt, BigInt)) {

  // If there are not sequences with at most 2 vowels, we consider this aggregate to be empty
  def notEmpty = (quantityByVowelNumber != (0,0,0))
}
