package ipvc.estg.easyparking.api

    data class User(
        val id: Int,
        val name: String,
        val email: String,
        val address: Address
    )

    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: Geo
        )

    data class Geo(
        val lat: String,
        val lng: String
    )