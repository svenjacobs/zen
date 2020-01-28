package com.svenjacobs.zen.common.coroutines.flow

import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object FlowUtilsTest : Spek({

    describe("Flow utils") {

        context("boxInFlow") {

            it("should box non-Flow value into Flow") {
                runBlockingTest {
                    val flow = boxInFlow<String>("Hello world")
                    flow.first() `should be equal to` "Hello world"
                }
            }

            it("should box null value into empty Flow if nullToEmpty = true") {
                runBlockingTest {
                    val flow = boxInFlow<String>(null, nullToEmpty = true)
                    flow.count() `should be equal to` 0
                }
            }

            it("should box null value into null Flow if nullToEmpty = false") {
                runBlockingTest {
                    val flow = boxInFlow<String>(null, nullToEmpty = false)
                    flow.first() `should be equal to` null
                }
            }

            it("should *not* box Flow value") {
                val originalFlow = flowOf("Hello world")
                val flow = boxInFlow<String>(originalFlow)

                (flow === originalFlow) `should be equal to` true
            }
        }
    }
})
