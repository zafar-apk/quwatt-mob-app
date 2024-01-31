package tj.ham_safar.app.trips.detailed_trip.data.remote

import core.domain.util.Resource
import io.ktor.client.HttpClient
import stations.all.data.remote.mapper.TripsMapper
import stations.all.domain.models.Station

class DetailedTripHttpClient(
    private val client: HttpClient,
    private val tripsMapper: TripsMapper
) : DetailedTripClient {

    override suspend fun getTripById(id: Int): Resource<Station> =
        Resource.Error(Exception("Not implemented yet"))
//        networkCall(
//        map = tripsMapper::createTrip,
//        call = {
//            client.get {
//                url("${AppConstants.BASE_URL}/trip/$id")
//                contentType(ContentType.Application.Json)
//            }
//        }
//    )
}