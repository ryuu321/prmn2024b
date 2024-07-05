package jp.ac.chitose.ir.presentation.component.scroll;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ScrollOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScrollManager {
    private final List<Scroll> scrolls;

    public ScrollManager() {
        scrolls = new ArrayList<>();
    }

    public ScrollManager(Component component, String idName) {
        scrolls = new ArrayList<>();
        scrolls.add(new Scroll(component, idName));
    }

    public ScrollManager(List<Component> components, List<String> idNames) {
        scrolls = new ArrayList<>();
        for(int i = 0; i < Math.min(components.size(), idNames.size()); i++) {
            scrolls.add(new Scroll(components.get(i), idNames.get(i)));
        }
    }

    public ScrollManager(Scroll scroll) {
        scrolls = new ArrayList<>();
        scrolls.add(scroll);
    }

    public ScrollManager(List<Scroll> scrolls) {
        this.scrolls = scrolls;
    }

    public void add(Component component, String idName) {
        scrolls.add(new Scroll(component, idName));
    }

    public void add(Scroll scroll) {
        scrolls.add(scroll);
    }

    public void remove(Component component, String idName) {
        scrolls.remove(new Scroll(component, idName));
    }

    public void remove(Scroll scroll) {
        scrolls.remove(scroll);
    }

    public void scrollToComponentById(String idName) {
        Optional<Scroll> optionalScroll = scrollFindById(idName);
        if(optionalScroll.isEmpty()) return;
        Scroll scroll = optionalScroll.get();
        scroll.scroll();
    }

    public void scrollToComponentById(String idName, ScrollOptions option) {
        Optional<Scroll> optionalScroll = scrollFindById(idName);
        if(optionalScroll.isEmpty()) return;
        Scroll scroll = optionalScroll.get();
        scroll.scroll(option);
    }

    private Optional<Scroll> scrollFindById(String idName) {
        return scrolls.stream()
                .filter(scroll -> scroll.getId().equals(idName))
                .findFirst();
    }
}
