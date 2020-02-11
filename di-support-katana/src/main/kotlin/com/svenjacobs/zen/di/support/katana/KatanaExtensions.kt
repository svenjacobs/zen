package com.svenjacobs.zen.di.support.katana

import com.svenjacobs.zen.core.action.Action
import com.svenjacobs.zen.core.master.NopMiddleware
import com.svenjacobs.zen.core.master.ZenMaster
import com.svenjacobs.zen.core.master.ZenMasterImpl
import com.svenjacobs.zen.core.state.State
import com.svenjacobs.zen.core.state.Transformer
import com.svenjacobs.zen.core.view.ZenView
import com.svenjacobs.zen.di.support.katana.Names.*
import kotlinx.coroutines.CoroutineScope
import org.rewedigital.katana.ModuleBindingContext
import org.rewedigital.katana.dsl.ProviderDsl
import org.rewedigital.katana.dsl.factory
import org.rewedigital.katana.dsl.get
import kotlin.coroutines.CoroutineContext

/**
 * Alias for binding a [Transformer.Transformation].
 *
 * @see ZenModule
 */
fun <A : Action, S : State> ModuleBindingContext.transformation(
    provider: ProviderDsl.() -> Transformer.Transformation<A, S>
) =
    factory(body = provider)

/**
 * Alias for binding a [Transformer].
 *
 * @see ZenModule
 */
fun <A : Action, S : State> ModuleBindingContext.transformer(
    transformationCoroutineContext: ProviderDsl.() -> CoroutineContext = { get(name = ZEN_COROUTINE_CONTEXT_TRANSFORMATION) }
) =
    factory<Transformer<A, S>> {
        Transformer(
            get(),
            get(),
            transformationCoroutineContext()
        )
    }

/**
 * Alias for binding a [ZenMaster.Contract].
 *
 * @see ZenModule
 */
fun <V : ZenView, A : Action, S : State> ModuleBindingContext.contract(
    provider: ProviderDsl.() -> ZenMaster.Contract<V, A, S>
) =
    factory(body = provider)

/**
 * Alias for binding a [ZenMaster].
 *
 * @see ZenModule
 */
inline fun <reified V : ZenView, A : Action, S : State> ModuleBindingContext.zenMaster(
    crossinline viewLifecycleCoroutineScopeProvider: ProviderDsl.() -> () -> CoroutineScope = { get(name = ZEN_COROUTINE_SCOPE_PROVIDER_VIEW_LIFECYCLE) },
    crossinline uiCoroutineContext: ProviderDsl.() -> CoroutineContext = { get(name = ZEN_COROUTINE_CONTEXT_UI) },
    crossinline middleware: ProviderDsl.() -> ZenMaster.Middleware<A, S> = { NopMiddleware() }
) =
    factory<ZenMaster> {
        ZenMasterImpl<V, A, S>(
            get(),
            viewLifecycleCoroutineScopeProvider(),
            get(),
            get(),
            get(),
            uiCoroutineContext(),
            middleware()
        )
    }

