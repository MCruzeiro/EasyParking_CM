package ipvc.estg.easyparking.api

data class Parque(
    val id: String,
    val nome_parque: String,
    val latitude: String,
    val longitude: String,
    val pago: String,
    val subterraneo: String,
    val abertoDiaInteiro: String,
    val incapacidade: String,
    val nVagas: String
)

