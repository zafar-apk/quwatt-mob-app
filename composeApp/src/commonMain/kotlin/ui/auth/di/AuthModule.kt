package ui.auth.di

import auth.enter_code.data.remote.VerifyOtpHttpClient
import auth.enter_code.domain.VerifyOtp
import auth.enter_code.presentation.EnterCodeViewModel
import auth.enter_phone.data.remote.SendOtpClient
import auth.enter_phone.data.remote.SendOtpHttpClient
import auth.enter_phone.domain.SendOtp
import auth.enter_phone.presentation.EnterPhoneViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val AuthModule = module {

    single<SendOtpClient> { SendOtpHttpClient(get()) }

    singleOf(::SendOtp)

    singleOf(::VerifyOtpHttpClient)

    singleOf(::VerifyOtp)

    factoryOf(::EnterPhoneViewModel)

    factoryOf(::EnterCodeViewModel)
}