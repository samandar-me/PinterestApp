package uz.context.pinterestapp.modelSearchFrag

data class CollectionsModelItem(
    val cover_photo: CoverPhoto,
    val curated: Boolean,
    val description: Any,
    val featured: Boolean,
    val id: String,
    val last_collected_at: String,
    val links: LinksXX,
    val preview_photos: List<PreviewPhoto>,
    val published_at: String,
    val share_key: String,
    val tags: List<Tag>,
    val title: String,
    val total_photos: Int,
    val updated_at: String,
    val user: UserX
)