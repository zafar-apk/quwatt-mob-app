package stations.booking.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource

class BookingTripHttpClient(
    private val client: HttpClient
) : BookingTripClient {

    override suspend fun book(tripId: Int, seatIds: List<Int>): Resource<Unit> = networkCall(
        map = { _: HttpResponse -> Unit },
        call = {
            client.post {
                url("${AppConstants.BASE_URL}/trip/${tripId}/book")
                contentType(ContentType.Application.Json)
                setBody(seatIds)
            }
        }
    )
}