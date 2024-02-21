package stations.all.domain.models

data class Connector(
    val id: Int,
    val type: ConnectorType,
    val power: Int,
    val price: Float,
    val isAvailable: Boolean
)