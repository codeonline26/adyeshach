package ink.ptms.adyeshach.internal

import ink.ptms.adyeshach.common.api.AdyeshachNetworkAPI
import ink.ptms.adyeshach.internal.network.NetworkAshcon
import ink.ptms.adyeshach.internal.network.NetworkMineskin

/**
 * Adyeshach
 * ink.ptms.adyeshach.internal.DefaultAdyeshachNetworkAPI
 *
 * @author 坏黑
 * @since 2022/6/18 23:44
 */
class DefaultAdyeshachNetworkAPI : AdyeshachNetworkAPI {

    val networkAshcon = NetworkAshcon()
    val networkSkin = NetworkMineskin()

    override fun getAshcon(): AdyeshachNetworkAPI.Ashcon {
        return networkAshcon
    }

    override fun getSkin(): AdyeshachNetworkAPI.Skin {
        return networkSkin
    }
}