/*
 * Copyright (c) 2019-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.geyser.translator.inventory.item.nbt;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.translator.inventory.item.ItemRemapper;
import org.geysermc.geyser.translator.inventory.item.NbtItemStackTranslator;
import org.geysermc.geyser.translator.text.MessageTranslator;
import org.geysermc.geyser.registry.type.ItemMapping;

import java.util.ArrayList;
import java.util.List;

@ItemRemapper
public class BookPagesTranslator extends NbtItemStackTranslator {

    @Override
    public void translateToBedrock(GeyserSession session, CompoundTag itemTag, ItemMapping mapping) {
        if (!itemTag.contains("pages")) {
            return;
        }
        List<Tag> pages = new ArrayList<>();
        ListTag pagesTag = itemTag.get("pages");
        for (Tag tag : pagesTag.getValue()) {
            if (!(tag instanceof StringTag textTag))
                continue;

            CompoundTag pageTag = new CompoundTag("");
            pageTag.put(new StringTag("photoname", ""));
            pageTag.put(new StringTag("text", MessageTranslator.convertMessageLenient(textTag.getValue())));
            pages.add(pageTag);
        }

        itemTag.remove("pages");
        itemTag.put(new ListTag("pages", pages));
    }

    @Override
    public void translateToJava(CompoundTag itemTag, ItemMapping mapping) {
        if (!itemTag.contains("pages")) {
            return;
        }
        List<Tag> pages = new ArrayList<>();
        ListTag pagesTag = itemTag.get("pages");
        for (Tag tag : pagesTag.getValue()) {
            if (!(tag instanceof CompoundTag pageTag))
                continue;

            StringTag textTag = pageTag.get("text");
            pages.add(new StringTag("", textTag.getValue()));
        }
        itemTag.remove("pages");
        itemTag.put(new ListTag("pages", pages));
    }
}
