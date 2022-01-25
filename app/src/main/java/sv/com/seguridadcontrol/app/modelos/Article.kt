package sv.com.seguridadcontrol.app.modelos

data class Article(
    val material: List<MaterialArticle>
)

data class MaterialArticle(
    val article: String,
    val article_id: String,
    val branch: String,
    val brand: String,
    val product: String,
    val status: String
)
