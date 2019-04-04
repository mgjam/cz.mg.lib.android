package cz.mg.lib.httpclient

enum class HttpClientFailureType {
    HttpFailure,
    RedirectionHttpFailure,
    ClientErrorHttpFailure,
    ServerErrorHttpFailure,
    RequestTimeout,
    UnableToDeserialize,
    OfflineDevice,
    BackgroundNetworkingRestricted
}