package cz.mg.lib.httpclient

fun HttpClientRequest.addBearerAuthorizationHeader(token: String) =
    copy(
        headers = (headers ?: emptyList())
            .union(sequenceOf(Pair("Authorization", "Bearer $token")).asIterable())
            .toList()
    )