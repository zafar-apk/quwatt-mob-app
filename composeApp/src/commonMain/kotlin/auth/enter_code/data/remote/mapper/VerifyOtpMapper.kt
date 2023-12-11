package auth.enter_code.data.remote.mapper

import auth.enter_code.data.remote.model.TransportDTO
import auth.enter_code.data.remote.model.UserDTO
import auth.enter_code.data.remote.model.VerifyOtpResultDTO
import auth.enter_code.domain.VerifyOtpResult
import profile.domain.User
import profile.domain.transport.Transport

object VerifyOtpMapper {

    fun mapVerifyResultDto(dto: VerifyOtpResultDTO): VerifyOtpResult = VerifyOtpResult(
        token = dto.token,
        user = dto.user?.toModel()
    )

    private fun UserDTO.toModel(): User = User(
        id = id,
        phone = phone,
        name = name,
        surname = surname,
        patronymic = patronymic,
        dateOfBirth = dateOfBirth,
        isDriver = isDriver,
        transport = transport?.toModel(),
        licenceNumber = licenceNumber,
        licenceExpiration = licenceExpiration,
        passportNumber = passportNumber,
        photo = photo,
        rating = rating
    )

    private fun TransportDTO.toModel(): Transport = Transport(
        id = id,
        photo = photo,
        type = type,
        brand = brand,
        model = model,
        colors = colors,
        capacity = capacity,
        dateOfIssue = dateOfIssue,
        hasConditioner = hasConditioner
    )

}