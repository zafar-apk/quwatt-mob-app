package passengers.all.data.mapper

import auth.enter_code.data.remote.model.UserDTO
import auth.enter_code.data.remote.model.toUser
import core.data.remote.cities.mapper.toCity
import passengers.all.data.model.PassengerDTO
import passengers.all.data.model.PassengerFilterBody
import passengers.all.domain.Passenger
import passengers.all.domain.PassengerFilter

fun PassengerDTO.toPassenger(): Passenger = Passenger(
    id = id,
    cityFrom = cityFrom.toCity(),
    cityTo = cityTo.toCity(),
    addressFrom = addressFrom,
    addressTo = addressTo,
    count = count,
    priceFrom = priceFrom,
    priceTo = priceTo,
    user = user.toUser(),
    date = date,
    time = time,
    rating = rating,
    driverId = driverId,
    requests = requests.map(UserDTO::toUser)
)

fun PassengerFilter.toBody() = PassengerFilterBody(
    fromPriceTrip = fromPriceTrip,
    toPriceTrip = toPriceTrip,
    fromCityId = fromCity?.id,
    toCityId = toCity?.id,
    tripDate = tripDate,
    tripTime = tripTime,
    tripRating = tripRating
)