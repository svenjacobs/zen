package com.svenjacobs.zen.common.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be empty`
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object DelayedDefaultTest : Spek(
    {
        describe("delayedDefault") {

            it("should produce default value after timeout") {
                val flow = flow {
                    delay(500)
                    emit("Hello world")
                }

                runBlockingTest {
                    val results = flow
                        .delayedDefault(200) { "Default" }
                        .toList()

                    advanceTimeBy(500)

                    results `should be equal to` listOf(
                        "Default",
                        "Hello world"
                    )
                }
            }

            it("should *not* produce default value if timeout was not reached") {
                val flow = flow {
                    delay(200)
                    emit("Hello world")
                }

                runBlockingTest {
                    val results = flow
                        .delayedDefault(500) { "Default" }
                        .toList()

                    advanceTimeBy(500)

                    results `should be equal to` listOf(
                        "Hello world"
                    )
                }
            }

            it("should *not* produce default value for empty flow") {
                runBlockingTest {
                    val results = emptyFlow<String>()
                        .delayedDefault(200) { "Default" }
                        .toList()

                    advanceTimeBy(500)

                    results.`should be empty`()
                }
            }
        }
    }
)
