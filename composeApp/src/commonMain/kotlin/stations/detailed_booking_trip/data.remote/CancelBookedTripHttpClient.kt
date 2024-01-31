package tj.ham_safar.app.trips.detailed_booking_trip.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource

class CancelBookedTripHttpClient(
    private val client: HttpClient,
) : CancelBookedTripClient {

    override suspend fun cancelBookedTrip(id: Int): Resource<Unit> = networkCall(
        map = { _: HttpResponse -> Unit },
        call = {
            client.post {
                url("${AppConstants.BASE_URL}/trip/$id/cancel")
                contentType(ContentType.Application.Json)
            }
        }
    )
}