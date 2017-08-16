package com.yury.knightSequences

import org.scalatest.{FlatSpec, Matchers}

class KeyboardSpec extends FlatSpec with Matchers {
  behavior of "Keyboard"

  it should "return correct key values by their position" in {
    Keyboard(0, 0) shouldBe 'A'
    Keyboard(0, 1) shouldBe 'F'
    Keyboard(1, 0) shouldBe 'B'
  }

  it should "report correct size of the keyboard" in {
    Keyboard.heigh shouldBe 4
    Keyboard.width shouldBe 5
  }

  it should "tell if the key exist by the coordinates" in {
    Keyboard.hasKeyAt(0, 0) shouldBe true
    Keyboard.hasKeyAt(0, 4) shouldBe false
    Keyboard.hasKeyAt(4, 4) shouldBe false
    Keyboard.hasKeyAt(-1, 0) shouldBe false
    Keyboard.hasKeyAt(0, -1) shouldBe false
    Keyboard.hasKeyAt(0, 6) shouldBe false
    Keyboard.hasKeyAt(5, 0) shouldBe false
  }

  it should "tell correcly if the key value is a vowel from the key coordinates" in {
    assert(Keyboard.isKeyAVowel(0, 0) == true)  // 'A'
    assert(Keyboard.isKeyAVowel(0, 1) == false) // 'F'
    assert(Keyboard.isKeyAVowel(1, 3) == false) // '1'
  }
}
