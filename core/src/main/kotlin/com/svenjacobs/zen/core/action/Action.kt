package com.svenjacobs.zen.core.action

interface Action {
    /**
     * Id that must be unique for *every* Action of application.
     */
    val id: Any
}
