package stations.all.domain.models

data class StationDetails(
    val station: Station,
    val connectors: List<Connector>
)