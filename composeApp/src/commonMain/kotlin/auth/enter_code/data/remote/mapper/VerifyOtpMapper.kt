package auth.enter_code.data.remote.mapper

import auth.enter_code.data.remote.model.VerifyOtpResponseDTO
import auth.enter_code.domain.VerifyOtpResult

class VerifyOtpMapper {

    fun mapVerifyResultDto(dto: VerifyOtpResponseDTO): VerifyOtpResult = VerifyOtpResult(
        token = dto.result?.token,
        tokenType = dto.result?.tokenType
    )

}