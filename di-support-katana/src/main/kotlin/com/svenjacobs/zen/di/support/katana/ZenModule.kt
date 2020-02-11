@file:Suppress("FunctionName")

package com.svenjacobs.zen.di.support.katana

import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.master.NopMiddleware
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.StateAccessor
import com.svenjacobs.zen.core.state.StateMutator
import com.svenjacobs.zen.core.state.Transformer
import com.svenjacobs.zen.core.view.ZenView
import com.svenjacobs.zen.di.support.katana.Names.*
import kotlinx.coroutines.CoroutineScope
import org.rewedigital.katana.Module
import org.rewedigital.katana.dsl.ProviderDsl
import org.rewedigital.katana.dsl.alias
import org.rewedigital.katana.dsl.factory
import org.rewedigital.katana.dsl.get
import kotlin.coroutines.CoroutineContext

/**
 * Provides bindings required for injection of a [ZenMaster].
 *
 * If [stateMutator], [viewLifecycleCoroutineScopeProvider], [transformationCoroutineContext] and [uiCoroutineContext]
 * are not bound in another module to default names (see [Names]), bindings must be provided here.
 */
inline fun <reified V : ZenView, A : Action, S : State> ZenModule(
    noinline view: ProviderDsl.() -> V,
    noinline transformation: ProviderDsl.() -> Transformer.Transformation<A, S>,
    noinline contract: ProviderDsl.() -> ZenMaster.Contract<V, A, S>,
    noinline stateMutator: ProviderDsl.() -> StateMutator<S> = { get(name = ZEN_STATE_MUTATOR) },
    crossinline viewLifecycleCoroutineScopeProvider: ProviderDsl.() -> () -> CoroutineScope = { get(name = ZEN_COROUTINE_SCOPE_PROVIDER_VIEW_LIFECYCLE) },
    noinline transformationCoroutineContext: ProviderDsl.() -> CoroutineContext = { get(name = ZEN_COROUTINE_CONTEXT_TRANSFORMATION) },
    crossinline uiCoroutineContext: ProviderDsl.() -> CoroutineContext = { get(name = ZEN_COROUTINE_CONTEXT_UI) },
    crossinline middleware: ProviderDsl.() -> ZenMaster.Middleware<A, S> = { NopMiddleware() }
) = Module {

    factory(body = view)

    transformation(transformation)

    transformer<A, S>(transformationCoroutineContext)

    contract(contract)

    zenMaster<V, A, S>(viewLifecycleCoroutineScopeProvider, uiCoroutineContext, middleware)

    factory(body = stateMutator)

    alias<StateAccessor<S>, StateMutator<S>>()
}
