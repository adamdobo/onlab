package hu.adamdobo.onlabproject.sectionedrecyclerview;

import com.intrusoft.sectionedrecyclerview.Section;

import java.util.List;

import hu.adamdobo.onlabproject.model.Item;

/**
 * Created by Ádám on 4/7/2018.
 */

public class SectionHeader implements Section<Item> {
    List<Item> items;
    String sectionText;

    public SectionHeader(List<Item> items, String sectionText) {
        this.items = items;
        this.sectionText = sectionText;
    }

    @Override
    public List<Item> getChildItems() {
        return items;
    }
}
