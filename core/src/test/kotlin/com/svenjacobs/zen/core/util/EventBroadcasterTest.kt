package com.svenjacobs.zen.core.util

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.amshove.kluent.`should contain`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object EventBroadcasterTest : Spek(
    {
        describe("Event broadcaster") {

            context("asFlow") {

                it("should collect events as Flow") {
                    val broadcaster = EventBroadcaster()

                    val strings = ArrayList<String>()
                    val integers = ArrayList<Int>()
                    var job1: Job
                    var job2: Job

                    runBlocking {
                        with(TestCoroutineScope()) {
                            job1 = launch { broadcaster.asFlow<String>().toList(strings) }
                            job2 = launch { broadcaster.asFlow<Int>().toList(integers) }
                        }

                        broadcaster.broadcast("Hello")
                        broadcaster.broadcast("World")
                        broadcaster.broadcast(1337)
                        broadcaster.broadcast(12345)
                        broadcaster.broadcast(67890)

                        broadcaster.close()

                        job1.join()
                        job2.join()

                        strings `should contain` "Hello"
                        strings `should contain` "World"

                        integers `should contain` 1337
                        integers `should contain` 12345
                        integers `should contain` 67890
                    }
                }
            }
        }
    }
)
