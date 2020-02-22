package interfaces

interface IServerResponseParseServiceFactory {

    fun < T, V > createService(service: String) : IServerResponseParseService< T, V >
}