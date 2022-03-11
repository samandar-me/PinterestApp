package uz.context.pinterestapp.relatedcollection

import uz.context.pinterestapp.model.Urls

data class SinglePhoto(
    val id: String,
    val urls: Urls,
    val likes: Long,
    val related_collections: RelatedCollections,
)
