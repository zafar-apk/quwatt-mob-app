package stations.all.data.remote

import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import stations.all.data.remote.mapper.StationsMapper
import stations.all.domain.models.Station
import stations.all.domain.models.StationsFilter

class AllStationsHttpClientClient(
    private val client: HttpClient,
    private val mapper: StationsMapper
) : AllStationsClient {

    override suspend fun getStations(query: StationsFilter?): Resource<List<Station>> = networkCall(
        map = mapper::createStations,
        call = {
            client.post("${AppConstants.BASE_URL}/stations") {
                setBody(query?.let(mapper::createStationsFilterBody))
                contentType(ContentType.Application.Json)
            }
        }
    )
}