package com.svenjacobs.zen.common

import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.contracts.ExperimentalContracts

@OptIn(ExperimentalContracts::class)
object TupleUtilsTest : Spek(
    {
        describe("Tuple utils") {

            context("SafePair") {

                it("should return Pair for non-null values") {
                    val pair = SafePair("Hello", 1337)

                    pair?.first `should be equal to` "Hello"
                    pair?.second `should be equal to` 1337
                }

                it("should return null for null values") {
                    val pair = SafePair<String?, Int>(null, 1337)

                    pair `should be equal to` null
                }
            }

            context("SafeTriple") {

                it("should return Triple for non-null values") {
                    val triple = SafeTriple("Hello", 1337, 1.5F)

                    triple?.first `should be equal to` "Hello"
                    triple?.second `should be equal to` 1337
                    triple?.third `should be equal to` 1.5F
                }

                it("should return null for null values") {
                    val triple = SafeTriple<String?, Int, Float>(null, 1337, 1.5F)

                    triple `should be equal to` null
                }
            }
        }
    }
)
