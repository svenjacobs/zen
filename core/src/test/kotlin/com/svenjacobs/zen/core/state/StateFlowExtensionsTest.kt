package com.svenjacobs.zen.core.state

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object StateFlowExtensionsTest : Spek(
    {
        data class TestState(
            val text: String?,
            val number: Int
        ) : State

        val flow = flowOf(
            TestState(
                text = "Hello world",
                number = 1
            ),
            TestState(
                text = "Hello world 2",
                number = 2
            ),
            TestState(
                text = null,
                number = 2
            ),
            TestState(
                text = "Hello world 2",
                number = 2
            ),
            TestState(
                text = "Hello world 3",
                number = 2
            )
        )

        describe("State Flow extensions") {

            context("sideEffect") {

                it("should select non-null values") {
                    runBlockingTest {
                        var textInvocations = 0
                        var numberInvocations = 0

                        merge(
                            flow.sideEffect(
                                select = { text },
                                onEach = { textInvocations++ }
                            ),
                            flow.sideEffect(
                                select = { number },
                                onEach = { numberInvocations++ }
                            )
                        ).collect()

                        textInvocations `should be equal to` 4
                        numberInvocations `should be equal to` 2
                    }
                }
            }

            context("distinctSideEffect") {

                it("should select distinct non-null values") {
                    runBlockingTest {
                        var textInvocations = 0
                        var numberInvocations = 0

                        merge(
                            flow.distinctSideEffect(
                                select = { text },
                                onEach = { textInvocations++ }
                            ),
                            flow.distinctSideEffect(
                                select = { number },
                                onEach = { numberInvocations++ }
                            )
                        ).collect()

                        textInvocations `should be equal to` 3
                        numberInvocations `should be equal to` 2
                    }
                }
            }

            context("nullableSideEffect") {

                it("should select null values") {
                    runBlockingTest {
                        var textInvocations = 0

                        flow.nullableSideEffect(
                            select = { text },
                            onEach = { textInvocations++ }
                        ).collect()

                        textInvocations `should be equal to` 5
                    }
                }
            }
        }
    }
)
