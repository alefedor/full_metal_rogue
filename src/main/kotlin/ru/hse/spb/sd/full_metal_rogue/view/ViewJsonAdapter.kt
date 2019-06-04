package ru.hse.spb.sd.full_metal_rogue.view

import com.google.gson.GsonBuilder
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.logic.map.MapJsonAdapter

object ViewJsonAdapter {
    private val baseGson = GsonBuilder()
        .registerTypeHierarchyAdapter(Menu::class.java, MapJsonAdapter.HierarchyAdapter<Menu<Item>>())
        .registerTypeHierarchyAdapter(Item::class.java, MapJsonAdapter.HierarchyAdapter<Item>())
        .create()
    private val viewGson = GsonBuilder()
        .registerTypeHierarchyAdapter(View::class.java, MapJsonAdapter.HierarchyAdapter<View>(baseGson))
        .create()

    fun serialize(view: View): String = viewGson.toJson(view)


    fun deserialize(json: String): View = viewGson.fromJson(json, View::class.java)
}