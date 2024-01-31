package stations.core.create_trip.domain.errors

sealed class TripCreationError : Exception() {
    class LocationMissingError : TripCreationError()
    class DateTimeMissingError : TripCreationError()
    class PricingMissingError : TripCreationError()
}