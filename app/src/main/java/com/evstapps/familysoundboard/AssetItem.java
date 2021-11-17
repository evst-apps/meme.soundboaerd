package com.evstapps.familysoundboard;

public class AssetItem implements Comparable<AssetItem>{
    public String name;

    @Override
    public int compareTo(AssetItem assetItem) {
        return this.name.compareTo(assetItem.name);
    }
}
