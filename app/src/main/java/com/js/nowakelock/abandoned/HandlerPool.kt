package com.js.nowakelock.abandoned

class HandlerPool {
    companion object {
        var handlerHMp = HashMap<String, Handler>()

        fun getMethod(method: String): Handler? {
            return handlerHMp[method]
        }

        fun register(handler: Handler) {
            handlerHMp[handler.getMethodName()] = handler
        }

        fun registers(handlers: List<Handler>) {
            handlers.forEach {
                register(it)
            }
        }

        /*start BackService*/
//        fun startBackService(context: Context) {
//            val service = Intent(context, Service::class.java)
//            context.startService(service)
//        }
    }
}