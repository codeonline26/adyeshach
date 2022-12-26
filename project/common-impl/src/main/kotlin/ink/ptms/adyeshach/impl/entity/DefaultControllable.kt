package ink.ptms.adyeshach.impl.entity

import ink.ptms.adyeshach.core.entity.Controllable
import ink.ptms.adyeshach.core.entity.StandardTags
import ink.ptms.adyeshach.core.entity.TagContainer
import ink.ptms.adyeshach.core.entity.controller.Controller
import ink.ptms.adyeshach.core.event.AdyeshachControllerAddEvent
import ink.ptms.adyeshach.core.event.AdyeshachControllerRemoveEvent

/**
 * Adyeshach
 * ink.ptms.adyeshach.impl.entity.DefaultControllable
 *
 * @author 坏黑
 * @since 2022/6/19 21:57
 */
@Suppress("UNCHECKED_CAST")
interface DefaultControllable : Controllable {

    override var isFreeze: Boolean
        set(value) {
            this as TagContainer
            setTag(StandardTags.IS_FROZEN, if (value) "true" else null)
        }
        get() {
            this as TagContainer
            return hasTag(StandardTags.IS_FROZEN)
        }

    override fun getController(): List<Controller> {
        this as DefaultEntityInstance
        return controller.toList()
    }

    override fun <T : Controller> getController(controller: String): T? {
        this as DefaultEntityInstance
        return this.controller.firstOrNull { it.id() == controller } as? T
    }

    override fun <T : Controller> getController(controller: Class<T>): T? {
        this as DefaultEntityInstance
        return this.controller.firstOrNull { it.javaClass == controller } as? T
    }

    override fun registerController(controller: Controller): Boolean {
        this as DefaultEntityInstance
        // 添加事件
        if (AdyeshachControllerAddEvent(this, controller).call()) {
            // 移除相同的控制器
            this.controller.removeIf { it.id() == controller.id() }
            // 注册控制器
            this.controller.add(controller)
            // 排序
            this.controller.sort()
            return true
        }
        return false
    }

    override fun unregisterController(controller: String): Boolean {
        this as DefaultEntityInstance
        return unregister(this.controller.firstOrNull { it.id() == controller } ?: return true)
    }

    override fun unregisterController(controller: Controller): Boolean {
        this as DefaultEntityInstance
        return unregister(this.controller.firstOrNull { it == controller } ?: return true)
    }

    override fun <T : Controller> unregisterController(controller: Class<T>): Boolean {
        this as DefaultEntityInstance
        return unregister(this.controller.firstOrNull { it.javaClass == controller } ?: return true)
    }

    override fun resetController() {
        this as DefaultEntityInstance
        this.controller.forEach { unregisterController(it.javaClass) }
    }

    private fun unregister(controller: Controller): Boolean {
        this as DefaultEntityInstance
        // 移除事件
        if (AdyeshachControllerRemoveEvent(this, controller).call()) {
            this.controller.remove(controller)
            return true
        }
        return false
    }
}