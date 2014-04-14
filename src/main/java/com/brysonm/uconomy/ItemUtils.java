package com.brysonm.uconomy;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public static String toFriendlyName(Material material) {

        String materialName = material.name().toLowerCase();

        if(!materialName.contains("_")) {

            return StringUtils.capitalize(materialName);

        }

        List<String> words = new ArrayList<String>();

        for(String word : materialName.split("_")) {

            StringUtils.capitalize(word);

        }

        return StringUtils.join(words, " ");

    }

}
