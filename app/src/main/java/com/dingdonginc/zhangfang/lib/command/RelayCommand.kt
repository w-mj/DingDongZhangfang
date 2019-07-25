package com.dingdonginc.zhangfang.lib.command

import rx.functions.Action0
import rx.functions.Action1
import rx.functions.Func0

class RelayCommand<T> {
    private val defaultAction0 = Action0 { TODO("not implemented") }
    private val defaultAction1 = Action1<T> { TODO("not implemented") }
    private val execute0: Action0
    private val execute1: Action1<T>

    private var canExecute0: Func0<Boolean> ?= null

    constructor(execute: Action0) {
        this.execute0 = execute
        this.execute1 = defaultAction1
    }

    constructor(execute: Action1<T>) {
        this.execute0 = defaultAction0
        this.execute1 = execute
    }

    /**
     * @param execute callback for event
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    constructor(execute: Action0, canExecute0: Func0<Boolean>) {
        this.execute0 = execute
        this.execute1 = defaultAction1
        this.canExecute0 = canExecute0
    }

    /**
     * @param execute callback for event,this callback need a params
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    constructor(execute: Action1<T>, canExecute0: Func0<Boolean>) {
        this.execute0 = defaultAction0
        this.execute1 = execute
        this.canExecute0 = canExecute0
    }

    fun execute() {
        if (execute0 != null && canExecute0()) {
            execute0.call()
        }
    }

    private fun canExecute0(): Boolean {
        if (canExecute0 == null) {
            return true
        }
        return canExecute0!!.call()
    }

    fun execute(parameter: T) {
        if (execute1 != null && canExecute0()) {
            execute1.call(parameter)
        }
    }
}

