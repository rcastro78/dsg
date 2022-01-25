package sv.com.seguridadcontrol.app.modelos

data class OrderProvisioning(
    val material: List<MaterialOP>
)

data class MaterialOP(
    val aprov_id: String,
    val brand: String,
    val materials_quantity: String,
    val max: String,
    val min: String,
    val product: String,
    val quantity: String,
    val unity: String
)





