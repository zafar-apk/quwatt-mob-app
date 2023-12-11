package ui.edit.user.di

import edit.user.data.mapper.EditUserMapper

//@Module
//@InstallIn(ViewModelComponent::class)
//object EditUserModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideEditUserClient(
//        httpClient: HttpClient,
//        mapper: EditUserMapper
//    ): EditUserClient =
//        EditUserHttpClient(client = httpClient, mapper = mapper)
//
//
//    @Provides
//    fun provideEditUserMapper(httpClient: HttpClient): EditUserMapper =
//        EditUserMapper()
//
//}