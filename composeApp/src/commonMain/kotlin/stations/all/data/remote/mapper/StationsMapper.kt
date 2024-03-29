package stations.all.data.remote.mapper

import core.data.remote.LocationDTO
import core.data.remote.cities.model.CityDTO
import core.domain.cities.model.City
import stations.all.data.remote.model.ConnectorDTO
import stations.all.data.remote.model.ConnectorTypeDTO
import stations.all.data.remote.model.StationDTO
import stations.all.data.remote.model.StationDetailsDTO
import stations.all.data.remote.model.StationsFilterBody
import stations.all.domain.models.Connector
import stations.all.domain.models.ConnectorType
import stations.all.domain.models.Location
import stations.all.domain.models.Station
import stations.all.domain.models.StationDetails
import stations.all.domain.models.StationsFilter

class StationsMapper {

    fun createStationDetails(dto: StationDetailsDTO): StationDetails = StationDetails(
        station = createStation(dto.stationDTO),
        connectors = dto.connectors.map(::createConnector)
    )

    private fun createConnector(dto: ConnectorDTO): Connector {
        return Connector(
            id = dto.id,
            type = createConnectorType(dto.type),
            power = dto.maxPower,
            price = dto.rate,
            isAvailable = dto.isAvailable
        )
    }

    private fun createConnectorType(dto: ConnectorTypeDTO): ConnectorType {
        return ConnectorType(
            id = dto.id,
            name = dto.name,
            image = dto.image
        )
    }

    fun createStations(stationDTOS: List<StationDTO>): List<Station> =
        stationDTOS.map(::createStation)

    fun createStation(dto: StationDTO): Station {
        return Station(
            id = dto.id,
            location = createLocation(dto.location),
            images = dto.images,
            workingHours = dto.workingHours,
            workingTimeOfDay = dto.workingTimeOfDay,
            isFavorite = dto.isFavorite,
            rateFrom = dto.rateFrom,
            rateTo = dto.rateTo,
            powerFrom = dto.powerFrom,
            powerTo = dto.powerTo,
            chargersCount = dto.chargersCount,
            isAvailable = dto.isAvailable
        )
    }

    private fun createLocation(dto: LocationDTO): Location = Location(
        latitude = dto.latitude,
        longitude = dto.longitude,
        city = createCity(dto.city),
        address = dto.address
    )

    private fun createCity(city: CityDTO): City {
        return City(
            id = city.id,
            name = city.name
        )
    }

    fun createStationsFilterBody(query: StationsFilter): StationsFilterBody = StationsFilterBody(
        autoType = query.autoType,
        autoModel = query.autoModel,
        fromPriceTrip = query.fromPriceTrip,
        toPriceTrip = query.toPriceTrip,
        fromCityId = query.fromCity?.id,
        toCityId = query.toCity?.id,
        tripDate = query.tripDate,
        tripTime = query.tripTime,
        tripRating = query.tripRating
    )
}